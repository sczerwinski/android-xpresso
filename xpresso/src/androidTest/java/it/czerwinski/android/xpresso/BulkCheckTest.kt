package it.czerwinski.android.xpresso

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class BulkCheckTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun allShouldPassViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all(matches(isDisplayed()))
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun allShouldFailViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all(matches(withId(R.id.text)))
    }

    @Test
    @Throws(Exception::class)
    fun allShouldMatchViewMatchers() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all(isDisplayed(), isEnabled())
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun allShouldNotMatchViewMatchers() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all(isDisplayed(), withId(R.id.text))
    }

    @Test
    @Throws(Exception::class)
    fun allShouldPassFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all {
            it.forEach { assertTrue(it is TextView) }
        }
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun allShouldFailFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all {
            it.forEach { assertTrue(it is EditText) }
        }
    }

    @Test(expected = NoMatchingViewException::class)
    @Throws(Exception::class)
    fun allShouldErrorInFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
            on<Spinner>()
        }.all {
            it.get()
        }
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldPassViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any(matches(withId(R.id.text)))
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun anyShouldFailViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any(doesNotExist())
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldMatchViewMatchers() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any(isDisplayed(), withId(R.id.text))
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun anyShouldNotMatchViewMatchers() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any(withId(R.id.button), withId(R.id.text))
    }

    @Test
    @Throws(Exception::class)
    fun anyShouldPassFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
            on<Spinner>()
        }.any {
            assertTrue(it.isSuccess)
        }
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun anyShouldFailFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any {
            assertTrue(it.isFailure)
        }
    }

    @Test(expected = AssertionError::class)
    @Throws(Exception::class)
    fun anyShouldErrorInFunctionalViewAssertion() {
        bulkCheck {
            on<EditText>()
            on<Button>()
        }.any {
            it.failed.get()
        }
    }
}
