package com.pigtrax.usermanagement.beans;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class PigTraxUser extends User {

	private Integer userRole;
	private Integer companyId;
	private String companyName;
	private Integer userRoleCode;
	
	
	 public PigTraxUser(String username, String password, boolean enabled, boolean accountNonExpired,
	            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Integer userRole, Integer companyId, String companyName, Integer userRoleCode) {

	        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	        this.userRole = userRole;
	        this.companyId = companyId;
	        this.companyName = companyName;
	        this.userRoleCode = userRoleCode;
	    }
	
	public Integer getUserRole() {
		return userRole;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

	
}
