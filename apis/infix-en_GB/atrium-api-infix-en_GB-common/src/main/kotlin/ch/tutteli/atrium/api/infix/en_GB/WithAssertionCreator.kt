package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.creating.Expect
import kotlin.reflect.KFunction1

/**
 * Represent a data structure which holds some [value] of type [V] together with an [assertionCreator]-lambda.
 *
 * Its purpose is to augment a feature which expects 1 argument with an assertionCreator-lambda,
 * eventually allowing to have a second overload which expects [WithAssertionCreator]. Thus make it possible that
 * the feature can have both variants, fail-fast without assertionCreator-lambda and non-fail fast,
 * ie. assertion group block syntax, which expects an assertionCreator-lambda. The following example shows both syntax
 * in action:
 * ```
 * expect(listOf(1,2)) get 1 isGreaterThan 2 isLessThan 4     // without assertionCreator-lambda
 * expect(listOf(1,2)) get 1 {                                // without assertionCreator-lambda
 *   o isGreaterThan 2
 *   o isLessThan 4
 * }
 * ```
 *
 * @property value The value which shall be joined with an assertionCreator-lambda.
 * @property assertionCreator The assertionCreator-lambda as such which defines assertion for a subject of type [T].
 *
 * @param V The value type
 * @param T The type of the subject of assertion for which the [assertionCreator] is defined.
 */
data class WithAssertionCreator<V, T>(val value: V, val assertionCreator: Expect<T>.() -> Unit)


//infix operator fun <V, T> V.invoke(assertionCreator: Expect<T>.() -> Unit): WithAssertionCreator<V, T> =
//    WithAssertionCreator(this, assertionCreator)
//
infix operator fun <V, T> V.invoke(f: A<T>.() -> Unit): Pair<V, T> = TODO()

class A<T> {
    fun test1() = 1
    fun <R> test2(t: T, f: (T) -> R): Triple<String, T, R> = TODO()
}

class B {
    fun zulu() = 1
}

val createB get(): B = TODO()

fun foo1(p: Pair<String, Int>): Unit = TODO()
fun foo2(p: Pair<A<String>, Int>): Unit = TODO()
fun foo3(p: Pair<B, Int>): Unit = TODO()
fun foo4(p: Pair<KFunction1<B, Int>, Int>): Unit = TODO()
fun <T> foo5(p: Pair<T, Int>): Unit = TODO()

fun <T, R> bar1(p: Pair<Triple<String, T, R>, R>): Pair<A<T>, T> = TODO()
infix fun <T, R> A<T>.bar2(p: Pair<Triple<String, T, R>, R>): Pair<A<T>, T> = TODO()

infix fun <T, R> A<T>.bar3(p: A<T>.() -> Triple<String, T, R>): Pair<A<T>, T> = TODO()
infix fun <T, R> A<T>.bar3(p: Pair<A<T>.() -> Triple<String, T, R>, R>): Pair<A<T>, T> = TODO()

fun <T, R> via(t: T, f: (T) -> R): Triple<String, T, R> = TODO()

fun test() {
    foo1("a" { test1() })
    foo2(A<String>() { test1() })         // fails - found A<String>
    foo2(A<String>() invoke { test1() })  // workaround 1 passes
    foo2((A<String>()) { test1() })       // workaround 2 passes

    foo3(B() { test1() })        // fails - found B
    foo4(B::zulu { test1() })    // succeeds :)

    foo5("a" { test1() })
    foo5(B() { test1() })        // fails - found B
    foo5(A<Int>() { test1() })   // fails - found A<Int>
    foo5(B::zulu { test1() })
    foo5(createB { test1() })

    val a = A<String>()
    foo2(a { test1() })

    bar1(via("B::zulu", { it }) { test1() })                          // fails - type inference
    bar1(via(B::zulu, { it }) { test1() })                            // fails - type inference
    bar1(via(B(), { it.zulu() }) { test1() })                         // fails - type inference
    bar1(via(A<String>(), { it.test1() }) { test1() })                // fails - type inference
    bar1(via(A<String>(), { it.test1() }) invoke { test1() })         // workaround 1 passes
    bar1((via(A<String>(), { it.test1() })) { test1() })              // workaround 2 passes

    A<String>() bar2 via("a", { it.isEmpty() }) { test1() }           // fails - type inference
    A<String>() bar2 (via("a", { it.isEmpty() }) { test1() })         // fails - type inference
    A<String>() bar2 via("a", { it.isEmpty() }) invoke { test1() }    // workaround 1 fails - type inference
    A<String>() bar2 (via("a", { it.isEmpty() })) { test1() }         // workaround 2 passes

    A<String>() bar3 { test2("a", { it.isEmpty() }) }
    A<String>() bar3 { test2("a", { it.isEmpty() }) } { test1() }            // fails - type inference
    A<String>() bar3 { test2("a", { it.isEmpty() }) } invoke { test1() }     // workaround 1 fails - type inference
    A<String>() bar3 ({ test2("a", { it.isEmpty() }) }) { test1() }          // workaround 2 fails - type inference
    A<String>() bar3 ({ test2("a", { it.isEmpty() }) }) invoke { test1() }   // workaround 3 fails - type inference
    A<String>() bar3 (({ test2("a", { it.isEmpty() }) }) invoke { test1() }) // workaround 4 fails - type inference
}
