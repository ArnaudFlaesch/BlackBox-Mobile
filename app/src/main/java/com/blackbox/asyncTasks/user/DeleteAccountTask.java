package com.blackbox.asyncTasks.user;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.UserInfoActivity;
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
 * Created by Arnaud on 30/09/2017.
 */

public class DeleteAccountTask extends AsyncTask<Void, Void, ResponseBody> {

    private final String URL = NetworkUtils.SERVICES_URL + "/user/delete";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String password;

    public DeleteAccountTask(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(URL).newBuilder();
            urlBuilder.addQueryParameter("userId", this.userId);
            urlBuilder.addQueryParameter("password", this.password);

            Request request = new Request.Builder()
                    .url(this.URL)
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
            UserInfoActivity.getDeletedAccountTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}