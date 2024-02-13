# Split Android Suite Example

This example demonstrates the evaluation of a Split feature flag using [Android Suite](https://help.split.io/hc/en-us/articles/22916666123277).

The project uses Baseline Profiles to improve app performance and Jetpack Macrobenchmark to automate testing.

## Requirements

To run the code you'll need

* A [Split.io](https://app.split.io) account with the Measurement and Learning add-on, or a free trial Split account.
* [Android Studio Hedgehog (2023.1.1)](https://developer.android.com/studio/preview) or newer
* Android Gradle Plugin 8.0 or higher
* A physical Android device (or virtual device on the emulator) with Android 9 (API level 28) or higher

## Quick Start

To get started, follow these steps

1. Clone this repo from Android Studio.
2. Add the following line to your local.properties file

   ```
   SPLIT_CLIENTSIDE_API_KEY="<your_split_sdk_client-side_api_key>"
   ```
   You can get your client-side **SDK API key** from your Admin settings in app.split.io. (After you log in, click the top button on the left-hand navigation bar, then click Admin settings | API keys.)

4. Sync project with gradle files.
5. Choose one of the `:app` build variants. The `:baselineprofile` `benchmarkRelease (default)` variant should be selected by default.
6. In Android Studio's Device Manager, add a virtual device (API level 28 or higher), or select it if it is already added.
7. Run 'app' or run the `scrollCompilationBaselineProfiles()` test.

## Performance Codelabs

This code is based on the Android Codelab example code found at https://github.com/android/codelab-android-performance.

Note that the original `codelab-android-performance` repository contains two projects (examples) in two subfolders (`benchmarking` and `baseline-profiles`). We have removed the `benchmarking` project, and restructured the `baseline-profiles` project to allow this repo to be cloned and opened directly in Android Studio.

To create this **Split Android Suite example** from the `baseline-profiles` Codelab example, the following additional changes were made 

* the `main` branch of the Codelab repo is not copied into this repository
* the `end` branch of the Codelab repo is renamed `main`
* Split [Android Suite](https://help.split.io/hc/en-us/articles/22916666123277) code is added to evaluate a feature flag
