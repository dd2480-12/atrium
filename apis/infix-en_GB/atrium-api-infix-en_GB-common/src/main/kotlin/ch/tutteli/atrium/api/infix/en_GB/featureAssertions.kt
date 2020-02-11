package ch.tutteli.atrium.api.infix.en_GB

import ch.tutteli.atrium.api.infix.en_GB.creating.feature.FeatureStep
import ch.tutteli.atrium.api.infix.en_GB.creating.feature.it
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.creating.FeatureExpect
import ch.tutteli.atrium.domain.builders.ExpectImpl
import ch.tutteli.atrium.domain.builders.creating.MetaFeatureOption
import ch.tutteli.atrium.domain.creating.MetaFeature
import kotlin.reflect.*

/**
 * Extracts the given [property] out of the current subject of the assertion
 * and creates a [FeatureStep] which allows to define what should be done with it.
 *
 * @return [FeatureStep] the next step in the building process.
 *
 * @since 0.10.0
 */
infix fun <T, R> Expect<T>.feature(property: KProperty1<T, R>): FeatureStep<T, R> =
    FeatureStep(ExpectImpl.feature.property(this, property))

/**
 * Extracts the value which is returned when calling [f] on the current subject of the assertion and
 * creates a [FeatureStep] which allows to define what should be done with it.
 *
 * @return [FeatureStep] the next step in the building process.
 *
 * @since 0.10.0
 */
infix fun <T, R> Expect<T>.feature(f: KFunction1<T, R>): FeatureStep<T, R> =
    FeatureStep(ExpectImpl.feature.f0(this, f))


/**
 * Represents a [description] of a feature together with an [extractor] which extract the feature as such of type [R]
 * out of the subject of the assertion with type [T].
 *
 * Use one of the [withArgs] overload to create this representation.
 *
 * @property description The description of the feature.
 * @property extractor The extractor which extracts the feature out of the subject of the assertion.
 *
 * @since 0.10.0
 */
data class DescriptionWithProvider<T, R>(val description: String, val extractor: (T) -> R)

/**
 * Extracts a feature using the given [DescriptionWithProvider.extractor] out of the current subject of the assertion
 * and creates a [FeatureStep] which allows to define what should be done with it.
 *
 * There are several function overloads named `withArgs` use them to get a [DescriptionWithProvider].
 *
 * @return [FeatureStep] the next step in the building process.
 *
 * @since 0.10.0
 */
infix fun <T, R> Expect<T>.feature(withArgs: DescriptionWithProvider<T, R>): FeatureStep<T, R> =
    FeatureStep(ExpectImpl.feature.manualFeature(this, withArgs.description, withArgs.extractor))


//@formatter:off
fun <T, A1, R> withArgs(p: KFunction2<T, A1, R>, a1: A1): DescriptionWithProvider<T, R> =
    DescriptionWithProvider(p.name) { p.invoke(it, a1) }

fun <T, A1, A2, R> withArgs(p: KFunction3<T, A1, A2, R>, a1: A1, a2: A2): DescriptionWithProvider<T, R> =
    DescriptionWithProvider(p.name) { p.invoke(it, a1, a2) }

fun <T, A1, A2, A3, R> withArgs(p: KFunction4<T, A1, A2, A3, R>, a1: A1, a2: A2, a3: A3): DescriptionWithProvider<T, R> =
    DescriptionWithProvider(p.name) { p.invoke(it, a1, a2, a3) }

fun <T, A1, A2, A3, A4, R> withArgs(p: KFunction5<T, A1, A2, A3, A4, R>, a1: A1, a2: A2, a3: A3, a4: A4): DescriptionWithProvider<T, R> =
    DescriptionWithProvider(p.name) { p.invoke(it, a1, a2, a3, a4) }

fun <T, A1, A2, A3, A4, A5, R> withArgs(p: KFunction6<T, A1, A2, A3, A4, A5, R>, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5): DescriptionWithProvider<T, R> =
    DescriptionWithProvider(p.name) { p.invoke(it, a1, a2, a3, a4, a5) }
//@formatter:on


/**
 * Extracts a feature out of the current subject of the assertion,
 * based on the given [provider] and
 * creates a [FeatureStep] which allows to define what should be done with it.
 *
 * @param provider Creates a [MetaFeature] where the subject of the assertion is available via
 *   implicit parameter `it`. Usually you use [f][MetaFeatureOption.f] to create a [MetaFeature],
 *   e.g. `feature { f(it::size) }`
 *
 * @return An [Expect] for the extracted feature.
 *
 * @since 0.10.0
 */
infix fun <T, R> Expect<T>.feature(provider: MetaFeatureOption<T>.(T) -> MetaFeature<R>): FeatureStep<T, R> =
    FeatureStep(ExpectImpl.feature.genericSubjectBasedFeature(this) { MetaFeatureOption(this).provider(it) })

/**
 * Creates a [MetaFeature] using the given [provider] and [description].
 *
 * @return The newly created [MetaFeature]
 */
@Suppress("unused" /* unused receiver, but that's fine */)
fun <T, R> MetaFeatureOption<T>.manual(description: String, provider: () -> R): MetaFeature<R> =
    MetaFeature(description, provider())


fun <T> notImplemented(): Expect<T> = TODO()

data class Person(val name: String, val firstName: String, val age: Int) {
    fun foo(int: Int) = "a"
    fun bar() = "b"
}

@ExperimentalWithOptions
fun test() {

    val l: Expect<List<Int>> = notImplemented()


    l get 1
    l get 1 it { }

    l get 1 it o toBe 1

    val p: Expect<Pair<String, Int>> = notImplemented()


    p and {
        first {
            o feature String::length it o toBe 1
        }
        first withRepresentation "name" toBe "robert"
        second toBe 1
    }

    val e: Expect<Person> = notImplemented()

    e feature Person::name withRepresentation "first name" it o toBe "robert"


    e feature Person::name it o toBe "a"
    e feature Person::bar it { o toBe "a" }

    e feature withArgs(Person::foo, 1) it o toBe "a"
    e feature withArgs(Person::foo, 1) it { o toBe "a" }

    e feature { f(it::age) } it o toBe 1
    e feature { manual("age") { it.age } } it o isGreaterThan 2
}
