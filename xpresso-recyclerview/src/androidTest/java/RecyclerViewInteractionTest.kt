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

import android.widget.TextView
import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.launchTestActivity
import it.czerwinski.android.xpresso.on
import it.czerwinski.android.xpresso.recyclerview.test.TestRecyclerViewActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RecyclerViewInteractionTest {

    @Test(expected = AmbiguousViewMatcherException::class)
    fun testCheckAmbiguous() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView()
            .check(isDisplayed())
    }

    @Test
    fun testCheckViewAssertionSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.selectedValue))
            .check(doesNotExist())
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewAssertionFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(doesNotExist())
    }

    @Test
    fun testCheckViewMatchersSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(isDisplayed())
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewMatchersFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check(withText("Yellow"))
    }

    @Test
    fun testCheckLambdaSuccess() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .check { result ->
                assertEquals(3, result.get().adapter?.itemCount)
            }
    }

    @Test
    fun testCheckLambdaFailure() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.selectedValue))
            .check { result ->
                assertTrue(result.isFailure)
            }
    }

    @Test
    fun testPerformViewActions() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .perform(swipeUp())
    }

    @Test
    fun testPerformLambda() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .perform { adapter = null }
        on<TextView>(withText("Red"))
            .check(doesNotExist())
    }

    @Test
    fun testPerformLambdaWithDescription() {
        launchTestActivity<TestRecyclerViewActivity>()
        onRecyclerView(withId(R.id.textList))
            .perform(description = "remove adapter") { adapter = null }
        on<TextView>(withText("Red"))
            .check(doesNotExist())
    }
}
