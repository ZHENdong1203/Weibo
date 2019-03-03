package com.example.a85161.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class WeatherDetail extends Activity {
    RequestQueue queue = null;
    RequestQueue queueSearch = null;
    private TextView cityName;
    private TextView cityDate;
    private TextView cityLat;
    private TextView cityLon;
    private TextView cityProvince;
    private TextView cityFeel;
    private TextView cityTemp;
    private TextView cityLow;
    private TextView cityHigh;
    private TextView cityStatus;
    private TextView cityWindDir;
    private TextView cityWindPow;
    private TextView cityWindV;
    private TextView cityHumidity;
    private TextView cityPcpn;
    private TextView cityPress;
    private TextView cityVis;
    private TextView cityCloud;
    private TextView cityConf;
    private TextView citySuggest;
    private EditText weatherSearch;
    private ImageButton weatherSeaBtn;
    private Button weatherBack;
    private Button forecastOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_detail);

        weatherBack=(Button)findViewById(R.id.weather_back_btn);
        weatherBackListener weatherBackListener=new weatherBackListener();
        weatherBack.setOnClickListener(weatherBackListener);

        queue = Volley.newRequestQueue(this);
        queueSearch = Volley.newRequestQueue(this);
        cityName=(TextView)findViewById(R.id.city_name);
        cityDate=(TextView)findViewById(R.id.city_date);
        cityLat=(TextView)findViewById(R.id.city_lat_count);
        cityLon=(TextView)findViewById(R.id.city_lon_count);
        cityProvince=(TextView)findViewById(R.id.city_pro_count);
        cityFeel=(TextView)findViewById(R.id.city_feel_count);
        cityTemp=(TextView)findViewById(R.id.city_temp_count);
        cityLow=(TextView)findViewById(R.id.city_low_temp_count);
        cityHigh=(TextView)findViewById(R.id.city_high_temp_count);
        cityStatus=(TextView)findViewById(R.id.city_weather_statue_count);
        cityWindDir=(TextView)findViewById(R.id.city_wind_direction_count);
        cityWindPow=(TextView)findViewById(R.id.city_wind_power_count);
        cityWindV=(TextView)findViewById(R.id.city_wind_v_count);
        cityHumidity=(TextView)findViewById(R.id.city_humidity_count);
        cityPcpn=(TextView)findViewById(R.id.city_pcpn_count);
        cityPress=(TextView)findViewById(R.id.city_press_count);
        cityVis=(TextView)findViewById(R.id.city_vis_count);
        cityCloud=(TextView)findViewById(R.id.city_cloud_count);
        cityConf=(TextView)findViewById(R.id.city_conf_count);
        citySuggest=(TextView)findViewById(R.id.city_suggest_count);
        weatherSearch=(EditText)findViewById(R.id.weather_search);
        weatherSeaBtn=(ImageButton)findViewById(R.id.weather_search_btn);
        weatherSearchListener weatherSearchListener = new weatherSearchListener();
        weatherSeaBtn.setOnClickListener(weatherSearchListener);
        forecastOpen=(Button)findViewById(R.id.forecast_open_btn);
        weatherForecastOpenListener weatherForecastOpenListener = new weatherForecastOpenListener();
        forecastOpen.setOnClickListener(weatherForecastOpenListener);

        String urlDetail = "https://free-api.heweather.com/s6/weather?location=changsha&key=5735efbb14f84bf48392a90d1f8be9e7";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlDetail, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                System.out.println(jsonObject);
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean.class);
                String cityname = weatherBean.getHeWeather6().get(0).getBasic().getLocation();
                String citylat = weatherBean.getHeWeather6().get(0).getBasic().getLat();
                String citylon = weatherBean.getHeWeather6().get(0).getBasic().getLon();
                String cityPro = weatherBean.getHeWeather6().get(0).getBasic().getAdmin_area();
                String date = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getDate();
                String maxtemp = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max();
                String mintemp = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min();
                String feel = weatherBean.getHeWeather6().get(0).getNow().getFl();
                String temp = weatherBean.getHeWeather6().get(0).getNow().getTmp();
                String cond = weatherBean.getHeWeather6().get(0).getNow().getCond_txt();
                String dir = weatherBean.getHeWeather6().get(0).getNow().getWind_dir();
                String pow = weatherBean.getHeWeather6().get(0).getNow().getWind_sc();
                String wv = weatherBean.getHeWeather6().get(0).getNow().getWind_spd();
                String hum = weatherBean.getHeWeather6().get(0).getNow().getHum();
                String pcpn = weatherBean.getHeWeather6().get(0).getNow().getPcpn();
                String pres = weatherBean.getHeWeather6().get(0).getNow().getPres();
                String vis = weatherBean.getHeWeather6().get(0).getNow().getVis();
                String cloud = weatherBean.getHeWeather6().get(0).getNow().getCloud();
                String conf = weatherBean.getHeWeather6().get(0).getLifestyle().get(0).getBrf();
                String suggest = weatherBean.getHeWeather6().get(0).getLifestyle().get(0).getTxt();



                cityName.setText(cityname);
                cityDate.setText(date);
                cityLat.setText(citylat);
                cityLon.setText(citylon);
                cityProvince.setText(cityPro);
                cityFeel.setText(feel);
                cityTemp.setText(temp);
                cityHigh.setText(maxtemp);
                cityLow.setText(mintemp);
                cityStatus.setText(cond);
                cityWindDir.setText(dir);
                cityWindPow.setText(pow);
                cityWindV.setText(wv);
                cityHumidity.setText(hum);
                cityPcpn.setText(pcpn);
                cityPress.setText(pres);
                cityVis.setText(vis);
                cityCloud.setText(cloud);
                cityConf.setText(conf);
                citySuggest.setText(suggest);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        queue.add(request);

    }

    class weatherBackListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    class weatherSearchListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String location = weatherSearch.getText().toString();
            InputMethodManager imm =(InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(weatherSearch.getWindowToken(), 0);
            String urlSearch = "https://free-api.heweather.com/s6/weather?location=" + location + "&key=5735efbb14f84bf48392a90d1f8be9e7";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlSearch, (String) null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    System.out.println(jsonObject);
                    Gson gsonSearch = new Gson();
                    WeatherBean weatherBean = gsonSearch.fromJson(jsonObject.toString(), WeatherBean.class);
                    String cityname = weatherBean.getHeWeather6().get(0).getBasic().getLocation();
                    String citylat = weatherBean.getHeWeather6().get(0).getBasic().getLat();
                    String citylon = weatherBean.getHeWeather6().get(0).getBasic().getLon();
                    String cityPro = weatherBean.getHeWeather6().get(0).getBasic().getAdmin_area();
                    String date = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getDate();
                    String maxtemp = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max();
                    String mintemp = weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min();
                    String feel = weatherBean.getHeWeather6().get(0).getNow().getFl();
                    String temp = weatherBean.getHeWeather6().get(0).getNow().getTmp();
                    String cond = weatherBean.getHeWeather6().get(0).getNow().getCond_txt();
                    String dir = weatherBean.getHeWeather6().get(0).getNow().getWind_dir();
                    String pow = weatherBean.getHeWeather6().get(0).getNow().getWind_sc();
                    String wv = weatherBean.getHeWeather6().get(0).getNow().getWind_spd();
                    String hum = weatherBean.getHeWeather6().get(0).getNow().getHum();
                    String pcpn = weatherBean.getHeWeather6().get(0).getNow().getPcpn();
                    String pres = weatherBean.getHeWeather6().get(0).getNow().getPres();
                    String vis = weatherBean.getHeWeather6().get(0).getNow().getVis();
                    String cloud = weatherBean.getHeWeather6().get(0).getNow().getCloud();
                    String conf = weatherBean.getHeWeather6().get(0).getLifestyle().get(0).getBrf();
                    String suggest = weatherBean.getHeWeather6().get(0).getLifestyle().get(0).getTxt();



                    cityName.setText(cityname);
                    cityDate.setText(date);
                    cityLat.setText(citylat);
                    cityLon.setText(citylon);
                    cityProvince.setText(cityPro);
                    cityFeel.setText(feel);
                    cityTemp.setText(temp);
                    cityHigh.setText(maxtemp);
                    cityLow.setText(mintemp);
                    cityStatus.setText(cond);
                    cityWindDir.setText(dir);
                    cityWindPow.setText(pow);
                    cityWindV.setText(wv);
                    cityHumidity.setText(hum);
                    cityPcpn.setText(pcpn);
                    cityPress.setText(pres);
                    cityVis.setText(vis);
                    cityCloud.setText(cloud);
                    cityConf.setText(conf);
                    citySuggest.setText(suggest);



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println(volleyError);
                }
            });
            queue.add(request);
        }
    }

    class weatherForecastOpenListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent forecastIntent = new Intent();
            forecastIntent.setClass(WeatherDetail.this,WeatherForecast.class);
            forecastIntent.putExtra("CityName",cityName.getText().toString());
            startActivity(forecastIntent);
        }
    }
}

