package it.czerwinski.android.xpresso

import android.support.test.espresso.Espresso
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf

class BulkCheckBuilder<T : View>(private val type: Class<T>) {

    val viewInteractions = mutableListOf<TypedViewInteraction<out T>>()

    /**
     * Adds the only View of type [V] to this bulk check.
     *
     * @param V Type of the View under test.
     */
    inline fun <reified V : T> on() {
        viewInteractions.add(TypedViewInteraction(V::class.java, Espresso.onView(instanceOf(V::class.java))))
    }

    /**
     * Adds the only View of type [V] matching all provided [viewMatchers] to this bulk check.
     *
     * @param V Type of the View under test.
     *
     * @param viewMatchers Matchers used to select the View.
     */
    inline fun <reified V : T> on(vararg viewMatchers: Matcher<View>) {
        viewInteractions.add(TypedViewInteraction(V::class.java, Espresso.onView(allOf(instanceOf(V::class.java), *viewMatchers))))
    }

    /**
     * Adds the only View of type [T] matching all provided [viewMatchers] to this bulk check.
     *
     * @param viewMatchers Matchers used to select the View.
     */
    fun onView(vararg viewMatchers: Matcher<View>) {
        viewInteractions.add(TypedViewInteraction(type, Espresso.onView(allOf(instanceOf(type), *viewMatchers))))
    }

    fun build(): BulkCheck<T> =
            BulkCheck(viewInteractions)
}

/**
 * Creates a new bulk check for Views of type [T].
 *
 * @param T Type of the Views under test.
 *
 * @param init Function initializing the new bulk check.
 *
 * @return New bulk check for Views of type [T].
 */
inline fun <reified T : View> bulkCheckFor(init: BulkCheckBuilder<T>.() -> Unit): BulkCheck<T> =
        BulkCheckBuilder(T::class.java)
                .apply(init)
                .build()

/**
 * Creates a new bulk check for any Views.
 *
 * @param init Function initializing the new bulk check.
 *
 * @return New bulk check for any Views.
 */
fun bulkCheck(init: BulkCheckBuilder<View>.() -> Unit): BulkCheck<View> =
        bulkCheckFor(init)
