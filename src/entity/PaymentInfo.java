package entity;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentInfo {

	private String lastName;
	private String firstName;
	private String email;
	private String address;
	private String phoneNumber;
	private String cardNumber;
	private String month;
	private String date;
	private String cvv;
	private String cardAddress;
	
	
	
	private PaymentInfo(PaymentInfoBuilder builder) {
		
		this.lastName = builder.lastName;
		this.firstName = builder.firstName;
		this.email = builder.email;
		this.address = builder.address;
		this.phoneNumber = builder.phoneNumber;
		this.cardNumber = builder.cardNumber;
		this.month = builder.month;
		this.date = builder.date;
		this.cvv = builder.cvv;
		this.cardAddress = builder.cardAddress;
	}
	
	public String getLastName() {
		return lastName;
	}
	public String getFirstname() {
		return firstName;
	}
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getMonth() {
		return month;
	}
	public String getDate() {
		return date;
	}
	public String getCardAddress() {
		return cardAddress;
	}
	public String getCvv() {
		return cvv;
	}
	
	public static class PaymentInfoBuilder {
		private String lastName;
		private String firstName;
		private String email;
		private String address;
		private String phoneNumber;
		private String cardNumber;
		private String month;
		private String date;
		private String cvv;
		private String cardAddress;
		
		public PaymentInfoBuilder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public PaymentInfoBuilder setFirstname(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public PaymentInfoBuilder setEmail(String email) {
			this.email = email;
			return this;
		}
		public PaymentInfoBuilder setAddress(String address) {
			this.address = address;
			return this;
		}
		public PaymentInfoBuilder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		public PaymentInfoBuilder setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
			return this;
		}
		public PaymentInfoBuilder setMonth(String month) {
			this.month = month;
			return this;
		}
		public PaymentInfoBuilder setDate(String date) {
			this.date = date;
			return this;
		}
		public PaymentInfoBuilder setCvv(String cvv) {
			this.cvv = cvv;
			return this;
		}
		public PaymentInfoBuilder setCardAddress(String cardAddress) {
			this.cardAddress = cardAddress;
			return this;
		}
		
		public PaymentInfo build() {
			return new PaymentInfo(this);
		}
	}
		
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("last_name", lastName);
			obj.put("first_name", firstName);
			obj.put("email", email);
			obj.put("address", address);
			obj.put("phone_number", phoneNumber);
			obj.put("card_number", cardNumber);
			obj.put("month", month);
			obj.put("date", date);
			obj.put("card_address", cardAddress);
			obj.put("cvv", cvv);
		} catch (JSONException e) {
			System.out.println("Error in entity/PaymentInfo -> "+e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

}
