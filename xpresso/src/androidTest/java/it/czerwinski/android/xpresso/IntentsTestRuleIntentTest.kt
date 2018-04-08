package it.czerwinski.android.xpresso

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class IntentsTestRuleIntentTest {

    @Rule
    @JvmField
    val activityTestRule = intentsTestRule<IntentsActivity>(launchActivity = false)

    @Test
    @Throws(Exception::class)
    fun launchActivityShouldLaunchIntentsActivityWithoutExtra() {
        activityTestRule.launchActivity()

        assertNotNull(activityTestRule.activity)
        assertTrue(activityTestRule.activity is IntentsActivity)

        onView(withId(R.id.text))
                .check(matches(withText("")))
    }

    @Test
    @Throws(Exception::class)
    fun launchActivityShouldLaunchIntentsActivityWithExtra() {
        activityTestRule.launchActivity {
            putExtra("extra", "some text")
        }

        assertNotNull(activityTestRule.activity)
        assertTrue(activityTestRule.activity is IntentsActivity)

        onView(withId(R.id.text))
                .check(matches(withText("some text")))
    }
}
