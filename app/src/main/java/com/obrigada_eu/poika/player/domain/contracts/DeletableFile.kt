package com.obrigada_eu.poika.player.domain.contracts

/**
 * An abstraction for file deletion operations.
 * Allows replacing real file system access with mocks in tests.
 */
interface DeletableFile {
    fun deleteRecursively(): Boolean
}