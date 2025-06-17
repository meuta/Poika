package com.obrigada_eu.poika.di

import android.content.Context
import com.obrigada_eu.poika.data.FileResolver
import com.obrigada_eu.poika.data.MetaDataParser
import com.obrigada_eu.poika.data.SongRepository
import com.obrigada_eu.poika.data.SongRepositoryImpl
import com.obrigada_eu.poika.data.ZipImporter
import com.obrigada_eu.poika.player.AudioController
import com.obrigada_eu.poika.ui.SongMetaDataMapper
import com.obrigada_eu.poika.ui.player.ProgressMapper
import com.obrigada_eu.poika.ui.player.ProgressTracker
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
    fun provideProgressState(): ProgressTracker = ProgressTracker()

    @Provides
    fun provideProgressUiMapper(): ProgressMapper = ProgressMapper(StringFormatter())

    @Provides
    @Singleton
    fun provideAudioController(
        @ApplicationContext context: Context,
        progressTracker: ProgressTracker,
    ): AudioController {
        return AudioController(context, progressTracker)
    }


    @Provides
    fun provideZipImporter(@ApplicationContext context: Context): ZipImporter =
        ZipImporter(context, MetaDataParser())


    @Provides
    @Singleton
    fun provideSongRepository(@ApplicationContext context: Context): SongRepository =
        SongRepositoryImpl(context, MetaDataParser())

    @Provides
    fun provideSongMetaDataMapper(): SongMetaDataMapper = SongMetaDataMapper()

    @Provides
    fun provideStringFormatter(): StringFormatter = StringFormatter()

    @Provides
    fun provideFileResolver(): FileResolver = FileResolver()
}