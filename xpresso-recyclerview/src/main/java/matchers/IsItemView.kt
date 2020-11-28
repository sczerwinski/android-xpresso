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

package it.czerwinski.android.xpresso.recyclerview.matchers

import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Matcher checking whether the [View] is an item view of a ViewHolder at the given [position]
 * inside a [RecyclerView] matching [recyclerViewMatcher].
 *
 * @param recyclerViewMatcher Matcher for checking a [RecyclerView].
 * @param position Position inside [RecyclerView].
 */
class IsItemView(
    private val recyclerViewMatcher: Matcher<RecyclerView>,
    private val position: Int
) : TypeSafeMatcher<View>() {

    private val View.ancestorsSequence: Sequence<ViewParent>
        get() = generateSequence(seed = parent) { it.parent }

    override fun describeTo(description: Description?) {
        description?.appendText("in item view at position $position in RecyclerView: ")
        recyclerViewMatcher.describeTo(description)
    }

    override fun matchesSafely(view: View): Boolean =
        view.ancestorsSequence
            .filterIsInstance<RecyclerView>()
            .filter { recyclerView -> recyclerViewMatcher.matches(recyclerView) }
            .firstOrNull()
            ?.findViewHolderForAdapterPosition(position)?.itemView == view
}
