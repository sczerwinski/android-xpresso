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

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.test.TestActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TypedViewInteractionTest {

    @Test
    fun testCheckViewAssertionSuccess() {
        launchTestActivity<TestActivity>()
        on<ImageView>()
            .check(doesNotExist())
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewAssertionFailure() {
        launchTestActivity<TestActivity>()
        on<TextView>(withText(R.string.text1))
            .check(doesNotExist())
    }

    @Test
    fun testCheckViewMatchersSuccess() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .check(withId(R.id.textInput), isDisplayed())
    }

    @Test(expected = AssertionFailedError::class)
    fun testCheckViewMatchersFailure() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .check(withText(TEST_TEXT))
    }

    @Test
    fun testCheckLambdaSuccess() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .check { result -> assertEquals(R.id.textInput, result.get().id) }
    }

    @Test
    fun testCheckLambdaFailure() {
        launchTestActivity<TestActivity>()
        on<ImageView>()
            .check { result -> assertTrue(result.isFailure) }
    }

    @Test
    fun testPerformViewActions() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .perform(
                clearText(),
                typeText(TEST_TEXT),
                closeSoftKeyboard()
            )
            .check(withText(TEST_TEXT))
    }

    @Test
    fun testPerformLambda() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .perform { setText(TEST_TEXT) }
            .check(withText(TEST_TEXT))
    }

    @Test
    fun testPerformLambdaWithDescription() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .perform(description = "set text") { setText(TEST_TEXT) }
            .check(withText(TEST_TEXT))
    }

    companion object {
        private const val TEST_TEXT = "Welcome"
    }
}
