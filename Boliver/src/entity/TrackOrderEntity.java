package entity;

import org.json.JSONObject;

public class TrackOrderEntity {
	private String orderId;
	private String robotId;
	private String robotType;
	private String currentLocation;
	private String origin;
	private String destination;
	private String e_arrival;
	private String create_time;
	private String cost; 

	private TrackOrderEntity(TrackOrderBuilder builder) {
		this.orderId = builder.orderId;
		this.robotId = builder.robotId;
		this.robotType=builder.robotType;
		this.currentLocation = builder.currentLocation;
		this.origin = builder.origin;
		this.destination = builder.destination;
		this.e_arrival = builder.e_arrival;
		this.create_time = builder.create_time;
		this.cost = builder.cost;

	}
	
	public static class TrackOrderBuilder {
		private String orderId;
		private String robotId;
		private String robotType;
		private String currentLocation;
		private String origin;
		private String destination;
		private String e_arrival;
		private String create_time;
		private String cost; 
		
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		
		public void setRobotId(String robotId) {
			this.robotId = robotId;
		}
		
		public void setRobotType(String robotType) {
			this.robotType = robotType;
		}
		
		public void setCurrentLocation(String currentLocation) {
			this.currentLocation = currentLocation;
		}
		
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		
		public void setDestination(String destination) {
			this.destination = destination;
		}
		
		public void setEstArrival(String e_arrival) {
			this.e_arrival = e_arrival;
		}
		
		public void setCreateTime(String create_time) {
			this.create_time = create_time;
		}
		
		public void setCost(String cost) {
			this.cost = cost;
		}	

		public TrackOrderEntity build() {
			return new TrackOrderEntity(this);
		}
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("order_id", orderId);
			obj.put("robot_id", robotId);
			obj.put("robot_type", robotType);
			obj.put("currentLocation", currentLocation);
			obj.put("origin", origin);
			obj.put("destination", destination);
			obj.put("estimated_arrival", e_arrival);
			obj.put("This order is created at: ", create_time);
			obj.put("cost", cost);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
