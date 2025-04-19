package com.obrigada_eu.poika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.obrigada_eu.poika.databinding.ActivityMainBinding
import com.obrigada_eu.poika.ui.ListDialog
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
        setSupportActionBar(binding.mainToolbar)
        addMenuProvider(menuProvider)

        binding.playButton.setOnClickListener { playerViewModel.play() }
        binding.pauseButton.setOnClickListener { playerViewModel.pause() }
        binding.stopButton.setOnClickListener { playerViewModel.stop() }

        setupVolumeSeekbars()
        setupPlaybackSeekbar()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playerViewModel.progressFlow.collect {
                    with(binding) {
                        if (!isUserSeeking) {
                            with(playbackSeekbar) { post { progress = it.currentPositionSec.toInt() } }
                        }
                        currentPositionText.text = it.currentPositionString
                        trackDurationText.text = it.durationString
                        playbackSeekbar.max = it.durationSec.toInt()
                    }
                }
            }
        }
    }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.main_activity_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {

                R.id.action_choose_song -> {
                    showChooseSongDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun showChooseSongDialog() {
        ListDialog(
            getString(R.string.choose_song),
            listOf("Moby - Natural Blues", "Muse - Uprising", "Radiohead - Creep")
        ) {
            when (it) {
                "Moby - Natural Blues" -> playerViewModel.loadTracks(
                    "android.resource://$packageName/raw/natural_blues_soprano",
                    "android.resource://$packageName/raw/natural_blues_alto",
                    "android.resource://$packageName/raw/natural_blues_minus"
                )
                "Muse - Uprising" -> playerViewModel.loadTracks(
                    "android.resource://$packageName/raw/uprising_soprano",
                    "android.resource://$packageName/raw/uprising_alto",
                    "android.resource://$packageName/raw/uprising_minus"
                )
                "Radiohead - Creep" -> playerViewModel.loadTracks(
                    "android.resource://$packageName/raw/creep_soprano",
                    "android.resource://$packageName/raw/creep_alto",
                    "android.resource://$packageName/raw/creep_minus"
                )
            }
            binding.songTitleText.text = it
        }.show(this)
    }

    private fun setupPlaybackSeekbar() {
        binding.playbackSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.currentPositionText.text =
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
                voiceOneSeekbar, voiceTwoSeekbar, minusSeekbar
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