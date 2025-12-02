package com.ttl.core.controler;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.ttl.common.constant.ApiResponse;
import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.common.utilities.CoreUtils;
import com.ttl.core.config.OtpGeneration;
import com.ttl.core.entities.User;
import com.ttl.core.request.LoginRequest;
import com.ttl.core.request.RegisterRequest;
import com.ttl.core.response.LoginResponse;
import com.ttl.core.service.AuthService;
import com.ttl.core.service.OtpEmailService;
import com.ttl.core.service.OtpRedisService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService mvAuthService;
	private final OtpGeneration mvOtpGeneration;
	private final OtpEmailService mvEmailService;
	private final OtpRedisService mvOtpRedisService;
	
	@Value("${jwt.refreshExpirationMs}")
    private long refreshExpirationMs;
	
	public AuthController(AuthService pAuthService, OtpGeneration pOtpGeneration, OtpEmailService pEmailService, 
			OtpRedisService pOtpRedisService) {
		mvAuthService= pAuthService;
		mvOtpGeneration = pOtpGeneration;
		mvEmailService = pEmailService;
		mvOtpRedisService = pOtpRedisService;
	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest pLoginRequest, HttpServletRequest pHttpRequest) throws BussinessException, InterruptedException, ExecutionException{
		LoginResponse lvLoginResponse = mvAuthService.login(pLoginRequest, pHttpRequest).get();
		ResponseCookie lvResponseCookie = ResponseCookie.from(ITag.REFRESH_TOKEN, lvLoginResponse.getRefreshToken())
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(refreshExpirationMs/1000)
				.sameSite("None")
				.build();
		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, lvResponseCookie.toString())
				.body(ApiResponse.success("Đăng nhâp thành công !", lvLoginResponse));
	}
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<?>> refreshToken(
//			@CookieValue(name = ITag.REFRESH_TOKEN, required = false) String pRefreshToken, 
			HttpServletRequest request) throws UserNotFoundException {
	    Cookie cookie = WebUtils.getCookie(request, ITag.REFRESH_TOKEN);

	    if (cookie == null) {
	        System.out.println("❌ Không có refresh_token cookie");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	            .body(ApiResponse.error("No refresh token cookie", ITagCode.UNKNOWN_ERROR, getClass()));
	    }
	    String lvRefreshToken = cookie.getValue();
		String lvAccessToken = mvAuthService.retrieveTokenByRefreshToken(lvRefreshToken);
		if(!CoreUtils.isNullStr(lvAccessToken)) {
			return ResponseEntity.ok(ApiResponse.success("Refresh token successfully!", lvAccessToken));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid Refresh Token", ITagCode.UNKNOWN_ERROR, getClass()) );
	}
	
	@PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest pHttpReq , HttpServletResponse pHttpRes) {
		
		if (pHttpReq.getCookies() != null) {
	        for (Cookie c : pHttpReq.getCookies()) {
	            System.out.println("🍪 Cookie logout : " + c.getName() + " = " + c.getValue());
	        }
	    } else {
	        System.out.println("❌ Không có cookie logout nào được gửi");
	    }
        // Xóa refresh token trong cookie
		Cookie lvCookie = mvAuthService.logout(pHttpReq);
        pHttpRes.addCookie(lvCookie);
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
	/*
	 * register : frontend send API /sendotp , send API /sendotpregister 
	 * user verify otp by send API /verifyotp -> if susscess -> send API :register
	 */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest pRegisterReq) throws BussinessException {
		User lvUser = mvAuthService.register(pRegisterReq);
		return ResponseEntity.ok(ApiResponse.success("", lvUser));
	}
	
	@PostMapping("/sendotp")
	public ResponseEntity<ApiResponse<?>> sendOtp(@RequestParam String pOtpType ,@RequestParam String pToEmail){
		String lvOtp = mvOtpGeneration.generationOtp(6);
		mvEmailService.sendOtpEmail(pToEmail, lvOtp);
		mvOtpRedisService.saveOtp(pOtpType, pToEmail, lvOtp);
		return ResponseEntity.ok(ApiResponse.success("OTP sent to: " + pToEmail, null));
	}
	@PostMapping("/sendotpregister")
	public ResponseEntity<ApiResponse<?>> sendOtpRegister(@RequestBody RegisterRequest pUser){
		String lvOtp = mvOtpGeneration.generationOtp(6);
		mvEmailService.sendOtpEmail(pUser.getEmail(), lvOtp);
		mvOtpRedisService.saveOtp(ITag.OTP_REGISTER, pUser.getEmail(), lvOtp);
		mvOtpRedisService.saveTempRegister(pUser);
		return ResponseEntity.ok(ApiResponse.success("OTP sent to: " + pUser.getEmail(), null));
	}
	@PostMapping("/verifyotp")
	public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestParam String pOtpType, @RequestParam String pEmail, @RequestParam String pOtp){
		boolean lvIsOtpSusscess = mvOtpRedisService.verifyOtp(pOtpType, pEmail, pOtp);
		boolean lvIsUpdate = mvAuthService.verifyRegister(pEmail);
		if(lvIsOtpSusscess && lvIsUpdate) {
			mvOtpRedisService.deleteOtp(pOtpType, pEmail);
			return ResponseEntity.ok(ApiResponse.success("Otp verify susscessfully !", pOtp));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid or expired OTP", ITagCode.OTP_INVALID, getClass()));
		}
	}
	
//	@GetMapping("/check")
//    public ResponseEntity<?> checkAuth(Authentication authentication) {
//        if (authentication != null && authentication.isAuthenticated()) {
//            return ResponseEntity.ok(Map.of(
//                "status", "OK",
//                "username", authentication.getName()
//            ));
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                             .body(Map.of("error", "Unauthorized"));
//    }
//	public ResponseEntity<ApiResponse<?>> getAccount
}
