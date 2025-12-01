package com.com.epattern.common.constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.tutv.common.util.utilities.CoreUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

	private boolean success;
	private String message;
	private T data;
	private String errorCode;
	private String sourceClassName;
	private String timestamp ;
	
    public static <T> ApiResponse<T> of(boolean success, String message, T data, String errorCode, String sourceClassName) {
        return ApiResponse.<T>builder()
                .success(success)
                .message(message)
                .data(data)
                .errorCode(errorCode)
                .sourceClassName(sourceClassName)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public static <T> ApiResponse<T> of(boolean success, String message, T data) {
        return of(success, message, data, null, null);
    }
    public static <T> ApiResponse<T> success(String message, T data) {
        return of(true, message, data, null, null);
    }

    public static <T> ApiResponse<T> error(String message, String errorCode, Class<?> sourceClassName) {
        return of(false, message, null, errorCode, sourceClassName == null ? "" : CoreUtils.trim(sourceClassName.getName()));
    }

}
