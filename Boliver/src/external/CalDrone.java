package external;

import java.util.HashMap;
import java.util.Map;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.GeoLocation;

public class CalDrone {
	public static Map<String, Integer> calculateDrone(GeoLocation origin, GeoLocation dest) {
		Map<String, Integer> map = new HashMap<>();
		DBConnection conn = DBConnectionFactory.getConnection();
		try {
			int speed = Integer.parseInt(conn.getDroneSpeed("drone")); 
			int distance = calculateDistance(origin,dest);
			int time = distance/speed;
			map.put("distance", distance);
			map.put("time", time);
			return map;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int calculateDistance(GeoLocation origin, GeoLocation dest) {
		// Calculate distance given two pairs of lat/lon coordinates
		
		return 360;
	}

}
