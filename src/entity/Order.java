package entity;

public class Order {

	private String orderId;
	private String itemId;
	private String courierId;
	private String status;
	private String type;
	private String startStreeNumber;
	private String startStreeName;
	private String startCity;
	private String endStreeNumber;
	private String endStreeName;
	private String endCity;
	private double routeDuration;
	private double routeDistance;
	private double routePrice;
	private double routePath;
	private boolean complete;
	
	public String getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public String getStartStreeNumber() {
		return startStreeNumber;
	}

	public String getStartStreeName() {
		return startStreeName;
	}

	public String getStartCity() {
		return startCity;
	}

	public String getEndStreeNumber() {
		return endStreeNumber;
	}

	public String getEndStreeName() {
		return endStreeName;
	}

	public String getEndCity() {
		return endCity;
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

	public double getRoutePath() {
		return routePath;
	}

	public boolean isComplete() {
		return complete;
	}
	
	private Order(OrderBuilder builder) {
		this.orderId = builder.orderId;
		this.itemId = builder.itemId;
		this.courierId = builder.courierId;
		this.status = builder.status;
		this.type = builder.type;
		this.startStreeNumber = builder.startStreeNumber;
		this.startStreeName  = builder.startStreeName;
		this.startCity  = builder.startCity;
		this.endStreeNumber  = builder.endStreeNumber;
		this.endStreeName  = builder.endStreeName;
		this.endCity  = builder.endCity;
		this.routeDuration  = builder.routeDuration;
		this.routeDistance  = builder.routeDistance;
		this.routePrice  = builder.routePrice;
		this.routePath  = builder.routePath;
		this.complete = builder.complete;
	
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

	
	public static class OrderBuilder {
		
		private String orderId;
		private String itemId;
		private String courierId;
		private String status;
		private String type;
		private String startStreeNumber;
		private String startStreeName;
		private String startCity;
		private String endStreeNumber;
		private String endStreeName;
		private String endCity;
		private double routeDuration;
		private double routeDistance;
		private double routePrice;
		private double routePath;
		private boolean complete;

		
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
		
		public OrderBuilder status(String status) {
			this.status = status;
			return this;
		}
		
		public OrderBuilder type(String type) {
			this.type = type;
			return this;
		}

		public OrderBuilder startStreeNumber(String startStreeNumber) {
			this.startStreeNumber = startStreeNumber;
			return this;
		}
		
		public OrderBuilder startStreeName(String startStreeName) {
			this.startStreeName = startStreeName;
			return this;
		}
		
		public OrderBuilder startCity(String startCity) {
			this.startCity = startCity;
			return this;
		}

		public OrderBuilder endStreeNumber(String endStreeNumber) {
			this.endStreeNumber = endStreeNumber;
			return this;
		}
		
		public OrderBuilder endStreeName(String endStreeName) {
			this.endStreeName = endStreeName;
			return this;
		}
		
		public OrderBuilder endCity(String endCity) {
			this.endCity = endCity;
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
		
		public OrderBuilder routePath(double routePath) {
			this.routePath = routePath;
			return this;
		}
		
		public OrderBuilder complete(boolean complete) {
			this.complete = complete;
			return this;
		}
		
		public Order build() {
			return new Order(this);
		}
	}
}
