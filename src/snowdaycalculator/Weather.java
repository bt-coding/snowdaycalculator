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
		 	double tempLow;
			double tempHigh;
			double tempNorm;
			boolean precipPresent = false;
			double precipAmount = 0;
			boolean alertPresent;
			int alertSeverity;
			boolean stormPresent;
			String stormStartTime;
			String stormEndTime;
			double percentUnusual;
			double snowDayChance;
			double delayChance;
			int zipcode;
			double latitude;
			double longitude;
			String state;
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
						System.out.println("Node: "+tempNode.getNodeName());
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							//checks if there is precipitation in the time period
							if(tempNode.getNodeName().equals("precipitation") && node.getNodeName().equals("value")) {
								precipPresent = true;
								precipAmount += Double.parseDouble(node.getNodeValue());
							}
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
		    System.out.println("Precipitation present: "+precipPresent+"\nAmount of precipitaion: "+precipAmount);
		  }

	public static void main(String[] args) {
		printNote(getWeather(13078),1);
	}
}
