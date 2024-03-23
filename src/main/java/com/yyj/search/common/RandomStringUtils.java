package com.yyj.search.common;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStringUtils {
    public static String SEEDS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String get(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(SEEDS.length());
            sb.append(SEEDS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Generated random string: " + get(5));
    }
}
