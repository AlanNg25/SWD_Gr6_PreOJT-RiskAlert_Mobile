package com.example.preojt_riskalert_mobile.constants;

public class ConstantApi {
    // Login and Authentication related endpoints
    public static final String SIGN_IN_GOOGLE = "Auth/google";
    public static final String SIGN_IN_EMAIL = "Auth/login";
    public static final String LOG_OUT = "Auth/logout";

    // Profile related endpoints
    public static final String GET_PROFILE = "User/{id}";

    // Attendance related endpoints
    public static final String GET_ATTENDANCE = "Attendance/by-user/{id}";

    // Grade related endpoints
    public static final String GET_ALL_GRADE= "Grade/by-user/{id}";
    public static final String GET_GRADE_DETAILS= "Grade/{id}";
}
