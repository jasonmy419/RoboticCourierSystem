package entity;

public class User {

	private String userId;
	private String firstName;
	private String lastName;
	private String password;
	private String address;
	private String zipCode;
	private String email;
	private String phoneNumber; // not pretty sure with the data type
	
	
	private User(UserBuilder builder) {
		this.userId = builder.userId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.password = builder.password;
		this.address = builder.address;
		this.zipCode = builder.zipCode;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
	}
	
	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getPasword() {
		return password;
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


	
	public static class UserBuilder{
		
		private String userId;
		private String firstName;
		private String lastName;
		private String password;
		private String address;
		private String zipCode;
		private String email;
		private String phoneNumber; 
		
		public UserBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		public UserBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public UserBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public UserBuilder address(String address) {
			this.address = address;
			return this;
		}
		public UserBuilder zipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}
		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}
		public UserBuilder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}
		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}
		public User build() {
			return new User(this);
		}
	}
	
	
}
