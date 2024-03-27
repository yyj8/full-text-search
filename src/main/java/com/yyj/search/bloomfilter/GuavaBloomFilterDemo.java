package com.yyj.search.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.fastfilter.xor.XorFuse8;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GuavaBloomFilterDemo {
    public static void main(String[] args) {
        // 创建一个预计要插入多少数据，和可接受的错误率
        int expectedInsertions = 10000000;
        double falsePositiveProbability = 0.001;

        // 创建布隆过滤器
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                expectedInsertions,
                falsePositiveProbability);

        // 向布隆过滤器中添加数据
        for (int i = 0; i < 1000000; i++) {
            filter.put(i);
        }

        serializeObject(filter,"GuavaBloomFilterDemo.txt");

        System.out.println("approximateElementCount=" + filter.approximateElementCount());
        System.out.println("expectedFpp=" + filter.expectedFpp());

        int exists = 0;
        for (int i = 0; i < expectedInsertions * 2; i++) {
            boolean cons = filter.mightContain(i);
            if (cons) {
                exists++;
            }
        }
        System.out.println("exists=" + exists);
        System.out.println("误报率=" + (exists-expectedInsertions)/(expectedInsertions*2.0));
    }

    public static void serializeObject(BloomFilter obj, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(obj);
            System.out.println("对象已序列化并保存到文件：" + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}