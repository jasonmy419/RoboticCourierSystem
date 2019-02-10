package entity;

public class Order {

	private String orderId;
	private String itemId;
	private String courierId;
	private Status status;
	private String startUserId;
	private String endUserId;
	
	private Order(OrderBuilder builder) {
		this.orderId = builder.orderId;
		this.itemId = builder.itemId;
		this.courierId = builder.courierId;
		this.status = builder.status;
		this.startUserId = builder.startUserId;
		this.endUserId = builder.endUserId;
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
	public Status getStatus() {
		return status;
	}
	public String getStartUserId() {
		return startUserId;
	}
	public String getEndUserId() {
		return endUserId;
	}
	
	public static class OrderBuilder {
		
		private String orderId;
		private String itemId;
		private String courierId;
		private Status status;
		private String startUserId;
		private String endUserId;
		
		public OrderBuilder orderId(String orderId) {
			this.orderId = orderId;
			return this;
		}
		
		public OrderBuilder courierId(String courierId) {
			this.courierId = courierId;
			return this;
		}

		public OrderBuilder status(Status status) {
			this.status = status;
			return this;
		}

		public OrderBuilder startUserId(String startUserId) {
			this.startUserId = startUserId;
			return this;
		}

		public OrderBuilder endUserId(String endUserId) {
			this.endUserId = endUserId;
			return this;
		}

		public OrderBuilder itemId(String itemId) {
			this.itemId = itemId;
			return this;
		}
		public Order build() {
			return new Order(this);
		}
	}
}
