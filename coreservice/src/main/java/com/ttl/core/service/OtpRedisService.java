package com.ttl.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutv.common.util.constant.ITag;
import com.tutv.common.util.constant.ITagCode;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.coreservice.request.RegisterRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class OtpRedisService {

	private final Duration OTP_TTL = Duration.ofMinutes(5);
	private final StringRedisTemplate mvRedisTemplate;
	private final ObjectMapper mvMapper;
	public OtpRedisService(StringRedisTemplate pRedisTemplate,ObjectMapper pMapper) {
		super();
		this.mvRedisTemplate = pRedisTemplate;
		mvMapper = pMapper;
	}
	public void saveOtp(String pOtpType , String pEmail, String pOtp) {
		mvRedisTemplate.opsForValue().set(buildKey(pOtpType, pEmail), pOtp, OTP_TTL);
	}
	
	public void saveTempRegister(RegisterRequest pRequest) {
		String lvKey = buildKey(ITag.OTP_REGISTER_TEMP, pRequest.getEmail());
		try {
			String lvJson = mvMapper.writeValueAsString(pRequest);
			mvRedisTemplate.opsForValue().set(lvKey, lvJson, OTP_TTL);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Could not serialize register request", e);
		}
	}
	public RegisterRequest getTemplateUser(String pEmail) throws BussinessException {
		String lvKey = buildKey(ITag.OTP_REGISTER_TEMP, pEmail);
		String lvRequestJson =  mvRedisTemplate.opsForValue().get(lvKey);
		if (lvRequestJson == null )throw new BussinessException("Đăng ký với email : " + pEmail, ITagCode.DATA_NOT_FOUND, getClass());
		try {
			return mvMapper.readValue(lvRequestJson, RegisterRequest.class);
		} catch (JsonMappingException e) {
			throw new BussinessException("", ITagCode.ERROR_MAPPING, getClass());
		} catch (JsonProcessingException e) {
			throw new BussinessException("", ITagCode.ERROR_MAPPING, getClass());
		}
	}
	public boolean verifyOtp(String pOtpType, String pEmail, String pOtp) {
		String lvKey = buildKey(pOtpType, pEmail);
		String lvStoreOtp = mvRedisTemplate.opsForValue().get(lvKey);
		return lvStoreOtp!= null && lvStoreOtp.equals(pOtp);
	}
	public void deleteOtp(String pOtpType , String pEmail) {
		mvRedisTemplate.delete(buildKey(pOtpType, pEmail));
	}
	
	private String buildKey(String pOtpType, String pEmail) {
		return pOtpType + ":" + pEmail;
	}
}
