package snowdaycalculator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Prediction
 */
@WebServlet("/Prediction")
public class Prediction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Prediction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response, boolean isValid, String errorMessage) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			try {
				if (!isValid) {
					out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Accurate Snowday Prediction</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h1> INVALID ZIPCODE ENTERED </h1>");
					out.println("<h3>error: " + errorMessage + "</h3>");
					out.println("<button onclick=\"goBack()\">Go Back</button>");
					out.println("</body>");
					out.println("</html>");
					out.println("<script>");
					out.println("function goBack() {");
					out.println("window.history.back()");
					out.println("}");
					out.println("</script>");
				} else {
					PredictionData data = Weather.processZip(Integer.parseInt(request.getParameter("zipcode")));
					double chance = data.getSnowDayChance();
					
					out.println("<!DOCTYPE html>\r\n" + 
							"<html>\r\n" + 
							"  <head>\r\n" + 
							"    <title>Accurate Snow Day Predictor</title>\r\n" + 
							"    <link rel=\"icon\" href=\"./images/icon.png\">\r\n" + 
							"    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">\r\n" + 
							"    <meta charset=\"UTF-8\">\r\n" + 
							"    <meta name=\"keywords\" content=\"snow,day,snowday,snow day calculator,snow day predictor,calculate snow day,predict snow day\">\r\n" + 
							"    <meta name=\"description\" content=\"Want to know if you will have a snow day tomorrow? Tired of inaccurate predictions? Try this highly accurate snow day calculator!\">\r\n" + 
							"    <meta name=\"language\" content=\"EN\">\r\n" + 
							"    <meta name=\"author\" content=\"Tristan Lonsway, Brian Hulbert\">\r\n" + 
							"    <!-- This website uses the snowstorm animation effect created by Scott Schiller, available at https://github.com/scottschiller/snowstorm/-->\r\n" + 
							"    <script src=\"./javascript/snowstorm.js\"></script>\r\n" + 
							"    <script async src=\"https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>\r\n" + 
							"    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\r\n" + 
							"    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js\"></script>\r\n" + 
							"    <meta name=\"google-site-verification\" content=\"Fu9jPP9wy3xYHwDDYe1NQUQORm9JYV23KLj7BYRUawo\" />\r\n" + 
							"    <!-- This website uses google AdSense just to maintain the server space, sorry :( -->\r\n" + 
							"    <script>\r\n" + 
							"      (adsbygoogle = window.adsbygoogle || []).push({\r\n" + 
							"        google_ad_client: \"ca-pub-1900731882126031\",\r\n" + 
							"        enable_page_level_ads: true\r\n" + 
							"      });\r\n" + 
							"    </script>\r\n" + 
							"    <!-- Global site tag (gtag.js) - Google Analytics -->\r\n" + 
							"    <script async src=\"https://www.googletagmanager.com/gtag/js?id=UA-146513691-1\"></script>\r\n" + 
							"    <script>\r\n" + 
							"      window.dataLayer = window.dataLayer || [];\r\n" + 
							"      function gtag(){dataLayer.push(arguments);}\r\n" + 
							"      gtag('js', new Date());\r\n" + 
							"\r\n" + 
							"      gtag('config', 'UA-146513691-1');\r\n" + 
							"    </script>\r\n" + 
							"    <link href=\"https://fonts.googleapis.com/css?family=Fira+Sans&display=swap\" rel=\"stylesheet\">\r\n" + 
							"  </head>\r\n" + 
							"        <body style=\"background-color:#7abbff\"> <!-- #deeeff, #99ccff -->\r\n" + 
							"      <div class=\"titleBlock\" style=\"margin-bottom:50px;margin-top:40px;text-align:center\">\r\n" + 
							"        <h1 style=\"margin-bottom:5px\">Accurate Snow Day Predictor</h1>\r\n" + 
							"        <h3 style=\"margin-top:0px\">Tired of inaccurate results? Try this data oriented prediction tool!</h3>\r\n" + 
							"      </div>\r\n" + 
							"      <div name=\"predictArea\" style=\"text-align:center\">\r\n" + 
							"        <div name=\"dataSection\" style=\"text-align:center;display:inline-block;margin-top:0px;width:33%\">\r\n" + 
							"        	<p>ZIPCODE: " + data.getZipcode() + "</p>\r\n" + 
							"            <p>City: " + data.getCounty() + "</p>\r\n" + 
							"            <p>State: " + data.getState() + "</p>\r\n" + 
							"            <p>Latitude: " + data.getLatitude() + "</p>\r\n" + 
							"            <p>Longitude: " + data.getLongitude() + "</p>\r\n" + 
							"            <p>Area(m^2): </p> \r\n" + 
							"            <p>Pop. Density: </p>\r\n" + 
							"            <p>Pred. Temperature Low: " + Calculate.convertKtoF(data.getTempLow()) + "</p>\r\n" +
							"            <p>Pred. Temperature High: " + Calculate.convertKtoF(data.getTempHigh()) + "</p>\r\n" +
							"            <p>Precip. Amount: " + data.getPrecipAmount() + "</p>\r\n" + 
							"            <p>Rel. humidity: " + data.getHumidity() + "</p>\r\n" + 
							"            <p>Wind speed: " + data.getWindSpeed() + "</p>\r\n" + 
							"            <p>Will Snow Stick: " + Calculate.willSnowStick(data.getHumidity(), Calculate.convertKtoC(data.getTempLow()))+ "</p>\r\n" + 
							"            <p>Wind chill: " + Calculate.calculateWindChill(data.getWindSpeed(), Calculate.convertKtoF(data.getTempLow())) + "</p>\r\n" + 
							"        </div>\r\n" + 
							"        <div name=\"predictionData\" style=\"align:center;display:inline-block;margin-top:100px\">\r\n" + 
							"          <div class=\"resultText\">\r\n" + 
							"            <t class=\"percentChance\" style=\"font-size:50px\"> " + (int)((100*chance)+.5) + "%</t>\r\n" + 
							"          </div>\r\n" + 
							"          <div class=\"container;width=400px\">\r\n" + 
							"          <div class=\"progress\" style=\"height:50px\">\r\n" + 
							"            <div class=\"progress-bar progress-bar-striped active\" role=\"progressbar\" style=\"width:" + (int)(400*chance) + "px\">\r\n" + 
							"            </div>\r\n" + 
							"            <div class=\"progress-bar progress-bar-success\" role=\"progressbar\" style=\"width:" + (400-((int)(400*chance))) + "px\">\r\n" + 
							"            </div>\r\n" + 
							"          </div>\r\n" + 
							"          </div>\r\n" + 
							"        </div>\r\n" + 
							"        <div name=\"twitterBox\" style=\"margin-right:0px;display:inline-block;width:33%;height:0px;vertical-align:top;margin-top:0px\">\r\n" + 
							"          <a class=\"twitter-timeline\" data-lang=\"en\" data-width=\"300\" data-height=\"600\" data-dnt=\"true\" data-theme=\"light\" href=\"https://twitter.com/NWS?ref_src=twsrc%5Etfw\">Tweets by NWS</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\r\n" + 
							"        </div>\r\n" + 
							"      </div>\r\n" + 
							"      <div name=\"cloudMap\" style=\"text-align:center;margin-right:400px;margin-top:74px;position:relative\">\r\n" + 
							"        <img src=\"./images/worldMap.jpg\" width=\"400\" height=\"400\" class=\"worldMap\">\r\n" + 
							"        <img style=\"-webkit-user-select: none;margin: auto;cursor: zoom-in;\" src=\"./backendGenerated/precipMap.png\" width=\"400\" height=\"400\" class=\"clouds\">\r\n" + 
							"      </div>\r\n" + 
							"    <body>\r\n" + 
							"</html>\r\n" + 
							"<style>\r\n" + 
							"  .worldMap {\r\n" + 
							"    position: absolute;\r\n" + 
							"  }\r\n" + 
							"  .clouds {\r\n" + 
							"    position: absolute;\r\n" + 
							"  }\r\n" + 
							"  body {\r\n" + 
							"    font-family: 'Fira Sans', serif;\r\n" + 
							"  }\r\n" + 
							" .progress-bar-success {	 background-color: #a4a4a4  }</style>\r\n" + 
							"<script>\r\n" + 
							"<!-- Change snow animation style -->\r\n" + 
							"snowStorm.snowColor = '#ffffff'; //#99ccff\r\n" + 
							"snowStorm.flakesMaxActive = 64;\r\n" + 
							"snowStorm.excludeMobile = false;\r\n" + 
							"snowStorm.followMouse = false;\r\n" + 
							"snowStorm.snowCharacter = '*';\r\n" + 
							"</script>");
							
				}
			} finally {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("retrieved zip code:" + request.getParameter("zipcode"));
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
			doGet(request,response,false,"data entered was non numerical, please enter numerical data");
			return;	
		}
		//CHECK FOR INVALID NUMERICAL VALUE FOR ZIPCODE
		if (zcode<501 || zcode>99950) {
			System.out.println("Invalid zipcode entered, entry out of valid zipcode range");
			doGet(request,response,false,"zipcode entered is not a valid US zipcode");
			return;
		}
		System.out.println("valid zipcode received");
		doGet(request,response,true,"");
	}

}
