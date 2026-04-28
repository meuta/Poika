package com.obrigada_eu.poika.player.data.infra.file

import android.content.Context
import androidx.core.net.toUri
import com.obrigada_eu.poika.shared.data.infra.file.SongInputStreamProvider
import java.io.InputStream

class AndroidSongInputStreamProvider(
    private val context: Context
) : SongInputStreamProvider {

    override fun open(source: String): InputStream? {
        return context.contentResolver.openInputStream(source.toUri())
    }
}