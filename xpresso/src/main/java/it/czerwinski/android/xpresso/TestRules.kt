package it.czerwinski.android.xpresso

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule

inline fun <reified T : Activity> activityTestRule(initialTouchMode: Boolean = false, launchActivity: Boolean = true) =
        ActivityTestRule(T::class.java, initialTouchMode, launchActivity)

fun <T : Activity> ActivityTestRule<T>.launchActivity(intentInit: Intent.() -> Unit = {}): T = Intent()
        .apply(intentInit)
        .let(this::launchActivity)
