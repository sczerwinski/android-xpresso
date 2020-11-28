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

import android.widget.TextView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.test.TestActivity
import junit.framework.AssertionFailedError
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class BulkCheckWithTypeTest {

    @Test
    fun testAllViewAssertionSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.textInvalid1))
            onView(withText(R.string.textInvalid2))
        }.all(doesNotExist())
    }

    @Test(expected = AssertionFailedError::class)
    fun testAllViewAssertionFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.textInvalid1))
            onView(withText(R.string.textInvalid2))
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.all(doesNotExist())
    }

    @Test
    fun testAllViewMatchersSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.all(isDisplayed())
    }

    @Test(expected = AssertionFailedError::class)
    fun testAllViewMatchersFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.all(withText(R.string.text1))
    }

    @Test
    fun testAllLambdaSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.all { result ->
            assertTrue(result.get().text.isNotBlank())
        }
    }

    @Test
    fun testAllLambdaFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.textInvalid1))
            onView(withText(R.string.textInvalid2))
        }.all { result -> assertTrue(result.isFailure) }
    }

    @Test
    fun testAnyViewAssertionSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.textInvalid1))
            onView(withText(R.string.text3))
        }.any(doesNotExist())
    }

    @Test(expected = AssertionError::class)
    fun testAnyViewAssertionFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.any(doesNotExist())
    }

    @Test
    fun testAnyViewMatchersSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.any(withText(R.string.text1))
    }

    @Test(expected = AssertionError::class)
    fun testAnyViewMatchersFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.any(withText(R.string.textInvalid1))
    }

    @Test
    fun testAnyLambdaSuccess() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.any { result ->
            assertEquals(R.id.text1, result.get().id)
        }
    }

    @Test
    fun testAnyLambdaFailure() {
        launchTestActivity<TestActivity>()
        bulkCheckFor<TextView> {
            onView(withText(R.string.textInvalid1))
            onView(withText(R.string.text1))
            onView(withText(R.string.text2))
            onView(withText(R.string.text3))
        }.any { result -> assertTrue(result.isFailure) }
    }
}
