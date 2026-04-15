package com.obrigada_eu.poika.player.domain.usecase

import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.repository.SongRepository
import javax.inject.Inject

class DeleteSongUseCase @Inject constructor(private val repository: SongRepository) {

    operator fun invoke(song: SongMetaData): Boolean {
        return repository.deleteSong(song)
    }
}