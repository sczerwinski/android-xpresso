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
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher

/**
 * Interface to perform actions or asserts on Views of type [V].
 *
 * @param V Type of the view under test.
 * @param T Type of the interaction.
 */
interface TypedInteraction<out V : View, out T : TypedInteraction<V, T>> {

    /**
     * Checks given [viewAssertion] on the view under test.
     *
     * @param viewAssertion Assertion to be performed on the view under test.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun check(viewAssertion: ViewAssertion): T

    /**
     * Checks if the given [viewMatchers] match the view under test.
     *
     * @param viewMatchers Matchers to be tested against the view under test.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun check(vararg viewMatchers: Matcher<View>): T

    /**
     * Checks given [viewAssertion] on the view under test.
     *
     * @param viewAssertion Assertion to be performed on the view under test.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun check(viewAssertion: (Try<V>) -> Unit): T

    /**
     * Performs given [viewActions] on the view under test.
     *
     * @param viewActions Actions to be performed on the view under test.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun perform(vararg viewActions: ViewAction): T

    /**
     * Performs given [viewAction] on the view under test.
     *
     * @param viewAction Actions to be performed on the view under test.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun perform(viewAction: V.() -> Unit): T

    /**
     * Performs given [viewAction] on the view under test.
     *
     * @param viewAction Actions to be performed on the view under test.
     * @param description Description of this action.
     *
     * @return [TypedInteraction] of type [T] to chain operations.
     */
    fun perform(description: String, viewAction: V.() -> Unit): T
}
