package ch.tutteli.atrium.api.infix.en_GB.creating.map.get.builders

import ch.tutteli.atrium.api.infix.en_GB.creating.map.get.builders.impl.MapGetOptionImpl
import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.creating.SubjectProvider

/**
 * Represents the extension point for another option after a `get key`-step within a
 * sophisticated `get` assertion building process for [Map].
 *
 * @param K The key type of the [Map].
 * @param V the value type of the [Map].
 * @param T A subtype of [Map].
 */
interface MapGetOption<K, V, T : Map<out K, V>> {
    /**
     * The [AssertionPlant] for which this assertion is created
     */
    val plant: Expect<T>

    /**
     * The given key which will be used to perform the [Map.get].
     */
    val key: K

    /**
     * Makes the assertion that the [Assert.subject][SubjectProvider.subject] contains the previously specified [key] and that the
     * corresponding value holds all assertions the given [assertionCreator] might create for it.
     *
     * @return This plant to support a fluent API.
     * @throws AssertionError Might throw an [AssertionError] if a created [Assertion]s (by calling [assertionCreator])
     * does not hold.
     * @throws IllegalArgumentException in case the given [assertionCreator] did not create a single assertion.
     */
    infix fun assertIt(assertionCreator: Expect<V>.() -> Unit): Expect<T>

    companion object {
        /**
         * Creates a [MapGetOption] based on the given [plant] and [key].
         */
        fun <K, V, T: Map<out K, V>> create(plant: Expect<T>, key: K): MapGetOption<K, V, T>
            = MapGetOptionImpl(plant, key)
    }
}

