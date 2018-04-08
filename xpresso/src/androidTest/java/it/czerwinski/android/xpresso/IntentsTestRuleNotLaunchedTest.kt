package it.czerwinski.android.xpresso

import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import it.czerwinski.android.xpresso.mock.activity.EmptyActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class IntentsTestRuleNotLaunchedTest {

    @Rule
    @JvmField
    val activityTestRule = intentsTestRule<EmptyActivity>(launchActivity = false)

    @Test
    @Throws(Exception::class)
    fun shouldNotLaunchActivity() {
        assertNull(activityTestRule.activity)
    }

    @Test
    @Throws(Exception::class)
    fun launchActivityShouldLaunchEmptyActivity() {
        activityTestRule.launchActivity()

        assertNotNull(activityTestRule.activity)
        assertTrue(activityTestRule.activity is EmptyActivity)
    }
}
