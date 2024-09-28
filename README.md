# Weather App Project

This is a simple Android Weather App built using the OpenWeather API. It fetches real-time weather data and displays various details such as temperature, humidity, wind speed, sunrise, sunset times, and weather conditions with dynamic animations and backgrounds.

The code tutorial is in Kotlin, but this project is built with Java, with only a few files in Kotlin.
YouTube Tutorial: https://youtu.be/QFGKV8j2ulY?si=Fjejp2La52rz0z0I   

# Very Important!!
This app will not work unless an API key from OpenWeather is added into it. 
Read `How to Run` Section for more info.

## Features

- Displays current weather information: 
  - Temperature
  - Humidity
  - Wind Speed
  - Sunrise/Sunset times (formatted as `hh:mm`)
  - Sea level pressure
  - Weather conditions with animations
  - Maximum and Minimum daily temperatures
- Dynamic backgrounds and Lottie animations based on weather conditions (sunny, cloudy, rainy, snowy).
- Search functionality to look up weather for any city using a SearchView.
- Fetches real-time weather data using the OpenWeather API.

## Dependencies

- **Lottie Animations** for rendering weather animations.
- **Retrofit** for making network requests to the OpenWeather API.
- **Gson Converter** for parsing the JSON data retrieved from the API.

### Key Libraries
```gradle
// Retrofit and GSON Converter for API calls
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// Lottie Animations for dynamic weather effects
implementation 'com.airbnb.android:lottie:3.7.0'

// Constraint Layout for flexible UI
implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
```

## How it works

- **Retrofit** is used to fetch weather data from the OpenWeather API.
- The **Gson Converter** parses the JSON response from the API into Java objects.
- Based on the weather conditions, appropriate backgrounds and Lottie animations are displayed.
- A **SearchView** allows the user to input city names and fetch corresponding weather data.

## How to Run
1. Clone the repository
```bash 
git clone https://github.com/CoolQMan/Weather-App-Project.git
```

2. Obtain an API key from OpenWeather and replace the APIKEY constant in the MainActivity class:
```java
public static final String APIKEY = "YOUR_API_KEY";
```

3. Build the project in Android Studio and run it on an emulator or physical device.

## Screenshots

![Screenshot_2024-09-28-22-42-33-42_77d0e59aee6826e2694298b5b46c5b23](https://github.com/user-attachments/assets/b4554128-1331-4178-826f-bd54c2999968)
![Screenshot_2024-09-28-22-40-51-52_77d0e59aee6826e2694298b5b46c5b23 1](https://github.com/user-attachments/assets/6f7440ed-3813-4403-9ae2-ffaf9d24a304)
![Screenshot_2024-09-28-22-39-59-71_77d0e59aee6826e2694298b5b46c5b23](https://github.com/user-attachments/assets/266892b9-b259-499d-96e1-4a849253e209)




## License

This project is licensed under the MIT License - see the LICENSE file for details.
