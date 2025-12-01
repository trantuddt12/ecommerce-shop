package com.ttl.core.event;

import com.tutv.epattern.coreservice.entities.User;
import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent{

	private final User mvUser;
	
	public UserRegisteredEvent(Object source, User pUser) {
		super(source);
		mvUser= pUser;
	}
	public User getUser() {
		return mvUser;
	}
}
