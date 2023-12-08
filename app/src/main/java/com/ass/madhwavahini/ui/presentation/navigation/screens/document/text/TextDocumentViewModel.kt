package com.ass.madhwavahini.ui.presentation.navigation.screens.document.text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.remote.Api.getAudioUrl
import com.ass.madhwavahini.domain.modals.Track
import com.ass.madhwavahini.domain.repository.DocumentRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.domain.wrapper.UiState
import com.ass.madhwavahini.ui.presentation.navigation.screens.document.modals.FileDocumentText
import com.ass.madhwavahini.util.player.MyPlayer
import com.ass.madhwavahini.util.player.PlaybackState
import com.ass.madhwavahini.util.player.PlayerEvents
import com.ass.madhwavahini.util.player.PlayerStates
import com.ass.madhwavahini.util.translation.LanguageTranslator
import com.ass.madhwavahini.util.translation.TranslationLanguages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TextDocumentViewModel @Inject constructor(
    private val filesRepository: DocumentRepository,
    private val myPlayer: MyPlayer,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), PlayerEvents {

    private val languageTranslator: LanguageTranslator = LanguageTranslator()

    private val _fileState = MutableStateFlow(UiState<File>())
    val fileState = _fileState.asStateFlow()

    private var index = savedStateHandle.get<Int>("index") ?: -1

    private val _fileDataQuery = MutableStateFlow(savedStateHandle["query"] ?: "")
    val fileDataQuery get() = _fileDataQuery.asStateFlow()

    private val _text = MutableStateFlow<List<FileDocumentText>>(emptyList())
    val text = _text.asStateFlow()

    /////////////////////////////////  AUDIO  /////////////////////////////////
    var currentTrack: Track by mutableStateOf(getTrackInfo())
        private set

    var hasAudioFile: Boolean by mutableStateOf(false)
        private set

    private var isTrackPlay: Boolean = false

    private var playbackStateJob: Job? = null

    private val _playbackState = MutableStateFlow(PlaybackState(0L, 0L))
    val playbackState: StateFlow<PlaybackState> get() = _playbackState.asStateFlow()

    ////////////////////////////////  AUDIO  /////////////////////////////////
    init {
        viewModelScope.launch(IO) {
            fetchDocument(
                savedStateHandle.get<Int>("file_id") ?: 0,
                savedStateHandle.get<String>("file_url") ?: ""
            )
        }
        if (currentTrack.trackUrl.isNotBlank()) {
            hasAudioFile = true
            myPlayer.initPlayer(currentTrack.trackUrl)
            observePlayerState()
        }
    }

    private suspend fun fetchDocument(homeFileId: Int, homeFileUrl: String) {
        filesRepository.getDocument("file_${homeFileId}.txt", homeFileUrl).collectLatest { result ->
            _fileState.value = result
            result.data?.let { file ->
                readTextFile(file)
            }
        }
    }

    private suspend fun readTextFile(file: File) = withContext(IO) {
        try {
            val br = BufferedReader(FileReader(file))
            val text = mutableListOf<FileDocumentText>()
            var line: String?
            var index = 0
            val sourceString = StringBuilder()
            while (br.readLine().also {
                    line = it
                    sourceString.append(line)
                } != null) {
                index++
                text.add(FileDocumentText(index, line!!))
            }
            br.close()
            _text.value = text.toList()
            languageTranslator.sourceText.postValue(sourceString.toString())
        } catch (e: Exception) {
            _fileState.update {
                it.copy(
                    isLoading = false,
                    data = null,
                    error = StringUtil.DynamicText(if (e is IOException) "The file is corrupted" else "Unable to display the file")
                )
            }
        }
    }

    val searchedText = combine(fileDataQuery, text) { query, list ->
        if (query.length > 2) list.filter { s ->
            s.text.contains(query, ignoreCase = true)
        }
        else emptyList()
    }.flowOn(Default).stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    fun updateQuery(newQuery: String = "") {
        _fileDataQuery.value = newQuery
    }

    fun getScrollIndex(): Int = index

    fun removeIndexFlag() {
        index = -1
    }

    fun setTranslateLanguage(language: TranslationLanguages) {
        languageTranslator.targetLang.value = language
    }

    fun getTranslatedText() = languageTranslator.translatedText

/////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**                                                AUDIO                                              */
/////////////////////////////////////////////////////////////////////////////////////////////////////////


    private fun getTrackInfo(): Track {
        val audioUrl = savedStateHandle.get<String>("audio_url").getAudioUrl()
        val audioImage = savedStateHandle.get<String>("audio_image").getAudioUrl()
        return if (audioUrl.isBlank()) Track() else Track(
            trackUrl = audioUrl, trackImage = audioImage
        )
    }

    private fun updateState(state: PlayerStates) {
        isTrackPlay = (state == PlayerStates.STATE_PLAYING)
        currentTrack = currentTrack.copy(state = state)
        updatePlaybackState(state)
    }

    private fun observePlayerState() {
        viewModelScope.launch {
            myPlayer.playerState.collect {
                updateState(it)
            }
        }
    }


    private fun updatePlaybackState(state: PlayerStates) {
        playbackStateJob?.cancel()
        playbackStateJob = viewModelScope.launch {
            do {
                _playbackState.emit(
                    PlaybackState(
                        currentPlaybackPosition = myPlayer.currentPlaybackPosition,
                        currentTrackDuration = myPlayer.currentTrackDuration
                    )
                )
                delay(1000)
            } while (state == PlayerStates.STATE_PLAYING && isActive)
        }
    }

    override fun onSeekForward() {
        myPlayer.seekForward()
    }

    override fun onSeekBackward() {
        myPlayer.seekBackward()
    }

    override fun onPlayPauseClick() {
        myPlayer.playPause()
    }

    override fun onSeekBarPositionChanged(position: Long) {
        myPlayer.seekToPosition(position)
    }

    override fun onCleared() {
        super.onCleared()
        languageTranslator.clear()
        myPlayer.releasePlayer()
    }
}
