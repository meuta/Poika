package com.obrigada_eu.poika.data

import java.io.File

/**
 * Responsible for resolving file paths relative to a given base directory.
 * Returns abstractions instead of raw java.io.File instances,
 * making file operations more flexible and easily testable.
 */

class FileResolver {

    /**
     * Resolves a deletable file located under the specified folder.
     *
     * @param base the base directory where the search starts
     * @param folder the relative path to the target folder
     * @return a DeletableFile abstraction wrapping the resolved path
     */
    fun resolveDeletable(base: File, folder: String): DeletableFile = DeletableFileImpl(File(base, folder))
}