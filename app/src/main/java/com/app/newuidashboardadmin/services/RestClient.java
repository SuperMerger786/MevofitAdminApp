package com.app.newuidashboardadmin.services;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class RestClient {public int responseType = 0;
    public final WeakReference<Context> weakReference;
    public final com.app.newuidashboardadmin.services.Response resp;
    private String jsonRequest;

    public RestClient(Context context, com.app.newuidashboardadmin.services.Response res) {
        weakReference = new WeakReference<>(context);
        this.resp = res;
    }

    public void Communicate(String url, Object value, int responseType) {
        System.out.println("RestClient.Communicate url "+url);
        this.responseType = responseType;
        try {

                RequestQueue queue = Volley.newRequestQueue(weakReference.get());
                JSONObject jsonObject = getJsonObject(value);
                jsonRequest = jsonObject.toString();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                        jsonObject, createResponseListener(),
                        createErrorListener());
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                jsonObjectRequest.setShouldCache(false);
                queue.add(jsonObjectRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Response.Listener<JSONObject> createResponseListener() {

        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                resp.onResponseObtained(response, responseType, false);
            }
        };
    }

    private Response.ErrorListener createErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String jsonString;
                if (error.toString().contains("Timeout")) {
                    jsonString = "{\"msg\": \"TimeoutError\",\"status\": \"0\"}";
                } else if (error.toString().contains("NoConnectionError")) {
                    jsonString = "{\"msg\": \"NoConnectionError\",\"status\": \"0\"}";
                } else {
                    if (error.networkResponse != null)
                        jsonString = new String(error.networkResponse.data);
                    else
                        jsonString = error.getMessage();
                }
                resp.onErrorObtained(jsonString, responseType);

            }
        };
    }

    public JSONObject getJsonObject(Object obj) throws JSONException {
        Gson mapper = new Gson();
        String json = mapper.toJson(obj);

        System.out.println("2222222<<<<<<<<Value is here data " + json);
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject;
    }

    public static String loadJSONFromAsset(Context context, String pathName) {
        String json;
        try {
            InputStream is = context.getAssets().open(pathName + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public String getCachedValue(RequestQueue queue, JsonObjectRequest request) {
        Cache.Entry entry = queue.getCache().get(request.getCacheKey());

        if (entry != null && entry.data != null)
            return new String(entry.data);
        else
            return null;

    }

    public boolean isCacheRequestApplicable(int responeType) {
        // if (responeType == MegouserController.ROOTPAGEDETAIL
        // || responseType == MegouserController.NEXTPAGEDETAIL)
        // return true;
        // else
        return false;

    }

    public void writeToFile(String data) {
        try {
            FileOutputStream stream = new FileOutputStream(new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/file.txt"));
            try {
                stream.write(data.getBytes());
            } finally {

                stream.close();

            }
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
