package com.uprint.android_pack.cloudprint4androidmanager.network;

/**
 * Created by tonychiang on 15/10/13.
 */
public class NetValue {
    // for config the url
    //线上环境
//    public static final String ORIGIN_PATH = "http://api.uprintf.com/v1";
    //下面这个是给测试环境的
    public static final String ORIGIN_PATH = "http://api.in.uprintf.com/v1";
    //manager info api
    public static final String SMS_CODE = "/manager/smsCode";
    public static final String SIGN_UP = "/manager/signup";
    public static final String IMPROVE_INFO = "/manager/info";
    public static final String LOGIN = "/manager/login";
    public static final String POST_PASSWORD = "/manager/password";
    public static final String UPDATE_MANAGER_INFO = "/manager/info/update";
    public static final String PASSWORD_UPDATE = "/manager/password/update";
    public static final String LOGOUT = "/manager/logout";
    //orders api
    public static final String GET_ORDERS = "/manager/orders";
    public static final String DETAIL_ORDER = "/orders/detail";
    public static final String CONFIRM_ORDER = "/orders/confirm";

    //other api
    public static final String RESIGN = "/manager/resign";
    public static final String COMPLAINS = "/shop/complaints";
    public static final String SCHOOL = "/address/school";
    public static final String BUILDINGS = "/buildings";

    public static final int STATUS_NO_NETWORK = 0;
    public static final int STATUS_TIMEOUT = 1;
    public static final int STATUS_URL_ERROR = 2;
    public static final int TIP_TIMEOUT = 4;
    public static final int TIP_URL_ERROR = 5;
    public static final int STATUS_SUCCESS = 9;
    public static final int NOT_LOGIN = 11;
    public static final int TIP_SHOULD_LOGIN = 12;
    public static final int NO_CONNECTION = 13;
    public static final int TIP_NO_CONNECTION = 14;
    public static final int REDIRECT_ERROR = 15;
    public static final int TIP_REDIRECT_ERROR = 16;
    public static final int SERVER_ERROR = 17;
    public static final int TIP_SERVER_ERROR = 18;


    public static StringBuilder GET_HOME_PATH() {
        StringBuilder builder = new StringBuilder();
        builder.append(ORIGIN_PATH);
        return builder;
    }

    public static String GET_SMS_CODE() {
        return GET_HOME_PATH().append(SMS_CODE).toString();
    }

    public static String GET_SIGN_UP() {
        return GET_HOME_PATH().append(SIGN_UP).toString();
    }

    public static String GET_SCHOOL() {
        return GET_HOME_PATH().append(SCHOOL).toString();
    }

    public static String IMPROVE_INFO() {
        return GET_HOME_PATH().append(IMPROVE_INFO).toString();
    }

    public static String LOGIN() {
        return GET_HOME_PATH().append(LOGIN).toString();
    }

    public static String POST_PASSWORD() {
        return GET_HOME_PATH().append(POST_PASSWORD).toString();
    }

    public static String UPDATE_MANAGER_INFO() {
        return GET_HOME_PATH().append(UPDATE_MANAGER_INFO).toString();
    }

    public static String PASSWORD_UPDATE() {
        return GET_HOME_PATH().append(PASSWORD_UPDATE).toString();
    }

    public static String GET_ORDERS() {
        return GET_HOME_PATH().append(GET_ORDERS).toString();
    }

    public static String DETAIL_ORDER() {
        return GET_HOME_PATH().append(DETAIL_ORDER).toString();
    }

    public static String CONFIRM_ORDER() {
        return GET_HOME_PATH().append(CONFIRM_ORDER).toString();
    }

    public static String RESIGN() {
        return GET_HOME_PATH().append(RESIGN).toString();
    }

    public static String COMPLAINS() {
        return GET_HOME_PATH().append(COMPLAINS).toString();
    }

    public static String BUILDINGS() {
        return GET_HOME_PATH().append(BUILDINGS).toString();
    }

    public static String LOGOUT() {
        return GET_HOME_PATH().append(LOGOUT).toString();
    }
}
