package com.com.epattern.common.constant;
public interface ITagCode {
    // E0xx: System Errors
    String SUCCESS = "E000";
    String UNKNOWN_ERROR = "E001";
    String INVALID_PARAM = "E002";
    String DATA_NOT_FOUND = "E003";
    String DATA_ALREADY_EXISTS = "E004";
    String PERMISSION_DENIED = "E005";
    String DB_CONNECTION_FAILED = "E006";
    String EXTERNAL_API_ERROR = "E007";
    String DATA_PROCESSING_ERROR = "E008";
    String DATA_IN_USE = "E009";
    String SYSTEM_MAINTENANCE = "E010";
    String REQUEST_TIMEOUT = "E011";
    String METHOD_NOT_SUPPORTED = "E012";
    String INVALID_FORMAT = "E013";
    String FILE_TOO_LARGE = "E014";
    String RESOURCE_NOT_FOUND = "E015";
    String SESSION_EXPIRED = "E016";
    String DUPLICATE_REQUEST = "E017";
    String INVALID_SYSTEM_CONFIG = "E018";
    String SERVICE_INIT_FAILED = "E019";
    String INTERNAL_SERVER_ERROR = "E020";

    // E1xx: User / Authentication Errors
    String INVALID_USERNAME = "E100";
    String INVALID_PASSWORD = "E101";
    String INVALID_CREDENTIALS = "E102";
    String ACCOUNT_LOCKED = "E103";
    String ACCOUNT_INACTIVE = "E104";
    String LOGIN_SESSION_EXPIRED = "E105";
    String TOKEN_INVALID_OR_EXPIRED = "E106";
    String FUNCTION_ACCESS_DENIED = "E107";
    String AUTHENTICATION_FAILED = "E108";
    String LOGIN_ATTEMPT_EXCEEDED = "E109";
    String USER_NOT_FOUND = "E110";
    String ACCOUNT_ALREADY_EXISTS = "E111";
    String ACCOUNT_REGISTRATION_FAILED = "E112";
    String OTP_INVALID = "E113";
    String OTP_EXPIRED = "E114";
    String PHONE_ALREADY_USED = "E115";
    String EMAIL_ALREADY_USED = "E116";
    String OLD_PASSWORD_INCORRECT = "E117";
    String NEW_PASSWORD_DUPLICATE = "E118";
    String LOGIN_REQUIRED = "E119";
    
    String ERROR_MAPPING = "E200";
}
