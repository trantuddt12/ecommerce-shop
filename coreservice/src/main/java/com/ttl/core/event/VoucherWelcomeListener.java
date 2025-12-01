package com.ttl.core.event;

import com.tutv.epattern.coreservice.entities.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VoucherWelcomeListener {

	@EventListener
	public void giveVoucherWelcome(UserRegisteredEvent pEvent) {
		User lvUser = pEvent.getUser();
		System.out.println("voucher welcome for : " + lvUser.getEmail());
	}
}
