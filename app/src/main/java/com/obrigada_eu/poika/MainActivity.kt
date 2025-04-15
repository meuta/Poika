package com.obrigada_eu.poika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.obrigada_eu.poika.databinding.ActivityMainBinding
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import com.obrigada_eu.poika.ui.player.StringFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val playerViewModel: PlayerViewModel by viewModels()

    private lateinit var seekBarVolumeList: List<SeekBar>

    private var isUserSeeking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val audioUri1 = "android.resource://$packageName/raw/soprano"
        val audioUri2 = "android.resource://$packageName/raw/alto"
        val audioUri3 = "android.resource://$packageName/raw/minus"

        if (savedInstanceState == null) playerViewModel.loadTracks(audioUri1, audioUri2, audioUri3)

        binding.playButton.setOnClickListener { playerViewModel.play() }
        binding.pauseButton.setOnClickListener { playerViewModel.pause() }
        binding.stopButton.setOnClickListener { playerViewModel.stop() }

        setupVolumeSeekbars()
        setupControllerSeekbar()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playerViewModel.progressFlow.collect {
                    with(binding) {
                        if (!isUserSeeking) {
                            with(seekBarController) { post { progress = it.currentPositionSec.toInt() } }
                        }
                        controllerTvCurrentTime.text = it.currentPositionString
                        controllerTvTotalTime.text = it.durationString
                        seekBarController.max = it.durationSec.toInt()
                    }
                }
            }
        }
    }

    private fun setupControllerSeekbar() {
        binding.seekBarController.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.controllerTvCurrentTime.text =
                        StringFormatter().formatSecToString(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                playerViewModel.setSongProgress(seekBar.progress)
                isUserSeeking = false
            }
        })
    }

    private fun setupVolumeSeekbars() {
        val seekVolumeAdapter = object : OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                onUpdateSeekBarVolume(seek)
            }
            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        }

        with(binding) {
            seekBarVolumeList = listOf(
                seekBarVoice1, seekBarVoice2, seekBarMinus
            ).apply { forEach { it.setOnSeekBarChangeListener(seekVolumeAdapter) } }
        }
    }

    private fun onUpdateSeekBarVolume(seek: SeekBar) {
        playerViewModel.setVolume(seekBarVolumeList.indexOf(seek), seek.progress)
    }

    companion object {

        const val TAG = "MainActivity"
    }
}