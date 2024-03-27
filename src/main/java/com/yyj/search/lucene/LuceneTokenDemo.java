/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.yyj.search.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class LuceneTokenDemo {
    public static void main(String[] args) throws IOException {
        // 创建一个IKAnalyzer分词器
        Analyzer analyzer = new IKAnalyzer();
//        Analyzer analyzer = new StandardAnalyzer ();

        // 要分词的字符串
//        String text = "/etc/haproxy/smbhap.sh";
//        String text = "未封禁.";
        String text = "http://10.52.1.252/disable/disable.htm?─�9�Ы������ъS`��d�UR���jq�o 8s?D�7�(����.$aW;/��:��#|��aȞ�k7�N�n=N��y=��<GBL})��B��7�̒z�3Wn~ET���>'�~O�e�[R�rV��J�3g����M:;o�|HtV�D�ڟ]�/v�j����───���───���──url_type=%E8%AE%BF%E9%97%AE%E7%BD%91%E7%AB%99/%49%54%E8%A1%8C%E4%B8%9A&plc_name=%E7%A0%94%E5%8F%91%E6%8B%92%E7%BB%9D%E4%B8%8A%E7%BD%91";

        // 使用StandardAnalyzer分词器对字符串text进行分词
        try (TokenStream tokenStream = analyzer.tokenStream("field", new StringReader(text))) {
            // CharTermAttribute是tokenStream的一个属性，它包含了分词后的词汇
            CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            // 遍历分词数据
            while (tokenStream.incrementToken()) {
                System.out.println(attr.toString());
            }

            tokenStream.end();
        }
    }
}
