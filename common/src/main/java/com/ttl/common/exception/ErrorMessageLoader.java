package com.ttl.common.exception;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;

import jakarta.annotation.PostConstruct;

@Component
public class ErrorMessageLoader {
	
	@Autowired
    private Environment environment;
	private final Map<String, String> mvErrorMessage = new HashMap<String, String>();
	
	@PostConstruct
	public void init() {
		String filePath = environment.getProperty(ITag.ERRORFILEProperties);
		try {
			
//			Đường dẫn tuyệt đối (/path/to/your/config/error.xml) 
//			Đường dẫn tương đối (config/error.xml) 
//			nếu ở spring boot, thì đường dẫn tương đối bắt đầu từ resource
			InputStream input = getClass().getClassLoader().getResourceAsStream(filePath);
			if(input == null) {
				throw new BussinessException(ITag.ERRORFILEProperties, ITagCode.DATA_NOT_FOUND, getClass());
			}
			Document doc =	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			NodeList nodeList = doc.getElementsByTagName("error");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element error = (Element)nodeList.item(i);
				String code = error.getAttribute("code");
				String message = error.getAttribute("message");
				mvErrorMessage.put(code, message);
			}
		}catch(Exception ex) {
			throw new RuntimeException("không thể load file lỗi : " + filePath);
		}
	}
	public String getMessage(String code, Object...args) {
		String lvTemplate = mvErrorMessage.getOrDefault(code, "Unknow error code : " + code).toString();
		return MessageFormat.format(lvTemplate, args);
	}
}
