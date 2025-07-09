package com.obrigada_eu.poika.player.domain.usecase

import android.net.Uri
import com.obrigada_eu.poika.player.data.infra.file.ZipImporter
import com.obrigada_eu.poika.player.domain.model.SongMetaData
import javax.inject.Inject

class ImportZipUseCase @Inject constructor(private val zipImporter: ZipImporter) {

    operator fun invoke(uri: Uri): SongMetaData? {
        return zipImporter.importDataFromUri(uri)
    }
}