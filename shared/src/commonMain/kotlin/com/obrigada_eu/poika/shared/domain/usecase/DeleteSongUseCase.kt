package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.repository.SongRepository

class DeleteSongUseCase(private val repository: SongRepository) {

    operator fun invoke(song: SongMetaData): Boolean {
        return repository.deleteSong(song)
    }
}