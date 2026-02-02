# Introduction
This project contains an Android app allowing you to search movies by their titles and displaying the matching movies as a list.
Reading this file you should understand what you have to do.

# Getting Started
The project uses Kotlin with Jetpack Compose.
You need JDK 21 and Android SDK 36.

Copy `local.properties.example` to `local.properties` and configure your API key:
```
cp local.properties.example local.properties
```

# Start the app
```
./gradlew installDevDebug && adb shell am start -n lu.post.eval.dev/lu.post.eval.ui.MainActivity
```

# Run tests
```
./gradlew test
```

# Useful resources
- Api documentation: http://www.omdbapi.com

# TODO
When implementing the TODO, we expect you will understand and follow the practice already in place in the application.
## High
 * Add a detail page of a movie displayed when clicking on a movie in the list.
 The design of the page is up to you, but must display the following informations:
    * Movie poster
    * Title
    * Year
    * Genre(s)
    * Director
    * Main actors
    * Plot
    * Ratings

## Medium
 * The list of movies on the search page is limited to 10 movies, add pagination on the page to be able to view all results matching the search query

## Low
 * Display loaders (spinners or skeleton) when the frontend is waiting for API results

## Bonus - not required
 * Improve the overall design of the application

## At the End
 * Push the result of your work on a dedicated branch: eval/name-surname
