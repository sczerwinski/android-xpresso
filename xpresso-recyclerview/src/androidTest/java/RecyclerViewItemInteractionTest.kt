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
import android.widget.FrameLayout
import android.widget.TextView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.launchTestActivity
import it.czerwinski.android.xpresso.on
import it.czerwinski.android.xpresso.recyclerview.test.TestRecyclerViewActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RecyclerViewItemInteractionTest {

    @Test
    fun testCheckViewAssertionSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                check(matches(isDisplayed()))
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewAssertionFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                check(doesNotExist())
            }
    }

    @Test
    fun testCheckViewMatchersSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                check(isDisplayed())
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewMatchersFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                check(isChecked())
            }
    }

    @Test
    fun testCheckLambdaSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 0) {
                check { result ->
                    assertTrue(result.get() is FrameLayout)
                }
            }
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckLambdaFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .onItem<TestRecyclerViewActivity.TextAdapter.ViewHolder>(position = 3) {
                check { }
            }
    }

    @Test
    fun testPerformViewActions() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 1) {
                perform(click())
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Magenta"))
    }

    @Test
    fun testPerformLambda() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 1) {
                perform {
                    findViewById<Button>(R.id.button).performClick()
                }
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Magenta"))
    }

    @Test
    fun testPerformLambdaWithDescription() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.buttonsList))
            .onItem<TestRecyclerViewActivity.ButtonAdapter.ViewHolder>(position = 1) {
                perform(description = "manual click") {
                    findViewById<Button>(R.id.button).performClick()
                }
            }
        on<TextView>(withId(R.id.selectedValue))
            .check(withText("Magenta"))
    }
}
