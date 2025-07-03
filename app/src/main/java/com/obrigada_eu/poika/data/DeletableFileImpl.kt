package com.obrigada_eu.poika.data

import java.io.File

/**
 * A real implementation of DeletableFile that delegates
 * to a java.io.File instance to perform actual file operations.
 */
class DeletableFileImpl(private val file: File) : DeletableFile {
    override fun deleteRecursively(): Boolean = file.deleteRecursively()
}
