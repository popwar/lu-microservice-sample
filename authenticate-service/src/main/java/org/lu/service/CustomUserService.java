package org.lu.service;

import org.springframework.security.core.Authentication;

/**
 * 
 * @author Lu
 *
 */
public interface CustomUserService {
	Authentication authenticate(String userID, String password);
}
