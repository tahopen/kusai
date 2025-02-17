/*  
 *   Copyright 2012 OSBI Ltd
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.kusai.plugin;

import org.kusai.plugin.util.PentahoAuditHelper;
import org.kusai.service.ISessionService;
import org.kusai.service.user.UserService;
import org.pentaho.platform.api.engine.ILogoutListener;
import org.pentaho.platform.api.engine.IPentahoSession;
import org.pentaho.platform.engine.core.system.PentahoSystem;

import bi.meteorite.license.LicenseVersionExpiredException;
import bi.meteorite.license.SaikuLicense2;

import org.pentaho.platform.util.logging.SimpleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.util.*;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PentahoSessionServiceSeven implements ISessionService {

	private LicenseUtils l;

	private static final Logger log = LoggerFactory.getLogger(PentahoSessionServiceSeven.class);

	private AuthenticationManager authenticationManager;

	private final Map<Object, Map<String, Object>> sessionHolder = new HashMap<Object, Map<String, Object>>();
	private UserService userService;

	public void setUserService(UserService us) {
		userService = us;
	}

	private final PentahoAuditHelper pah = new PentahoAuditHelper();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kusai.web.service.ISessionService#setAuthenticationManager(org.
	 * springframework.security.authentication.AuthenticationManager)
	 */
	public void setAuthenticationManager(AuthenticationManager auth) {
		this.authenticationManager = auth;
	}

	public PentahoSessionServiceSeven() {
		PentahoSystem.addLogoutListener(new ILogoutListener() {

			@Override
			public void onLogout(IPentahoSession pentahoSession) {
				System.out.println("processing pentaho logout");
				UUID uuid = pah.startAudit("Saiku", "Logout", this.getClass().getName(), null, null, "Attempted Logout",
						getLogger());
				if (SecurityContextHolder.getContext() != null
						&& SecurityContextHolder.getContext().getAuthentication() != null) {
					Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					if (sessionHolder.containsKey(p)) {
						sessionHolder.remove(p);
					}
				} else if (pentahoSession != null && pentahoSession.getId() != null) {
					Iterator it = sessionHolder.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry) it.next();
						if (pair.getValue() instanceof HashMap) {
							HashMap<String, String> hm = (HashMap<String, String>) pair.getValue();
							Iterator it2 = hm.entrySet().iterator();
							while (it2.hasNext()) {
								Map.Entry pair2 = (Map.Entry) it2.next();
								if (pair2.getKey().equals("sessionid")
										&& pair2.getValue().equals(pentahoSession.getId())) {
									sessionHolder.remove(pair.getKey());
								}
							}
						}

					}
				}
				try {
					pah.endAudit("Saiku", "Logout Successful", this.getClass().getName(), null, null,
							getLogger(), (long) 1, uuid, (long) 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kusai.web.service.ISessionService#login(javax.servlet.http.
	 * HttpServletRequest, java.lang.String, java.lang.String)
	 */
	public Map<String, Object> login(HttpServletRequest req, String username, String password) {
		pah.startAudit("Saiku", "Login", this.getClass().getName(), this.toString(), this.toString(), null,
				new SimpleLogger(
						PentahoSessionServiceSeven.class
								.getName()));
		if (authenticationManager != null) {
			authenticate(req, username, password);
		}
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			populateSession(p, username, password);
			return sessionHolder.get(p);
		}

		return new HashMap<String, Object>();
	}

	private void populateSession(Object key) {

		String username;
		if (key instanceof User) {
			User u = (User) key;
			username = u.getUsername();
		} else {
			username = "existinguser";
		}
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

		UUID uuid = pah.startAudit("Saiku", "Login", this.getClass().getName(), username, sessionId, username +
				" Attempted "
				+ "Login",
				getLogger());
		if (!sessionHolder.containsKey(key)) {
			sessionHolder.put(key, new HashMap<String, Object>());
		}
		sessionHolder.get(key).put("sessionid", sessionId);
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority ga : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		sessionHolder.get(key).put("roles", roles);

		sessionHolder.get(key).put("isadmin", userService.isAdmin());
		sessionHolder.get(key).put("username", username);
		pah.endAudit("Saiku", "Login", this.getClass().getName(), username, sessionId, getLogger(),
				(long) 1, uuid, (long) 1);
	}

	private void populateSession(Object key, String username, String password) {
		populateSession(key);
		sessionHolder.get(key).put("username", username);
		sessionHolder.get(key).put("password", password);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kusai.web.service.ISessionService#logout(javax.servlet.http.
	 * HttpServletRequest)
	 */
	public void logout(HttpServletRequest req) {
		UUID uuid = pah.startAudit("Saiku", "Logout", this.getClass().getName(), null, null, "Attempted Logout",
				getLogger());
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (sessionHolder.containsKey(p)) {
				sessionHolder.remove(p);
			}
		}
		SecurityContextHolder.clearContext();
		HttpSession session = req.getSession(true);
		session.invalidate();
		try {
			pah.endAudit("Saiku", "Logout Successful", this.getClass().getName(), null, this.getSession().toString(),
					getLogger(), (long) 1, uuid, (long) 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kusai.web.service.ISessionService#authenticate(javax.servlet.http.
	 * HttpServletRequest, java.lang.String, java.lang.String)
	 */
	public void authenticate(HttpServletRequest req, String username, String password) {
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			token.setDetails(new WebAuthenticationDetails(req));
			Authentication authentication = this.authenticationManager.authenticate(token);
			log.debug("Logging in with [{}]", authentication.getPrincipal());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception bd) {
			throw new RuntimeException("Authentication failed for: " + username, bd);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.kusai.web.service.ISessionService#getSession(javax.servlet.http.
	 * HttpServletRequest)
	 */
	public Map<String, Object> getSession() throws Exception {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!sessionHolder.containsKey(p)) {
				populateSession(p);
			}
			Map<String, Object> r = new HashMap<>();
			r.putAll(sessionHolder.get(p));
			if (r.containsKey("password")) {
				r.remove("password");
			}
			return r;
		}
		return null;
	}

	public Map<String, Object> getAllSessionObjects() {
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!sessionHolder.containsKey(p)) {
				populateSession(p);
			}
			Map<String, Object> r = new HashMap<String, Object>();
			r.putAll(sessionHolder.get(p));
			if (r.containsKey("password")) {
				r.remove("password");
			}
			return r;
		}
		return new HashMap<>();
	}

	public void clearSessions(HttpServletRequest req, String username, String password) {
		if (authenticationManager != null) {
			authenticate(req, username, password);
		}
		if (SecurityContextHolder.getContext() != null
				&& SecurityContextHolder.getContext().getAuthentication() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object p = auth.getPrincipal();
			if (sessionHolder.containsKey(p)) {
				sessionHolder.remove(p);
			}
		}
	}

	private SimpleLogger getLogger() {
		return new SimpleLogger(
				PentahoSessionServiceSeven.class
						.getName());
	}

	public Map<Object, Map<String, Object>> getSessionHolder() {
		return sessionHolder;
	}
}
