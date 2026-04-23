package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.repository.SongRepository

class GetAllSongsUseCase(private val repository: SongRepository) {

    operator fun invoke(): List<SongMetaData> {
        return repository.getAllSongsMetadata()
    }
}
