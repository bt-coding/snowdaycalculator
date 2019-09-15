package snowdaycalculator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PredictAPI
 */
@WebServlet("/PredictAPI")
public class PredictAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PredictAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorMessage = "";
		boolean isValid = true;
		int zcode = 0;
		String zipraw = request.getParameter("zipcode");
		System.out.println("zipcode data received:" + zipraw);
		//CHECK FOR NUMERICAL DATA IN ZIPCODE ENTRY
		try {
			zcode = Integer.parseInt(zipraw);
		} catch (NumberFormatException nfe) {
			System.out.println("Invalid zipcode received");
			if (zipraw.indexOf("..") != -1 || zipraw.indexOf("`") != -1) {
				System.out.println("POSSIBLE HACK DATA DETECTED");
			}
			errorMessage = "data entered was non numerical, please enter numerical data";
			isValid = false;
		}
		//CHECK FOR INVALID NUMERICAL VALUE FOR ZIPCODE
		if (zcode<501 || zcode>99950) {
			System.out.println("Invalid zipcode entered, entry out of valid zipcode range");
			errorMessage = "zipcode entered is not a valid US zipcode";
			isValid = false;
		}
		System.out.println("valid zipcode received");		
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			boolean valid2 = true;
			PredictionData data = null;
			try {
				data = Weather.processZip(Integer.parseInt(request.getParameter("zipcode")));
			} catch (Exception e) {
				if (errorMessage == "") {
					errorMessage = "BACKEND ERROR, we will try to resolve this as soon as possible";
				}
				valid2 = false;
			}
			try {
				if (!isValid || !valid2) {
					String JSONDATA = " {"
							+ "\"success\": \"NO\","
							+ "\"error\": \"" + errorMessage + "\""
							+ "}";
					out.println(JSONDATA);					
				} else {
					double chance = data.getSnowDayChance();
					String message = "";
					if (chance<=.30) {
						message = "Sorry, a snow day is highly unlikely :(";
					} else if (chance<=.5) {
						message = "A delay is possible, but not likely";
					} else if (chance<=.75) {
						message = "Decent chance of a delay, snowday unlikely but possible";
					} else if (chance<=.9) {
						message = "Likely delay, if not a snow day";
					} else {
						message = "Snow day highly likely! Good luck!";
					}
					String JSONDATA = "{\r\n" + 
							"  \"success\": \"YES\"," +
							"  \"chance\": \"" + chance + "\",\r\n" + 
							"  \"templow\": \"" + Calculate.convertKtoF(data.getTempLow()) + "\",\r\n" + 
							"  \"temphigh\": \"" + Calculate.convertKtoF(data.getTempHigh()) + "\",\r\n" + 
							"  \"unusual\": \"" + data.getStatUnusual() + "\",\r\n" + 
							"  \"lat\": \"" + data.getLatitude() + "\",\r\n" + 
							"  \"long\": \"" + data.getLongitude() + "\",\r\n" + 
							"  \"windchill\": \"" + Calculate.calculateWindChill(data.getWindSpeed(), Calculate.convertKtoF(data.getTempLow())) + "\",\r\n" + 
							"  \"willsnowstay\": \"" + Calculate.willSnowStick(data.getHumidity(), Calculate.convertKtoC(data.getTempLow())) + "\",\r\n" + 
							"  \"humidity\": \"" + data.getHumidity() + "\",\r\n" + 
							"  \"reason\": \"" + data.getReason() + "\",\r\n" + 
							"  \"message\": \"" + message + "\"\r\n" + 
							"}";
					
					out.println(JSONDATA);
							
				}
			} finally {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
