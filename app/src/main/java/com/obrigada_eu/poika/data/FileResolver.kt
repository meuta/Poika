package com.obrigada_eu.poika.data

import java.io.File

class FileResolver {
    fun resolveDeletable(base: File, folder: String): DeletableFile = DeletableFileImpl(File(base, folder))
}