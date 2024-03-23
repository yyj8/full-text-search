package com.yyj.search.rocksdb;

import com.yyj.search.common.FileUtils;
import com.yyj.search.common.RandomStringUtils;
import org.rocksdb.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RocksDBDemo {

    private static String DATA_DIR = "D:\\workspace\\data\\rocksdb";
    private static String KEY_DIR = "D:\\workspace\\data\\rocksdbkeys\\key.txt";

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) {
        final Options options = new Options()
                .setCreateIfMissing(true)
                .setCompressionType(CompressionType.ZSTD_COMPRESSION)
                .setAllowMmapReads(true)
                .setAllowMmapWrites(true);

        try {
            final RocksDB db = RocksDB.open(options, DATA_DIR);
            long start = System.currentTimeMillis();
            put(db, 100000000);
            long end = System.currentTimeMillis();
            System.out.println("写入完成，耗时：" + (end - start));
//            get(db, 1000000);
            db.close();
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public static void put(RocksDB db, int recordCount) throws RocksDBException {
        StringBuilder valueSB = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            valueSB.append("http_log-");
            valueSB.append(System.currentTimeMillis() / 1000);
            valueSB.append("-");
            valueSB.append(RandomStringUtils.get(5));
        }
        int count = 0;
        StringBuilder keySB = new StringBuilder();
        for (int i = 0; i < recordCount; i++) {
            String keyStr = String.valueOf(RandomStringUtils.get(i % 50 + 5));
            keySB.append(keyStr + "\n");
            byte[] key = keyStr.getBytes();
            byte[] value = valueSB.toString().getBytes();
            db.put(key, value);
            if (count % 100000 == 0 && count != 0) {
                System.out.println("已经写入记录：" + count);
                FileUtils.write(KEY_DIR, keySB.toString());
                keySB = new StringBuilder();
            }
            count++;
        }
    }

    public static void get(RocksDB db, int recordCount) throws RocksDBException {
        BufferedReader reader = null;
        int count = 0;
        try {
            // 创建BufferedReader对象
            reader = new BufferedReader(new FileReader(KEY_DIR));

            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                long start = System.currentTimeMillis();
                byte[] value = db.get(line.getBytes(StandardCharsets.UTF_8));
                long end = System.currentTimeMillis();
                if (null != value & (end - start) > 0) {
                    System.out.println("耗时" + (end - start) + ", key=" + line + ", value=" + new String(value));
                }
                count++;
                if (count > recordCount) {
                    break;
                }
            }
        } catch (IOException e) {
            // 处理可能发生的IOException异常
            e.printStackTrace();
        } finally {
            // 最后，确保BufferedReader被关闭释放资源
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}