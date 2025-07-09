package com.obrigada_eu.poika.player.domain.usecase

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.data.infra.audio.AudioController
import javax.inject.Inject

class LoadSongUseCase @Inject constructor(private val audioController: AudioController) {

    operator fun invoke(song: SongMetaData) {
        audioController.loadTracks(song)
    }
}