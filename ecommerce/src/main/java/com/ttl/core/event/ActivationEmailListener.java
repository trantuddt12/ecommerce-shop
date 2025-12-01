package com.ttl.core.event;

import com.tutv.epattern.coreservice.entities.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ActivationEmailListener {

	@EventListener
	public void handlerUserRegister(UserRegisteredEvent pEvent) {
		User lvUser = pEvent.getUser();
		
		System.out.println("sent email register : " + lvUser.getEmail());
	}
}
