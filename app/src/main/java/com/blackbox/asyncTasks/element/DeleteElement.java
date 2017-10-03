package com.blackbox.asyncTasks.element;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.HomeActivity;
import com.blackbox.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Arnaud on 01/10/2017.
 */

public class DeleteElement extends AsyncTask<Void, Void, ResponseBody> {

    private final String URL = NetworkUtils.SERVICES_URL + "/element/deleteElement";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String elementName;
    private String path;

    public DeleteElement(String userId, String elementName, String path) {
        this.userId = userId;
        this.elementName = elementName;
        this.path = path;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
            urlBuilder.addQueryParameter("userId", this.userId);
            urlBuilder.addQueryParameter("elementName", this.elementName);
            urlBuilder.addQueryParameter("path", this.path);

            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .delete()
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException error) {
            Log.e("ERROR", error.getMessage());
            return (null);
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONObject result = new JSONObject(responseBody.string());
            HomeActivity.getDeleteElementTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
