package it.czerwinski.android.xpresso

import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.view.View
import it.czerwinski.kotlin.util.Failure
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf

/**
 * Provides a typed interface to perform actions or asserts on Views of type [T].
 *
 * @param T Type of the view under test.
 *
 * @param type Type of the view under test.
 * @param viewInteraction Wrapped untyped [ViewInteraction].
 */
class TypedViewInteraction<T : View>(
        private val type: Class<T>,
        private val viewInteraction: ViewInteraction) {

    /**
     * Checks given [viewAssertion] on the view under test.
     *
     * @param viewAssertion Assertion to be performed on the view under test.
     *
     * @return [TypedViewInteraction] to chain operations.
     */
    fun check(viewAssertion: ViewAssertion) =
            TypedViewInteraction(type, viewInteraction.check(viewAssertion))

    /**
     * Checks if the given [viewMatchers] match the view under test.
     *
     * @param viewMatchers Matchers to be tested against the view under test.
     *
     * @return [TypedViewInteraction] to chain operations.
     */
    fun check(vararg viewMatchers: Matcher<View>) =
            TypedViewInteraction(type, viewInteraction.check(matches(allOf(*viewMatchers))))

    /**
     * Checks given [viewAssertion] on the view under test.
     *
     * @param viewAssertion Assertion to be performed on the view under test.
     *
     * @return [TypedViewInteraction] to chain operations.
     */
    fun check(viewAssertion: (Try<T>) -> Unit) =
            TypedViewInteraction(type, viewInteraction.check { view, noMatchingViewException ->
                (view?.let { Try { type.cast(view) } }
                        ?: Failure(noMatchingViewException))
                        .also { viewAssertion(it) }
            })

    /**
     * Performs given [viewActions] on the view under test.
     *
     * @param viewActions Actions to be performed on the view under test.
     *
     * @return [TypedViewInteraction] to chain operations.
     */
    fun perform(vararg viewActions: ViewAction) =
            TypedViewInteraction(type, viewInteraction.perform(*viewActions))

    /**
     * Performs given [viewAction] on the view under test.
     *
     * @param viewAction Actions to be performed on the view under test.
     *
     * @return [TypedViewInteraction] to chain operations.
     */
    fun perform(viewAction: T.() -> Unit) =
            TypedViewInteraction(type, viewInteraction.perform(TypedViewAction(type = type, action = viewAction)))
}
