package com.obrigada_eu.poika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.obrigada_eu.poika.databinding.ActivityMainBinding
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playerView.player = playerViewModel.player

        val audioUri = "android.resource://$packageName/raw/minus".toUri()

        binding.playButton.setOnClickListener {
            playerViewModel.play(audioUri)
        }

        binding.stopButton.setOnClickListener {
            playerViewModel.stop()
        }
    }

    override fun onStop() {
        super.onStop()
        playerViewModel.stop()
    }
}