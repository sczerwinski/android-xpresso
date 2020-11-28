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
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import it.czerwinski.android.xpresso.TypedInteraction
import it.czerwinski.android.xpresso.TypedViewInteraction
import it.czerwinski.android.xpresso.on
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Matcher

/**
 * Provides a typed interface to perform actions or assertions on a [RecyclerView].
 *
 * @param matchers Matchers used to select the [RecyclerView].
 */
class RecyclerViewInteraction(
    internal val matchers: List<Matcher<View>>
) : TypedInteraction<RecyclerView, RecyclerViewInteraction> {

    @Suppress("SpreadOperator")
    private val typedViewInteraction: TypedViewInteraction<RecyclerView> =
        on(*matchers.toTypedArray())

    override fun check(viewAssertion: ViewAssertion): RecyclerViewInteraction {
        typedViewInteraction.check(viewAssertion)
        return this
    }

    override fun check(vararg viewMatchers: Matcher<View>): RecyclerViewInteraction {
        typedViewInteraction.check(*viewMatchers)
        return this
    }

    override fun check(viewAssertion: (Try<RecyclerView>) -> Unit): RecyclerViewInteraction {
        typedViewInteraction.check(viewAssertion)
        return this
    }

    override fun perform(vararg viewActions: ViewAction): RecyclerViewInteraction {
        typedViewInteraction.perform(*viewActions)
        return this
    }

    override fun perform(viewAction: RecyclerView.() -> Unit): RecyclerViewInteraction {
        typedViewInteraction.perform(viewAction)
        return this
    }

    override fun perform(
        description: String,
        viewAction: RecyclerView.() -> Unit
    ): RecyclerViewInteraction {
        typedViewInteraction.perform(description, viewAction)
        return this
    }

    /**
     * Creates a [RecyclerViewItemInteraction] for a given ViewHolder of type [VH]
     * at the given [position].
     *
     * @param VH Type of the ViewHolder under test.
     *
     * @param position ViewHolder position inside [RecyclerView].
     * @param viewHolderType Type of the ViewHolder under test.
     * @param block Block of instructions executed on the [RecyclerViewItemInteraction].
     *
     * @return [RecyclerViewItemInteraction] for the given [position].
     */
    fun <VH : RecyclerView.ViewHolder> onItem(
        position: Int,
        viewHolderType: Class<VH>,
        block: RecyclerViewItemInteraction<VH>.() -> Unit
    ): RecyclerViewInteraction {
        RecyclerViewItemInteraction(recyclerViewInteraction = this, position, viewHolderType)
            .apply(block)
        return this
    }

    /**
     * Creates a [RecyclerViewItemInteraction] for a given ViewHolder of type [VH]
     * at the given [position].
     *
     * @param VH Type of the ViewHolder under test.
     *
     * @param position ViewHolder position inside [RecyclerView].
     * @param block Block of instructions executed on the [RecyclerViewItemInteraction].
     *
     * @return [RecyclerViewItemInteraction] for the given [position].
     */
    inline fun <reified VH : RecyclerView.ViewHolder> onItem(
        position: Int,
        noinline block: RecyclerViewItemInteraction<VH>.() -> Unit
    ): RecyclerViewInteraction =
        onItem(position, VH::class.java, block)
}
