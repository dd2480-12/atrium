package ch.tutteli.atrium.api.cc.de_CH

import ch.tutteli.atrium.assertions.*
import ch.tutteli.atrium.creating.IAssertionPlant
import ch.tutteli.atrium.creating.IAssertionPlantNullable

/**
 * Makes the assertion that [IAssertionPlant.subject] is (equals) [expected].
 *
 * This method might enforce in the future, that [expected] has to be the same type as [IAssertionPlant.subject].
 * Currently the following is possible: `esGilt(1).ist(1.0)`
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Any> IAssertionPlant<T>.ist(expected: T): IAssertionPlant<T>
    = addAssertion(_toBe(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] is not (does not equal) [expected].
 *
 * This method might enforce in the future, that [expected] has to be the same type as [IAssertionPlant.subject].
 * Currently the following is possible: `esGilt(1).istNicht(1.0)`
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Any> IAssertionPlant<T>.istNicht(expected: T): IAssertionPlant<T>
    = addAssertion(_notToBe(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] is the same instance as [expected].
 *
 * This method might enforce in the future, that [expected] has to be the same type as [IAssertionPlant.subject].
 * Currently the following is possible: `esGilt(1).istSelbeInstanzWie(1.0)`
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Any> IAssertionPlant<T>.istSelbeInstanzWie(expected: T): IAssertionPlant<T>
    = addAssertion(_isSame(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] is not the same instance as [expected].
 *
 * This method might enforce in the future, that [expected] has to be the same type as [IAssertionPlant.subject].
 * Currently the following is possible: `esGilt(1).istNichtSelbeInstanzWie(1.0)`
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Any> IAssertionPlant<T>.istNichtSelbeInstanzWie(expected: T): IAssertionPlant<T>
    = addAssertion(_isNotSame(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] is `null`.
 *
 * @return Does not support a fluent API because: what else would you want to assert about `null` anyway?
 *
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Any?> IAssertionPlantNullable<T>.istNull() {
    addAssertion(_isNull(this))
}

/**
 * Can be used to separate assertions when using the fluent API.
 *
 * For instance `assert(1).isLessThan(2).and.isGreaterThan(0)` creates
 * two assertions (not one assertion with two sub-assertions).
 *
 * @return This plant to support a fluent API.
 */
val <T : Any> IAssertionPlant<T>.und: IAssertionPlant<T> get() = this