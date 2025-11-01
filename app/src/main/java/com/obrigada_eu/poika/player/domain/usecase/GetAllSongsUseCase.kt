package com.obrigada_eu.poika.player.domain.usecase

import com.obrigada_eu.poika.player.domain.repository.SongRepository
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import javax.inject.Inject

class GetAllSongsUseCase @Inject constructor(private val repository: SongRepository) {

    operator fun invoke(): List<SongMetaData> {
        return repository.getAllSongsMetadata()
    }
}
