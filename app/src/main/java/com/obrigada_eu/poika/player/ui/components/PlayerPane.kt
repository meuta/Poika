package com.obrigada_eu.poika.player.ui.components

import androidx.compose.runtime.Composable
import com.obrigada_eu.poika.player.ui.model.ImageButtonItem

@Composable
fun PlayerPane(
    currentPositionText: String,
    trackDurationText: String,
    playbackSeekbarPosition: Float,
    playbackSeekbarMax: Float,
    onSeekChanged: (Float) -> Unit,
    onSeekReleased: () -> Unit,
    changeSpeedButtons: Map<String, () -> Unit>,
    currentSpeed: String,
    playbackButtons: List<ImageButtonItem>,
    volumeStates: Map<String, Float>,
    setVolume: (String, Float) -> Unit,
) {

    // Playback Seekbar
    PlaybackSeekbar(
        currentPositionText = currentPositionText,
        trackDurationText = trackDurationText,
        sliderPosition = playbackSeekbarPosition,
        playbackSeekbarMax = playbackSeekbarMax,
        onValueChange = onSeekChanged,
        onValueChangeFinished = onSeekReleased,
        changeSpeedButtons = changeSpeedButtons,
        currentSpeedText = currentSpeed,
    )

    // Buttons row
    PlaybackButtonsRow(playbackButtons)

    // Volume sliders
    VolumeSliderColumn(
        volumeStates = volumeStates,
        setVolume = setVolume
    )
}