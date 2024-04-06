package com.example.national_parks.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.national_parks.controller.AppController;
import com.example.national_parks.model.Activities;
import com.example.national_parks.model.EntranceFees;
import com.example.national_parks.model.Images;
import com.example.national_parks.model.OperatingHours;
import com.example.national_parks.model.Park;
import com.example.national_parks.model.StandardHours;
import com.example.national_parks.model.Topics;
import com.example.national_parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getParks(final AsyncResponse callback,String stateCode) {
        String url = Util.getParksUrl(stateCode);
        Log.d("URL", "getParks: "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("data");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    Park park = new Park();
                    JSONObject jsonObject =jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setUrl(jsonObject.getString("url"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setDescription(jsonObject.getString("description"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setLatLong(jsonObject.getString("latLong"));


                    JSONArray ImageList = jsonObject.getJSONArray("images");
                    List<Images> imagesList = new ArrayList<>();
                    for (int j = 0; j < ImageList.length(); j++) {
                        Images images = new Images();
                        images.setCredit(ImageList.getJSONObject(j).getString("credit"));
                        images.setUrl(ImageList.getJSONObject(j).getString("url"));
                        images.setCaption(ImageList.getJSONObject(j).getString("caption"));
                        images.setTitle(ImageList.getJSONObject(j).getString("title"));
                        images.setAltText(ImageList.getJSONObject(j).getString("altText"));


                        imagesList.add(images);
                    }
                    park.setImages(imagesList);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setName(jsonObject.getString("name"));
                    park.setDesignation(jsonObject.getString("designation"));


                    // Setting UP Activites
                    JSONArray ActivityArr = jsonObject.getJSONArray("activities");
                    List<Activities> ActitvityList = new ArrayList<>();
                    for (int j = 0; j <ActivityArr.length(); j++) {
                        Activities acc = new Activities();
                        acc.setId(ActivityArr.getJSONObject(j).getString("id"));
                        acc.setName(ActivityArr.getJSONObject(j).getString("name"));
                        ActitvityList.add(acc);
                    }
                    park.setActivities(ActitvityList);

                    JSONArray TopicsArr = jsonObject.getJSONArray("topics");
                    List<Topics> topicsList = new ArrayList<>();
                    for (int j = 0; j < TopicsArr.length(); j++) {
                        Topics t1 = new Topics();
                        t1.setId(TopicsArr.getJSONObject(j).getString("id"));
                        t1.setName(TopicsArr.getJSONObject(j).getString("name"));
                        topicsList.add(t1);
                    }
                    park.setTopics(topicsList);

                    JSONArray entryArr = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> EntryFeesList = new ArrayList<>();
                    for (int j = 0; j < entryArr.length(); j++) {
                        EntranceFees E = new EntranceFees();
                        E.setCost(entryArr.getJSONObject(j).getString("cost"));
                        E.setDescription(entryArr.getJSONObject(j).getString("description"));
                        E.setTitle(entryArr.getJSONObject(j).getString("title"));
                        EntryFeesList.add(E);
                    }

                    park.setEntranceFees(EntryFeesList);

                    JSONArray OpHour = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> OP = new ArrayList<>();
                    for (int j = 0; j < OpHour.length(); j++) {
                        OperatingHours oo = new OperatingHours();
                        oo.setDescription(OpHour.getJSONObject(j).getString("description"));
                        oo.setName(OpHour.getJSONObject(j).getString("name"));
                        StandardHours sh = new StandardHours();
                        JSONObject hour = OpHour.getJSONObject(j).getJSONObject("standardHours");
                        sh.setMonday(hour.getString("monday"));
                        sh.setTuesday(hour.getString("tuesday"));
                        sh.setWednesday(hour.getString("wednesday"));
                        sh.setThursday(hour.getString("thursday"));
                        sh.setFriday(hour.getString("friday"));
                        sh.setSaturday(hour.getString("saturday"));
                        sh.setSunday(hour.getString("sunday"));
                        oo.setStandardHours(sh);
                        OP.add(oo);
                    }
                    park.setOperatingHours(OP);
                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));
                    park.setDirectionsUrl(jsonObject.getString("directionsUrl"));
                    parkList.add(park);
                }
                if(callback!=null) {callback.processPark(parkList);}
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, Throwable::getStackTrace);


        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
