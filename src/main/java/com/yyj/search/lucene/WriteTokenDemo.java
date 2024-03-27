package com.yyj.search.lucene;

import com.yyj.search.common.FileUtils;
import com.yyj.search.common.RandomStringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class WriteTokenDemo {
    private static final String INDEX_DIR = "D:\\workspace\\data\\lucene";
    private static final String TOKENS_DIR = "D:\\workspace\\data\\lucenetokens\\token.txt";

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        createIndex(50000000, 1000000);
        long end = System.currentTimeMillis();
        System.out.println("插入耗时：" + (end - start));
    }

    private static void createIndex(int writeDocCount, int maxDoc) throws IOException {
        // 指定索引存储的位置
        Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));
        // 使用标准分析器，如果有引入其他的分词器，这里可以使用其他分词器实现，比如IK分词器
        // 配置IndexWriter
        IndexWriterConfig config = new IndexWriterConfig();
        //设置为非复合存储索引，这行如果不设置，会被打包到.cfs、.cfe、.si文件
        config.setUseCompoundFile(false);
        // 创建IndexWriter
        try (IndexWriter writer = new IndexWriter(directory, config)) {

            int count = 0;
            StringBuilder tokenSB = new StringBuilder();
            for (int i = 0; i < writeDocCount; i++) {
                // 添加字段，【Field.Store.YES/NO】表示字段原始内容是否存储下来，YES:存储；NO:不存储
                //StringField字段不分词，整个字段内容看做一个整体
                String token = RandomStringUtils.get(i % 50 + 5);
                String id = RandomStringUtils.get(36);
                StringField field1 = new StringField("id", id, Field.Store.YES);
                StringField field2 = new StringField("token", token, Field.Store.YES);

                Term delTerm = new Term("id", "");
                Document doc = new Document();
                doc.add(field1);
                doc.add(field2);

                if (i > maxDoc) {
                    writer.updateDocuments(delTerm, List.of(doc));
                } else {
                    writer.addDocument(doc);
                }
                tokenSB.append(token + "\n");
                if (count % 100000 == 0 && count != 0) {
                    System.out.println("已经写入记录：" + count);
                    FileUtils.write(TOKENS_DIR, tokenSB.toString());
                    tokenSB = new StringBuilder();
                    writer.forceMerge(2);
                }
                count++;
            }
            //把所有segment强制合并成一个segment
            writer.forceMerge(2);
            writer.close();
        } finally {
            directory.close();
        }
    }

}