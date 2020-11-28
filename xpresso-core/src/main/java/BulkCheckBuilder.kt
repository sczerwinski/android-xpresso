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
import androidx.test.espresso.Espresso
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class BulkCheckBuilder<BV : View>(private val type: Class<BV>) {

    val viewInteractions = mutableListOf<TypedViewInteraction<BV>>()

    /**
     * Adds the only View of type [V] to this bulk check.
     *
     * @param V Type of the View under test.
     */
    inline fun <reified V : BV> on() {
        viewInteractions.add(
            TypedViewInteraction(
                V::class.java,
                Espresso.onView(Matchers.instanceOf(V::class.java))
            )
        )
    }

    /**
     * Adds the only View of type [V] matching all provided [viewMatchers] to this bulk check.
     *
     * @param V Type of the View under test.
     *
     * @param viewMatchers Matchers used to select the View.
     */
    inline fun <reified V : BV> on(vararg viewMatchers: Matcher<View>) {
        viewInteractions.add(
            TypedViewInteraction(
                V::class.java,
                Espresso.onView(Matchers.allOf(Matchers.instanceOf(V::class.java), *viewMatchers))
            )
        )
    }

    /**
     * Adds the only View of type [BV] matching all provided [viewMatchers] to this bulk check.
     *
     * @param viewMatchers Matchers used to select the View.
     */
    fun onView(vararg viewMatchers: Matcher<View>) {
        viewInteractions.add(
            TypedViewInteraction(
                type,
                Espresso.onView(Matchers.allOf(Matchers.instanceOf(type), *viewMatchers))
            )
        )
    }

    fun build(): BulkCheck<BV> =
        BulkCheck(viewInteractions)
}

/**
 * Creates a new bulk check for Views of type [BV].
 *
 * @param BV Type of the Views under test.
 *
 * @param init Function initializing the new bulk check.
 *
 * @return New bulk check for Views of type [BV].
 */
inline fun <reified BV : View> bulkCheckFor(init: BulkCheckBuilder<BV>.() -> Unit): BulkCheck<BV> =
    BulkCheckBuilder(BV::class.java)
        .apply(init)
        .build()

/**
 * Creates a new bulk check for any Views.
 *
 * @param init Function initializing the new bulk check.
 *
 * @return New bulk check for any Views.
 */
fun bulkCheck(init: BulkCheckBuilder<View>.() -> Unit): BulkCheck<View> =
    bulkCheckFor(init)
