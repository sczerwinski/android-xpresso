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
import org.hamcrest.Matcher

/**
 * Creates a simple [RecyclerViewInteraction].
 *
 * @return [RecyclerViewInteraction].
 */
fun onRecyclerView(): RecyclerViewInteraction = RecyclerViewInteraction(emptyList())

/**
 * Creates a simple [RecyclerViewInteraction].
 *
 * @param viewMatchers Matchers used to select the RecyclerView.
 *
 * @return [RecyclerViewInteraction].
 */
fun onRecyclerView(vararg viewMatchers: Matcher<View>): RecyclerViewInteraction =
    RecyclerViewInteraction(viewMatchers.toList())
