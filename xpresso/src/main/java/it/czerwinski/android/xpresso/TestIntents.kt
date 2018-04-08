package it.czerwinski.android.xpresso

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.OngoingStubbing
import android.support.test.espresso.intent.matcher.IntentMatchers
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.hamcrest.Matchers

/**
 * Asserts that the only Intent sent by the application under test was targeted to [T].
 *
 * @param T Type of the class to which the Intent is expected to be targeted.
 *
 * @throws AssertionFailedError If the Intent was not targeted to [T].
 */
inline fun <reified T : Activity> intended() =
        Intents.intended(IntentMatchers.hasComponent(T::class.java.name))

/**
 * Asserts that the only Intent sent by the application under test was targeted to [T]
 * and that all provided [matchers] match this Intent.
 *
 * @param T Type of the class to which the Intent is expected to be targeted.
 *
 * @param matchers Matchers that are expected to match the Intent.
 *
 * @throws AssertionFailedError If the Intent was not targeted to [T]
 * or any of provided [matchers] does not match this Intent.
 */
inline fun <reified T : Activity> intended(vararg matchers: Matcher<Intent>) =
        Intents.intended(Matchers.allOf(IntentMatchers.hasComponent(T::class.java.name), *matchers))

/**
 * Sets a "Cancel" response for the Intent being stubbed.
 */
fun OngoingStubbing.respondWithCancel() {
    respondWith(Activity.RESULT_CANCELED)
}

/**
 * Sets a response for the Intent being stubbed.
 *
 * @param resultCode The result code.
 * @param resultDataInit Function initializing the result data.
 */
fun OngoingStubbing.respondWith(resultCode: Int, resultDataInit: Intent.() -> Unit = {}) {
    respondWith(resultCode, Intent().apply(resultDataInit))
}

/**
 * Sets a response for the Intent being stubbed.
 *
 * @param resultCode The result code.
 * @param resultData The result data.
 */
fun OngoingStubbing.respondWith(resultCode: Int, resultData: Intent) {
    respondWith(Instrumentation.ActivityResult(resultCode, resultData))
}
