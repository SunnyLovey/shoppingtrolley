package com.bawei.demo.shoppingtrolley.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class Method {
    public static boolean isPhone(String phone){
        Pattern compile = compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = compile.matcher(phone);
        return matcher.matches();
    }
    public static boolean isPass(String pass){
        return pass.length()>=6 && pass.length()<=20;
    }
    public static String getRandom(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }
        return sb.toString();
    }
}
