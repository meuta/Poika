package com.obrigada_eu.poika.domain.usecase

import com.obrigada_eu.poika.data.SongRepository
import com.obrigada_eu.poika.domain.SongMetaData
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(private val repository: SongRepository) {
    operator fun invoke(): List<SongMetaData> {
        return repository.getAllSongsMetadata()
    }
}
