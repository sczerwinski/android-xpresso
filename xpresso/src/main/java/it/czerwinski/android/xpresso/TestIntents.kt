package it.czerwinski.android.xpresso

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

inline fun <reified T : Activity> intended() =
        Intents.intended(IntentMatchers.hasComponent(T::class.java.name))

inline fun <reified T : Activity> intended(vararg matchers: Matcher<Intent>) =
        Intents.intended(Matchers.allOf(IntentMatchers.hasComponent(T::class.java.name), *matchers))
