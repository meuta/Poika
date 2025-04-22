package com.obrigada_eu.poika.domain.usecase

import android.content.Context
import androidx.core.net.toUri
import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.player.AudioController
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class LoadSongUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioController: AudioController
) {
    operator fun invoke(song: SongMetaData) {
        val base = File(context.filesDir, "songs/${song.folderName}")
        val track1 = song.tracks.find { it.name == "Soprano" }?.file?.let { File(base, it) }
        val track2 = song.tracks.find { it.name == "Alto" }?.file?.let { File(base, it) }
        val track3 = song.tracks.find { it.name == "Minus" }?.file?.let { File(base, it) }

        audioController.loadTracks(track1?.toUri(), track2?.toUri(), track3?.toUri())
    }
}