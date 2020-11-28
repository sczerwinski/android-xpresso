/*
 * Copyright 2020 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package it.czerwinski.android.xpresso

import android.view.View
import androidx.test.espresso.ViewAssertion
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher
import org.junit.Assert

/**
 * Bulk check for multiple Views of type [V].
 *
 * @param V Type of the Views under test.
 *
 * @param viewInteractions [TypedViewInteraction]s for Views under test.
 */
class BulkCheck<out V : View>(private val viewInteractions: List<TypedViewInteraction<out V>>) {

    /**
     * Checks given [viewAssertion] on all Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(viewAssertion: ViewAssertion): BulkCheck<V> = apply {
        viewInteractions.forEach { it.check(viewAssertion) }
    }

    /**
     * Checks if the given [viewMatchers] match all Views under test.
     *
     * @param viewMatchers Matchers to be tested against the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(vararg viewMatchers: Matcher<View>): BulkCheck<V> = apply {
        viewInteractions.forEach { it.check(*viewMatchers) }
    }

    /**
     * Checks given [viewAssertion] on all Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun all(viewAssertion: (Try<V>) -> Unit): BulkCheck<V> = apply {
        viewInteractions.forEach { it.check(viewAssertion) }
    }

    /**
     * Checks that given [viewAssertion] is successful on any of the Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(viewAssertion: ViewAssertion): BulkCheck<V> = apply {
        Assert.assertTrue(viewInteractions.any { Try { it.check(viewAssertion) }.isSuccess })
    }

    /**
     * Checks if the given [viewMatchers] match any of the Views under test.
     *
     * @param viewMatchers Matchers to be tested against the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(vararg viewMatchers: Matcher<View>): BulkCheck<V> = apply {
        Assert.assertTrue(viewInteractions.any { Try { it.check(*viewMatchers) }.isSuccess })
    }

    /**
     * Checks that given [viewAssertion] is successful on any of the Views under test.
     *
     * @param viewAssertion Assertion to be performed on the Views under test.
     *
     * @return This [BulkCheck] to chain operations.
     */
    fun any(viewAssertion: (Try<V>) -> Unit): BulkCheck<V> = apply {
        Assert.assertTrue(viewInteractions.any { Try { it.check(viewAssertion) }.isSuccess })
    }
}
