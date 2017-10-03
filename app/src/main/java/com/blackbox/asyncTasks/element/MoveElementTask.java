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

public class MoveElementTask extends AsyncTask<Void, Void, ResponseBody> {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String URL = NetworkUtils.SERVICES_URL + "/element/moveElement";
    private OkHttpClient client = new OkHttpClient();

    private String userId;
    private Boolean moveOrCopy;
    private String elementName;
    private String originFolder;
    private String originPath;
    private String destinationFolder;
    private String destinationPath;

    public MoveElementTask(String userId, Boolean moveOrCopy, String elementName, String originFolder, String originPath, String destinationFolder, String destinationPath) {
        this.userId = userId;
        this.moveOrCopy = moveOrCopy;
        this.elementName = elementName;
        this.originFolder = originFolder;
        this.originPath = originPath;
        this.destinationFolder = destinationFolder;
        this.destinationPath = destinationPath;
    }

    @Override
    protected ResponseBody doInBackground(Void... params) {
        try {
            JSONObject jObjectData = new JSONObject();
            jObjectData.put("userId", this.userId);
            jObjectData.put("moveOrCopy", this.moveOrCopy);
            jObjectData.put("elementName", this.elementName);
            jObjectData.put("originFolder", this.originFolder);
            jObjectData.put("originPath", this.originPath);
            jObjectData.put("destinationFolder", this.destinationFolder);
            jObjectData.put("destinationPath", this.destinationPath);

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
            HomeActivity.getMoveElementTaskResult(result);
        }
        catch (IOException | JSONException error) {
            Log.e("ERROR", error.getMessage());
        }
    }
}
