[![Build](https://github.com/sczerwinski/android-xpresso/workflows/Build/badge.svg)](https://github.com/sczerwinski/android-xpresso/actions?query=workflow%3ABuild)
[![Release](https://github.com/sczerwinski/android-xpresso/workflows/Release/badge.svg)](https://github.com/sczerwinski/android-xpresso/actions?query=workflow%3A%22Release%22)
[![Snapshot Release](https://github.com/sczerwinski/android-xpresso/workflows/Snapshot%20Release/badge.svg)](https://github.com/sczerwinski/android-xpresso/actions?query=workflow%3A%22Snapshot+Release%22)

# Xpresso: Kotlin Extensions for Android Espresso

## Core

[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski.android/xpresso-core.svg)](https://repo1.maven.org/maven2/it/czerwinski/android/xpresso-core/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/it.czerwinski.android/xpresso-core?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/xpresso-core/)

### Build Configuration

```groovy
dependencies {
    androidTestImplementation 'it.czerwinski.android:xpresso-core:1.0'
}
```

### Examples

#### Launching `ActivityScenario`

```kotlin
@Test
fun myTestMethod() {
    val scenario = launchTestActivity<MyTestActivity>()
    // […]
}
```

#### Type-Aware View Interactions

```kotlin
on<TextView>(withText(R.string.hello_world))
    .check(isDisplayed())

on<Button>()
    .check(isDisplayed(), isEnabled())
    .perform(click())
```

Custom checks:
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
    .perform(description = "set date") {
        date = Date().time
    }
```

#### Bulk Checks

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

## `RecyclerView`

[![Maven Central](https://img.shields.io/maven-central/v/it.czerwinski.android/xpresso-recyclerview.svg)](https://repo1.maven.org/maven2/it/czerwinski/android/xpresso-recyclerview/)
[![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/it.czerwinski.android/xpresso-recyclerview?server=https%3A%2F%2Foss.sonatype.org)](https://oss.sonatype.org/content/repositories/snapshots/it/czerwinski/android/xpresso-recyclerview/)

### Build Configuration

```groovy
dependencies {
    androidTestImplementation 'it.czerwinski.android:xpresso-recyclerview:1.0'
}
```

### Examples

#### Interactions With Items Of `RecyclerView`

```kotlin
onRecyclerView(withId(R.id.list))
    .check(isDisplayed())
    .onItem<MyAdapter.ViewHolder>(position = 0) {
        // Interaction with ViewHolder.itemView
        check(hasDescendant(withText("Actions")))
        perform(click())
    }
    .onItem<MyAdapter.ViewHolder>(position = 1) {
        // Interaction with a descendant of ViewHolder.itemView:
        on<Button>(withText("Actions"))
            .check(isDisplayed())
            .perform(click())
    }
```
