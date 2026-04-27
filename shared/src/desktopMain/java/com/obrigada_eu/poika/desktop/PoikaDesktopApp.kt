package com.obrigada_eu.poika.desktop

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.obrigada_eu.poika.desktop.fake.DesktopFakeAudioService
import com.obrigada_eu.poika.desktop.fake.DesktopFakeSongRepository
import com.obrigada_eu.poika.desktop.ui.screen.DesktopPlayerScreenHost
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.presentation.player.progress.ProgressTracker
import com.obrigada_eu.poika.shared.presentation.player.session.PlayerSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

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
        DesktopPlayerScreenHost(presenter)
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
    val repository = DesktopFakeSongRepository()

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