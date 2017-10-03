package com.blackbox.asyncTasks.element;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.HomeActivity;
import com.blackbox.utils.NetworkUtils;

import org.json.JSONArray;
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

public class DirectoryTask extends AsyncTask<Void, Void, ResponseBody> {

    private String URL = NetworkUtils.SERVICES_URL + "/element/directory";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String elementName;
    private String path;

    public DirectoryTask(String userId, String elementName, String path) {
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
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONArray result = new JSONArray(responseBody.string());
            HomeActivity.getDirectoryTaskResult(this.elementName, this.path, result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
