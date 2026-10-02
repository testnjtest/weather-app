# Weather Forecast Android App

An Android app (Java, Android Studio) that shows real-time, location-based weather forecasts using the [OpenWeatherMap API](https://openweathermap.org/api).

## Features

- **ZIP code lookup:** enter a ZIP code and the app looks up the location (name, latitude, longitude), then turns it into an accurate 5-day forecast.
- **Live API data:** shows current conditions and a 5-day outlook (daily highs and lows, weather conditions and dates), parsed from the API and displayed in a clean list view.
- **Dynamic UI:** the background image and message change to match the current weather. Each condition has its own Demon Slayer character and quote:

  | Weather | Character |
  | --- | --- |
  | Clear | Shinobu |
  | Sunny | Zenitsu |
  | Cloudy | Nezuko |
  | Rain | Giyu |
  | Snow | Tanjiro |

- **Asynchronous loading:** network requests run in the background (`AsyncTask`), so the app stays responsive while it pulls live data. That is a common challenge for apps that depend on outside data.

## How it works

1. **Geocoding:** the OpenWeatherMap Geocoding API (`/geo/1.0/zip`) converts the ZIP code into coordinates.
2. **Forecast:** the coordinates go to the 5-day forecast API (`/data/2.5/forecast`, imperial units).
3. **Display:** the JSON response is parsed into a list of `Weather` objects, shown in a custom `ListView`, and the UI updates its theme to match the current conditions.

## Setup

1. Get a free API key from [openweathermap.org](https://home.openweathermap.org/api_keys).
2. Add it to `local.properties` in the project root (Android Studio creates this file, and it is not committed):
   ```
   OPENWEATHER_API_KEY=your_key_here
   ```
3. Open the project in Android Studio and run it.
