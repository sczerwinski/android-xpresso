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

/**
 * Creates a custom interaction on a given View element of type [V].
 *
 * @param V Type of the View under test.
 *
 * @param action Action to be performed on the View under test.
 */
inline fun <reified V : View> viewAction(
    noinline action: V.() -> Unit
): ViewAction =
    TypedViewAction(
        viewType = V::class.java,
        action = action
    )

/**
 * Creates a custom interaction on a given View element of type [V].
 *
 * @param V Type of the View under test.
 *
 * @param description Description of this action.
 * @param action Action to be performed on the View under test.
 */
inline fun <reified V : View> viewAction(
    description: String,
    noinline action: V.() -> Unit
): ViewAction =
    TypedViewAction(
        viewType = V::class.java,
        description = description,
        action = action
    )
