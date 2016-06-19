package org.lu.security;

import org.lu.service.CustomUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * custom authentication manager to do authentication operation
 * 
 * @author Lu
 *
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);

	@Autowired
	private CustomUserService userService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		logger.info("--- start to authenticate the user ---");

		Authentication authentication =  userService.authenticate(auth.getPrincipal().toString(), auth.getCredentials().toString());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		logger.info("--- successfully authenticated the user ---");
		
		return authentication;
	}

}
