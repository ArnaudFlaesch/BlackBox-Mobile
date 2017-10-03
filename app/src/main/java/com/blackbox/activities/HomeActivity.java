package com.blackbox.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.blackbox.R;
import com.blackbox.adapters.GridAdapter;
import com.blackbox.asyncTasks.element.DirectoryTask;
import com.blackbox.model.Element;
import com.blackbox.model.User;
import com.blackbox.utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    /**
     * UI
     */
    public static GridView gridview;

    /**
     * Tasks
     */
    private DirectoryTask directoryTask = null;

    public static Context context;

    private String userId;
    private static String pageTitle = "";
    private static String currentPath = "";
    private static String currentFolder = "";
    private static User userData = new User();
    private static ArrayList<Element> navigationBar = new ArrayList<Element>();
    private static Boolean isSharedFolderPage = false;
    private static String newElementName = "";
    private static FileUtils fileUtils = new FileUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();

        if (getIntent().getStringExtra("userId") == null && this.userId == null) {
            Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
            this.getApplicationContext().startActivity(intent);
        } else {
            this.userId = getIntent().getStringExtra("userId");
            // get user info
            //set user data
            userData.set_id(this.userId);
            this.displayPersonnalFolder();
        }

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.d("CLICK", "Click " + position);
            }
        });

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(false);
        }
    }

    public void displayPersonnalFolder() {
        pageTitle = "Dossier personnel";
        isSharedFolderPage = false;
        currentPath = "";
        currentFolder = userData.get_id();
        displayFolderContents(currentFolder, currentPath);
    }

    public void displaySharedFolders() {
        pageTitle = "Dossiers partagés";
        isSharedFolderPage = true;
        currentFolder = "";
        currentPath = "";
        /*this.fileService.getSharedFolders(this._userData._id)
                .then(elementList => {
                this._fileUtils.elementList = elementList;
        this._fileUtils.filterList();
        this.navigationBar = this.fileUtils.createSharedNavigationTab(this.currentPath, this.currentFolder);
            })
            .catch(error => this._error = error);*/
    }

    public void displayFolderContents(String elementName, String path) {
        directoryTask = new DirectoryTask(this.userId, elementName, path);
        directoryTask.execute((Void) null);
    }

    public void navigateToFolder(String elementName) {
        String path;
        if (currentPath.equals("") && currentFolder.equals("")) {
            path = "";
        } else {
            path = currentPath += "/" + currentFolder;
        }
        this.displayFolderContents(elementName, path);
    }

    public void getElement(String elementName, Boolean isFolder) {
        if (!isFolder) {
            String path;
            if (currentPath.equals("") && currentFolder.equals("")) {
                path = "";
            } else {
                path = currentPath += "/" + currentFolder;
            }
        } else {
            this.navigateToFolder(elementName);
        }
    }

    public static void getUserInfoTaskResult(JSONObject userDataJSON) {
        //int =
    }

    public static void getDirectoryTaskResult(String elementName, String path, JSONArray foldersJson) {
        currentFolder = elementName;
        currentPath = path;

        ArrayList<Element> elementList = new ArrayList<Element>();
        try {
            for (int i = 0; i < foldersJson.length(); i++) {
                Element element = new Element(foldersJson.getJSONObject(i).getString("name"));
                element.setFolder(foldersJson.getJSONObject(i).getBoolean("isFolder"));
                element.setPath(foldersJson.getJSONObject(i).getString("path"));
                elementList.add(element);
            }
            gridview.setAdapter(new GridAdapter(context, elementList));
            fileUtils.setElementList(elementList);
            fileUtils.filterList();
            navigationBar = (isSharedFolderPage) ? fileUtils.createSharedNavigationTab(currentPath, currentFolder) : fileUtils.createNavigationTab(currentPath, currentFolder);

        } catch (JSONException error) {
            Log.e("ERROR", error.getMessage());
        }

    }

    public static void getSharedFoldersTaskResult(JSONObject foldersJson) {

    }

    public static void getMoveElementTaskResult(JSONObject foldersJson) {

    }

    public static void getNewFolderTaskResult(JSONObject foldersJson) {

    }

    public static void getNewFileTaskResult(JSONObject foldersJson) {

    }

    public static void getRenameElementTaskResult(JSONObject foldersJson) {

    }

    public static void getSharedUsersListTaskResult(JSONObject foldersJson) {

    }

    public static void getAddShareUserTaskResult(JSONObject foldersJson) {

    }

    public static void getDeletedSharedUserTaskResult(JSONObject foldersJson) {

    }

    public static void getDeleteElementTaskResult(JSONObject foldersJson) {

    }

}
