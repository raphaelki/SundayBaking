# Sunday Baking Android App

## Project Overview

Android sample app that retrieves bake recipes from a [provided source](http://go.udacity.com/android-baking-app-json) and displays baking guidance and instruction videos.\
This is the 3rd project of the [Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801).

The main goal of this project is to get familiar with some advanced architecture concepts in Android that are not covered by the course material. The app makes use of the [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html) and dependency injection to achieve a better seperation of business and UI logic.

## Android concepts used

- **Fragment** for swapping views at runtime
- **Widget** for showing recipe instructions on the home screen

## Libraries used

- **[Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)** to avoid boilerplate UI code 
- **[ExoPlayer](https://github.com/google/ExoPlayer)** for streaming and showing instruction videos
- **[Retrofit](http://square.github.io/retrofit)** for API calls
- **[Glide](https://github.com/bumptech/glide)** for image manipulation and caching
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)** to retrieve data changes from the database, although in this case the data is static
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)** to handle business logic
- **[Room](https://developer.android.com/topic/libraries/architecture/room.html)** for database access
- **[Timber](https://github.com/JakeWharton/timber)** for logging

## Building

Get the code via the git client and import the project into Android Studio:
```bash
$ git clone https://github.com/raphaki/SundayBaking.git
```
