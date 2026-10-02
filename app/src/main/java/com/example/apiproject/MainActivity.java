package com.example.apiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText zipCode;

    TextView quote, latUI, lonUI, locUI, cT;

    Button button;
    ArrayList<JSONObject> weather;

    ArrayList<Weather> WeatherArrayList;

    ImageView image;

    String lat, lon, name, country, currentTemp;

    String[] mins, maxs, dates, descriptions;

    String zipOrCityState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zipCode = findViewById(R.id.zipCode);
        quote = findViewById(R.id.quote);
        button = findViewById(R.id.button);
        latUI = findViewById(R.id.lat);
        lonUI = findViewById(R.id.lon);
        locUI = findViewById(R.id.Location);
        cT = findViewById(R.id.CurrentTemp);
        listView = findViewById(R.id.customListView);
        image = findViewById(R.id.imageView);
        mins = new String[5];
        maxs = new String[5];
        descriptions = new String[5];
        dates = new String[5];
        weather = new ArrayList<>();
        WeatherArrayList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipOrCityState = zipCode.getText().toString();

                if (zipCode.length() < 5) {
                    Toast.makeText(MainActivity.this, "Zipcode should be 5 digits", Toast.LENGTH_LONG).show();
                } else {
                    AsyncThread task = new AsyncThread();
                    task.execute(zipOrCityState);
                }

            }
        });

    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        //String that is used for the AsyncTask
        @Override
        protected Void doInBackground(String... strings) {
            zipOrCityState = strings[0];
            String api = BuildConfig.OPENWEATHER_API_KEY;
            //check to see if zipcode is sent to the string
            Log.d("ZIPCODE", zipOrCityState);
            try {
                String urlLink = "https://api.openweathermap.org/geo/1.0/zip?zip=" + zipOrCityState + "&appid=" + api;
                Log.d("URLLINK", urlLink);
                URL urlGeo = new URL(urlLink);
                URLConnection connection = urlGeo.openConnection();
                InputStream a1 = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(a1));

                String line = null;
                String message = "";//new String();
                //StringBuffer buffer = new StringBuffer(2048);
                Log.d("TAG1BEFORE", "LINE:" + message.toString());
                while ((line = reader.readLine()) != null) {
                    message += line;
                    Log.d("TAG", message);
                }
                reader.close();
                Log.d("TAG1AFTER", "LINE:" + message);
                Log.d("TAG1", message);
                JSONObject zipObject = new JSONObject(message);
                lat = zipObject.getString("lat");
                lon = zipObject.getString("lon");
                name = zipObject.getString("name");
                country = zipObject.getString("country");
                Log.d("TAG", zipObject.toString());
                Log.d("name", name);
                Log.d("lat", lat);
                Log.d("lon", lon);
                Log.d("country", country);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            try {
                String urlLink2 = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + api + "&units=imperial";
                Log.d("URLLINK2", urlLink2);
                URL url = new URL(urlLink2);
                URLConnection connection = url.openConnection();
                InputStream a = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(a));
                String line = null;
                String message = "";//new String();
                //StringBuffer buffer = new StringBuffer(2048);
                Log.d("TAG1BEFOREFOR2", "LINE:" + message.toString());
                while ((line = reader.readLine()) != null) {
                    message += line;
                    Log.d("TAG3", message);
                }
                reader.close();

                ArrayList<JSONObject> weather2 = new ArrayList<>();
                ArrayList<JSONObject> weather3 = new ArrayList<>();
                JSONObject zipObject1 = new JSONObject(message.toString());
                JSONArray weatherArray = zipObject1.getJSONArray("list");

                mins = new String[5];
                maxs = new String[5];
                dates = new String[5];
                descriptions = new String[5];
                weather.clear();
                //You were having an issue here before, if you are using a global variable, you have to clear the array to update the new points or
                // else it would just add new points and take the first five from it.


                for (int i = 0; i < 40; i += 8) {
                    Log.d("ArrayListWeather123", String.valueOf(weatherArray.getJSONObject(i)));
                    weather.add(weatherArray.getJSONObject(i));
                }
                for (int i = 0; i < 5; i++) {
                    weather2.add(weather.get(i).getJSONObject("main"));
                    Log.d("Before Min Values", Arrays.toString(mins));
                    mins[i] = weather2.get(i).get("temp_min").toString();
                    Log.d("After Min Values", Arrays.toString(mins));
                    maxs[i] = weather2.get(i).get("temp_max").toString();
                    currentTemp = weather2.get(0).get("temp").toString();
                    Log.d("Temp", currentTemp.toString());
                }
                Log.d("length", String.valueOf(weather.size()));
                for (int i = 0; i < 5; i++) {
                    Log.d("WeatherI", String.valueOf(weather.get(i).get("weather")));
                    weather3.add(weather.get(i).getJSONArray("weather").getJSONObject(0));
                    descriptions[i] = weather3.get(i).get("description").toString();
                }

                for (int i = 0; i < 5; i++) {
                    dates[i] = weather.get(i).get("dt_txt").toString();
                }

                Log.d("ArrayListWeather", weather.toString());
                Log.d("ArrayListWeather3", weather3.toString());
                Log.d("Min", Arrays.toString(mins));
                Log.d("Max", Arrays.toString(maxs));
                Log.d("Current Temperature", currentTemp.toString());
                Log.d("Description", Arrays.toString(descriptions));
                Log.d("Dates", Arrays.toString(dates));


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            latUI.setText("Latitude: " + lat);
            lonUI.setText("Longitude: " + lon);
            locUI.setText("Location: " + name + ", " + country);
            cT.setText("Current Temperature: " + currentTemp);

            WeatherArrayList.clear();
            for (int i = 0; i < 5; i++) {
                Weather w = new Weather(dates[i], descriptions[i], mins[i], maxs[i]);
                WeatherArrayList.add(w);
                Log.d("WeatherList", String.valueOf(WeatherArrayList.size()));
            }
            if (descriptions[0].contains("cloud")) {
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.nezukocloud));
                quote.setText("Amidst the drifting clouds,each moment is a chance for renewal and growth.");
            } else if (descriptions[0].contains("sun")) {
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.zenistusun));
                quote.setText("Bathed in the golden rays of the sun, even the darkest shadows fade away, " +
                        "leaving only warmth and hope in its wake.");
            } else if (descriptions[0].contains("snow")) {
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.tanjirosnowy));
                quote.setText("In the hush of falling snow, the world is blanketed in serenity, " +
                        "each flake a testament to the beauty of perseverance.");
            } else if (descriptions[0].contains("rain")) {
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.giyurain));
                quote.setText("In the relentless downpour, we find our strength tested, " +
                        "each raindrop a reminder of our resilience against life's tempests.");
            } else if (descriptions[0].contains("clear")) {
                image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.clearshinobu));
                quote.setText("Beneath the vast expanse of the clear sky, we find solace in its purity," +
                        " a canvas untouched by sorrow or strife, where dreams take flight.");
            }

            CustomAdapter adapter = new CustomAdapter(MainActivity.this, R.layout.adapter_layout, WeatherArrayList);
            listView.setAdapter(adapter);
        }

        public class CustomAdapter extends ArrayAdapter<Weather> {
            List list;
            Context context;
            int xmlResource;

            public CustomAdapter(Context context, int resource, ArrayList<Weather> objects) {
                super(context, resource, objects);
                xmlResource = resource;
                list = objects;
                this.context = context;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                View adapterLayout = layoutInflater.inflate(xmlResource, null);
                String date1 = getItem(position).getDate();
                String wc1 = getItem(position).getCondition();
                String minimum1 = getItem(position).getMinimum();
                String maximum1 = getItem(position).getMaximum();
                TextView date = adapterLayout.findViewById(R.id.Date);
                ImageView image1 = adapterLayout.findViewById(R.id.imageViewAdapter);
                TextView wc = adapterLayout.findViewById(R.id.weatherCondition);
                TextView minimum = adapterLayout.findViewById(R.id.minimum);
                TextView maximum = adapterLayout.findViewById(R.id.maximum);
                wc.setText(wc1);
                date.setText(date1.split(" ")[0]);
                if (wc1.contains("cloud")) {
                    Log.d("wc1", wc1);
                    image1.setImageResource(R.drawable.nezukocloud);
                } else if (wc1.contains("sun")) {
                    image1.setImageResource(R.drawable.zenistusun);
                } else if (wc1.contains("snow")) {
                    image1.setImageResource(R.drawable.tanjirosnowy);
                } else if (wc1.contains("rain")) {
                    image1.setImageResource(R.drawable.giyurain);
                } else if (wc1.contains("clear")) {
                    image1.setImageResource(R.drawable.clearshinobu);
                }
                maximum.setText(maximum1);
                minimum.setText(minimum1);

                return adapterLayout;
            }
        }
    }
}
