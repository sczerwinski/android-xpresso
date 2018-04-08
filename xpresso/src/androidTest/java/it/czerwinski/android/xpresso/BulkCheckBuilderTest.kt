package it.czerwinski.android.xpresso

import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import it.czerwinski.android.xpresso.mock.activity.IntentsActivity
import it.czerwinski.android.xpresso.test.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class BulkCheckBuilderTest {

    @Rule
    @JvmField
    val activityTestRule = activityTestRule<IntentsActivity>(launchActivity = true)

    @Test
    @Throws(Exception::class)
    fun bulkCheckShouldFindTextAndButton() {
        var count = 0

        bulkCheck {
            on<EditText>()
            on<Button>()
        }.all {
            it.forEach { count++ }
        }

        assertEquals(2, count)
    }

    @Test
    @Throws(Exception::class)
    fun bulkCheckShouldFindTextViewsWithIds() {
        var count = 0

        bulkCheck {
            on<TextView>(withId(R.id.text))
            on<TextView>(withId(R.id.button))
        }.all {
            it.forEach { count++ }
        }

        assertEquals(2, count)
    }

    @Test
    @Throws(Exception::class)
    fun bulkCheckForTextViewShouldFindViewsWithIds() {
        var count = 0

        bulkCheckFor<TextView> {
            onView(withId(R.id.text))
            onView(withId(R.id.button))
        }.all {
            it.forEach { count++ }
        }

        assertEquals(2, count)
    }
}
