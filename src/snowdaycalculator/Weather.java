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
import java.util.*;
public class Weather {
	public Weather() {
		
	}
	public static NodeList getWeather(int zipcode) {
		try {
			String key = "";
			/*System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
			BufferedReader apiReader = new BufferedReader(new FileReader(new File("../resources/apikey.txt")));
			key=apiReader.readLine();
			apiReader.close();
		    */
			key = "37fdd7c46ca515fc4b1a10c205022244";
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
	 private static void printNote(NodeList nodeList, int a,PredictionData predictionInfo) {
		 	//1 == not yet reached the time, 2 == has reached the time, 3 == has passed the time
		 	int hasReachedNearestMorningData = a;
		 	int start = 13;
			int end = 13;
		    for (int count = 0; count < nodeList.getLength(); count++) {
				Node tempNode = nodeList.item(count);
				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
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
						if(end <= 12){
							predictionInfo.setCondition(predictionInfo.getCondition()+start+"-"+end+": ");
						}
						
						//checks if that time of the data is within that morning range
						if(start <= 12 && end <= 12 && hasReachedNearestMorningData != 3) {
							//System.out.println("\nIs within the nearest morning time");
							//System.out.println("Start: "+start);
							//System.out.println("End: "+end);
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
						//System.out.println("Node: "+tempNode.getNodeName());
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							//checks if there is precipitation in the time period
							if(tempNode.getNodeName().equals("precipitation") && node.getNodeName().equals("value")) {
								predictionInfo.setPrecipPresent(true);
								predictionInfo.setPrecipAmount(predictionInfo.getPrecipAmount()+Double.parseDouble(node.getNodeValue()));
							}
							//gets min and max temps
							if(tempNode.getNodeName().equals("temperature")){
								if(node.getNodeName().equals("max")) {
									if(predictionInfo.getTempHigh() == 0) {
										predictionInfo.setTempHigh(Double.parseDouble(node.getNodeValue()));
									}
									else if(predictionInfo.getTempHigh() < Double.parseDouble(node.getNodeValue())) {
										predictionInfo.setTempHigh(Double.parseDouble(node.getNodeValue()));
									}
								}
								else if(node.getNodeName().equals("min")){
									if(predictionInfo.getTempLow() == 0) {
										predictionInfo.setTempLow(Double.parseDouble(node.getNodeValue()));
									}
									else if(predictionInfo.getTempLow() > Double.parseDouble(node.getNodeValue())) {
										predictionInfo.setTempLow(Double.parseDouble(node.getNodeValue()));
									}
								}
							}
							if(tempNode.getNodeName().equals("windSpeed") && node.getNodeName().equals("mps")) {
								if(predictionInfo.getWindSpeed() == -1) {
									predictionInfo.setWindSpeed(Double.parseDouble(node.getNodeValue()));
								}
								else{
									predictionInfo.setWindSpeed((predictionInfo.getWindSpeed()+Double.parseDouble(node.getNodeValue()))/2);
								}
							}
							//gets the weather conditions
							if(tempNode.getNodeName().equals("symbol") && node.getNodeName().equals("name")){
								predictionInfo.setCondition(predictionInfo.getCondition()+node.getNodeValue()+"\n");
							}
							//gets the humidity
							if(tempNode.getNodeName().equals("humidity") && node.getNodeName().equals("value")){
								if(predictionInfo.getHumidity() == -1){
									predictionInfo.setHumidity(Double.parseDouble(node.getNodeValue()));
								}
								else {
									predictionInfo.setHumidity((predictionInfo.getHumidity()+Double.parseDouble(node.getNodeValue()))/2);
								}
							}
						}
					}
					if (tempNode.hasChildNodes()) {
						// loop again if has child nodes
						printNote(tempNode.getChildNodes(),hasReachedNearestMorningData,predictionInfo);
					}
					
				}
		    }
		  }
	public static void getZipSpecifications(int zipcode) {
		String info = "";
		try (Scanner scanner = new Scanner(new File("resources/zipCodeInfo.csv"));) {
		    while (scanner.hasNextLine()) {
		    	String temp = scanner.nextLine();
		    	//System.out.println(temp.substring(0,5));
		    	if(temp.substring(0,5).matches("[0-9]+") && Integer.parseInt(temp.substring(0,5)) == zipcode) {
		    		info = temp;
		    		break;
		    	}
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		double popDensity = Double.parseDouble(info.substring(info.lastIndexOf(",")+1));
		info = info.substring(0,info.lastIndexOf(","));
		double areaOfZip = Double.parseDouble(info.substring(info.lastIndexOf(",")+1));
		System.out.println("Pop density: "+popDensity);
		System.out.println("Area of zipcode: "+areaOfZip);
		info = "";
		try (Scanner scanner = new Scanner(new File("resources/zipCodeInfo2.csv"));) {
		    while (scanner.hasNextLine()) {
		    	String temp = scanner.nextLine();
		    	//System.out.println(temp.substring(0,5));
		    	if(temp.substring(0,5).matches("[0-9]+") && Integer.parseInt(temp.substring(0,5)) == zipcode) {
		    		info = temp;
		    		break;
		    	}
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		double lon = Double.parseDouble(info.substring(info.lastIndexOf(",")+1));
		System.out.println("lon: "+lon);
		double lat = Double.parseDouble(info.substring(info.lastIndexOf(";")+1,info.lastIndexOf(",")));
		System.out.println("lat: "+lat);
		info = info.substring(info.indexOf(";")+1);
		String loc = info.substring(0,info.indexOf(";"))+" "+info.substring(info.indexOf(";")+1,info.indexOf(";")+3);
		System.out.println(loc);
	}
	public static void main(String[] args) {
		PredictionData weatherInfo = new PredictionData();
		printNote(getWeather(13066),1,weatherInfo);
		System.out.println("Is there precipitation: "+weatherInfo.isPrecipPresent());
		System.out.println("Amount of precipitation: "+weatherInfo.getPrecipAmount());
		System.out.println("Max Temp: "+weatherInfo.getTempHigh());
		System.out.println("Min Temp: "+weatherInfo.getTempLow());
		System.out.println(weatherInfo.getCondition());
		System.out.println("Average wind speed: "+weatherInfo.getWindSpeed());
		getZipSpecifications(13078);
	}
	public static PredictionData processZip(int zip) {
		PredictionData weatherInfo = new PredictionData();
		printNote(getWeather(zip),1,weatherInfo);
		Calculate calc = new Calculate(weatherInfo);
		calc.snowDayChance();
		return weatherInfo;
	}
}
