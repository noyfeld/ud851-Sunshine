/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;
import com.example.android.sunshine.utilities.SunshineDateUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;
    private TextView mUrlDisplayTextView;
    Handler h ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);
        mUrlDisplayTextView=(TextView)findViewById(R.id.tvUrlDisplay);

        h = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==8){
                    mUrlDisplayTextView.setText((String)msg.obj);
                    return true;
                }else {
                    return false;
                }
            }
        });

        loadWeatherData();

    }

    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData
    private void loadWeatherData(){
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new FetchWeatherTask().execute(location);
    }

    private class FetchWeatherTask extends AsyncTask<String,Void,String[]>{
        @Override
        protected String[] doInBackground(String... params) {
            URL mForecastUrl = NetworkUtils.buildUrl(params[0]);

            Message msg = new Message();
            msg.what=8;
            msg.obj=mForecastUrl.toString();

            h.sendMessage(msg);

            String rawResult = null;
            try {
                rawResult=NetworkUtils.getResponseFromHttpUrl(mForecastUrl);
                return OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson(MainActivity.this,rawResult);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String[] arrayResult) {

            if (arrayResult!=null) {
                StringBuilder sb = new StringBuilder("");
                for (String s : arrayResult){
                    sb.append(s).append("\n\n");
                }
                mWeatherTextView.setText(sb.toString());
            }
        }
    }
    // TODO (5) Create a class that extends AsyncTask to perform network requests
    // TODO (6) Override the doInBackground method to perform your network requests
    // TODO (7) Override the onPostExecute method to display the results of the network request
}