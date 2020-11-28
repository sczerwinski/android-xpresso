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
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import it.czerwinski.android.xpresso.test.TestActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TypedViewActionTest {

    @Test
    fun testPerformTypedViewAction() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .perform(
                viewAction<EditText> { setText(TEST_TEXT) }
            )
            .check(ViewMatchers.withText(TEST_TEXT))
    }

    @Test
    fun testPerformTypedViewActionWithDescription() {
        launchTestActivity<TestActivity>()
        on<EditText>()
            .perform(
                viewAction<EditText>(description = "set text") { setText(TEST_TEXT) }
            )
            .check(ViewMatchers.withText(TEST_TEXT))
    }

    companion object {
        private const val TEST_TEXT = "Welcome"
    }
}