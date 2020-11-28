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

package it.czerwinski.android.xpresso.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import it.czerwinski.android.xpresso.TypedInteraction
import it.czerwinski.android.xpresso.TypedViewInteraction
import it.czerwinski.android.xpresso.matchers.ViewAssertionMatcher
import it.czerwinski.android.xpresso.recyclerview.matchers.ViewHolderItemViewMatcher
import it.czerwinski.android.xpresso.recyclerview.matchers.IsItemView
import it.czerwinski.kotlin.util.Failure
import it.czerwinski.kotlin.util.Success
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Assert.fail

/**
 * Provides a typed interface to perform actions or assertions on a ViewHolder item view.
 *
 * @param VH Type of the ViewHolder under test.
 *
 * @param recyclerViewInteraction Interface for interactions with the [RecyclerView].
 * @param position ViewHolder position inside [RecyclerView].
 * @param viewHolderType Type of the ViewHolder under test.
 */
class RecyclerViewItemInteraction<VH : RecyclerView.ViewHolder>(
    private val recyclerViewInteraction: RecyclerViewInteraction,
    private val position: Int,
    private val viewHolderType: Class<VH>
) : TypedInteraction<View, RecyclerViewItemInteraction<VH>> {

    private val itemViewHolderViewMatcher =
        IsItemView(allOf(recyclerViewInteraction.matchers), position)

    override fun check(viewAssertion: ViewAssertion): RecyclerViewItemInteraction<VH> =
        check(ViewAssertionMatcher(viewAssertion))

    override fun check(vararg viewMatchers: Matcher<View>): RecyclerViewItemInteraction<VH> {
        recyclerViewInteraction.check(
            ViewHolderItemViewMatcher(
                position = position,
                viewHolderType = viewHolderType,
                itemViewMatcher = allOf(*viewMatchers)
            )
        )
        return this
    }

    override fun check(viewAssertion: (Try<View>) -> Unit): RecyclerViewItemInteraction<VH> =
        check(
            ViewAssertionMatcher { view, noViewFoundException ->
                (view?.let(::Success) ?: Failure(noViewFoundException))
                    .also { viewAssertion(it) }
            }
        )

    override fun perform(vararg viewActions: ViewAction): RecyclerViewItemInteraction<VH> {
        @Suppress("SpreadOperator")
        recyclerViewInteraction.perform(
            *viewActions.map { action ->
                RecyclerViewActions.actionOnItemAtPosition<VH>(position, action)
            }.toTypedArray()
        )
        return this
    }

    override fun perform(viewAction: View.() -> Unit): RecyclerViewItemInteraction<VH> {
        recyclerViewInteraction.perform {
            val itemView = viewHolderType.cast(findViewHolderForAdapterPosition(position))?.itemView
            if (itemView == null) {
                fail("ViewHolder of type ${viewHolderType.name} not found at position $position")
            } else {
                itemView.viewAction()
            }
        }
        return this
    }

    override fun perform(
        description: String,
        viewAction: View.() -> Unit
    ): RecyclerViewItemInteraction<VH> {
        recyclerViewInteraction.perform(description) {
            val itemView = viewHolderType.cast(findViewHolderForAdapterPosition(position))?.itemView
            if (itemView == null) {
                fail("ViewHolder of type ${viewHolderType.name} not found at position $position")
            } else {
                itemView.viewAction()
            }
        }
        return this
    }

    fun <V : View> onView(
        viewType: Class<V>,
        vararg viewMatchers: Matcher<View>
    ): TypedViewInteraction<V> = TypedViewInteraction(
        viewType,
        Espresso.onView(
            allOf(
                Matchers.instanceOf(viewType),
                ViewMatchers.isDescendantOfA(itemViewHolderViewMatcher),
                *viewMatchers
            )
        )
    )

    /**
     * Creates a [TypedViewInteraction] for a given View of type [V] inside this ViewHolder.
     *
     * @param V Type of the view under test.
     *
     * @return [TypedViewInteraction] for the given View.
     */
    inline fun <reified V : View> on(): TypedViewInteraction<V> =
        onView(V::class.java)

    /**
     * Creates a [TypedViewInteraction] for a given View of type [V] inside this ViewHolder.
     *
     * @param V Type of the view under test.
     *
     * @param viewMatchers Matchers used to select the view.
     *
     * @return [TypedViewInteraction] for the given View.
     */
    inline fun <reified V : View> on(vararg viewMatchers: Matcher<View>): TypedViewInteraction<V> =
        onView(V::class.java, *viewMatchers)
}
