package com.yyj.search.common;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class StrMatchDemo {
    public static void main(String[] args) {

        Set<String> sets = new HashSet<>(1000000);
        long byteSize = 0;
        for (int i = 0; i < 1000000; i++) {
            String token = RandomStringUtils.get(i % 50 + 5);
            sets.add(token);
            byteSize = byteSize + token.length();
        }

        long start = System.currentTimeMillis();
        for (String token : sets) {
            if (token.contains(RandomStringUtils.get(25))) {
                continue;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("匹配耗时：" + (end - start) + " ms");
        System.out.println(">>>>>>>>>>>>>>>>>>>byteSize=" + byteSize / 1024 / 1024 + "MB");

    }
}
