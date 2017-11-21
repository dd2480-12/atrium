package ch.tutteli.atrium.api.cc.de_CH

import ch.tutteli.atrium.assertions._containsEntriesInAnyOrder
import ch.tutteli.atrium.assertions._containsObjectsInAnyOrder
import ch.tutteli.atrium.assertions.iterable.contains.builders.IterableContainsCheckerBuilder
import ch.tutteli.atrium.assertions.iterable.contains.decorators.IterableContainsInAnyOrderDecorator
import ch.tutteli.atrium.creating.IAssertionPlant

/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] value shall be searched
 * within the [Iterable].
 *
 * @param expected The value which is expected to be contained within the [Iterable].
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.value(expected: E): IAssertionPlant<T>
    = values(expected)

/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] value as well as the
 * [otherExpected] values shall be searched within the [Iterable].
 *
 * Notice, that it does not search for unique matches. Meaning, if the iterable is `setOf('a', 'b')` and [expected] is
 * defined as `'a'` and one [otherExpected] is defined as `'a'` as well, then both match, even though they match the
 * same entry. Use an option such as [zumindest], [hoechstens] and [genau] to control the number of occurrences you expect.
 *
 * Meaning you might want to use:
 *   `enthaelt.inAnyOrder.genau(2).value('a')`
 * instead of:
 *   `enthaelt.inAnyOrder.zumindest(1).values('a', 'a')`
 *
 * @param expected The value which is expected to be contained within the [Iterable].
 * @param otherExpected Additional values which are expected to be contained within [Iterable].
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.values(expected: E, vararg otherExpected: E): IAssertionPlant<T>
    = addAssertion(_containsObjectsInAnyOrder(this, expected, otherExpected))

/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] object shall be searched
 * within the [Iterable].
 *
 * @param expected The object which is expected to be contained within the [Iterable].
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.`object`(expected: E): IAssertionPlant<T>
    = objects(expected)

/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] object as well as the
 * [otherExpected] objects shall be searched within the iterable.
 *
 * Notice, that it does not search for unique matches. Meaning, if the iterable is `setOf('a', 'b')` and [expected] is
 * defined as `'a'` and one [otherExpected] is defined as `'a'` as well, then both match, even though they match the
 * same entry. Use an option such as [zumindest], [hoechstens] and [genau] to control the number of occurrences you expect.
 *
 * Meaning you might want to use:
 *   `enthaelt.inAnyOrder.genau(2).objects('a')`
 * instead of:
 *   `enthaelt.inAnyOrder.zumindest(1).objects('a', 'a')`
 *
 * @param expected The object which is expected to be contained within the [Iterable].
 * @param otherExpected Additional objects which are expected to be contained within [Iterable].
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.objects(expected: E, vararg otherExpected: E): IAssertionPlant<T>
    = addAssertion(_containsObjectsInAnyOrder(this, expected, otherExpected))

/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which holds
 * all assertions [assertionCreator] might create.
 *
 * @param assertionCreator The lambda function which creates the assertions which the entry we are looking for
 *        has to hold; or in other words, the function which defines whether an entry is the one we are looking for.
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E : Any, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.entry(assertionCreator: IAssertionPlant<E>.() -> Unit): IAssertionPlant<T>
    = entries(assertionCreator)

/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which holds
 * all assertions [assertionCreator] might create and search for entries which hold (one by one) the assertions
 * created by the [otherAssertionCreators].
 *
 * @param assertionCreator The lambda function which creates the assertions which the entry we are looking for
 *        has to hold; or in other words, the function which defines whether an entry is the one we are looking for.
 * @param otherAssertionCreators Additional lambda functions which each kind of identify (separately) an entry
 *        which we are looking for.
 *
 * @return The [IAssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <E : Any, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderDecorator>.entries(assertionCreator: IAssertionPlant<E>.() -> Unit, vararg otherAssertionCreators: IAssertionPlant<E>.() -> Unit): IAssertionPlant<T>
    = addAssertion(_containsEntriesInAnyOrder(this, assertionCreator, otherAssertionCreators))