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
import it.czerwinski.android.xpresso.internals.onView
import org.hamcrest.Matcher

/**
 * Creates a [TypedViewInteraction] for a given View of type [V].
 *
 * @param T Type of the view under test.
 *
 * @return [TypedViewInteraction] for the given View.
 */
inline fun <reified V : View> on(): TypedViewInteraction<V> =
    TypedViewInteraction(V::class.java, onView(V::class.java))

/**
 * Creates a [TypedViewInteraction] for a given View of type [V].
 *
 * @param V Type of the view under test.
 *
 * @param viewMatchers Matchers used to select the view.
 *
 * @return [TypedViewInteraction] for the given View.
 */
inline fun <reified V : View> on(vararg viewMatchers: Matcher<View>): TypedViewInteraction<V> =
    TypedViewInteraction(V::class.java, onView(V::class.java, *viewMatchers))
