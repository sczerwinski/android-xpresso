package it.czerwinski.android.xpresso

import android.support.test.espresso.AmbiguousViewMatcherException
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class OnTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun onShouldFindViewByType() {
        on<Button>()
                .check(matches(isDisplayed()))
    }

    @Test
    @Throws(Exception::class)
    fun onShouldFindViewByTypeAndMatcher() {
        on<TextView>(withId(R.id.text))
                .check(matches(isDisplayed()))
    }

    @Test(expected = NoMatchingViewException::class)
    @Throws(Exception::class)
    fun onShouldFailToFindViewByType() {
        on<Spinner>()
                .check(matches(isDisplayed()))
    }

    @Test(expected = NoMatchingViewException::class)
    @Throws(Exception::class)
    fun onShouldFailToFindViewByTypeAndMatcher() {
        on<Button>(withText("invalid text"))
                .check(matches(isDisplayed()))
    }

    @Test(expected = AmbiguousViewMatcherException::class)
    @Throws(Exception::class)
    fun onShouldFindMultipleMatchingViews() {
        on<TextView>()
                .check(matches(isDisplayed()))
    }
}