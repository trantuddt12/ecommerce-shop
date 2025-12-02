package com.ttl.core.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ttl.core.entities.User;

@Component
public class VoucherWelcomeListener {

	@EventListener
	public void giveVoucherWelcome(UserRegisteredEvent pEvent) {
		User lvUser = pEvent.getUser();
		System.out.println("voucher welcome for : " + lvUser.getEmail());
	}
}
