package com.blackbox.utils;

import com.blackbox.R;
import com.blackbox.model.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Arnaud on 01/10/2017.
 */

public class FileUtils {
    private String search = "";
    private List<Element> elementList = new ArrayList<Element>();
    private List<Element> searchList = null;

    public FileUtils() {}

    public ArrayList<Element> createNavigationTab(String currentPath, String currentFolder) {
        final ArrayList<Element> navigationBar = new ArrayList<Element>();
        if (currentPath != "") {
            final String[] folders = (currentPath + "/" + currentFolder).split("/");
            for (int ind = 0; ind < folders.length; ind++) {
                navigationBar.add(new Element(folders[ind]));
            }
            navigationBar.get(0).setTitle("Mon dossier");
            navigationBar.get(0).setPath("");
            for (int ind = 1; ind < folders.length; ind++) {
                if (navigationBar.get(ind - 1).getPath() != "") {
                    navigationBar.get(ind).setPath("/" + navigationBar.get(ind - 1).getPath() +  "/" + folders[ind]);
                } else {
                    navigationBar.get(ind).setPath("/" + folders[ind - 1]);
                }
                navigationBar.get(ind).setTitle(navigationBar.get(ind).getName());
            }
        } else {
            navigationBar.add(new Element(""));
            navigationBar.get(0).setTitle("Mon dossier");
            navigationBar.get(0).setPath("");
        }
        return (navigationBar);
    }

    public ArrayList<Element> createSharedNavigationTab(String currentPath, String currentFolder) {
        final ArrayList<Element> navigationBar = new ArrayList<Element>();
        String[] folders = (currentPath + "/" + currentFolder).split("/");
        navigationBar.add(new Element(""));
        navigationBar.get(0).setTitle("Dossiers partagés");
        navigationBar.get(0).setName("");
        navigationBar.get(0).setPath("");
        for (int ind = 0; ind < folders.length; ind++) {
            navigationBar.add(new Element(folders[ind]));
        }
        int folderIndex = 1;
        for (int ind = 0; ind < folders.length; ind++) {
            if (navigationBar.get(folderIndex - 1).getPath() != "") {
                navigationBar.get(folderIndex).setPath(navigationBar.get(folderIndex - 1).getPath());
            } else {
                navigationBar.get(folderIndex).setPath("");
            }
            navigationBar.get(folderIndex).setTitle(navigationBar.get(folderIndex).getName());
            folderIndex++;
        }
        return (navigationBar);
    }

    public void filterList() {
        this.searchList = this.elementList.stream().filter(element -> {
            boolean isAuthorized = element.getName().indexOf(this.search) != -1;
            return isAuthorized;
        })
        .collect(Collectors.toList());
    }

    public static int getIcon(String elementName, Boolean isFolder) {
        if (!isFolder) {
            final int index = (elementName.charAt(elementName.length() - 4) == '.') ? 3 : 4;
            switch (elementName.substring(elementName.length(), elementName.length() - index)) {
                case ("png"):
                case ("jpg"):
                    return (R.drawable.img_icon);
                case ("txt"):
                case ("doc"):
                case ("docx"):
                    return (R.drawable.file_icon);
                case ("mp4"):
                case ("avi"):
                case ("mkv"):
                case ("mov"):
                    return (R.drawable.video_icon);
                case ("pdf"):
                    return (R.drawable.pdf_icon);
                default :
                    return (R.drawable.file_icon);
            }
        } else {
            return (R.drawable.folder_icon);
        }
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }
}
