package com.ttl.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttl.core.repository.GenerateSerialRepository;

@Service
public class SerialService {

	@Autowired
	private GenerateSerialRepository mvGenerateSerialRepository;
	
	public String getNextSerial(String pSerialNo) {
		mvGenerateSerialRepository.updateLastSerial(pSerialNo);
		return mvGenerateSerialRepository.getNextSerial(pSerialNo);
	}
}