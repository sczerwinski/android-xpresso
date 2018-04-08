package it.czerwinski.android.xpresso

import android.support.test.espresso.ViewAction
import android.view.View

/**
 * Creates a custom interaction on a given View element of type [T].
 *
 * @param T Type of the View under test.
 *
 * @param action Action to be performed on the View under test.
 */
inline fun <reified T : View> viewAction(noinline action: T.() -> Unit): ViewAction =
        TypedViewAction(type = T::class.java, action = action)

/**
 * Creates a custom interaction on a given View element of type [T].
 *
 * @param T Type of the View under test.
 *
 * @param description Description of this action.
 * @param action Action to be performed on the View under test.
 */
inline fun <reified T : View> viewAction(description: String, noinline action: T.() -> Unit): ViewAction =
        TypedViewAction(type = T::class.java, description = description, action = action)
