package com.obrigada_eu.poika.player.ui.preview

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.model.TrackInfo

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

    val volumes = listOf(1f, 0.58f, 0.35f)

    val currentPos = 34f
    val duration = 228f

    val timeStringZeroZero = "00:00"

    val menuItems = mapOf(
        "Choose Song" to {},
        "Delete Song" to {},
        "Help" to {}
    )

    val dialogButtonLabels = listOf("OK", "Cancel")

    val playbackButtonLabels = listOf("Play", "Pause", "Stop")
    val playbackButtons = listOf("Play", "Stop").associateWith { {} }

    val volumeSliderTitles = listOf("soprano", "alto", "minus")

}