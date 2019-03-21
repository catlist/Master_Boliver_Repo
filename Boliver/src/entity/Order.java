package entity;

import org.json.JSONObject;

public class Order {
	
	private String orderId;
	private String robotId;
	private String userId;
	private String orderStatus;
	private String origin;
	private String destination;
	private String aArrival;
	private String eArrival;
	private String createTime;
	private String cost;


	/**
	 * This is a builder pattern in Java.
	 */
	
	private Order(OrderBuilder builder) {
		this.orderId = builder.orderId;
		this.robotId = builder.robotId;
		this.userId = builder.userId;
		this.orderStatus = builder.orderStatus;
		this.origin = builder.origin;
		this.destination = builder.destination;
		this.aArrival = builder.aArrival;
		this.eArrival = builder.eArrival;
		this.createTime = builder.createTime;
		this.cost = builder.cost;
	}
	
	public static class OrderBuilder {
		
		private String orderId;
		private String robotId;
		private String userId;
		private String orderStatus;
		private String origin;
		private String destination;
		private String aArrival;
		private String eArrival;
		private String createTime;
		private String cost;
		
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public void setRobotId(String robotId) {
			this.robotId = robotId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public void setaArrival(String aArrival) {
			this.aArrival = aArrival;
		}
		public void seteArrival(String eArrival) {
			this.eArrival = eArrival;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public void setCost(String cost) {
			this.cost = cost;
		}
		
		public Order build() {
			return new Order(this);
		}
	}


	public String getOrderId() {
		return orderId;
	}


	public String getRobotId() {
		return robotId;
	}


	public String getUserId() {
		return userId;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public String getOrigin() {
		return origin;
	}


	public String getDestination() {
		return destination;
	}


	public String getaArrival() {
		return aArrival;
	}


	public String geteArrival() {
		return eArrival;
	}


	public String getCreateTime() {
		return createTime;
	}
	
	public String getCost() {
		return cost;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("order_id", orderId);
			obj.put("robot_id", robotId);
			obj.put("user_id", userId);
			obj.put("cost", cost);
			obj.put("orderStatus", orderStatus);
			obj.put("origin",origin);
			obj.put("destination", destination);
			obj.put("a_arrival", aArrival);
			obj.put("e_arrival", eArrival);
			obj.put("create_time", createTime);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return obj;
	}
	
}

