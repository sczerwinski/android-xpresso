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

package it.czerwinski.android.xpresso.internals

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun <V : View> onView(
    viewType: Class<V>
): ViewInteraction =
    Espresso.onView(Matchers.instanceOf(viewType))

fun <V : View> onView(
    viewType: Class<V>,
    vararg viewMatchers: Matcher<View>
): ViewInteraction =
    Espresso.onView(
        Matchers.allOf(
            Matchers.instanceOf(viewType),
            *viewMatchers
        )
    )