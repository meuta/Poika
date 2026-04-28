package com.obrigada_eu.poika.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.obrigada_eu.poika.desktop.fake.DesktopFakeAudioService
import com.obrigada_eu.poika.desktop.ui.DesktopFilePicker
import com.obrigada_eu.poika.shared.data.infra.file.FileResolver
import com.obrigada_eu.poika.shared.data.infra.file.ZipImporter
import com.obrigada_eu.poika.shared.data.metadata.MetaDataParser
import com.obrigada_eu.poika.shared.data.repository.SongRepositoryImpl
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.presentation.player.progress.ProgressTracker
import com.obrigada_eu.poika.shared.presentation.player.session.PlayerSession
import com.obrigada_eu.poika.shared.ui.root.PoikaRoot
import data.infra.file.DesktopSongInputStreamProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.io.File

fun main() = application {

    val scope = remember { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    val session = remember { PlayerSession(scope) }
    val progressTracker = remember { ProgressTracker(scope) }
    val presenter = remember { createDesktopPresenter(session, progressTracker) }

    Window(
        onCloseRequest = {
            presenter.dispose()
            exitApplication()
        },
        title = "Poika"
    ) {
        PoikaRoot(
            playerPresenter = presenter,
            onImportZip = {
                DesktopFilePicker().pickZipFile()?.let {
                    presenter.importZipSong(it.absolutePath)
                }
            }
        )
    }
}

fun createDesktopPresenter(
    session: PlayerSession,
    progressTracker: ProgressTracker
): PlayerPresenter {

    val audio = DesktopFakeAudioService(
        progressTracker = progressTracker,
        playerSessionWriter = session
    )

    val fileResolver =
        FileResolver(
            File(
                System.getProperty("user.home"),
                ".poika"
            )
        )
    val metaDataParser = MetaDataParser()

    val zipImporter = ZipImporter(
        fileResolver = fileResolver,
        metadataParser = metaDataParser
    )

    val repository = SongRepositoryImpl(
        zipImporter = zipImporter,
        metaDataParser = metaDataParser,
        fileResolver = fileResolver,
        streamProvider = DesktopSongInputStreamProvider(),
    )

    return PlayerPresenter(
        audioService = audio,
        importZipUseCase = ImportZipUseCase(repository),
        getAllSongsUseCase = GetAllSongsUseCase(repository),
        loadSongUseCase = LoadSongUseCase(audio),
        deleteSongUseCase = DeleteSongUseCase(repository),
        progressProvider = progressTracker,
        playerSessionReader = session
    )
}