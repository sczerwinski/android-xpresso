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
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Matcher testing whether item view of a ViewHolder in the [RecyclerView] at the given [position]
 * matches given [itemViewMatcher].
 *
 * @param VH Type of the ViewHolder under test.
 *
 * @param position ViewHolder position inside [RecyclerView].
 * @param itemViewMatcher Matcher for testing the `itemView`.
 * @param viewHolderType Type of the ViewHolder under test.
 */
class ViewHolderItemViewMatcher<VH : RecyclerView.ViewHolder>(
    private val position: Int,
    private val viewHolderType: Class<VH>,
    private val itemViewMatcher: Matcher<View>
) : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText(
            "has item of type ${viewHolderType.name} at position $position: "
        )
        itemViewMatcher.describeTo(description)
    }

    override fun matchesSafely(recyclerView: RecyclerView?): Boolean {
        val viewHolder = recyclerView?.findViewHolderForAdapterPosition(position) ?: return false
        return viewHolderType.isAssignableFrom(viewHolder.javaClass) &&
                itemViewMatcher.matches(viewHolder.itemView)
    }
}