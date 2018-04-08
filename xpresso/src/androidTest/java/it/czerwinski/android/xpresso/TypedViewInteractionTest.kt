package it.czerwinski.android.xpresso

import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import junit.framework.AssertionFailedError
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class TypedViewInteractionTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun checkShouldSucceedForViewAssertion() {
        on<Button>()
                .check(matches(withId(R.id.button)))
    }

    @Test(expected = AssertionFailedError::class)
    @Throws(Exception::class)
    fun checkShouldFailForViewAssertion() {
        on<Button>()
                .check(matches(withId(R.id.text)))
    }

    @Test
    @Throws(Exception::class)
    fun checkShouldSucceedForViewMatchers() {
        on<Button>().check(
                withId(R.id.button),
                withText(R.string.intents_activity_button))
    }

    @Test(expected = AssertionFailedError::class)
    @Throws(Exception::class)
    fun checkShouldFailForViewMatchers() {
        on<Button>().check(
                withId(R.id.text),
                withText(R.string.intents_activity_button))
    }

    @Test
    @Throws(Exception::class)
    fun checkShouldSucceedForFunction() {
        on<Button>()
                .check { assertTrue(it.isSuccess) }
    }

    @Test
    @Throws(Exception::class)
    fun checkShouldFailForFunction() {
        on<Spinner>()
                .check { assertTrue(it.isFailure) }
    }

    @Test(expected = AssertionFailedError::class)
    @Throws(Exception::class)
    fun checkShouldThrowExceptionWhenFunctionalAssertionFails() {
        on<Button>()
                .check { fail("expected fail") }
    }

    @Test
    @Throws(Exception::class)
    fun performShouldPerformViewActions() {
        on<EditText>().perform(
                typeText("some text"),
                clearText(),
                typeText("other text"))

        on<EditText>()
                .check(withText("other text"))
    }

    @Test
    @Throws(Exception::class)
    fun performShouldPerformFunctionalViewAction() {
        on<EditText>().perform {
            setText("some")
            append(" text")
        }

        on<EditText>()
                .check(withText("some text"))
    }
}
