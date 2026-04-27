package com.obrigada_eu.poika.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.shared.domain.audio.AudioService
import com.obrigada_eu.poika.shared.domain.progress.ProgressStateProvider
import com.obrigada_eu.poika.shared.domain.session.PlayerSessionReader
import com.obrigada_eu.poika.shared.domain.usecase.DeleteSongUseCase
import com.obrigada_eu.poika.shared.domain.usecase.GetAllSongsUseCase
import com.obrigada_eu.poika.shared.domain.usecase.ImportZipUseCase
import com.obrigada_eu.poika.shared.domain.usecase.LoadSongUseCase
import com.obrigada_eu.poika.shared.presentation.player.PlayerPresenter
import com.obrigada_eu.poika.shared.ui.root.PoikaRoot
import com.obrigada_eu.poika.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var audioService: AudioService
    @Inject lateinit var importZipUseCase: ImportZipUseCase
    @Inject lateinit var getAllSongsUseCase: GetAllSongsUseCase
    @Inject lateinit var loadSongUseCase: LoadSongUseCase
    @Inject lateinit var deleteSongUseCase: DeleteSongUseCase
    @Inject lateinit var progressProvider: ProgressStateProvider
    @Inject lateinit var playerSessionReader: PlayerSessionReader

    private val playerPresenter by lazy {
        PlayerPresenter(
            audioService = audioService,
            importZipUseCase = importZipUseCase,
            getAllSongsUseCase = getAllSongsUseCase,
            loadSongUseCase = loadSongUseCase,
            deleteSongUseCase = deleteSongUseCase,
            progressProvider = progressProvider,
            playerSessionReader = playerSessionReader
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoikaRoot(
                playerPresenter = playerPresenter,
            )
        }
        if (savedInstanceState == null) handleIncomingZip(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIncomingZip(intent)
    }

    override fun onDestroy() {
        playerPresenter.dispose()
        super.onDestroy()
    }

    private fun handleIncomingZip(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
            intent.data?.let { uri ->
                playerPresenter.handleZipImport(uri.toString())
            } ?: { Logger.e(getString(R.string.app_name), "URI is null") }
        }
    }
}