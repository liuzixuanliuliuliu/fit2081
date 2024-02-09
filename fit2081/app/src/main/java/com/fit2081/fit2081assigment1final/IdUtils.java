package com.fit2081.fit2081assigment1final;

import java.util.Random;
import java.util.UUID;

public class IdUtils {

    public static String genId(){
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Random rand = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randomLetter = letters[rand.nextInt(letters.length)];
            sb.append(randomLetter);
        }

        String first = sb.toString();


        int min = 1000;
        int max = 9999;
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return first + "-" + randomNum;
    }
}
