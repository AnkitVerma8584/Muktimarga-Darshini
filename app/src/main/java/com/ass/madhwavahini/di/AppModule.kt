package com.ass.madhwavahini.di

import android.app.Application
import androidx.media3.exoplayer.ExoPlayer
import com.ass.madhwavahini.domain.player.MyPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideExoPLayer(application: Application): ExoPlayer {
        return ExoPlayer.Builder(application).build()
    }

    @Provides
    @Singleton
    fun provideMyPlayer(player: ExoPlayer): MyPlayer {
        return MyPlayer(player)
    }
}