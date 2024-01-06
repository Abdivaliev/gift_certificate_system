package com.epam.esm.constant;

public class SecurityConstants {
    public static final String BEARER = "Bearer ";
    public static final int BEARER_LENGTH = 7;
    public static final String TOKEN_TYPE="token-type";
    public static final String ACCESS_TOKEN="access-token";
    public static final String REFRESH_TOKEN="refresh-token";
    public static final Integer EXPIRATION_TIME_ACCESS_TOKEN = 1000 * 60 * 3;// 3 minutes
    public static final Integer EXPIRATION_TIME_REFRESH_TOKEN = 1000 * 60 * 6;// 6 minutes
    public static final String SECRET_KEY = "3D2B7E151628AED2A6ABF7158809CF4F3C76E22D36F5D9E4C5F3C311B4A8A62F";

}
