package data.infra.file

import com.obrigada_eu.poika.shared.data.infra.file.SongInputStreamProvider
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class DesktopSongInputStreamProvider : SongInputStreamProvider {

    override fun open(source: String): InputStream? {
        return FileInputStream(File(source))
    }
}