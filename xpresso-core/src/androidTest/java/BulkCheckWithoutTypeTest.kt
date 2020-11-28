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

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.test.TestActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class BulkCheckWithoutTypeTest {

    @Test
    fun testAllViewAssertionSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.textInvalid1))
            on<Button>(withText(R.string.textInvalid2))
            on<ImageView>()
        }.all(doesNotExist())
    }

    @Test(expected = AssertionFailedError::class)
    fun testAllViewAssertionFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.textInvalid1))
            on<Button>(withText(R.string.textInvalid2))
            on<EditText>()
        }.all(doesNotExist())
    }

    @Test
    fun testAllViewMatchersSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<TextView>(withText(R.string.text2))
            on<Button>(withText(R.string.buttonShow))
            on<Button>(withText(R.string.buttonHide))
        }.all(isDisplayed())
    }

    @Test(expected = AssertionFailedError::class)
    fun testAllViewMatchersFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<TextView>(withText(R.string.text2))
            on<Button>(withText(R.string.buttonShow))
            on<Button>(withText(R.string.buttonHide))
        }.all(withText(R.string.text1))
    }

    @Test
    fun testAllLambdaSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<TextView>(withText(R.string.text2))
            on<Button>(withText(R.string.buttonShow))
            on<Button>(withText(R.string.buttonHide))
        }.all { result ->
            assertNotEquals(R.id.textInput, result.get().id)
        }
    }

    @Test
    fun testAllLambdaFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.textInvalid1))
            on<Button>(withText(R.string.textInvalid2))
        }.all { result -> assertTrue(result.isFailure) }
    }

    @Test
    fun testAnyViewAssertionSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.textInvalid1))
            on<Button>(withText(R.string.textInvalid2))
            on<EditText>()
        }.any(doesNotExist())
    }

    @Test(expected = AssertionError::class)
    fun testAnyViewAssertionFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<Button>(withText(R.string.buttonShow))
        }.any(doesNotExist())
    }

    @Test
    fun testAnyViewMatchersSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<Button>(withText(R.string.buttonShow))
        }.any(withText(R.string.text1))
    }

    @Test(expected = AssertionError::class)
    fun testAnyViewMatchersFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text2))
            on<Button>(withText(R.string.buttonShow))
        }.any(withText(R.string.text1))
    }

    @Test
    fun testAnyLambdaSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.text1))
            on<TextView>(withText(R.string.text2))
            on<TextView>(withText(R.string.text3))
            on<Button>(withText(R.string.buttonShow))
            on<Button>(withText(R.string.buttonHide))
        }.any { result ->
            assertEquals(R.id.text1, result.get().id)
        }
    }

    @Test
    fun testAnyLambdaFailure() {
        launchTestActivity<TestActivity>()
        bulkCheck {
            on<TextView>(withText(R.string.textInvalid1))
            on<Button>(withText(R.string.text1))
            on<ImageView>()
        }.any { result -> assertTrue(result.isFailure) }
    }
}
