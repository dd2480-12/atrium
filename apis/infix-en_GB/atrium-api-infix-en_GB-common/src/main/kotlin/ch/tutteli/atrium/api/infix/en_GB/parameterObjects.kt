package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.domain.builders.creating.MetaFeatureBuilder
import ch.tutteli.atrium.domain.creating.MetaFeature
import kotlin.reflect.KCallable

/**
 * Wrapper for a single index -- can be used as distinguishable type for an overload where Int is already in use.
 */
data class Index(val index: Int)

/**
 * Wrapper for a type [T] -- can be used as distinguishable type for an overload where no type parameter is used yet.
 */
data class Of<T> internal constructor(val t: T, val args: Array<out Any?>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Of<*>

        if (t != other.t) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = t?.hashCode() ?: 0
        result = 31 * result + args.contentHashCode()
        return result
    }
}


//fun <T : KCallable<R>, R> of(t: T): Of<T> = Of(t, emptyArray())
//fun <T : KCallable<R>, A1, R> of(t: T, a1: A1): Of<T> = TODO()
