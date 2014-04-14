package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

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

	public static Font createFont(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to load font: " + path);
		}

		Font font = null;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException | IOException e) {
			System.out.println("Unable to load font: " + e.getMessage());
		}

		return font;
	}
}
