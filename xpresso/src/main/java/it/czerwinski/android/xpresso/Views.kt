package it.czerwinski.android.xpresso

import android.support.test.espresso.Espresso
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf

/**
 * Creates a [TypedViewInteraction] for a given View of type [T].
 *
 * @param T Type of the view under test.
 *
 * @return [TypedViewInteraction] for the given View.
 */
inline fun <reified T : View> on(): TypedViewInteraction<T> =
        TypedViewInteraction(T::class.java, Espresso.onView(instanceOf(T::class.java)))

/**
 * Creates a [TypedViewInteraction] for a given View of type [T].
 *
 * @param T Type of the view under test.
 *
 * @param viewMatchers Matchers used to select the view.
 *
 * @return [TypedViewInteraction] for the given View.
 */
inline fun <reified T : View> on(vararg viewMatchers: Matcher<View>): TypedViewInteraction<T> =
        TypedViewInteraction(T::class.java, Espresso.onView(allOf(instanceOf(T::class.java), *viewMatchers)))
