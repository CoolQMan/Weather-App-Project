package com.CoolQMan.weatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String APIKEY = "YOUR_API_KEY";


    TextView cityNameView, temp, weather, max_temp, min_temp, day, date, humidity, windSpeed, condition, sunRise, sunSet, sea;
    SearchView searchView;
    ConstraintLayout main;
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        cityNameView = findViewById(R.id.cityName);
        temp = findViewById(R.id.temp);
        max_temp = findViewById(R.id.max_temp);
        min_temp = findViewById(R.id.min_temp);
        day = findViewById(R.id.day);
        date = findViewById(R.id.date);
        humidity = findViewById(R.id.humidity);
        windSpeed = findViewById(R.id.windSpeed);
        condition = findViewById(R.id.condition);
        sunRise = findViewById(R.id.sunRise);
        sunSet = findViewById(R.id.sunSet);
        sea = findViewById(R.id.sea);
        searchView = findViewById(R.id.searchView);
        weather = findViewById(R.id.weather);

        main = findViewById(R.id.main);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        searchCity();

        SharedPreferences pref =getSharedPreferences("SavedCity", MODE_PRIVATE);
        String city = pref.getString("City", "Delhi");

        // Fetch weather data
        fetchWeatherData(city);
    }

    public void fetchWeatherData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<WeatherApp> response = apiInterface.getWeatherData(cityName, APIKEY, "metric");
        response.enqueue(new Callback<WeatherApp>() {
            @Override
            public void onResponse(@NonNull Call<WeatherApp> call, @NonNull Response<WeatherApp> response) {
                WeatherApp responseBody = response.body();
                if (response.isSuccessful() && responseBody != null) {

                    String temperature = Double.toString(responseBody.component7().component6());
                    String humidityValue = Integer.toString(responseBody.component7().component3());
                    String windSpeedValue = Double.toString(responseBody.component13().component2());
                    long sunRiseValue = responseBody.component9().component3();
                    long sunSetValue = responseBody.component9().component4();
                    String seaLevel = Integer.toString(responseBody.component7().component4());
                    String conditionValue = responseBody.component12().get(0).component4();
                    String maxTemperature = Double.toString(responseBody.component7().component7());
                    String minTemperature = Double.toString(responseBody.component7().component8());

                    String dayValue = dayName(System.currentTimeMillis());
                    String dateValue = date(System.currentTimeMillis());

                    // Update the TextViews directly here


                    temp.setText(temperature + " °C");
                    humidity.setText(humidityValue + "%");
                    windSpeed.setText(windSpeedValue + " m/s");
                    sunRise.setText(formatTime(sunRiseValue));
                    sunSet.setText(formatTime(sunSetValue));
                    sea.setText(seaLevel + " hPa");
                    condition.setText(conditionValue);
                    max_temp.setText("Max : " + maxTemperature + " °C");
                    min_temp.setText("Min : " + minTemperature + " °C");
                    day.setText(dayValue);
                    date.setText(dateValue);
                    cityNameView.setText(cityName);
                    weather.setText(conditionValue);

                    changeImage(conditionValue);

                }
            }

            @Override
            public void onFailure(Call<WeatherApp> call, Throwable throwable) {
                Log.e("WeatherApp", "Failed to fetch data", throwable);
            }
        });
    }

    public String dayName(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault());
        return sdf.format(new Date());
    }

    public String date(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
    private String formatTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        Date date = new Date(millis * 1000); // Convert to seconds
        return sdf.format(date);
    }

    public void searchCity(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null){
                    SharedPreferences pref = getSharedPreferences("SavedCity", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("City", query);
                    editor.apply();
                    fetchWeatherData(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }

    private void changeImage(String conditions) {
        switch (conditions) {
            case "Clear Sky":
            case "Sunny":
            case "Clear":
            case "Few Clouds":
            case "Dust":
            case "Sand":
            case "Ash":
            case "Squall":
                main.setBackgroundResource(R.drawable.sunny_background);
                lottieAnimationView.setAnimation(R.raw.sun);
                break;

            case "Partly Clouds":
            case "Clouds":
            case "Overcast":
            case "Mist":
            case "Haze":
            case "Foggy":

                main.setBackgroundResource(R.drawable.colud_background);
                lottieAnimationView.setAnimation(R.raw.cloud);
                break;

            case "Light Rain":
            case "Drizzle":
            case "Moderate Rain":
            case "Showers":
            case "Heavy Rain":
            case "Extreme Rain":
            case "Thunderstorm":
            case "Thunderstorm with Rain":
            case "Thunderstorm with Light Rain":
            case "Thunderstorm with Heavy Rain":
            case "Tornado":
            case "Rain":
                main.setBackgroundResource(R.drawable.rain_background);
                lottieAnimationView.setAnimation(R.raw.rain);
                break;

            case "Light Snow":
            case "Moderate Snow":
            case "Heavy Snow":
            case "Blizzard":
            case "Sleet":
                main.setBackgroundResource(R.drawable.snow_background);
                lottieAnimationView.setAnimation(R.raw.snow);
                break;

            default:
                main.setBackgroundResource(R.drawable.sunny_background);
                lottieAnimationView.setAnimation(R.raw.sun);
                break;
        }
    }
}
