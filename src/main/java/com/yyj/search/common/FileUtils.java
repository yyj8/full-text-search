package com.yyj.search.common;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    public static void write(String filePath, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, true);
            fos.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        write("D:\\workspace\\github\\yyj8\\full-text-search\\example.txt","hello");
    }
}
