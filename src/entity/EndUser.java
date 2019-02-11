package entity;

public class EndUser {

	private String endUserId;
	private String firstName;
	private String lastName;
	private String address;
	private String zipCode;
	private String email;
	private String phoneNumber; 
	
	
	private EndUser(EndUserBuilder builder) {
		this.endUserId = builder.endUserId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.address = builder.address;
		this.zipCode = builder.zipCode;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
	}
	
	public String getUserId() {
		return endUserId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public static class EndUserBuilder{
		
		private String endUserId;
		private String firstName;
		private String lastName;
		private String address;
		private String zipCode;
		private String email;
		private String phoneNumber; 
		
		public EndUserBuilder userId(String endUserId) {
			this.endUserId = endUserId;
			return this;
		}
		public EndUserBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public EndUserBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public EndUserBuilder address(String address) {
			this.address = address;
			return this;
		}
		public EndUserBuilder zipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}
		public EndUserBuilder email(String email) {
			this.email = email;
			return this;
		}
		public EndUserBuilder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public EndUser build() {
			return new EndUser(this);
		}
	}
}
