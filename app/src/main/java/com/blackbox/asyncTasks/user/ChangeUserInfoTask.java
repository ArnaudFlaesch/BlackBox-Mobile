package com.blackbox.asyncTasks.user;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.UserInfoActivity;
import com.blackbox.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Arnaud on 30/09/2017.
 */

public class ChangeUserInfoTask  extends AsyncTask<Void, Void, ResponseBody> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String URL = NetworkUtils.SERVICES_URL + "/user/update";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String name;
    private String firstname;
    private String email;

    public ChangeUserInfoTask(String userId, String name, String firstname, String email) {
        this.userId = userId;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            JSONObject jObjectData = new JSONObject();
            jObjectData.put("_id", this.userId);
            jObjectData.put("name", this.name);
            jObjectData.put("firstname", this.firstname);
            jObjectData.put("email", this.email);

            RequestBody body = RequestBody.create(JSON, jObjectData.toString());
            Request request = new Request.Builder()
                    .url(this.URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
            return (null);
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONObject result = new JSONObject(responseBody.string());
            UserInfoActivity.getChangeUserInfoTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}