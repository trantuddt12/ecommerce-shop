package com.ttl.core.service.login;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginFactory {
	
	private final Map<String , LoginStrategy> mvStrategies = new HashMap<String, LoginStrategy>();
	
	public LoginFactory(List<LoginStrategy> pStrategies) {
		for(LoginStrategy strategy : pStrategies) {
			mvStrategies.put(strategy.strategyName(), strategy);
		}
	}
	
	public LoginStrategy getLoginStrategy(String pStrateyName) {
		return mvStrategies.get(pStrateyName);
	}
}
