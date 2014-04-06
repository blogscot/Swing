package gui;

import javax.swing.*;
import java.net.URL;

public class Utils {

    public static String getFileExtension(String name) {

        int pointIndex = name.lastIndexOf(".");

        // dot not found
        if (pointIndex == -1) {
            return null;
        }

        // no extension found
        if (pointIndex == name.length() - 1) {
            return null;
        }

        return name.substring(pointIndex + 1, name.length());
    }

    public static ImageIcon createIcon(String path) {
        URL url = System.class.getResource(path);

        if (url == null) {
            System.err.println("Unable to load image: " + path);
        }

        return new ImageIcon(url);
    }
}
