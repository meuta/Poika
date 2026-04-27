package com.obrigada_eu.poika.player.di

import android.content.Context
import com.obrigada_eu.poika.di.ApplicationScope
import com.obrigada_eu.poika.shared.presentation.player.session.PlayerSession
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionWriter
import com.obrigada_eu.poika.shared.data.metadata.MetaDataParser
import com.obrigada_eu.poika.shared.domain.repository.SongRepository
import com.obrigada_eu.poika.player.data.repository.SongRepositoryImpl
import com.obrigada_eu.poika.player.data.infra.file.ZipImporter
import com.obrigada_eu.poika.player.data.infra.audio.AudioController
import com.obrigada_eu.poika.player.data.infra.file.FileResolver
import com.obrigada_eu.poika.shared.presentation.player.progress.ProgressTracker
import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateUpdater
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
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
        @ApplicationScope scope: CoroutineScope,
    ): ProgressTracker = ProgressTracker(scope)

    @Provides
    fun provideProgressUpdater(tracker: ProgressTracker): ProgressStateUpdater = tracker

    @Provides
    fun provideProgressStateProvider(tracker: ProgressTracker): ProgressStateProvider = tracker


    @Provides
    @Singleton
    fun provideAudioService(
        @ApplicationContext context: Context,
        progressStateUpdater: ProgressStateUpdater,
        playerSessionWriter: PlayerSessionWriter,
    ): AudioService = AudioController(context, progressStateUpdater, playerSessionWriter)


    @Provides
    @Singleton
    fun providePlayerSession(
        @ApplicationScope scope: CoroutineScope,
    ): PlayerSession = PlayerSession(scope)

    @Provides
    @Singleton
    fun providePlayerSessionReader(session: PlayerSession): PlayerSessionReader = session

    @Provides
    @Singleton
    fun providePlayerSessionWriter(session: PlayerSession): PlayerSessionWriter = session

    @Provides
    fun provideZipImporter(@ApplicationContext context: Context): ZipImporter =
        ZipImporter(context, MetaDataParser())


    @Provides
    @Singleton
    fun provideSongRepository(
        @ApplicationContext context: Context,
        zipImporter: ZipImporter,
    ): SongRepository =
        SongRepositoryImpl(context, zipImporter, MetaDataParser())

    @Provides
    fun provideFileResolver(@ApplicationContext context: Context): FileResolver =
        FileResolver(context)


    @Provides
    fun provideGetAllSongsUseCase(repository: SongRepository): GetAllSongsUseCase =
        GetAllSongsUseCase(repository)

    @Provides
    fun provideDeleteSongUseCase(repository: SongRepository): DeleteSongUseCase =
        DeleteSongUseCase(repository)

    @Provides
    fun provideImportZipUseCase(repository: SongRepository): ImportZipUseCase =
        ImportZipUseCase(repository)

    @Provides
    fun provideLoadSongUseCase(audioService: AudioService): LoadSongUseCase =
        LoadSongUseCase(audioService)



}