package org.lu.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lu.data.Customer;
import org.lu.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * authentication service, only to get the roles from database to restrict the
 * access to certain urls
 * 
 * @author Lu
 *
 */
@Service
public class CustomUserServiceImpl implements CustomUserService {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomUserServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Authentication authenticate(String userName, String password) {
		logger.info(">>>>> authenticate() is started<<<<<");

		Customer customer = customerRepository.findByUserNameAndPassword(
				userName, password);

		if (customer != null) {
			List<GrantedAuthority> authorities = setAuthority(customer);

			return new UsernamePasswordAuthenticationToken(userName, null,
					authorities);
		} else {
			logger.info("Login failed.");
			throw new BadCredentialsException("Login failed.");
		}

	}

	protected List<GrantedAuthority> setAuthority(Customer user) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		Set<String> permissions = user.getPermissions();
		if (permissions != null && !permissions.isEmpty()) {
			permissions.forEach(permission -> authorities
					.add(new SimpleGrantedAuthority(permission)));
		}

		return authorities;
	}

	protected static enum customAuthority {
		HOME("ROLE_HOME"), CUSTOMER("ROLE_CUSTOMER");

		private String value;

		private customAuthority(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}
}
