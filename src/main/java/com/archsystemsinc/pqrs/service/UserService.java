package com.archsystemsinc.pqrs.service;

import com.archsystemsinc.pqrs.model.User;

/**
 * This is the Service interface for user database table.
 * 
 * @author Murugaraj Kandaswamy
 * @since 6/19/2017
 * 
 */
public interface UserService {

	void save(User user);

	User findByUsername(String username);

}
