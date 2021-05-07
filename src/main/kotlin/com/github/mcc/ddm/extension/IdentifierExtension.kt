package com.github.mcc.ddm.extension

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.util.Identifier

/**
 * An [Exception] for [Identifier]s that couldn't be parsed.
 */
class IdentifierParseException(private val ident: String): Exception() {
    override val message: String
        get() = "Could not parse ident $ident"
}

/**
 * Convert a string into an [Identifier].
 * @receiver the string to convert
 * @return the new parsed Identifier
 * @throws IdentifierParseException if the string is not a valid identifier
 */
fun String.toIdentifier(): Identifier = Identifier.tryParse(this) ?: throw IdentifierParseException(this)


/**
 * Convert a [JsonArray] of Strings into a list of [Identifier]s.
 * @receiver the json array that must contain strings, or null
 * @return a List of parsed [Identifier]s
 * @throws UnsupportedOperationException if an element in the array is not a string
 * @throws IdentifierParseException if a string in the array cannot parse into an [Identifier]
 */
fun JsonArray?.idents(): List<Identifier> = this?.map {
    it.asString.toIdentifier()
}?.toList() ?: listOf()

/**
 * Construct a List of [T]s from a [JsonArray] using the provided function.
 * If this is null, returns an empty list
 * @receiver the json array that must contain only [JsonObject]s
 * @return a list of [T]s
 * @throws UnsupportedOperationException if an element is not a [JsonObject]
 */
inline fun <T> JsonArray?.list(f: (JsonObject) -> T): List<T> = this?.map {
    f(it.asJsonObject)
}?.toList() ?: listOf()


/**
 * Append to this [Identifier]'s extension, returning a new identifier.
 * @receiver the original identifier to append to
 * @param extension the new extension to amend to the current extension
 * @return a copied identifier with the joined extension
 */
fun Identifier.append(extension: String): Identifier = Identifier(this.namespace, "$path/$extension")

/**
 * Prepend to this [Identifier]'s extension, returning a new identifier.
 * @receiver the original identifier to prepend to
 * @param extension the new extension to prefix to the current path
 * @return a copied identifier with the joined prefix
 */
fun Identifier.prepend(prefix: String): Identifier = Identifier(this.namespace, "$prefix/$path")