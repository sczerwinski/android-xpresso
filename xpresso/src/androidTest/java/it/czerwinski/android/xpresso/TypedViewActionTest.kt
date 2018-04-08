package it.czerwinski.android.xpresso

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.PerformException
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.TextView
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TypedViewActionTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun shouldPerformCustomTypedAction() {
        onView(withId(R.id.text))
                .perform(viewAction<TextView> {
                    text = "some custom text"
                })
        onView(withId(R.id.text))
                .check(matches(withText("some custom text")))
    }

    @Test(expected = PerformException::class)
    @Throws(Exception::class)
    fun shouldThrowExceptionWhenTypeNotMatching() {
        onView(withId(R.id.text))
                .perform(viewAction<Button>("action intended to fail") {})
    }
}
