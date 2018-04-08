package it.czerwinski.android.xpresso

import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.anyIntent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import it.czerwinski.android.xpresso.mock.activity.EmptyActivity
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class IntendedTest {

    @Rule
    @JvmField
    val activityTestRule = intentsTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun intendedShouldSucceedWhenActivityMatching() {
        intending(anyIntent())
                .respondWith(Instrumentation.ActivityResult(0, Intent()))

        onView(withId(R.id.button))
                .perform(click())

        intended<EmptyActivity>()
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun intendedShouldFailWhenActivityNotMatching() {
        intending(anyIntent())
                .respondWith(Instrumentation.ActivityResult(0, Intent()))

        onView(withId(R.id.button))
                .perform(click())

        intended<IntentsActivity>()
    }

    @Test
    @Throws(Exception::class)
    fun intendedShouldSucceedWhenIntentMatching() {
        intending(anyIntent())
                .respondWith(Instrumentation.ActivityResult(0, Intent()))

        onView(withId(R.id.button))
                .perform(click())

        intended<EmptyActivity>(hasExtra("extra", "start"))
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun intendedShouldFailWhenIntentNotMatching() {
        intending(anyIntent())
                .respondWith(Instrumentation.ActivityResult(0, Intent()))

        onView(withId(R.id.button))
                .perform(click())

        intended<EmptyActivity>(hasExtra("extra", "invalid value"))
    }
}
