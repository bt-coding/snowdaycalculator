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
					out.println("<!DOCTYPE html>");
					out.println("<html>");
					out.println("<head>");
					out.println("<title>Accurate Snowday Prediction</title>");
					out.println("</head>");
					out.println("<body>");
					out.println("<h1> VALID ZIPCODE ENTERED </h1>");
					out.println("</body>");
					out.println("</html>");
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
		doGet(request,response,true,"");
	}

}
