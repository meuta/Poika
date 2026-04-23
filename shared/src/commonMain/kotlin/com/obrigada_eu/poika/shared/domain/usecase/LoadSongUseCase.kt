package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.audio.AudioService

class LoadSongUseCase(private val audioService: AudioService) {

    operator fun invoke(song: SongMetaData) {
        audioService.loadTracks(song)
    }
}