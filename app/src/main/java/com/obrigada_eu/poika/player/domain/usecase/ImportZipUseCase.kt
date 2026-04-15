package com.obrigada_eu.poika.player.domain.usecase

import android.net.Uri
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import com.obrigada_eu.poika.player.domain.repository.SongRepository
import javax.inject.Inject

class ImportZipUseCase @Inject constructor(private val repository: SongRepository) {

    operator fun invoke(uri: Uri): SongMetaData? {
        return repository.importSong(uri)
    }
}