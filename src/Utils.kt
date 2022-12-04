@file:Suppress("unused")

import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String): List<String> = File("src/inputs", "$name.txt")
    .readLines()

/**
 * Gets lines from the given formatted input string.
 */
@Deprecated("Not helpful because of IDE behaviour with pasting data", ReplaceWith("trimIndent().lines()"), DeprecationLevel.WARNING)
val String.readInput: List<String> get() = trimIndent().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
