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

import android.widget.Button
import android.widget.TextView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import it.czerwinski.android.xpresso.launchTestActivity
import it.czerwinski.android.xpresso.on
import it.czerwinski.android.xpresso.recyclerview.test.TestRecyclerViewActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.assertEquals
import org.junit.Test

class RecyclerViewItemTypedViewInteractionTest {

    @Test
    fun testCheckViewAssertionSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                on<Button>()
                    .check(doesNotExist())
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewAssertionFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 0) {
                on<Button>()
                    .check(doesNotExist())
            }
    }

    @Test
    fun testCheckViewMatchersSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                on<TextView>()
                    .check(isDisplayed())
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewMatchersFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                on<TextView>()
                    .check(withText("Purple"))
            }
    }

    @Test
    fun testCheckLambdaSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                on<TextView>()
                    .check { result ->
                        assertEquals("Red", result.get().text)
                    }
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckLambdaFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 1) {
                on<TextView>()
                    .check { result ->
                        assertEquals("Red", result.get().text)
                    }
            }
    }

    @Test
    fun testPerformViewActions() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 2) {
                on<Button>()
                    .perform(click())
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Yellow"))
    }

    @Test
    fun testPerformLambda() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 2) {
                on<Button>()
                    .perform {
                        performClick()
                    }
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Yellow"))
    }

    @Test
    fun testPerformLambdaWithDescription() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 2) {
                on<Button>()
                    .perform(description = "manual click") {
                        performClick()
                    }
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Yellow"))
    }
}
