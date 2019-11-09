package snowdaycalculator;
import java.net.*;
import java.util.*;
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
	final static String resourceloc = "B:/javaweb/snowdaycalculator/resources";
	public Weather() {
		
	}
	//The constructor method for the class
	public static NodeList getWeather(int zipcode) {
		String zip = Integer.toString(zipcode);
		//0 pads the zipcode
		while(zip.length() < 5) {
			zip = "0"+zip;
		}
		System.out.println(zip);
		try {
			String key = "";
			key = "37fdd7c46ca515fc4b1a10c205022244";			
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse("https://api.openweathermap.org/data/2.5/forecast?zip="+zip+"&mode=xml&&APPID="+key);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
			return nList;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * pulls information off of the openWeather map api
	 * extracts information out using an XML reader
	 * puts that information into the PredictionData class
	 * 
	 */
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
	public static void getZipSpecifications(int zipcode,PredictionData weatherInfo) {
		String info = "";
		String zip = Integer.toString(zipcode);
		try (Scanner scanner = new Scanner(new File(resourceloc + "/zipCodeInfo.csv"));) {
		    while (scanner.hasNextLine()) {
		    	String temp = scanner.nextLine();
		    	if(temp.substring(0,5).matches("[0-9]+") && temp.substring(0,temp.indexOf(",")).equals(zip)) {
		    		info = temp;
		    		System.out.println("Found the zipcode info");
		    		break;
		    	}
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(info.equals("")){
			System.out.println("zipcode not in the csv database");
		}
		else {
			double popDensity = Double.parseDouble(info.substring(info.lastIndexOf(",")+1));
			info = info.substring(0,info.lastIndexOf(","));
			double areaOfZip = Double.parseDouble(info.substring(info.lastIndexOf(",")+1));
			System.out.println("Pop density: "+popDensity);
			weatherInfo.setPopDensity(popDensity);
			System.out.println("Area of zipcode: "+areaOfZip);
			weatherInfo.setArea(areaOfZip);
		}
		info = "";
		try (Scanner scanner = new Scanner(new File(resourceloc + "/zipCodeInfo2.csv"));) {
		    while (scanner.hasNextLine()) {
		    	String temp = scanner.nextLine();
		    	if(temp.substring(0,5).matches("[0-9]+") && Integer.parseInt(temp.substring(0,temp.indexOf(";"))) == zipcode) {
		    		info = temp;
		    		break;
		    	}
		    }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(info.equals("")){
			System.out.println("zipcode not in the csv2 database");
		}
		else {
			info = info.substring(info.indexOf(";")+1);
			String city = info.substring(0,info.indexOf(";"));
			System.out.println(city);
			info = info.substring(info.indexOf(";")+1);
			String state = info.substring(0,info.indexOf(";"));
			info = info.substring(info.indexOf(";")+1);
			double lat = Double.parseDouble(info.substring(0,info.indexOf(";")));
			info = info.substring(info.indexOf(";")+1);
			double lon = Double.parseDouble(info.substring(0,info.indexOf(";")));
			System.out.println("lon: "+lon);
			weatherInfo.setLongitude(lon);
			System.out.println("lat: "+lat);
			weatherInfo.setLatitude(lat);
			weatherInfo.setState(state);
			weatherInfo.setCounty(city);
			System.out.println(weatherInfo.getCounty()+" "+weatherInfo.getState());
		}
	}
	public static void main(String[] args) {
		int zip = 5501;
		PredictionData weatherInfo = new PredictionData();
		printNote(getWeather(zip),1,weatherInfo);
		System.out.println("Is there precipitation: "+weatherInfo.isPrecipPresent());
		System.out.println("Amount of precipitation: "+weatherInfo.getPrecipAmount());
		System.out.println("Max Temp: "+weatherInfo.getTempHigh());
		System.out.println("Min Temp: "+weatherInfo.getTempLow());
		System.out.println(weatherInfo.getCondition());
		System.out.println("Average wind speed: "+weatherInfo.getWindSpeed());
		getZipSpecifications(zip,weatherInfo);
		getDataPoints(weatherInfo.getLongitude(),weatherInfo.getLatitude(),weatherInfo.getState(),1);
	}
	public static double[] getDataPoints(double lon, double lat, String state,int monthNum) {
		double[] dataPoints = new double[1];
		ArrayList<String> Stations;
		int monthLength = 0;
		for(int year = 2016; year <= 2018; year++) {
			//sorts all of the stations by distance to the input cords
			Stations = new ArrayList<String>();
			
	 		try (Scanner scanner = new Scanner(new File(resourceloc + "/snowfalldata/"+state+"/"+year+"/"+state+"-"+year+"-"+monthNum+"_STATION_COUNTY_SNOWFALL.csv"));) {
			scanner.nextLine();
	 			String numDays = scanner.nextLine();
	 			if(dataPoints.length == 1) {
	 				//the 5 nearest places for the past 3 years
	 				monthLength = countOccurances(numDays,",")-5;
	 				dataPoints = new double[3*5*monthLength];
	 			}
	 			while (scanner.hasNextLine()) {
			    	String temp = scanner.nextLine();
			    	double distance = getDistance(lat,lon,Double.parseDouble(temp.substring(nthIndexOf(temp,",",4)+1,nthIndexOf(temp,",",5))),Double.parseDouble(temp.substring(nthIndexOf(temp,",",5)+1,nthIndexOf(temp,",",6))));
			    	if(Stations.size() == 0 && countOccurances(temp.substring(nthIndexOf(temp,",",6)+1),"M") <= 10) {
		    			Stations.add(temp+","+distance);
		    		}
			    	else {
			    		
				    	for(int i = 0; i < Stations.size();i++) {
				    		if(countOccurances(temp.substring(nthIndexOf(temp,",",6)+1),"M") <= 10 && distance < Double.parseDouble(Stations.get(i).substring(Stations.get(i).lastIndexOf(",")+1))) {
				    			Stations.add(i,temp+","+distance);
				    			i = Stations.size();
				    		}
				    		else if(i == Stations.size()-1){
				    			Stations.add(temp+","+distance);
				    			i = Stations.size();
				    		}
				    	}
			    	}
	 			}
	 			//pulls out the top five closest stations and extracts the snow fall data
	 			//putting all of the data into an array of doubles
	 			//converting M's to -1 so they can be removed later and T to 0.01 inches of snow
	 			for(int i = 0; i < 5; i++) {
	 				String stationInfo = Stations.get(i);
	 				stationInfo = stationInfo.substring(nthIndexOf(stationInfo,",",6)+1,stationInfo.lastIndexOf(","));
	 				int count = 0;
	 				//System.out.println(stationInfo);
	 				while(!stationInfo.equals("")) {
	 					if(stationInfo.substring(0,1).equals("T")) {
	 						if(stationInfo.length() == 1) {
	 							stationInfo = "";
	 						}
	 						else {
	 							stationInfo = stationInfo.substring(stationInfo.indexOf(",")+1);
	 						}
	 						dataPoints[i*monthLength+count+((year-2016)*monthLength*5)] = 0.01;
	 					}
	 					else if(stationInfo.substring(0,1).equals("M")) {
	 						if(stationInfo.length() == 1) {
	 							stationInfo = "";
	 						}
	 						else {
	 							stationInfo = stationInfo.substring(stationInfo.indexOf(",")+1);
	 						}
	 						dataPoints[i*monthLength+count+((year-2016)*monthLength*5)] = -1;
	 					}
	 					else if(stationInfo.contains(",")) {
	 						dataPoints[i*monthLength+count+((year-2016)*monthLength*5)] = Double.parseDouble(stationInfo.substring(0,stationInfo.indexOf(",")));
	 						stationInfo = stationInfo.substring(stationInfo.indexOf(",")+1);
	 					}
	 					else{
	 						dataPoints[i*monthLength+count+((year-2016)*monthLength*5)] = Double.parseDouble(stationInfo);
	 						stationInfo = "";
	 					}
	 					count++;
	 				}
	 			}
			}
	 		catch(Exception e) {
	 			e.printStackTrace();
	 		}
		}
 		int numMs = 0;
 		//finds the number of missing data points
 		for(double e: dataPoints) {
 			if(e == -1) {
 				numMs++;
 			}
 		}
 		//creates a new array of doubles without the missing data points
 		double[] dataPointsExcludingMs = new double[dataPoints.length-numMs]; 
 		int count = 0;
 		for(int i = 0; i < dataPoints.length; i++){
 			if(dataPoints[i] != -1) {
 				dataPointsExcludingMs[i-count] = dataPoints[i];
 				
 			}
 			else {
 				count++;
 			}
 		}
 		/*for(double e: dataPointsExcludingMs) {
 			System.out.println(e);
 		}*/
 		return dataPointsExcludingMs;
	}
	// finds the x index of a specific sub string
	public static int nthIndexOf(String str, String substr, int n) {
	    int pos = str.indexOf(substr);
	    while (--n > 0 && pos != -1)
	        pos = str.indexOf(substr, pos + 1);
	    return pos;
	}
	//counts the number of occurrences of a specific substring 
	public static int countOccurances(String txt, String rep){
		return txt.length()-txt.replace(rep,"").length();
	}
	//The distance formula
	public static double getDistance(double x1,double y1,double x2,double y2) {
		return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
	}
	//Uses multiple methods to gather information on a specific zipcode
	public static PredictionData processZip(int zip) {
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		PredictionData weatherInfo = new PredictionData();
		printNote(getWeather(zip),1,weatherInfo);
		getZipSpecifications(zip,weatherInfo);
		Calculate calc = new Calculate(weatherInfo);
		calc.snowDayChance();
		weatherInfo.setZipcode(zip);
		return weatherInfo;
	}
}
