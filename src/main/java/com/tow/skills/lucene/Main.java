package com.tow.skills.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

//@Slf4j
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        StringReader reader = new StringReader("Lucene is mainly used for information retrieval " +
                "and you can read more about it at lucene.apache.org.");
        Map<String, Analyzer> analyzerPerField = new HashMap<String, Analyzer>();

        analyzerPerField.put("myField", new WhitespaceAnalyzer());

        PerFieldAnalyzerWrapper defAnalizer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), analyzerPerField);
        TokenStream ts = null;

        OffsetAttribute offsetAtt = null;
        CharTermAttribute charAtt = null;
        try {
            ts = defAnalizer.tokenStream("myField", new StringReader("lucene.apache.org AB-978"));
            process(ts, "== Processing field 'myField' using WhitespaceAnalyzer (per field) ==");

            ts = defAnalizer.tokenStream("content", new StringReader("lucene.apache.org AB-978"));
            process(ts, "== Processing field 'content' using StandartAnalyzer (per field) ==");
        } finally {
            if (ts != null) {
                ts.close();
            }
        }
    }

    private static void process(TokenStream ts, String message) throws IOException {
        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
        CharTermAttribute charAtt = ts.addAttribute(CharTermAttribute.class);
        ts.reset();

        log.info(message);
        while (ts.incrementToken()) {
            log.info(charAtt.toString());
            log.info("token start offset: {}", offsetAtt.startOffset());
            log.info("token end offset: {}", offsetAtt.endOffset());
        }
        ts.end();
    }
}