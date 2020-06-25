package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment {

	private String userId;
	private String lastName;
	private String firstName;
	private String cardNumber;
	private int month;
	private int year;
	private int cvv;
	private int zipCode;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	
	
	private Payment(PaymentBuilder builder) {
		
		this.userId = builder.userId;
		this.lastName = builder.lastName;
		this.firstName = builder.firstName;
		this.cardNumber = builder.cardNumber;
		this.month = builder.month;
		this.year = builder.year;
		this.cvv = builder.cvv;
		this.addressLine1 = builder.addressLine1;
		this.addressLine2 = builder.addressLine2;
		this.city = builder.city;
		this.state = builder.state;
		this.zipCode = builder.zipCode;
	}
	

	public String getUserId() {
		return userId;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
	public String getaddressLine1() {
		return addressLine1;
	}
	public String getaddressLine2() {
		return addressLine2;
	}
	public int getCvv() {
		return cvv;
	}
	public int getZipCode() {
		return zipCode;
	}
	
	public static class PaymentBuilder {

		private String userId;
		private String lastName;
		private String firstName;
		private String cardNumber;
		private int month;
		private int year;
		private int cvv;
		private int zipCode;
		private String addressLine1;
		private String addressLine2;
		private String city;
		private String state;
		
		public PaymentBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}
		public PaymentBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public PaymentBuilder setFirstname(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public PaymentBuilder setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
			return this;
		}
		public PaymentBuilder setMonth(int month) {
			this.month = month;
			return this;
		}
		public PaymentBuilder setYear(int year) {
			this.year = year;
			return this;
		}
		public PaymentBuilder setCvv(int cvv) {
			this.cvv = cvv;
			return this;
		}
		public PaymentBuilder setCardAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
			return this;
		}
		public PaymentBuilder setCardAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
			return this;
		}
		public PaymentBuilder setCity(String city) {
			this.city = city;
			return this;
		}
		public PaymentBuilder setState(String state) {
			this.state = state;
			return this;
		}
		public Payment build() {
			return new Payment(this);
		}
	}
		
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("userIud", userId);
			obj.put("last_name", lastName);
			obj.put("first_name", firstName);
			obj.put("card_number", cardNumber);
			obj.put("month", month);
			obj.put("year", year);
			obj.put("address_line_1", addressLine1);
			obj.put("address_line_2", addressLine2);
			obj.put("cvv", cvv);
			obj.put("zipcode", zipCode);
			obj.put("city", city);
			obj.put("state", state);
		} catch (JSONException e) {
			System.out.println("Error in entity/PaymentInfo -> "+e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

}
