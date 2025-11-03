package com.obrigada_eu.poika.player.ui.components

import androidx.compose.runtime.Composable

@Composable
fun PlayerPane(
    currentPositionText: String,
    trackDurationText: String,
    playbackSeekbarPosition: Float,
    playbackSeekbarMax: Float,
    onSeekChanged: (Float) -> Unit,
    onSeekReleased: () -> Unit,
    playbackButtons: Map<String, () -> Unit>,
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
    )

    // Buttons row
    PlaybackButtonsRow(playbackButtons)

    // Volume sliders
    VolumeSliderColumn(
        volumeStates = volumeStates,
        setVolume = setVolume
    )
}