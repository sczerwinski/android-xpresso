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
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import it.czerwinski.kotlin.util.Failure
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * Provides a typed interface to perform actions or asserts on Views of type [V].
 *
 * @param V Type of the view under test.
 *
 * @param viewType Type of the view under test.
 * @param viewInteraction Wrapped untyped [ViewInteraction].
 */
class TypedViewInteraction<out V : View>(
    private val viewType: Class<V>,
    private val viewInteraction: ViewInteraction
) : TypedInteraction<V, TypedViewInteraction<V>> {

    override fun check(viewAssertion: ViewAssertion): TypedViewInteraction<V> =
        TypedViewInteraction(viewType, viewInteraction.check(viewAssertion))

    override fun check(vararg viewMatchers: Matcher<View>): TypedViewInteraction<V> =
        TypedViewInteraction(
            viewType,
            viewInteraction.check(ViewAssertions.matches(Matchers.allOf(*viewMatchers)))
        )

    override fun check(viewAssertion: (Try<V>) -> Unit): TypedViewInteraction<V> =
        TypedViewInteraction(viewType, viewInteraction.check { view, noMatchingViewException ->
            (view?.let { Try { viewType.cast(view) } }
                ?: Failure(noMatchingViewException))
                .also { viewAssertion(it) }
        })

    override fun perform(vararg viewActions: ViewAction): TypedViewInteraction<V> =
        TypedViewInteraction(viewType, viewInteraction.perform(*viewActions))

    override fun perform(viewAction: V.() -> Unit): TypedViewInteraction<V> =
        TypedViewInteraction(
            viewType,
            viewInteraction.perform(TypedViewAction(viewType, action = viewAction))
        )

    override fun perform(description: String, viewAction: V.() -> Unit): TypedViewInteraction<V> =
        TypedViewInteraction(
            viewType,
            viewInteraction.perform(TypedViewAction(viewType, description, viewAction))
        )
}
