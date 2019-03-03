package com.example.a85161.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class WeatherForecast extends Activity {
    RequestQueue queueForecast = null;
    private Button forecastBack;
    private String forecastCityName;
    private TextView forecastName;
    //未来第一天
    private TextView forecast1Date;
    private TextView forecast1MorningSta;
    private TextView forecast1EveningSta;
    private TextView forecast1Low;
    private TextView forecast1High;
    private TextView forecast1SunRise;
    private TextView forecast1SunSet;
    private TextView forecast1MoonRise;
    private TextView forecastt1MoonSet;
    private TextView forecast1WindDir;
    private TextView forecast1WindPow;
    private TextView forecast1WindV;
    private TextView forecast1Hum;
    private TextView forecast1Pcpn;
    private TextView forecast1Pop;
    private TextView forecast1Press;
    private TextView forecast1Uv;
    private TextView forecast1Vis;
    //未来第二天
    private TextView forecast2Date;
    private TextView forecast2MorningSta;
    private TextView forecast2EveningSta;
    private TextView forecast2Low;
    private TextView forecast2High;
    private TextView forecast2SunRise;
    private TextView forecast2SunSet;
    private TextView forecast2MoonRise;
    private TextView forecastt2MoonSet;
    private TextView forecast2WindDir;
    private TextView forecast2WindPow;
    private TextView forecast2WindV;
    private TextView forecast2Hum;
    private TextView forecast2Pcpn;
    private TextView forecast2Pop;
    private TextView forecast2Press;
    private TextView forecast2Uv;
    private TextView forecast2Vis;
    //未来第三天
    private TextView forecast3Date;
    private TextView forecast3MorningSta;
    private TextView forecast3EveningSta;
    private TextView forecast3Low;
    private TextView forecast3High;
    private TextView forecast3SunRise;
    private TextView forecast3SunSet;
    private TextView forecast3MoonRise;
    private TextView forecastt3MoonSet;
    private TextView forecast3WindDir;
    private TextView forecast3WindPow;
    private TextView forecast3WindV;
    private TextView forecast3Hum;
    private TextView forecast3Pcpn;
    private TextView forecast3Pop;
    private TextView forecast3Press;
    private TextView forecast3Uv;
    private TextView forecast3Vis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_forecast);
        forecastBack=(Button)findViewById(R.id.weather_forecast_back_btn);
        weatherForecastBackListener weatherForecastBackListener = new weatherForecastBackListener();
        forecastBack.setOnClickListener(weatherForecastBackListener);
        Intent detailIntent=getIntent();
        forecastCityName=detailIntent.getStringExtra("CityName");
        forecastName=(TextView)findViewById(R.id.forecast_city_name);
        forecastName.setText(forecastCityName);

        queueForecast = Volley.newRequestQueue(this);
        //未来第一天
        forecast1Date=(TextView)findViewById(R.id.forecast1_date_count);
        forecast1MorningSta=(TextView)findViewById(R.id.forecast1_morning_status_count);
        forecast1EveningSta=(TextView)findViewById(R.id.forecast1_evening_status_count);
        forecast1Low=(TextView)findViewById(R.id.forecast1_temp_low_count);
        forecast1High=(TextView)findViewById(R.id.forecast1_temp_high_count);
        forecast1SunRise=(TextView)findViewById(R.id.forecast1_sunrise_count);
        forecast1SunSet=(TextView)findViewById(R.id.forecast1_sunset_count);
        forecast1MoonRise=(TextView)findViewById(R.id.forecast1_moonrise_count);
        forecastt1MoonSet=(TextView)findViewById(R.id.forecast1_moonset_count);
        forecast1WindDir=(TextView)findViewById(R.id.forecast1_wind_dir_count);
        forecast1WindPow=(TextView)findViewById(R.id.forecast1_wind_pow_count);
        forecast1WindV=(TextView)findViewById(R.id.forecast1_wind_v_count);
        forecast1Hum=(TextView)findViewById(R.id.forecast1_hum_count);
        forecast1Pcpn=(TextView)findViewById(R.id.forecast1_pcpn_count);
        forecast1Pop=(TextView)findViewById(R.id.forecast1_pop_count);
        forecast1Press=(TextView)findViewById(R.id.forecast1_press_count);
        forecast1Uv=(TextView)findViewById(R.id.forecast1_uv_index_count);
        forecast1Vis=(TextView)findViewById(R.id.forecast1_vis_count);
        //未来第二天
        forecast2Date=(TextView)findViewById(R.id.forecast2_date_count);
        forecast2MorningSta=(TextView)findViewById(R.id.forecast2_morning_status_count);
        forecast2EveningSta=(TextView)findViewById(R.id.forecast2_evening_status_count);
        forecast2Low=(TextView)findViewById(R.id.forecast2_temp_low_count);
        forecast2High=(TextView)findViewById(R.id.forecast2_temp_high_count);
        forecast2SunRise=(TextView)findViewById(R.id.forecast2_sunrise_count);
        forecast2SunSet=(TextView)findViewById(R.id.forecast2_sunset_count);
        forecast2MoonRise=(TextView)findViewById(R.id.forecast2_moonrise_count);
        forecastt2MoonSet=(TextView)findViewById(R.id.forecast2_moonset_count);
        forecast2WindDir=(TextView)findViewById(R.id.forecast2_wind_dir_count);
        forecast2WindPow=(TextView)findViewById(R.id.forecast2_wind_pow_count);
        forecast2WindV=(TextView)findViewById(R.id.forecast2_wind_v_count);
        forecast2Hum=(TextView)findViewById(R.id.forecast2_hum_count);
        forecast2Pcpn=(TextView)findViewById(R.id.forecast2_pcpn_count);
        forecast2Pop=(TextView)findViewById(R.id.forecast2_pop_count);
        forecast2Press=(TextView)findViewById(R.id.forecast2_press_count);
        forecast2Uv=(TextView)findViewById(R.id.forecast2_uv_index_count);
        forecast2Vis=(TextView)findViewById(R.id.forecast2_vis_count);
        //未来第三天
        forecast3Date=(TextView)findViewById(R.id.forecast3_date_count);
        forecast3MorningSta=(TextView)findViewById(R.id.forecast3_morning_status_count);
        forecast3EveningSta=(TextView)findViewById(R.id.forecast3_evening_status_count);
        forecast3Low=(TextView)findViewById(R.id.forecast3_temp_low_count);
        forecast3High=(TextView)findViewById(R.id.forecast3_temp_high_count);
        forecast3SunRise=(TextView)findViewById(R.id.forecast3_sunrise_count);
        forecast3SunSet=(TextView)findViewById(R.id.forecast3_sunset_count);
        forecast3MoonRise=(TextView)findViewById(R.id.forecast3_moonrise_count);
        forecastt3MoonSet=(TextView)findViewById(R.id.forecast3_moonset_count);
        forecast3WindDir=(TextView)findViewById(R.id.forecast3_wind_dir_count);
        forecast3WindPow=(TextView)findViewById(R.id.forecast3_wind_pow_count);
        forecast3WindV=(TextView)findViewById(R.id.forecast3_wind_v_count);
        forecast3Hum=(TextView)findViewById(R.id.forecast3_hum_count);
        forecast3Pcpn=(TextView)findViewById(R.id.forecast3_pcpn_count);
        forecast3Pop=(TextView)findViewById(R.id.forecast3_pop_count);
        forecast3Press=(TextView)findViewById(R.id.forecast3_press_count);
        forecast3Uv=(TextView)findViewById(R.id.forecast3_uv_index_count);
        forecast3Vis=(TextView)findViewById(R.id.forecast3_vis_count);

        String urlFore = "https://free-api.heweather.com/s6/weather?location=" + forecastCityName + "&key=5735efbb14f84bf48392a90d1f8be9e7";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlFore, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                System.out.println(jsonObject);
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(jsonObject.toString(), WeatherBean.class);

                //未来第一天
                String day1_date=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getDate();
                String day1_sr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getSr();
                String day1_ss=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getSs();
                String day1_mr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getMr();
                String day1_ms=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getMs();
                String day1_maxtmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_max();
                String day1_mintmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getTmp_min();
                String day1_mor_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_d();
                String day1_eve_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getCond_txt_n();
                String day1_wind_dir=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getWind_dir();
                String day1_wind_pow=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getWind_sc();
                String day1_wind_v=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getWind_spd();
                String day1_hum=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getHum();
                String day1_pcpn=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getPcpn();
                String day1_pop=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getPop();
                String day1_pres=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getPres();
                String day1_uv=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getUv_index();
                String day1_vis=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(0).getVis();

                //未来第二天
                String day2_date=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getDate();
                String day2_sr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getSr();
                String day2_ss=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getSs();
                String day2_mr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getMr();
                String day2_ms=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getMs();
                String day2_maxtmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getTmp_max();
                String day2_mintmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getTmp_min();
                String day2_mor_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getCond_txt_d();
                String day2_eve_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getCond_txt_n();
                String day2_wind_dir=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getWind_dir();
                String day2_wind_pow=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getWind_sc();
                String day2_wind_v=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getWind_spd();
                String day2_hum=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getHum();
                String day2_pcpn=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getPcpn();
                String day2_pop=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getPop();
                String day2_pres=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getPres();
                String day2_uv=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getUv_index();
                String day2_vis=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(1).getVis();

                //未来第三天
                String day3_date=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getDate();
                String day3_sr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getSr();
                String day3_ss=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getSs();
                String day3_mr=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getMr();
                String day3_ms=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getMs();
                String day3_maxtmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getTmp_max();
                String day3_mintmp=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getTmp_min();
                String day3_mor_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getCond_txt_d();
                String day3_eve_sta=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getCond_txt_n();
                String day3_wind_dir=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getWind_dir();
                String day3_wind_pow=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getWind_sc();
                String day3_wind_v=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getWind_spd();
                String day3_hum=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getHum();
                String day3_pcpn=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getPcpn();
                String day3_pop=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getPop();
                String day3_pres=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getPres();
                String day3_uv=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getUv_index();
                String day3_vis=weatherBean.getHeWeather6().get(0).getDaily_forecast().get(2).getVis();

                //未来第一天
                forecast1Date.setText(day1_date);
                forecast1MorningSta.setText(day1_mor_sta);
                forecast1EveningSta.setText(day1_eve_sta);
                forecast1High.setText(day1_maxtmp);
                forecast1Low.setText(day1_mintmp);
                forecast1SunRise.setText(day1_sr);
                forecast1SunSet.setText(day1_ss);
                forecast1MoonRise.setText(day1_mr);
                forecastt1MoonSet.setText(day1_ms);
                forecast1WindDir.setText(day1_wind_dir);
                forecast1WindPow.setText(day1_wind_pow);
                forecast1WindV.setText(day1_wind_v);
                forecast1Hum.setText(day1_hum);
                forecast1Pcpn.setText(day1_pcpn);
                forecast1Pop.setText(day1_pop);
                forecast1Press.setText(day1_pres);
                forecast1Uv.setText(day1_uv);
                forecast1Vis.setText(day1_vis);

                //未来第二天
                forecast2Date.setText(day2_date);
                forecast2MorningSta.setText(day2_mor_sta);
                forecast2EveningSta.setText(day2_eve_sta);
                forecast2High.setText(day2_maxtmp);
                forecast2Low.setText(day2_mintmp);
                forecast2SunRise.setText(day2_sr);
                forecast2SunSet.setText(day2_ss);
                forecast2MoonRise.setText(day2_mr);
                forecastt2MoonSet.setText(day2_ms);
                forecast2WindDir.setText(day2_wind_dir);
                forecast2WindPow.setText(day2_wind_pow);
                forecast2WindV.setText(day2_wind_v);
                forecast2Hum.setText(day2_hum);
                forecast2Pcpn.setText(day2_pcpn);
                forecast2Pop.setText(day2_pop);
                forecast2Press.setText(day2_pres);
                forecast2Uv.setText(day2_uv);
                forecast2Vis.setText(day2_vis);

                //未来第三天
                forecast3Date.setText(day3_date);
                forecast3MorningSta.setText(day3_mor_sta);
                forecast3EveningSta.setText(day3_eve_sta);
                forecast3High.setText(day3_maxtmp);
                forecast3Low.setText(day3_mintmp);
                forecast3SunRise.setText(day3_sr);
                forecast3SunSet.setText(day3_ss);
                forecast3MoonRise.setText(day3_mr);
                forecastt3MoonSet.setText(day3_ms);
                forecast3WindDir.setText(day3_wind_dir);
                forecast3WindPow.setText(day3_wind_pow);
                forecast3WindV.setText(day3_wind_v);
                forecast3Hum.setText(day3_hum);
                forecast3Pcpn.setText(day3_pcpn);
                forecast3Pop.setText(day3_pop);
                forecast3Press.setText(day3_pres);
                forecast3Uv.setText(day3_uv);
                forecast3Vis.setText(day3_vis);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError);
            }
        });
        queueForecast.add(request);
    }

    class weatherForecastBackListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }

}
