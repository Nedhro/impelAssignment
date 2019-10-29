package com.impelitAssignment.service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.impelitAssignment.service.config.JwtTokenUtil;
import com.impelitAssignment.service.config.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private JwtTokenUtil jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Value("${jwt.token.bearer}")
	private String bearer;

	@RequestMapping(value = "auth", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> authenticationToken(@RequestBody Map<String, String> authenticationRequest)
			throws AuthenticationException {

		Map<String, String> token = new HashMap<String, String>();
		try {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.get("username"),
							authenticationRequest.get("password")));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.get("username"));
			final String accessToken = jwtProvider.generateToken(userDetails);
			token.put("accessToken : "+bearer, accessToken);
			token.put("username", userDetails.getUsername());
			token.put("password", userDetails.getPassword());
			return ResponseEntity.ok(token);
		} catch (BadCredentialsException bce) {
			token.put("error", "invalid username or password");
			return ResponseEntity.ok(token);
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
