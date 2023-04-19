package com.admin.core.jwt;

public class JwtVO {
    public static final String SECRET = "study";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

}
