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

public class SharedFolderTask extends AsyncTask<Void, Void, ResponseBody> {

    private String URL = NetworkUtils.SERVICES_URL + "/element/sharedFolders";
    private OkHttpClient client = new OkHttpClient();

    private String userId;

    public SharedFolderTask(String userId) {
        this.userId = userId;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
            urlBuilder.addQueryParameter("userId", this.userId);

            Request request = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException error) {
            Log.e("ERROR", error.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONObject result = new JSONObject(responseBody.string());
            HomeActivity.getSharedFoldersTaskResult(result);
        } catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
