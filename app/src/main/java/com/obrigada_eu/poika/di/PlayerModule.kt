package com.obrigada_eu.poika.di

import android.content.Context
import com.obrigada_eu.poika.player.AudioController
import com.obrigada_eu.poika.ui.player.ProgressMapper
import com.obrigada_eu.poika.ui.player.ProgressStateFlow
import com.obrigada_eu.poika.ui.player.StringFormatter
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
    fun provideProgressState(): ProgressStateFlow {
        return ProgressStateFlow()
    }

    @Provides
    @Singleton
    fun provideProgressUiMapper(): ProgressMapper {
        return ProgressMapper(StringFormatter())
    }

    @Provides
    @Singleton
    fun provideAudioController(
        @ApplicationContext context: Context,
        progressStateFlow: ProgressStateFlow,
    ): AudioController {
        return AudioController(context, progressStateFlow)
    }
}