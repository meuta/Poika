package com.obrigada_eu.poika.player.di

import android.content.Context
import com.obrigada_eu.poika.player.data.infra.session.PlayerSession
import com.obrigada_eu.poika.player.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.player.domain.session.PlayerSessionWriter
import com.obrigada_eu.poika.player.data.infra.file.FileResolver
import com.obrigada_eu.poika.player.data.infra.file.MetaDataParser
import com.obrigada_eu.poika.player.domain.repository.SongRepository
import com.obrigada_eu.poika.player.data.repository.SongRepositoryImpl
import com.obrigada_eu.poika.player.data.infra.file.ZipImporter
import com.obrigada_eu.poika.player.data.infra.audio.AudioController
import com.obrigada_eu.poika.player.data.infra.progress.ProgressTracker
import com.obrigada_eu.poika.player.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.player.domain.progress.ProgressStateUpdater
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @ApplicationScope
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)


    @Provides
    @Singleton
    fun provideProgressTracker(
        @ApplicationScope scope: CoroutineScope
    ): ProgressTracker = ProgressTracker(scope)

    @Provides
    fun provideProgressUpdater(tracker: ProgressTracker): ProgressStateUpdater = tracker

    @Provides
    fun provideProgressStateProvider(tracker: ProgressTracker): ProgressStateProvider = tracker


    @Provides
    @Singleton
    fun provideAudioController(
        @ApplicationContext context: Context,
        progressStateUpdater: ProgressStateUpdater,
        playerSessionWriter: PlayerSessionWriter,
    ): AudioController {
        return AudioController(context, progressStateUpdater, playerSessionWriter)
    }

    @Provides
    @Singleton
    fun providePlayerSessionReader(): PlayerSessionReader = PlayerSession

    @Provides
    @Singleton
    fun providePlayerSessionWriter(): PlayerSessionWriter = PlayerSession

    @Provides
    fun provideZipImporter(@ApplicationContext context: Context): ZipImporter =
        ZipImporter(context, MetaDataParser())


    @Provides
    @Singleton
    fun provideSongRepository(@ApplicationContext context: Context): SongRepository =
        SongRepositoryImpl(context, MetaDataParser())

    @Provides
    fun provideFileResolver(): FileResolver = FileResolver()
}