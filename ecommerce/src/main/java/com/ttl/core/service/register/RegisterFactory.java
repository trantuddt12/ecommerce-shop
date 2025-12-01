package com.ttl.core.service.register;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RegisterFactory {
	private final Map<String,RegisterStrategy> mvStrategy = new HashedMap(); 
	
	public RegisterFactory(List<RegisterStrategy> pStrategy) {
		for(RegisterStrategy strategy : pStrategy) {
			mvStrategy.put(strategy.registerName(), strategy);
		}
	}
	
	public RegisterStrategy getRegisterStrategy(String pName) {
		return mvStrategy.get(pName);
	}
}
