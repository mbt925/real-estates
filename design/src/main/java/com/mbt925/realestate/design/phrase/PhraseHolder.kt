package com.mbt925.realestate.design.phrase

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
sealed interface PhraseHolder : (Resources) -> String {
    override fun invoke(resources: Resources): String = toString(resources)
}

private data class Raw(
    val phrase: String,
) : PhraseHolder

private data class Single(
    val id: Int,
    val formatArgs: ImmutableList<Any>?,
) : PhraseHolder

private data class Plural(
    val id: Int,
    val quantity: Int,
    val formatArgs: ImmutableList<Any>?,
) : PhraseHolder


fun phraseHolderRaw(
    phrase: String,
): PhraseHolder = Raw(
    phrase = phrase,
)

fun phraseHolderSingle(
    id: Int,
    vararg formatArgs: Any,
): PhraseHolder = Single(
    id = id,
    formatArgs = formatArgs.toList().toImmutableList().takeIf { it.isNotEmpty() }
)

fun phraseHolderPlural(
    id: Int,
    quantity: Int,
    vararg formatArgs: Any,
): PhraseHolder = Plural(
    id = id,
    quantity = quantity,
    formatArgs = formatArgs.toList().toImmutableList().takeIf { it.isNotEmpty() }
)

fun PhraseHolder.toString(resources: Resources): String = when (this) {
    is Raw -> phrase
    is Single ->
        if (formatArgs.isNullOrEmpty())
            resources.getString(id)
        else
            resources.getString(
                id,
                *formatArgs.toPhraseTypedArray(resources),
            )
    is Plural ->
        if (formatArgs.isNullOrEmpty())
            resources.getQuantityString(
                id,
                quantity,
            )
        else
            resources.getQuantityString(
                id,
                quantity,
                *formatArgs.toPhraseTypedArray(resources),
            )
}

private fun List<Any>.toPhraseTypedArray(resources: Resources): Array<Any> = map {
    when (it) {
        is PhraseHolder -> it.toString(resources)
        is Int -> {
            try {
                resources.getString(it)
            } catch (_: Resources.NotFoundException) {
                it
            }
        }
        else -> it
    }
}.toTypedArray()

@Composable
fun ((Resources) -> String).resolve() = this(resources())

@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
