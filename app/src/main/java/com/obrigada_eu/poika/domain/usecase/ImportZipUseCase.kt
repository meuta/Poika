package com.obrigada_eu.poika.domain.usecase

import android.net.Uri
import com.obrigada_eu.poika.data.ZipImporter
import com.obrigada_eu.poika.domain.SongMetaData
import javax.inject.Inject

class ImportZipUseCase @Inject constructor(private val zipImporter: ZipImporter) {

    operator fun invoke(uri: Uri): SongMetaData? {
        return zipImporter.importDataFromUri(uri)
    }
}