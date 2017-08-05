package com.alienlab.sms;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 橘 on 2017/8/4.
 */
public class SmsCodePool
{
    public static Map<String,String> CodePool=new HashMap<>();

    public static String getRandomCode(int length){
        return String.valueOf(((Math.random()*9+1)*100000)).substring(0,6);
    }
    public static void setPhoneCode(String phone,String code){
        CodePool.put(phone,code);
    }
    public boolean validatePhoneCode(String phone,String code){
        if(CodePool.containsKey(phone)){
            if(CodePool.get(phone).equalsIgnoreCase(code)){
                CodePool.remove(phone);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
