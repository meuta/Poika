package com.obrigada_eu.poika.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.player.ui.PlayerViewModel
import com.obrigada_eu.poika.ui.root.PoikaRoot
import com.obrigada_eu.poika.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoikaRoot(
                playerViewModel = playerViewModel,
            )
        }
        if (savedInstanceState == null) handleIncomingZip(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIncomingZip(intent)
    }

    private fun handleIncomingZip(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
            intent.data?.let { uri ->
                playerViewModel.handleZipImport(uri)
            } ?: { Logger.e(getString(R.string.app_name), "URI is null") }
        }
    }
}