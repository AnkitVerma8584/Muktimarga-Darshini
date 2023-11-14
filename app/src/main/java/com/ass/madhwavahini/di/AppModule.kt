package com.ass.madhwavahini.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import com.ass.madhwavahini.data.Constants.AUDIO_SKIP_TIME
import com.ass.madhwavahini.domain.player.MyPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    @ViewModelScoped
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun provideExoPLayer(application: Application): ExoPlayer {
        return ExoPlayer.Builder(application)
            .setSeekBackIncrementMs(AUDIO_SKIP_TIME)
            .setSeekBackIncrementMs(AUDIO_SKIP_TIME)
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideMyPlayer(player: ExoPlayer): MyPlayer {
        return MyPlayer(player)
    }
}