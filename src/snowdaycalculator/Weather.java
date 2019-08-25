package snowdaycalculator;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
public class Weather {
	public Weather() {
		
	}
	public static void getWeather(int zipcode) {
		try {
			URLConnection connection = new URL("api.openweathermap.org/data/2.5/weather?zip={"+zipcode+"},{}").openConnection();
		    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		    connection.connect();
		    
		    BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
		    
		    StringBuilder sb = new StringBuilder();
		    String line;
		    while ((line = r.readLine()) != null) {
		        sb.append(line);
		    }
		    
		    String get = sb.toString();
		    System.out.println(get);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		getWeather(13078);
	}
}
