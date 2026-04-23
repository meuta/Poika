package com.obrigada_eu.poika.shared.domain.usecase

import com.obrigada_eu.poika.shared.domain.model.SongMetaData
import com.obrigada_eu.poika.shared.domain.repository.SongRepository

class ImportZipUseCase(private val repository: SongRepository) {

    operator fun invoke(uriString: String): SongMetaData? {
        return repository.importSong(uriString)
    }
}