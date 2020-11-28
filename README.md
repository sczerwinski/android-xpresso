[ ![Download](https://api.bintray.com/packages/sczerwinski/android/xpresso/images/download.svg?version=0.1) ](https://bintray.com/sczerwinski/android/xpresso/0.1/link)

# Xpresso: Kotlin Extensions for Android Espresso

## Key features

### Test rules

**REMOVED:** Use `ActivityScenario` instead.

Functions creating Activity test rules:

```kotlin
@Rule
@JvmField
val rule = activityTestRule<MyActivity>(launchActivity = false)
```

And Intents test rules:

```kotlin
@Rule
@JvmField
val rule = intentsTestRule<MyActivity>(launchActivity = false)
```

### Views assertions and actions

Find views by type:

```kotlin
on<Button>(withText("OK"))
        .check(isDisplayed(), isEnabled())
```

Perform custom checks:

```kotlin
on<CheckBox>(withId(R.id.terms_and_conditions))
        .check {
            when (it) {
                is Success -> assertFalse(it.value.isChecked)
                is Failure -> assertTrue(it.exception is NoMatchingViewException)
            }
        }
```

Perform custom actions:

```kotlin
on<CalendarView>()
        .perform {
            date = Date().time
        }
```

### Bulk checks

Perform check on all views in a bulk check:

```kotlin
bulkCheck {
    onView(withId(R.id.my_layout))
    on<CheckBox>()
    on<Button>(withText("OK"))
}.all(isDisplayed())
```

Assert that any of the views passes the check:

```kotlin
bulkCheckFor<Button> {
    onView(withText("OK"))
    onView(withText("Cancel"))
}.any(isEnabled())
```

### Intents

Mock activity results easily:

```kotlin
intending(anyIntent())
        .respondWith(Activity.RESULT_OK) {
            putExtra("response", "Hello, World!")
        }
```

Verify intents sent by the application:

```kotlin
intended<MyActivity>(hasExtra("number", 256))
```

## Build configuration

```groovy
// Espresso:
androidTestImplementation 'com.android.support.test:runner:1.0.1'
androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
androidTestImplementation 'com.android.support.test.espresso:espresso-intents:3.0.1'

// Kotlin utilities (Try):
androidTestImplementation 'it.czerwinski:kotlin-util:0.1'

// X-presso:
androidTestImplementation 'it.czerwinski.android:xpresso:0.1'
```
