package it.czerwinski.android.xpresso

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.rule.ActivityTestRule

/**
 * Creates [ActivityTestRule] for Activity of type [T].
 *
 * @param T Type of the Activity under test.
 *
 * @param initialTouchMode `true` if the Activity should be placed into "touch mode" when started.
 * @param launchActivity `true` if the Activity should be launched before the test starts.
 *
 * @return New [ActivityTestRule] for Activity of type [T].
 */
inline fun <reified T : Activity> activityTestRule(initialTouchMode: Boolean = false, launchActivity: Boolean = true) =
        ActivityTestRule(T::class.java, initialTouchMode, launchActivity)

/**
 * Creates [IntentsTestRule] for Activity of type [T].
 *
 * @param T Type of the Activity under test.
 *
 * @param initialTouchMode `true` if the Activity should be placed into "touch mode" when started.
 * @param launchActivity `true` if the Activity should be launched before the test starts.
 *
 * @return New [IntentsTestRule] for Activity of type [T].
 */
inline fun <reified T : Activity> intentsTestRule(initialTouchMode: Boolean = false, launchActivity: Boolean = true) =
        IntentsTestRule(T::class.java, initialTouchMode, launchActivity)

/**
 * Launches the Activity under test.
 *
 * @param T Type of the Activity under test.
 *
 * @param intentInit Function initializing the Intent with which the Activity should be started.
 *
 * @return The Activity launched by this rule.
 */
fun <T : Activity> ActivityTestRule<T>.launchActivity(intentInit: Intent.() -> Unit = {}): T = Intent()
        .apply(intentInit)
        .let(this::launchActivity)
