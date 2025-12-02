package com.ttl.core.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ttl.core.entities.User;

@Component
public class ActivationEmailListener {

	@EventListener
	public void handlerUserRegister(UserRegisteredEvent pEvent) {
		User lvUser = pEvent.getUser();
		
		System.out.println("sent email register : " + lvUser.getEmail());
	}
}
