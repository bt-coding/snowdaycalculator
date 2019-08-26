package snowdaycalculator;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
public class Weather {
	public Weather() {
		
	}
	public static NodeList getWeather(int zipcode) {
		try {
			String key = "37fdd7c46ca515fc4b1a10c205022244\r\n";
			/*
			URLConnection connection = new URL("https://api.openweathermap.org/data/2.5/forecast?zip="+zipcode+"&mode=xml&&APPID="+key).openConnection();
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
		    */
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("https://api.openweathermap.org/data/2.5/forecast?zip="+zipcode+"&mode=xml&&APPID="+key);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
			return nList;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 private static void printNote(NodeList nodeList, int a) {
		 	//1 == not yet reached the time, 2 == has reached the time, 3 == has passed the time
		 	int hasReachedNearestMorningData = a;
		    for (int count = 0; count < nodeList.getLength(); count++) {
				Node tempNode = nodeList.item(count);
				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
					// get node name and value
					
					int start = 13;
					int end = 13;
					if(tempNode.getNodeName().equals("time") && (hasReachedNearestMorningData == 1 || hasReachedNearestMorningData == 2)) {
						//gets the start and end times 
						NamedNodeMap nodeMap = tempNode.getAttributes();
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							if( node.getNodeName().equals("from")){
								start = Integer.parseInt(node.getNodeValue().substring(node.getNodeValue().indexOf("T")+1,node.getNodeValue().indexOf(":")));
								//System.out.println("Start: "+start);
							}
							else if(node.getNodeName().equals("to")) {
								end = Integer.parseInt(node.getNodeValue().substring(node.getNodeValue().indexOf("T")+1,node.getNodeValue().indexOf(":")));
								//System.out.println("End: "+end);
							}
						}
						//checks if that time of the data is within that morning range
						if(start <= 12 && end <= 12 && hasReachedNearestMorningData != 3) {
							System.out.println("\nIs within the nearest morning time");
							System.out.println("Start: "+start);
							System.out.println("End: "+end);
							if(hasReachedNearestMorningData == 1) {
								hasReachedNearestMorningData = 2;
							}
						}
						else if(hasReachedNearestMorningData == 2){
							hasReachedNearestMorningData = 3;
						}
					}
					//extracts the information for data within the relevant time zone
					if(hasReachedNearestMorningData == 2 && tempNode.hasAttributes() ) {
						NamedNodeMap nodeMap = tempNode.getAttributes();
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							//System.out.println("relevant information found");
							System.out.println("attr name : " + node.getNodeName());
							System.out.println("attr value : " + node.getNodeValue());
						}
					}
					
					if (tempNode.hasChildNodes()) {
						// loop again if has child nodes
						printNote(tempNode.getChildNodes(),hasReachedNearestMorningData);
					}
					
				}
		    }
		  }

	public static void main(String[] args) {
		printNote(getWeather(13078),1);
	}
}
