package com.example.hackerrank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taner YILDIRIM
 */
public class EmailValidation {

    public static boolean emailValidation(String email) {
        //String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String email = "TULUMBOBINAJ@HOTMAIL.COM.55";

        if(emailValidation(email))
            System.out.println("valid");
    }
}
