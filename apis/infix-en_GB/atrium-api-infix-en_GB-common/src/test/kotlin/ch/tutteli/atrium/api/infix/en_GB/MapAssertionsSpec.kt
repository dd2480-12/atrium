package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.builders.utils.mapArguments
import ch.tutteli.atrium.specs.*

class MapExpectionsSpec : ch.tutteli.atrium.specs.integration.MapAssertionsSpec(
    fun2(Companion::contains),
    fun2(Companion::contains).name to Companion::containsNullable,
    "${fun2(Companion::contains).name} ${KeyValue::class.simpleName}" to Companion::containsKeyWithValueAssertions,
    "${fun2(Companion::contains).name} ${KeyValue::class.simpleName}" to Companion::containsKeyWithNullableValueAssertions,
    fun1(Companion::containsKey),
    fun1(Companion::containsNullableKey),
    fun1(Companion::containsNotKey),
    fun1(Companion::containsNotNullableKey),
    /* string toBe, notToBe to avoid ambiguity error */
    "toBe ${Empty::class.simpleName}" to Companion::isEmpty,
    "notToBe ${Empty::class.simpleName}" to Companion::isNotEmpty
) {
    companion object {
        private fun contains(plant: Expect<Map<out String, Int>>, pair: Pair<String, Int>, otherPairs: Array<out Pair<String, Int>>): Expect<Map<out String, Int>> {
            return if (otherPairs.isEmpty()) {
                plant contains (pair.first to pair.second)
            } else {
                plant contains Pairs(pair, *otherPairs)
            }
        }

        private fun containsNullable(plant: Expect<Map<out String?, Int?>>, pair: Pair<String?, Int?>, otherPairs: Array<out Pair<String?, Int?>>): Expect<Map<out String?, Int?>> {
            return if (otherPairs.isEmpty()) {
                plant contains (pair.first to pair.second)
            } else {
                plant contains Pairs(pair, *otherPairs)
            }
        }

        private fun containsKeyWithValueAssertions(plant: Expect<Map<out String, Int>>, keyValue: Pair<String, Expect<Int>.() -> Unit>, otherKeyValues: Array<out Pair<String, Expect<Int>.() -> Unit>>) : Expect<Map<out String, Int>> {
            return if (otherKeyValues.isEmpty()) {
                plant contains KeyValue(keyValue.first, keyValue.second)
            } else {
                mapArguments(keyValue, otherKeyValues).to { KeyValue(it.first, it.second) }.let { (first, others) ->
                    plant contains All(first, *others)
                }
            }
        }

        private fun containsKeyWithNullableValueAssertions(plant: Expect<Map<out String?, Int?>>, keyValue: Pair<String?, (Expect<Int>.() -> Unit)?>, otherKeyValues: Array<out Pair<String?, (Expect<Int>.() -> Unit)?>>): Expect<Map<out String?, Int?>> {
            return if (otherKeyValues.isEmpty()) {
                plant contains KeyValue(keyValue.first, keyValue.second)
            } else {
                mapArguments(keyValue, otherKeyValues).to { KeyValue(it.first, it.second) }.let { (first, others) ->
                    plant contains All(first, *others)
                }
            }
        }

        private fun containsKey(plant: Expect<Map<out String, *>>, key: String)
            = plant containsKey key

        private fun containsNullableKey(plant: Expect<Map<out String?, *>>, key: String?)
            = plant containsKey key

        private fun containsNotKey(plant: Expect<Map<out String, *>>, key: String)
            = plant containsNotKey key

        private fun containsNotNullableKey(plant: Expect<Map<out String?, *>>, key: String?)
            = plant containsNotKey key

        private fun isEmpty(plant: Expect<Map<*, *>>)
            = plant toBe Empty

        private fun isNotEmpty(plant: Expect<Map<*, *>>)
            = plant notToBe Empty
    }

    @Suppress("unused", "UNUSED_VALUE")
    private fun ambiguityTest() {
        var map: Expect<Map<Number, CharSequence>> = notImplemented()
        var subMap: Expect<LinkedHashMap<out Number, String>> = notImplemented()
        var nullableKeyMap: Expect<Map<Number?, CharSequence>> = notImplemented()
        var nullableValueMap: Expect<Map<Number, CharSequence?>> = notImplemented()
        var nullableKeyValueMap: Expect<Map<Number?, CharSequence?>> = notImplemented()
        var readOnlyNullableKeyValueMap: Expect<Map<out Number?, CharSequence?>> = notImplemented()
        var starMap: Expect<Map<*, *>> = notImplemented()

        map contains (1 to "a")
        map contains Pairs(1 to "a", 2 to "b")
        map contains (KeyValue(1) {})
        map contains All(KeyValue(1) {}, KeyValue(2) {})
        map contains Pairs(1.0 to StringBuilder("a"))
        map contains Pairs(12f to "a", 2L to StringBuilder("b"))
        map contains (KeyValue(1) {})
        map contains All(KeyValue(1) {}, KeyValue(2) {})

        subMap contains (1 to "a")
        subMap contains Pairs(1 to "a", 2 to "b")
        subMap contains (KeyValue(1) {})
        subMap contains All(KeyValue(1) {}, KeyValue(2) {})
        subMap contains (1.0 to StringBuilder("a"))
        subMap contains Pairs(12f to "a", 2L to StringBuilder("b"))
        subMap contains (KeyValue(1) {})
        subMap contains All(KeyValue(1) {}, KeyValue(2) {})

        nullableKeyMap contains (1 to "a")
        nullableKeyMap contains Pairs(1 to "a", 2 to "b")
        nullableKeyMap contains (KeyValue(1) {})
        nullableKeyMap contains All(KeyValue(1) {}, KeyValue(2) {})
        nullableKeyMap contains (null to "a")
        nullableKeyMap contains Pairs(null to "a", null to "b")
        nullableKeyMap contains Pairs(null to "a", 2 to "b")
        nullableKeyMap contains (KeyValue(null) {})
        nullableKeyMap contains All(KeyValue(null) {}, KeyValue(null) {})
        nullableKeyMap contains All(KeyValue(null) {}, KeyValue(2) {})

        nullableValueMap contains (1 to "a")
        nullableValueMap contains Pairs(1 to "a", 2 to "b")
        nullableValueMap contains (KeyValue(1) {})
        nullableValueMap contains All(KeyValue(1) {}, KeyValue(2) {})
        nullableValueMap contains (1 to null)
        nullableValueMap contains Pairs(1 to null, 2 to null)
        nullableValueMap contains Pairs(1 to null, 2 to "a")
        nullableValueMap contains (KeyValue(1, null))
        nullableValueMap contains All(KeyValue(1, null), KeyValue(2, null))
        nullableValueMap contains All(KeyValue(1, null), KeyValue(2) {})

        nullableKeyValueMap contains (1 to "a")
        nullableKeyValueMap contains Pairs(1 to "a", 2 to "b")
        nullableKeyValueMap contains (KeyValue(1) {})
        nullableKeyValueMap contains All(KeyValue(1) {}, KeyValue(2) {})

        nullableKeyValueMap contains (null to "a")
        nullableKeyValueMap contains Pairs(null to "a", null to "b")
        nullableKeyValueMap contains Pairs(null to "a", 2 to "b")
        nullableKeyValueMap contains (KeyValue(null) {})
        nullableKeyValueMap contains All(KeyValue(null) {}, KeyValue(null) {})
        nullableKeyValueMap contains All(KeyValue(null) {}, KeyValue(2) {})

        nullableKeyValueMap contains (1 to null)
        nullableKeyValueMap contains Pairs(1 to null, 2 to null)
        nullableKeyValueMap contains Pairs(1 to null, 2 to "a")
        nullableKeyValueMap contains (KeyValue(1, null))
        nullableKeyValueMap contains All(KeyValue(1, null), KeyValue(2, null))
        nullableKeyValueMap contains All(KeyValue(1, null), KeyValue(2) {})

        nullableKeyValueMap contains (null to null)
        nullableKeyValueMap contains Pairs(null to null, null to null)
        nullableKeyValueMap contains Pairs(1 to null, null to "a")
        nullableKeyValueMap contains (KeyValue(null, null))
        nullableKeyValueMap contains All(KeyValue(null, null), KeyValue(null, null))
        nullableKeyValueMap contains All(KeyValue(1, null), KeyValue(null) {})

        readOnlyNullableKeyValueMap contains (1 to "a")
        readOnlyNullableKeyValueMap contains Pairs(1 to "a", 2 to "b")
        readOnlyNullableKeyValueMap contains (KeyValue(1) {})
        readOnlyNullableKeyValueMap contains All(KeyValue(1) {}, KeyValue(2) {})

        readOnlyNullableKeyValueMap contains (null to "a")
        readOnlyNullableKeyValueMap contains Pairs(null to "a", null to "b")
        readOnlyNullableKeyValueMap contains Pairs(null to "a", 2 to "b")
        readOnlyNullableKeyValueMap contains (KeyValue(null) {})
        readOnlyNullableKeyValueMap contains All(KeyValue(null) {}, KeyValue(null) {})
        readOnlyNullableKeyValueMap contains All(KeyValue(null) {}, KeyValue(2) {})

        readOnlyNullableKeyValueMap contains (1 to null)
        readOnlyNullableKeyValueMap contains Pairs(1 to null, 2 to null)
        readOnlyNullableKeyValueMap contains Pairs(1 to null, 2 to "a")
        readOnlyNullableKeyValueMap contains (KeyValue(1, null))
        readOnlyNullableKeyValueMap contains All(KeyValue(1, null), KeyValue(2, null))
        readOnlyNullableKeyValueMap contains All(KeyValue(1, null), KeyValue(2) {})

        readOnlyNullableKeyValueMap contains (null to null)
        readOnlyNullableKeyValueMap contains Pairs(null to null, null to null)
        readOnlyNullableKeyValueMap contains Pairs(1 to null, null to "a")
        readOnlyNullableKeyValueMap contains (KeyValue(null, null))
        readOnlyNullableKeyValueMap contains All(KeyValue(null, null), KeyValue(null, null))
        readOnlyNullableKeyValueMap contains All(KeyValue(1, null), KeyValue(null) {})

        readOnlyNullableKeyValueMap contains (1 to "a")
        readOnlyNullableKeyValueMap contains Pairs(1 to "a", 2 to "b")
        readOnlyNullableKeyValueMap contains (KeyValue(1) {})
        readOnlyNullableKeyValueMap contains All(KeyValue(1) {}, KeyValue(2) {})

        starMap contains (null to "a")
        starMap contains Pairs(null to "a", null to "b")
        starMap contains Pairs(null to "a", 2 to "b")
        starMap contains (KeyValue(null) {})
        starMap contains All(KeyValue(null) {}, KeyValue(null) {})
        starMap contains All(KeyValue(null) {}, KeyValue(2) {})

        starMap contains (1 to null)
        starMap contains Pairs(1 to null, 2 to null)
        starMap contains Pairs(1 to null, 2 to "a")
        starMap contains (KeyValue(1, null))
        starMap contains All(KeyValue(1, null), KeyValue(2, null))
        starMap contains All(KeyValue(1, null), KeyValue(2) {})

        starMap contains (null to null)
        starMap contains Pairs(null to null, null to null)
        starMap contains Pairs(1 to null, null to "a")
        starMap contains (KeyValue(null, null))
        starMap contains All(KeyValue(null, null), KeyValue(null, null))
        starMap contains All(KeyValue(1, null), KeyValue(null) {})

        map.containsKey(1)
        map.containsKey(1f)
        subMap.containsKey(1)
        subMap.containsKey(1f)
        nullableKeyMap.containsKey(1)
        nullableKeyMap.containsKey(1f)
        readOnlyNullableKeyValueMap.containsKey(1)
        readOnlyNullableKeyValueMap.containsKey(1f)

        map.containsNotKey(1)
        map.containsNotKey(1f)
        subMap.containsNotKey(1)
        subMap.containsNotKey(1f)
        nullableKeyMap.containsNotKey(1)
        nullableKeyMap.containsNotKey(1f)
        readOnlyNullableKeyValueMap.containsNotKey(1)
        readOnlyNullableKeyValueMap.containsNotKey(1f)


        map = map toBe Empty
        subMap = subMap toBe Empty
        nullableKeyMap = nullableKeyMap toBe Empty
        nullableValueMap = nullableValueMap toBe Empty
        nullableKeyValueMap = nullableKeyValueMap toBe Empty
        readOnlyNullableKeyValueMap = readOnlyNullableKeyValueMap toBe Empty
        starMap = starMap toBe Empty

        map = map notToBe Empty
        subMap = subMap notToBe Empty
        nullableKeyMap = nullableKeyMap notToBe Empty
        nullableValueMap = nullableValueMap notToBe Empty
        nullableKeyValueMap = nullableKeyValueMap notToBe Empty
        readOnlyNullableKeyValueMap = readOnlyNullableKeyValueMap notToBe Empty
        starMap = starMap notToBe Empty

    }
}
