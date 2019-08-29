package snowdaycalculator;

import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageDownload implements Runnable {
	int waitMins = 10;
	String APIKEY = "";
	public ImageDownload() {
		try {
			BufferedReader apiReader = new BufferedReader(new FileReader(new File("resources/apikey.txt")));
			APIKEY = apiReader.readLine();
			apiReader.close();
		} catch (Exception e) {
			System.out.println("Failed to read API key");
			e.printStackTrace();
			return;
		}
		run();
	}
	public void run() {
		while(true) {
			System.out.println("Updating precipitation map");
			File imageLoc = new File("WebContent/backendGenerated/precipMap.png");
			URL url = null;
			try {
				url = new URL("https://tile.openweathermap.org/map/precipitation_new/0/0/0.png?appid=" + APIKEY);
			} catch (Exception e) {
				System.out.println("Failed to download API map image");
				e.printStackTrace();
				return;
			}
			if (imageLoc.exists()) {
				imageLoc.delete();
				while(imageLoc.exists()) {
					imageLoc.delete();
				}
			}
			try {
				BufferedImage newImage = ImageIO.read(url);
				ImageIO.write(newImage,"png",imageLoc);
			} catch (Exception e) {
				System.out.println("Failed to write new image");
				e.printStackTrace();
				return;
			}
			try {
				Thread.sleep(60000*waitMins);
			} catch (Exception e) {
				System.out.println("Thread waiting error in ImageDownload");
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		(new Thread(new ImageDownload())).start();		
	}
}