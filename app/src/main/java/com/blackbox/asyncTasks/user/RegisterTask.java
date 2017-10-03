package com.blackbox.asyncTasks.user;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.LoginActivity;
import com.blackbox.activities.RegisterActivity;
import com.blackbox.model.User;
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

public class RegisterTask extends AsyncTask<Void, Void, ResponseBody> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String URL = NetworkUtils.SERVICES_URL + "/user/register";
    private OkHttpClient client = new OkHttpClient();

    private final String mEmail;
    private final String mPassword;
    private final String mName;
    private final String mFirstName;

    public RegisterTask(String email, String password, String name, String firstName) {
        mEmail = email;
        mPassword = password;
        mName = name;
        mFirstName = firstName;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            JSONObject jObjectData = new JSONObject();
            jObjectData.put("firstname", this.mFirstName);
            jObjectData.put("name", this.mName);
            jObjectData.put("email", this.mEmail);
            jObjectData.put("password", this.mPassword);

            RequestBody body = RequestBody.create(JSON, jObjectData.toString());
            Request request = new Request.Builder()
                    .url(this.URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException | JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONObject result = new JSONObject(responseBody.string());
            RegisterActivity.getAsyncTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
