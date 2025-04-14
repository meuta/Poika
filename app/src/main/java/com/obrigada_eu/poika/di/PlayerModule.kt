package com.obrigada_eu.poika.di

import android.content.Context
import com.obrigada_eu.poika.player.AudioController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun provideAudioController(@ApplicationContext context: Context): AudioController {
        return AudioController(context)
    }
}