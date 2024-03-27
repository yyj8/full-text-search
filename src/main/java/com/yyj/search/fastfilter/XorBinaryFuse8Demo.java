package com.yyj.search.fastfilter;

import org.fastfilter.xor.XorFuse8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class XorBinaryFuse8Demo {
    public static void main(String[] args) {
        long[] attr = new long[1000000];
        for (int i = 0; i < 1000000; i++) {
            attr[i] = i;
        }
        XorFuse8 construct = XorFuse8.construct(attr);

        serializeObject(construct,"XorBinaryFuse8Demo.txt");
        System.out.println(construct.mayContain(1111));
        System.out.println(construct.supportsAdd());

        int exists = 0;
        for (int i = 0; i < 2000000; i++) {
            if(construct.mayContain(i)){
                exists++;
            }
        }


        System.out.println(exists);
        System.out.println((exists-1000000)/2000000.0);

        System.out.println("hash="+"fsdafaa".hashCode());
        System.out.println("hash="+"fsdafaa".hashCode());
        System.out.println("hash="+"fsdafab ".hashCode());
        System.out.println("hash="+"中国 ".hashCode());
        System.out.println("hash="+"中国人 ".hashCode());
    }

    public static void serializeObject(XorFuse8 obj, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(obj);
            System.out.println("对象已序列化并保存到文件：" + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
