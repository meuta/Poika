package com.obrigada_eu.poika.domain.usecase

import com.obrigada_eu.poika.domain.SongMetaData
import com.obrigada_eu.poika.player.AudioController
import javax.inject.Inject

class LoadSongUseCase @Inject constructor(
    private val audioController: AudioController
) {
    operator fun invoke(song: SongMetaData) {
       audioController.loadTracks(song)
    }
}