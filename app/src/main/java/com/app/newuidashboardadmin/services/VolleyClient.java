package com.app.newuidashboardadmin.services;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.newuidashboardadmin.AppController;
import com.app.newuidashboardadmin.IResponseUpdater;
import com.app.newuidashboardadmin.MyLogger;

import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class VolleyClient {

    private String TAG = "Mevo_Urgent";
    IResponseUpdater responseUpdater = null;
    Context activity;
    String finalUrl;

    public VolleyClient(Context activity, IResponseUpdater responseUpdater) {
        this.activity = activity;
        this.responseUpdater = responseUpdater;
    }

    public void cancelRequest() {
        if (reqeustJsonPost != null) {
            reqeustJsonPost.cancel();
        }

    }

    JsonObjectRequest reqeustJsonPost;

    public void makeRequest(String url, final String jsonRequestParam,
                            final String requestTag) {
//        try {
            try {
                finalUrl = new URL(url).toURI().toString();
//                finalUrl = url;
            } catch (Exception e1) {
                MyLogger.println("admin>>>>>0volleyclient=Exception=" + e1.getMessage());
            }
            JSONObject jsonObj = null;
            try {

                jsonObj = new JSONObject(jsonRequestParam);

                MyLogger.println("admin>>>>>1volleyclient Inside Request Param for " + requestTag
                        + " jsonObj : =" + jsonObj + "=== ==== " + url);
            } catch (Exception e) {
                MyLogger.println("admin>>>>>2Volley Client exception 1=" + e.getMessage());
            }

            reqeustJsonPost = new JsonObjectRequest(
                    Method.POST, finalUrl, jsonObj,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            MyLogger.println("admin>>>>>4volleyclient Inside  on response==" + response);
                            if (reqeustJsonPost != null && reqeustJsonPost.isCanceled()) {
                                return;
                            }
                            try {
                                responseUpdater.onServerResponseSuccess(requestTag, response.toString());
                            } catch (Exception e) {
                                MyLogger.println("admin>>>>>5volleyclient<<<<<response 2 = " + e.getMessage() + "========" + requestTag);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,"response "+error.toString());

                    responseUpdater.onServerResponseError(requestTag,
                            error.getMessage());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };


//            reqeustJsonPost.setRetryPolicy(new DefaultRetryPolicy(30000, 0, 1f));
            reqeustJsonPost.setShouldCache(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    reqeustJsonPost.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS
                            .toMillis(60), DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(reqeustJsonPost, requestTag);

                }
            }).start();

       /* } catch (Exception e) {
            MyLogger.println("admin>>>>>6Volley>>>>>>exceprtion>>" + e.toString());
        }*/

    }

    private JSONObject JSONObject(String jsonRequestParam) {
        // TODO Auto-generated method stub
        return null;
    }
}
