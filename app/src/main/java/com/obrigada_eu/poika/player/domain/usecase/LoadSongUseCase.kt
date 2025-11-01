package com.obrigada_eu.poika.player.domain.usecase

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.contracts.AudioService
import javax.inject.Inject

class LoadSongUseCase @Inject constructor(private val audioService: AudioService) {

    operator fun invoke(song: SongMetaData) {
        audioService.loadTracks(song)
    }
}