package com.ass.muktimargadarshini.ui.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.data.local.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _isUserLoggedInChannel = Channel<Boolean>()
    val isUserLoggedInChannel = _isUserLoggedInChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            userDataStore.userLoggedIn.collectLatest {
                _isUserLoggedInChannel.send(it)
            }
        }
    }


}