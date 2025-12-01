package com.ttl.core.service;

import com.tutv.epattern.coreservice.repository.GenerateSerialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerialService {

	@Autowired
	private GenerateSerialRepository mvGenerateSerialRepository;
	
	public String getNextSerial(String pSerialNo) {
		mvGenerateSerialRepository.updateLastSerial(pSerialNo);
		return mvGenerateSerialRepository.getNextSerial(pSerialNo);
	}
}