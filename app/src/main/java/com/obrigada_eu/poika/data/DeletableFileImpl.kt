package com.obrigada_eu.poika.data

import java.io.File

class DeletableFileImpl(private val file: File) : DeletableFile {
    override fun deleteRecursively(): Boolean = file.deleteRecursively()
}
