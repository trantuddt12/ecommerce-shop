package com.ttl.core.event;

import org.springframework.context.ApplicationEvent;

import com.ttl.core.entities.User;

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
