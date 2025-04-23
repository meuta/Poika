package com.obrigada_eu.poika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import android.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.obrigada_eu.poika.databinding.ActivityMainBinding
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.ui.SongMetaDataMapper
import com.obrigada_eu.poika.ui.Toaster
import com.obrigada_eu.poika.ui.UiEvent
import com.obrigada_eu.poika.ui.player.PlayerViewModel
import com.obrigada_eu.poika.ui.player.StringFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var stringFormatter: StringFormatter
    @Inject lateinit var metaMapper: SongMetaDataMapper

    private lateinit var binding: ActivityMainBinding

    private val playerViewModel: PlayerViewModel by viewModels()

    private lateinit var seekBarVolumeList: List<SeekBar>

    private var isUserSeeking = false


    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIncomingZip(intent)

        setSupportActionBar(binding.mainToolbar)
        addMenuProvider(menuProvider)

        binding.playButton.setOnClickListener { playerViewModel.play() }
        binding.pauseButton.setOnClickListener { playerViewModel.pause() }
        binding.stopButton.setOnClickListener { playerViewModel.stop() }

        setupVolumeSeekbars()
        setupPlaybackSeekbar()

        observePlayback()

        observeUiEvents()
        observeSongTitleText()
    }

    override fun onResume() {
//        Log.d(TAG, "onResume: ")
        super.onResume()
        playerViewModel.refreshUiState()
    }
    
    private fun observePlayback() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playerViewModel.progressFlow.collect { playback ->
                    with(binding) {
                        if (!isUserSeeking) {
                            playbackSeekbar.apply { post { progress = playback.currentPositionSec.toInt() } }
                        }
                        currentPositionText.text = playback.currentPositionString
                        trackDurationText.text = playback.durationString
                        playbackSeekbar.max = playback.durationSec.toInt()
                    }
                }
            }
        }
    }


    fun observeUiEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playerViewModel.uiEvent.collect { event ->
//                    Log.d(TAG, "observeUiEvents: uiEvent = $event")
                    when (event) {
                        is UiEvent.ShowSongDialog -> showChooseSongDialog(event.songs)
                        is UiEvent.ShowToast -> if (event.message.isNotBlank()) {
                            Toaster(event.message).show(this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    fun observeSongTitleText() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                playerViewModel.songTitleText.collect {
//                    Log.d(TAG, "observeSongTitleText: songTitleText = $it")
                    it?.let { binding.songTitleText.text = it }
                }
            }
        }
    }


    override fun onNewIntent(intent: Intent) {
//        Log.d(TAG, "onNewIntent: ")
        super.onNewIntent(intent)
        handleIncomingZip(intent)
    }


    private fun handleIncomingZip(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null) {
            intent.data?.let { uri ->
                playerViewModel.handleZipImport(uri)
            } ?: run {
                Log.e(getString(R.string.app_name), "URI is null")
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
                    playerViewModel.loadSongsList()
                    true
                }

                else -> false
            }
        }
    }

    private fun showChooseSongDialog(songList: List<SongMetaData>) {
        val songTitles = songList.map { song -> metaMapper.mapToSongTitle(song) }

        AlertDialog.Builder(this)
            .setTitle("Choose a song")
            .setItems(songTitles.toTypedArray()) { _, which ->
                val selectedSong = songList[which]
                playerViewModel.loadSong(selectedSong)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }


    private fun setupPlaybackSeekbar() {
        binding.playbackSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    binding.currentPositionText.text = stringFormatter.formatSecToString(progress)
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