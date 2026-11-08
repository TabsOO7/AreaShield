# AreaShield

## 1. Project Overview

AreaShield is an offline Android application prototype designed for residents of Ndola to record and view reports of suspected gang activity and gang-related violence. The application provides community members with a localized platform to document incident details safely and stay informed about reported safety concerns in surrounding areas.

### Important Notice & System Boundaries

* **Local Storage Only**: Incident reports are stored exclusively on the user's local device.
* **Unverified Status**: All submitted reports are marked as **Unverified** by default and represent unconfirmed community reports.
* **Not an Emergency Service**: AreaShield is a reporting prototype and is **not** an emergency response system or a substitute for law enforcement or emergency services.
* **Offline Privacy**: Reports are not automatically transmitted, broadcasted, or shared with other users or remote servers.

---

## 2. Main Features

AreaShield consists of three core screens connected by a state-driven navigation architecture:

1. **Home Screen**:
   * Displays the application title and tagline.
   * Provides quick action navigation buttons to submit a new report or view safety guidelines.
   * Renders a scrollable list (`LazyColumn`) of all locally stored incident reports, showing affected area, description, date, time, and report status.
   * Displays a friendly fallback message when no reports have been submitted.

2. **Report Incident Screen**:
   * Provides an input form for residents to submit new incident details:
     * **Affected Area** (e.g., Chifubu)
     * **Incident Description**
     * **Incident Date** (DD/MM/YYYY format)
     * **Incident Time** (HH:MM 24-hour format)
   * Includes strict input validation: checks for empty fields and validates date/time formatting (checking month bounds, day limits per month including leap years, and valid 24-hour time ranges).
   * Features vertical scrolling (`verticalScroll`) to ensure input fields and action buttons remain fully accessible on smaller screen sizes and when the soft keyboard is visible.

3. **About and Safety Screen**:
   * Outlines the application's background and purpose for the Ndola community.
   * Presents essential safety guidelines (e.g., avoiding confrontation with gang members, excluding personal identifying information, contacting emergency services in immediate danger).
   * Features vertical scrolling to support diverse device display sizes.

### System Capabilities

* **Input Validation**: Form entries are checked before submission. Inline error messages highlight missing or invalid inputs.
* **Local Persistence**: Reports are permanently saved to device storage using `SharedPreferences` with JSON array serialization, ensuring data persists across application restarts.
* **Navigation & Back-Button Handling**: Screen transitions are managed via state holding (`rememberSaveable`), and system back-button presses are intercepted using Compose `BackHandler` to safely return to the Home screen.
* **System Light/Dark Theme Support**: UI components automatically adjust color schemes according to the Android system dark mode preference.

---

## 3. Technologies Used

The project is built using native Android development tools and libraries specified in `app/build.gradle.kts`:

* **Language**: Kotlin `2.2.10`
* **UI Framework**: Jetpack Compose (Compose BOM `2026.02.01`)
  * `androidx.compose.material3:material3` — Material Design 3 components
  * `androidx.compose.ui:ui` — Core Compose layout and graphics engine
  * `androidx.compose.ui:ui-tooling-preview` — Design previews in IDE
* **Core Libraries**:
  * `androidx.core:core-ktx:1.10.1` — Kotlin extension functions for Android framework APIs
  * `androidx.activity:activity-compose:1.8.0` — Integration of Compose with ComponentActivity
  * `androidx.lifecycle:lifecycle-runtime-ktx:2.6.1` — Lifecycle-aware coroutine support
* **Data Storage**:
  * `android.content.SharedPreferences` — Native key-value disk storage
  * `org.json.JSONArray` & `org.json.JSONObject` — Native JSON serialization and deserialization
* **Build System**:
  * Gradle with Kotlin DSL (`build.gradle.kts`)
  * Android Gradle Plugin (AGP `9.4.1`)
  * Target SDK: `37`, Compile SDK: `37`, Minimum SDK: `23` (Android 6.0 Marshmallow)

---

## 4. Application Structure

Source code is organized under the `com.tabeni.areashield` package following separation of concerns:

```
com.tabeni.areashield/
├── MainActivity.kt
├── model/
│   └── IncidentReport.kt
├── data/
│   └── ReportStorage.kt
└── ui/
    ├── screens/
    │   ├── HomeScreen.kt
    │   ├── ReportScreen.kt
    │   └── AboutScreen.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

### Key Source Files

* `MainActivity.kt`: Entry point of the application. Houses `MainActivity` (`ComponentActivity`) and the `AreaShieldApp` root composable, which manages navigation state and intercepts system back-button presses.
* `model/IncidentReport.kt`: Data class defining the domain model for an incident report (`area`, `description`, `date`, `time`, and default status `"Unverified"`).
* `data/ReportStorage.kt`: Data storage manager object. Handles serializing `IncidentReport` objects to JSON strings and reading/writing them to `SharedPreferences` (`areashield_storage`).
* `ui/screens/HomeScreen.kt`: Composable UI displaying recent incident report cards in a `LazyColumn` and primary navigation buttons.
* `ui/screens/ReportScreen.kt`: Composable UI providing the incident reporting form, regex and numerical date/time validation, vertical scrolling, and live error announcements.
* `ui/screens/AboutScreen.kt`: Composable UI displaying prototype context and safety guidelines.
* `ui/theme/Theme.kt`, `Color.kt`, `Type.kt`: Defines Material 3 color schemes for light and dark modes, dynamic color support for Android 12+, and application typography.

---

## 5. Building and Running the Application

### Prerequisites

* **Android Studio**: Android Studio Ladybug (2024.2.1) or newer recommended.
* **JDK**: Java Development Kit 11 or higher (configured in Android Studio).
* **Android SDK**: Android 14 / 15 SDK (API Level 34–37 installed).

### Instructions

1. **Open Project**:
   * Open Android Studio.
   * Select **Open** and navigate to the project directory (`MyApplication` / `AreaShield`).

2. **Gradle Sync**:
   * Android Studio will automatically trigger a Gradle sync. If prompted, click **Sync Project with Gradle Files**.

3. **Build Project**:
   * Select **Build > Make Project** from the top menu, or run the Gradle task in the terminal:
     ```bash
     ./gradlew :app:assembleDebug
     ```

4. **Run Application**:
   * Connect a physical Android device (running Android 6.0 / API 23 or higher with USB Debugging enabled) or start an Android Virtual Device (AVD).
   * Select `app` in the run configuration dropdown and click **Run 'app'** (or press `Shift + F10`).

---

## 6. Research Notes

### Research Note 1: Android Accessibility

#### 1. What Android Accessibility Means
Android accessibility encompasses design practices, frameworks, and APIs that ensure mobile applications are usable by individuals with visual, auditory, motor, or cognitive impairments. On Android, accessibility is primarily driven by assistive technologies such as screen readers (Google TalkBack), Switch Access, and high-contrast display settings.

#### 2. Why Accessibility Matters for AreaShield
AreaShield is a community safety prototype designed to deliver critical safety information and facilitate incident reporting in Ndola. In tense or urgent situations, all residents—regardless of visual acuity, motor limitations, or age—must be able to navigate the app, read safety guidelines, and submit reports without operational barriers. Ensuring high contrast, clear screen reader navigation, and accessible touch targets makes community reporting inclusive and usable for all community members.

#### 3. Implementation in AreaShield
AreaShield incorporates several accessibility enhancements using Jetpack Compose Semantics APIs:
* **Heading Semantics (`heading()`)**: Applied `Modifier.semantics { heading() }` to screen titles and section headers ("AreaShield", "Recent Incident Reports", "Report an Incident", "About AreaShield", "Safety Guidelines"). This enables TalkBack users to navigate between sections using heading navigation gestures.
* **Semantic Merging (`mergeDescendants = true`)**: Configured report cards on the Home screen with `Modifier.semantics(mergeDescendants = true) {}`. TalkBack reads each report card as a single grouped statement rather than requiring multiple swipes across separate text elements.
* **Live Region Error Announcements (`LiveRegionMode.Polite`)**: Attached `Modifier.semantics { liveRegion = LiveRegionMode.Polite }` to form validation error text. When a validation error occurs, TalkBack automatically announces the error to the user without requiring manual focus.
* **Explicit Form Labels & Error States**: `OutlinedTextField` components feature clear text labels, placeholders, and dynamic `isError` flags.
* **Touch Target Size**: Action buttons enforce a minimum 48dp touch target height (`defaultMinSize(minHeight = 48.dp)`) adhering to Android material accessibility guidelines.

#### 4. Official Documentation References
* [Android Developer Guide: Accessibility in Jetpack Compose](https://developer.android.com/guide/topics/ui/accessibility/views/apps-views)
* [Android Developer Principles: Accessibility Principles](https://developer.android.com/guide/topics/ui/accessibility/views/principles-views)

#### 5. Technical Implementation Challenge
* **Challenge**: In Jetpack Compose, screen readers treat individual `Text` nodes inside a `Card` as separate focusable elements. On the Home screen, TalkBack initially required 5 individual swipe gestures per card to read the area, description, date, time, and status.
* **Solution**: Wrapping the card modifier with `Modifier.semantics(mergeDescendants = true) {}` consolidated all descendant text nodes into a single semantic tree node, allowing TalkBack to read the complete incident report in one fluent announcement.

---

### Research Note 2: Android Dark Mode

#### 1. What Android Dark Mode Is
Android Dark Mode is a system-wide display setting that transitions application user interfaces from a light color scheme to a dark background palette with light text. Dark mode reduces screen glare in low-light environments, reduces power consumption on AMOLED/OLED displays, and improves overall visual comfort.

#### 2. Why Dark Mode Matters for AreaShield
AreaShield is designed for reporting safety incidents in Ndola, which frequently occur or are reported during night hours or in low-light conditions. A bright white screen emitting intense light in unlit or unsafe public environments can draw unwanted attention to a user holding a phone. Providing a seamless dark theme reduces screen emissions, protecting user privacy and physical safety during night-time reporting.

#### 3. Implementation in AreaShield
Dark mode is implemented using Jetpack Compose Material Design 3 theming principles:
* **System Setting Detection (`isSystemInDarkTheme()`)**: The root theme wrapper `AreaShieldTheme` in `Theme.kt` dynamically evaluates `isSystemInDarkTheme()`, automatically matching the Android OS system dark theme toggle.
* **Material 3 Color Schemes**: Defined explicit `LightColorScheme` and `DarkColorScheme` in `Theme.kt` using color tokens from `Color.kt`.
* **Dynamic Color Support**: Preserved support for Android 12+ (API Level 31+) dynamic color extraction (`dynamicDarkColorScheme` and `dynamicLightColorScheme`), allowing the app to align with user wallpaper palettes when supported.
* **Semantic Material Tokens**: Eliminated hardcoded hex colors (`Color.White` / `Color.Black`) across all composable screens. Text, cards, buttons, text fields, and container backgrounds rely exclusively on `MaterialTheme.colorScheme` tokens (`primary`, `onSurface`, `onSurfaceVariant`, `error`), ensuring automatic contrast adaptation across light and dark themes.

#### 4. Official Documentation References
* [Android Developer Guide: Dark Theme](https://developer.android.com/develop/ui/views/theming/darktheme)
* [Android Developer Guide: Themes and Styles](https://developer.android.com/develop/ui/views/theming/themes)

#### 5. Technical Implementation Challenge
* **Challenge**: Maintaining legibility of report status labels ("Status: Unverified") and card containers across both light and dark themes without creating custom color conditional logic inside screen composables.
* **Solution**: Configured `MaterialTheme.colorScheme.primary` and `onSurfaceVariant` tokens to serve as the unified color references across screens. By defining contrasting light and dark primary color values (`0xFF006689` for light mode and `0xFF7DD0FF` for dark mode) in `Theme.kt`, all status labels automatically adapt with high visual contrast in both modes without any screen-level conditional code.
