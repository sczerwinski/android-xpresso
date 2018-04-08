package it.czerwinski.android.xpresso

import android.support.test.espresso.ViewAssertion
import android.view.View
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher
import org.junit.Assert.assertTrue

/**
 * Bulk check for multiple Views of type [T].
 *
 * @param T Type of the Views under test.
 *
 * @param viewInteractions [TypedViewInteraction]s for Views under test.
 */
class BulkCheck<out T : View>(private val viewInteractions: List<TypedViewInteraction<out T>>) {

    /**
     * Checks given [viewAssertion] on all Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(viewAssertion: ViewAssertion): BulkCheck<T> = apply {
        viewInteractions.forEach { it.check(viewAssertion) }
    }

    /**
     * Checks if the given [viewMatchers] match all Views under test.
     *
     * @param viewMatchers Matchers to be tested against the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(vararg viewMatchers: Matcher<View>): BulkCheck<T> = apply {
        viewInteractions.forEach { it.check(*viewMatchers) }
    }

    /**
     * Checks given [viewAssertion] on all Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(viewAssertion: (Try<T>) -> Unit): BulkCheck<T> = apply {
        viewInteractions.forEach { it.check(viewAssertion) }
    }

    /**
     * Checks that given [viewAssertion] is successful on any of the Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(viewAssertion: ViewAssertion): BulkCheck<T> = apply {
        assertTrue(viewInteractions.any { Try { it.check(viewAssertion) }.isSuccess })
    }

    /**
     * Checks if the given [viewMatchers] match any of the Views under test.
     *
     * @param viewMatchers Matchers to be tested against the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(vararg viewMatchers: Matcher<View>): BulkCheck<T> = apply {
        assertTrue(viewInteractions.any { Try { it.check(*viewMatchers) }.isSuccess })
    }

    /**
     * Checks that given [viewAssertion] is successful on any of the Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(viewAssertion: (Try<T>) -> Unit): BulkCheck<T> = apply {
        assertTrue(viewInteractions.any { Try { it.check(viewAssertion) }.isSuccess })
    }
}
