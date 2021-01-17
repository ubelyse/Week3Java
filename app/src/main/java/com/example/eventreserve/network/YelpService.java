package com.example.eventreserve.network;

import com.example.eventreserve.Constants;
import com.example.eventreserve.models.Event;
import com.example.eventreserve.models.Events;
import com.example.eventreserve.models.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.eventreserve.Constants.YELP_BASE_URL;
import static com.example.eventreserve.Constants.YELP_TOKEN;


public class YelpService {
    public static void findEvents(String location, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.YELP_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Events> processResults(Response response){
        ArrayList<Events> events = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray("events");
            if (response.isSuccessful()){
                for (int i = 0; i < businessesJSON.length(); i++){
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    int attendingCount=restaurantJSON.getInt("attendingCount");
                    String category=restaurantJSON.getString("category");
                    String description=restaurantJSON.getString("description");
                    String eventSiteUrl=restaurantJSON.getString("eventSiteUrl");
                    String id=restaurantJSON.getString("id");
                    int interestedCount=restaurantJSON.getInt("interestedCount");
                    String ticketsUrl=restaurantJSON.getString("ticketsUrl");
                    String timeEnd=restaurantJSON.getString("timeEnd");
                    String timeStart=restaurantJSON.getString("timeStart");
                    ArrayList<String> location = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location").getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++){
                        location.add(addressJSON.get(y).toString());
                    }

                    String businessId=restaurantJSON.getString("businessId");

                    String imageUrl = restaurantJSON.getString("image_url");
                    double latitude = restaurantJSON.getJSONObject("coordinates").getDouble("latitude");
                    double longitude = restaurantJSON.getJSONObject("coordinates").getDouble("longitude");
                    Events event = new Events(attendingCount,category,description,eventSiteUrl,id,imageUrl,interestedCount,latitude,longitude,name, ticketsUrl,timeEnd,timeStart,location,businessId);
                    events.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    /*private static Retrofit retrofit = null;

    public static YelpApi getClient() {

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", YELP_TOKEN)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(YELP_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(YelpApi.class);
    }*/
}