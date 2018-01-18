package com.tow.skills.lucene;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.io.StringReader;

//@Slf4j
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        StringReader reader = new StringReader("Lucene is mainly used for information retrieval " +
                "and you can read more about it at lucene.apache.org.");
        StandardAnalyzer wa = new StandardAnalyzer();
        TokenStream ts = null;
        try {
            ts = wa.tokenStream("field", reader);
            OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
            CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);

            ts.reset();

            while (ts.incrementToken()) {
                String token = termAtt.toString();
                log.info("[{}]", token);
                log.info("Token starting offset: {}", offsetAtt.startOffset());
                log.info(" Token ending offset: {}\n", offsetAtt.endOffset());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ts != null) {
                ts.close();
            }
            wa.close();
        }


    }
}