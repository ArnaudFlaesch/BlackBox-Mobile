package com.blackbox.asyncTasks.user;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.LoginActivity;
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

public class ChangePasswordTask extends AsyncTask<Void, Void, ResponseBody> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String URL = NetworkUtils.SERVICES_URL + "/user/updateUserPassword";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordTask(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            JSONObject jObjectData = new JSONObject();
            jObjectData.put("userId", this.userId);
            jObjectData.put("oldPassword", this.oldPassword);
            jObjectData.put("newPassword", this.newPassword);

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
            UserInfoActivity.getChangePasswordTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
