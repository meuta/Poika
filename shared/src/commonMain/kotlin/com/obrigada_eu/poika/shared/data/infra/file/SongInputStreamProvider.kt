package com.obrigada_eu.poika.shared.data.infra.file

import java.io.InputStream

interface SongInputStreamProvider {
    fun open(source: String): InputStream?
}