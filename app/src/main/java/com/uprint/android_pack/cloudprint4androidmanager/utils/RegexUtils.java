package com.uprint.android_pack.cloudprint4androidmanager.utils;

import com.rengwuxian.materialedittext.validation.RegexpValidator;

/**
 * Created by zhangxiaang on 15/10/16.
 */
public class RegexUtils {
    public static RegexpValidator email() {
        return new RegexpValidator("请输入正确格式的email", "^[a-z0-9]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$");
    }

    public static RegexpValidator name() {
        //判断2-5个中文
        return new RegexpValidator("2-5个中文", "^[\\u4e00-\\u9fa5]{2,5}$");
    }

    public static RegexpValidator qq() {
        return new RegexpValidator("请输入正确格式的qq", "[1-9][0-9]{5,11}");
    }

    public static RegexpValidator zhifubao() {
        return new RegexpValidator("请输入正确格式的账号", "^[a-z0-9]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$|" +
                "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    }

    public static RegexpValidator phone() {
        return new RegexpValidator("请输入正确的手机号", "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    }

    public static RegexpValidator year() {
        return new RegexpValidator("输入4位数字即可", "^20[1-5][0-9]$");
    }

    public static RegexpValidator idnum() {
        return new RegexpValidator("请输入正确的身份证号", "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");
    }
}
