package entity;

import java.sql.Timestamp;
import java.util.Date;

public class Order {

	private String userId;
	private String orderId;
	private String itemId;
	private String courierId;
	private Timestamp endTime;
	private String type;
	private String startAddressId;
	private String endAddressId;
	private double routeDuration;
	private double routeDistance;
	private double routePrice;
	private String routePath;
	private boolean complete;
	private boolean isRecommended;
	
	public Timestamp getEndTime() {
		return endTime;
	}

	public boolean isRecommended() {
		return isRecommended;
	}

	public String getUserId() {
		return userId;
	}
	
	public Timestamp getTimestamp() {
		return endTime;
	}

	public String getType() {
		return type;
	}

	public String getStartAddressId() {
		return startAddressId;
	}

	public String getEndAddressId() {
		return endAddressId;
	}


	public double getRouteDuration() {
		return routeDuration;
	}

	public double getRouteDistance() {
		return routeDistance;
	}

	public double getRoutePrice() {
		return routePrice;
	}

	public String getRoutePath() {
		return routePath;
	}

	public boolean isComplete() {
		return complete;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public String getCourierId() {
		return courierId;
	}
	
	private Order(OrderBuilder builder) {
		this.userId = builder.userId;
		this.orderId = builder.orderId;
		this.itemId = builder.itemId;
		this.courierId = builder.courierId;
		this.endTime = builder.endTime;
		this.type = builder.type;
		this.startAddressId = builder.startAddressId;
		this.endAddressId  = builder.endAddressId;
		this.routeDuration  = builder.routeDuration;
		this.routeDistance  = builder.routeDistance;
		this.routePrice  = builder.routePrice;
		this.routePath  = builder.routePath;
		this.complete = builder.complete;
		this.isRecommended = builder.isRecommended;
	}
		
	@Override
	public String toString() {
		
		String entire = "{" 
				 	  + "UserId: " + this.getUserId() 
					  + ", OrderId: " + this.getOrderId() 
					  + ", ItemId: "  + this.getItemId()
					  + ", CourierId: " + this.getCourierId()
					  + ", EndTime: " + this.getTimestamp()
					  + ", Type: "   + this.getType() 
					  + ", StartAddressId: " + this.getStartAddressId()
					  + ", EndAddressId: "+ this.getEndAddressId()
					  + ", RouteDuration: " + this.getRouteDuration()
					  + ", Distance: " + this.getRouteDistance() 
					  + ", RoutePrice: " + this.getRoutePrice()    
					  + ", RoutePath: " + this.getRoutePath() 
					  + ", Complete: " + this.isComplete() 	
					  + ", Recomended: " + this.isRecommended()
					  + "}";
		return entire;
	}

	
	public static class OrderBuilder {
		
		private String userId;
		private String orderId;
		private String itemId;
		private String courierId;
		private Timestamp endTime;
		private String type;
		private String startAddressId;
		private String endAddressId;
		private double routeDuration;
		private double routeDistance;
		private double routePrice;
		private String routePath;
		private boolean complete;
		private boolean isRecommended;

		public OrderBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		public OrderBuilder orderId(String orderId) {
			this.orderId = orderId;
			return this;
		}
		
		public OrderBuilder courierId(String courierId) {
			this.courierId = courierId;
			return this;
		}

		public OrderBuilder itemId(String itemId) {
			this.itemId = itemId;
			return this;
		}
		
		public OrderBuilder endTime(Timestamp endTime) {
			this.endTime = endTime;
			return this;
		}
		
		public OrderBuilder type(String type) {
			this.type = type;
			return this;
		}

		public OrderBuilder startAddressId(String startAddressId) {
			this.startAddressId = startAddressId;
			return this;
		}
		
		public OrderBuilder endAddressId(String endAddressId) {
			this.endAddressId = endAddressId;
			return this;
		}
		
		public OrderBuilder routeDuration(double routeDuration) {
			this.routeDuration = routeDuration;
			return this;
		}
		
		public OrderBuilder routeDistance(double routeDistance) {
			this.routeDistance = routeDistance;
			return this;
		}

		public OrderBuilder routePrice(double routePrice) {
			this.routePrice = routePrice;
			return this;
		}
		
		public OrderBuilder routePath(String routePath) {
			this.routePath = routePath;
			return this;
		}
		
		public OrderBuilder complete(boolean complete) {
			this.complete = complete;
			return this;
		}
		
		public OrderBuilder isRecommended(boolean isRecommended) {
			this.isRecommended = isRecommended;
			return this;
		}
		
		public Order build() {
			return new Order(this);
		}
	}
	
	public static void main(String [] args) {
		Order ord = new Order.OrderBuilder()
							 .userId("dwya")
							 .orderId("111")
							 .courierId("aaa")
							 .itemId("zzz")
							 .endTime(new Timestamp((new Date()).getTime() + 300000))
							 .type("D")
							 .startAddressId("3869")
							 .endAddressId("sdva")
							 .routeDuration(476)
							 .routeDistance(11932)
							 .routePrice(18.415068798218403)
							 .routePath("cmtgE`{fjUvNtbFb`I}eL")
							 .complete(false)
							 .isRecommended(false)
							 .build();

		System.out.println(ord.toString());
	}
}
