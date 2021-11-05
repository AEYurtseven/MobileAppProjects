package com.example.weatherapiapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {

    Context context;
    String cityId;

    String QUERY_FOR_CITY_ID ="https://www.metaweather.com/api/location/search/?query=";
    String  QUERY_FOR_CITY_WEATHER_BY_ID = "https://www.metaweather.com/api/location/";

    public WeatherDataService(Context context){
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String message);
    }



    public void getCityId(String cityName,VolleyResponseListener volleyResponseListener){


        String  url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                cityId = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityId = cityInfo.getString("woeid");
                    //
                    volleyResponseListener.onResponse(cityId);
                } catch (JSONException e) {
                    volleyResponseListener.onResponse("Something wrong !");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface ForeCastByIDResponse{
        void onError(String message);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public void  getCityForecastByID(String cityID, ForeCastByIDResponse foreCastByIDResponse){
        List<WeatherReportModel> weatherReportModels = new ArrayList<>();
        String url = QUERY_FOR_CITY_WEATHER_BY_ID + cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();

                try {
                    JSONArray consolidatedWeatherList = response.getJSONArray("consolidated_weather");
                    //get the first item  in the array


                    for(int i = 0;i< consolidatedWeatherList.length();i++){

                        WeatherReportModel one_day_weather = new WeatherReportModel();
                        JSONObject first_day_from_api = (JSONObject)consolidatedWeatherList.get(i);
                        one_day_weather.setId(first_day_from_api.getInt("id"));
                        one_day_weather.setWeather_state_name(first_day_from_api.getString("weather_state_name"));
                        one_day_weather.setWeather_state_abbr(first_day_from_api.getString("weather_state_abbr"));
                        one_day_weather.setWind_direction_compass(first_day_from_api.getString("wind_direction_compass"));
                        one_day_weather.setCreated(first_day_from_api.getString("created"));
                        one_day_weather.setApplicable_date(first_day_from_api.getString("applicable_date"));
                        one_day_weather.setMin_temp((float) first_day_from_api.getDouble("min_temp"));
                        one_day_weather.setMax_temp((float) first_day_from_api.getDouble("max_temp"));
                        one_day_weather.setThe_temp((float) first_day_from_api.getDouble("the_temp"));
                        one_day_weather.setWind_speed((float) first_day_from_api.getDouble("wind_speed"));
                        one_day_weather.setWind_direction((float) first_day_from_api.getDouble("wind_direction"));
                        one_day_weather.setAir_pressure((float) first_day_from_api.getDouble("air_pressure"));
                        one_day_weather.setHumidity(first_day_from_api.getInt("humidity"));
                        one_day_weather.setVisibility((float) first_day_from_api.getDouble("visibility"));
                        one_day_weather.setPredictability(first_day_from_api.getInt("predictability"));
                        weatherReportModels.add(one_day_weather);
                    }



                    foreCastByIDResponse.onResponse(weatherReportModels);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface GetCityForecastByNameCallback{
        void onError(String name);
        void onResponse(List<WeatherReportModel> weatherReportModels);
    }

    public List<WeatherReportModel> getCityForecastByName(String cityName , GetCityForecastByNameCallback getCityForecastByNameCallback){
        getCityId(cityName, new VolleyResponseListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(String cityId) {
                getCityForecastByID(cityId, new ForeCastByIDResponse() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        getCityForecastByNameCallback.onResponse(weatherReportModels);
                    }
                });
            }
        });
        return null;
    }

}
