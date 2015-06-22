package com.pigtrax.usermanagement.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;

public class PigTraxUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeDao employeeDao;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("in pigtrax userdetails service : "+username);
		com.pigtrax.usermanagement.beans.Employee employee = null;
		try {
			employee = employeeDao.findByUserName(username);
		} catch (SQLException e) {
			new UsernameNotFoundException("User not found");
		}
		List<GrantedAuthority> authorities = buildUserAuthority(); 
		return buildUserForAuthentication(employee, authorities);
	}
	
	/**
	 * Build spring security core UserDetails object based on the logged in user information
	 * @param employee
	 * @param authorities
	 * @return
	 */
	private User buildUserForAuthentication(com.pigtrax.usermanagement.beans.Employee employee, List<GrantedAuthority> authorities) {
			return new User(employee.getEmployeeId(), 
					employee.getPtPassword(), employee.isEnabled(), 
	                        true, true, true, authorities);
		}
	
	private List<GrantedAuthority> buildUserAuthority() {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority("USER_ROLE"));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}
}
