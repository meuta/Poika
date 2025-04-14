package com.obrigada_eu.poika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.obrigada_eu.poika.databinding.ActivityMainBinding
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val playerViewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playerView1.player = playerViewModel.players[0]
        binding.playerView2.player = playerViewModel.players[1]
        binding.playerView3.player = playerViewModel.players[2]

        val audioUri1 = "android.resource://$packageName/raw/minus"
        val audioUri2 = "android.resource://$packageName/raw/soprano"
        val audioUri3 = "android.resource://$packageName/raw/alto"

        playerViewModel.loadTracks(audioUri1, audioUri2, audioUri3)

        binding.playButton.setOnClickListener { playerViewModel.play() }
        binding.pauseButton.setOnClickListener { playerViewModel.pause() }
        binding.stopButton.setOnClickListener { playerViewModel.stop() }
    }

    override fun onStop() {
        super.onStop()
        playerViewModel.stop()
    }
}