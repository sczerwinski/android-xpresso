package it.czerwinski.android.xpresso

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.view.View
import org.hamcrest.Matcher

/**
 * Custom interaction on a given View element of type [T].
 *
 * @param T Type of the View under test.
 *
 * @param type Type of the View under test.
 * @param description Description of this action.
 * @param action Action to be performed on the View under test.
 */
class TypedViewAction<T : View>(
        private val type: Class<T>,
        private val description: String = "typed view action",
        private val action: T.() -> Unit) : ViewAction {

    override fun getConstraints(): Matcher<View> =
            isAssignableFrom(type)

    override fun perform(uiController: UiController?, view: View?) =
            type.cast(view).action()

    override fun getDescription(): String = description
}