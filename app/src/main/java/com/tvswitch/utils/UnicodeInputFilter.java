package com.tvswitch.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tuong on 5/28/15.
 */
public class UnicodeInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Pattern classPattern = Pattern.compile("[\u0000-\u1ffe]");
        Pattern classPattern2 = Pattern.compile("[\u2100-\u215f]");
        for (int i = 0; i < source.length(); i++) {
            Matcher classMatcher = classPattern.matcher(String.valueOf(source.charAt(i)));
            Matcher classMatcher2 = classPattern2.matcher(String.valueOf(source.charAt(i)));
            if (!classMatcher.find() && !classMatcher2.find()) {
                return "";
            }
        }
        return null;
    }
}
