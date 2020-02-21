package ch.tutteli.atrium.api.infix.en_GB.creating.map.get.builders.impl

import ch.tutteli.atrium.api.infix.en_GB.creating.map.get.builders.MapGetOption
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.ExpectImpl

internal class MapGetOptionImpl<K, V : Any, T: Map<out K, V>>(
    override val plant: Expect<T>,
    override val key: K
) : MapGetOption<K, V, T> {

    @Suppress("DEPRECATION")
    override infix fun assertIt(assertionCreator: Expect<V>.() -> Unit): Expect<T>
        = plant.addAssertion(ExpectImpl.map.getExisting(plant, key).collect(assertionCreator))
}
