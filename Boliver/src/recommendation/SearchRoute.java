package recommendation;

import java.io.IOException;
import java.util.Arrays;
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
			String origin = input.getString("origin").replaceAll("\\s+", "+");
			String dest = input.getString("destination").replaceAll("\\s+", "+");
			
			// Convert address into lat,lon
			GeoLocation encoding_origin = GoogleAPI.getGeoEncoding(origin);
			GeoLocation encoding_dest = GoogleAPI.getGeoEncoding(dest);
			
			/*
			 * Codes here are used to test whether GoogleAPI.getGeoEncoding is working
			   JSONObject obj_encoding = new JSONObject();
			   obj_encoding.put("lat_origin", encoding_origin.getLat())
			            .put("lng_origin", encoding_origin.getLon())
			            .put("lat_dest", encoding_dest.getLat())
			            .put("lng_dest", encoding_dest.getLon());
			   RpcHelper.writeJsonObject(response, obj_encoding);
			 * */
			
			GeoLocation base1 = new GeoLocation(37.751200, -122.490463, "1935 32nd Ave, San Francisco, CA 94116");
			GeoLocation base2 = new GeoLocation(37.781316, -122.465367, "75 Geary Blvd, San Francisco, CA 94118");
			GeoLocation base3 = new GeoLocation(37.739150, -122.416729, "448 Cortland Ave, San Francisco, CA 94110");
			
			List<GeoLocation> robotBasesAvailable = Arrays.asList(new GeoLocation[] {base1, base2, base3});
			List<GeoLocation> DroneBasesAvailable = Arrays.asList(new GeoLocation[] {base2, base3});
			
			// Get the closest ground robot base and Distance Matrix based on travel time.
			GeoLocation baseClosestInTimeGround = null; // Select base based on the distance and availability.
			DistanceMatrix dmClosestInTimeGround = null;
			for (GeoLocation base : robotBasesAvailable) {
				DistanceMatrix dm = GoogleAPI.getDistanceMatrix(base, encoding_origin).get(0);
				
				if (baseClosestInTimeGround == null) {
					baseClosestInTimeGround = base;
					dmClosestInTimeGround = dm;
				} else {
					baseClosestInTimeGround = (dm.getDuration_seconds() < dmClosestInTimeGround.getDuration_seconds()) ? base : baseClosestInTimeGround;
					dmClosestInTimeGround = (dm.getDuration_seconds() < dmClosestInTimeGround.getDuration_seconds()) ? dm : dmClosestInTimeGround;
				}
			}
			
			// Get the closest drone base and Distance Matrix based on travel time.
			GeoLocation baseClosestInTimeDrone = null; // Select base based on the distance and availability.
			DistanceMatrix dmClosestInTimeDrone = null;
			for (GeoLocation base : DroneBasesAvailable) {
				DistanceMatrix dm = GoogleAPI.getDistanceMatrix(base, encoding_origin).get(0);
				
				if (baseClosestInTimeDrone == null) {
					baseClosestInTimeDrone = base;
					dmClosestInTimeDrone = dm;
				} else {
					baseClosestInTimeDrone = (dm.getDuration_seconds() < dmClosestInTimeDrone.getDuration_seconds()) ? base : baseClosestInTimeDrone;
					dmClosestInTimeDrone = (dm.getDuration_seconds() < dmClosestInTimeDrone.getDuration_seconds()) ? dm : dmClosestInTimeDrone;
				}
			}
			 
			// Get travel distance and time for groundRobots from pickup location to destination
			DistanceMatrix groundResultOriToDest = GoogleAPI.getDistanceMatrix(encoding_origin, encoding_dest).get(0);
			
			// Get travel distance and time for drones pickup location to destination
			Map<String, Double> droneResultOriToDest = CalDrone.calculateDrone(encoding_origin, encoding_dest);

			JSONObject obj = new JSONObject();
			obj.put("origin", encoding_origin.getAddress())
			   .put("destination", encoding_dest.getAddress())
			   .put("distance_drone", droneResultOriToDest.get("distance") + " mile")
			   .put("time_needed_drone", droneResultOriToDest.get("time") + " mins")
			   .put("distance_groundRobot:", groundResultOriToDest.getDistance_text())
			   .put("time_needed_groundRobot:", groundResultOriToDest.getDuration_text());
			if (baseClosestInTimeGround != null) {
				obj.put("ground_pickupTime", dmClosestInTimeGround.getDuration_seconds())
				   .put("ground_base", baseClosestInTimeGround.getAddress());
			}
			if (baseClosestInTimeGround != null) {
				obj.put("drone_pickupTime", dmClosestInTimeDrone.getDuration_seconds())
				   .put("drone_base", baseClosestInTimeDrone.getAddress());
			}
			RpcHelper.writeJsonObject(response, obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
