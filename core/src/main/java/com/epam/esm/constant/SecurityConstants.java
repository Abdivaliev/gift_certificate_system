package com.epam.esm.constant;

public class SecurityConstants {
    public static final String BEARER = "Bearer ";
    public static final Integer EXPIRATION_TIME_ACCESS_TOKEN = 1000 * 86400;// a day
    public static final Integer EXPIRATION_TIME_REFRESH_TOKEN = 1000 * 86400 * 7;// 7 days
    public static final String SECRET_KEY = "3D2B7E151628AED2A6ABF7158809CF4F3C76E22D36F5D9E4C5F3C311B4A8A62F";
}
