package com.pigtrax.usermanagement.beans;

import java.math.BigDecimal;
import java.util.Date;

public class Company {
	
	private int id;
	private String companyId;
	private String name;
	private String address;
	private String country;
	private String city;
	private String registrationNumber;
	private String email;
	private String phone;
	private String contactName;
	private BigDecimal payment;
	private Date paymentDate;
	private boolean isActive;
	private Date lastUpdated;
	private String userUpdated;
	private String otherCity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public BigDecimal  getPayment() {
		return payment;
	}
	public void setPayment(BigDecimal  payment) {
		this.payment = payment;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getUserUpdated() {
		return userUpdated;
	}
	public void setUserUpdated(String userUpdated) {
		this.userUpdated = userUpdated;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	public String getOtherCity() {
		return otherCity;
	}
	public void setOtherCity(String otherCity) {
		this.otherCity = otherCity;
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyId=" + companyId + ", name="
				+ name + ", address=" + address + ", city=" + city
				+ ", registrationNumber=" + registrationNumber + ", email="
				+ email + ", phone=" + phone + ", contactName=" + contactName
				+ ", payment=" + payment + ", paymentDate=" + paymentDate
				+ ", isActive=" + isActive + ", lastUpdated=" + lastUpdated
				+ ", userUpdated=" + userUpdated + "]";
	}
	
	

}