package it.czerwinski.android.xpresso

import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import it.czerwinski.android.xpresso.mock.activity.EmptyActivity
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class IntentsTestRuleLaunchedTest {

    @Rule
    @JvmField
    val activityTestRule = intentsTestRule<EmptyActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun shouldLaunchEmptyActivity() {
        assertNotNull(activityTestRule.activity)
        assertTrue(activityTestRule.activity is EmptyActivity)
    }
}
