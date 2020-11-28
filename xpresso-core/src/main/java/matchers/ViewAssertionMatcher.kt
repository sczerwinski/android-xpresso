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

package it.czerwinski.android.xpresso.matchers

import android.view.View
import androidx.test.espresso.ViewAssertion
import it.czerwinski.kotlin.util.Try
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Matcher wrapping a [viewAssertion].
 *
 * @param viewAssertion [ViewAssertion] that must be met for the view to match.
 */
class ViewAssertionMatcher(
    private val viewAssertion: ViewAssertion
) : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("meets view assertion")
    }

    override fun matchesSafely(view: View): Boolean =
        Try { viewAssertion.check(view, null) }.isSuccess
}
