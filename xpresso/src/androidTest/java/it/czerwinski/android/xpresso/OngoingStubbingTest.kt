package it.czerwinski.android.xpresso

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class OngoingStubbingTest {

    @Rule
    @JvmField
    val activityTestRule = intentsTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun shouldRespondWithCancel() {
        intending(anyIntent())
                .respondWithCancel()

        onView(withId(R.id.button))
                .perform(click())

        onView(withId(R.id.text))
                .check(matches(withText("0|")))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRespondWithCustomResonseCode() {
        intending(anyIntent())
                .respondWith(256)

        onView(withId(R.id.button))
                .perform(click())

        onView(withId(R.id.text))
                .check(matches(withText("256|")))
    }

    @Test
    @Throws(Exception::class)
    fun shouldRespondWithCustomResonseCodeAndIntent() {
        intending(anyIntent())
                .respondWith(256) {
                    putExtra("extra", "response")
                }

        onView(withId(R.id.button))
                .perform(click())

        onView(withId(R.id.text))
                .check(matches(withText("256|response")))
    }
}