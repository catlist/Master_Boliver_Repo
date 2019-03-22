package recommendation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import entity.DistanceMatrix;
import entity.GeoLocation;
import external.CalDrone;
import external.GoogleAPI;
import rpc.RpcHelper;

/**
 * Servlet implementation class SearchRoute
 */
@WebServlet("/searchroute")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject input = RpcHelper.readJSONObject(request);
		try {
			String origin = input.getString("origin");
			String dest = input.getString("destination");
			
			// Convert address into lat,lon
			GeoLocation encoding_origin = GoogleAPI.getGeoEncoding(origin);
			GeoLocation encoding_dest = GoogleAPI.getGeoEncoding(dest);
			
			//Codes below are used to test whether GoogleAPI.getGeoEncoding is working
			//JSONObject obj_encoding = new JSONObject();
			//obj_encoding.put("lat_origin", encoding_origin.getLat())
			//            .put("lng_origin", encoding_origin.getLon())
			//            .put("lat_dest", encoding_dest.getLat())
			//            .put("lng_dest", encoding_dest.getLon());
			//RpcHelper.writeJsonObject(response, obj_encoding);
			 
			// Get travel distance and time for groundRobots
			List<DistanceMatrix> groundResult = GoogleAPI.getDistanceMatrix(encoding_origin, encoding_dest);
			
			// Get droneResult;
			Map<String, Integer> droneResult = CalDrone.calculateDrone(encoding_origin, encoding_dest);

			JSONObject obj = new JSONObject();
			obj.put("origin", origin)
			   .put("destination", dest)
			   .put("distance_drone", droneResult.get("distance") + " mi")
			   .put("time_needed_drone", droneResult.get("time") + " mins")
			   .put("distance_groundRobot:", groundResult.get(0).getDistance_text())
			   .put("time_needed__groundRobot:", groundResult.get(0).getDuration_text());
			RpcHelper.writeJsonObject(response, obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
