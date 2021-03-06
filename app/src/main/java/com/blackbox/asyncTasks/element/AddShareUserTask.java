package com.blackbox.asyncTasks.element;

import android.os.AsyncTask;
import android.util.Log;

import com.blackbox.activities.HomeActivity;
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
 * Created by Arnaud on 01/10/2017.
 */

public class AddShareUserTask extends AsyncTask<Void, Void, ResponseBody> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String URL = NetworkUtils.SERVICES_URL + "/element/saveSharedUser";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private String elementName;
    private String path;
    private String sharedUserEmail;

    public AddShareUserTask(String userId, String elementName, String path, String sharedUserEmail) {
        this.userId = userId;
        this.elementName = elementName;
        this.path = path;
        this.sharedUserEmail = sharedUserEmail;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            JSONObject jObjectData = new JSONObject();
            jObjectData.put("userId", this.userId);
            jObjectData.put("elementName", this.elementName);
            jObjectData.put("path", this.path);
            jObjectData.put("sharedUserEmail", this.sharedUserEmail);

            RequestBody body = RequestBody.create(JSON, jObjectData.toString());
            Request request = new Request.Builder()
                    .url(this.URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return (response.body());

        } catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(final ResponseBody responseBody) {
        try {
            JSONObject result = new JSONObject(responseBody.string());
            HomeActivity.getAddShareUserTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
