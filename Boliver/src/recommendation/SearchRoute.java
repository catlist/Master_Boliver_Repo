package recommendation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.DistanceMatrix;
import entity.GeoLocation;
import external.GoogleAPI;

/**
 * Servlet implementation class SearchRoute
 */
@WebServlet("/SearchRoute")
public class SearchRoute extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRoute() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		GeoLocation origin = new GeoLocation(37.367902, -121.986246);
		GeoLocation dest = new GeoLocation(37.367182, -121.994983);
		List<DistanceMatrix> searchResult = GoogleAPI.getDistanceMatrix(origin, dest);
		for (DistanceMatrix result : searchResult) {
			System.out.println(result.getDistance_text());
			System.out.println(result.getDuration_text());
		}

		response.getWriter().append("Served at: ").append(request.getContextPath()).append(" SearchRoute");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
