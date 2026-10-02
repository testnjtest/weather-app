# Weather App

A Demon Slayer themed weather app for Android. Enter a ZIP code to get the forecast, and the character on screen changes with the weather:

| Weather | Character |
| --- | --- |
| Clear | Shinobu |
| Sunny | Zenitsu |
| Cloudy | Nezuko |
| Rain | Giyu |
| Snow | Tanjiro |

Weather data comes from the [OpenWeather API](https://openweathermap.org/api).

## Setup

1. Get a free API key from [openweathermap.org](https://home.openweathermap.org/api_keys).
2. Add it to `local.properties` in the project root (Android Studio creates this file; it is not committed):
   ```
   OPENWEATHER_API_KEY=your_key_here
   ```
3. Open the project in Android Studio and run it.
