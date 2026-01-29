package com.obrigada_eu.poika.player.ui.preview

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo
import com.obrigada_eu.poika.player.ui.model.ImageButtonItem

object PreviewData {

    val dropdownMenuItemText = "Choose Song"

    val songs = listOf(
        SongMetaData(
            "Natural Blues",
            "Moby",
            tracks = listOf(TrackInfo("", "")),
            voiceInstrument = "voice",
            folderName = ""
        ),
        SongMetaData(
            "Uprising",
            "Muse",
            tracks = listOf(TrackInfo("", "")),
            voiceInstrument = "piano",
            folderName = ""
        ),
        SongMetaData(
            "Creep",
            "Radiohead",
            tracks = listOf(TrackInfo("", "")),
            voiceInstrument = "piano",
            folderName = ""
        )
    )

    val songTitle = "Moby - Natural Blues (piano version)"

    val volumes = mapOf("Soprano" to 1f, "Alto" to 0.58f, "Bass" to 0.78f, "Minus" to 0.35f)

    val currentPos = 34f
    val duration = 228f

    val timeStringZeroZero = "00:00"

    val menuItems = mapOf(
        "Choose Song" to {},
        "Delete Song" to {},
        "Help" to {}
    )

    val dialogButtonLabels = listOf("OK", "Cancel")

    val playbackButtons = listOf(
        ImageButtonItem("-5s", Icons.Filled.Replay5, 2f, {}),
        ImageButtonItem("Play", Icons.Filled.PlayArrow, 4f, {}),
        ImageButtonItem("Pause", Icons.Filled.Pause, 4f, {}),
        ImageButtonItem("Stop", Icons.Filled.Stop, 3f, {}),
        ImageButtonItem("+5s", Icons.Filled.Forward5, 2f, {}),
    )

    val playbackButtonsRow = playbackButtons.filterNot { it.label == "Pause" }

    val changeSpeedButtons = listOf("-", "+").associateWith { {} }

    val currentSpeed = "x0.8"

    val volumeSliderTitles = listOf("soprano", "alto", "minus")

}