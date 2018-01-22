package com.tow.skills.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory directory = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        Document doc = new Document();
        TextField textField = new TextField("content", "", Field.Store.YES);

        String[] contents = {"Humpty Dumpty sat on wail,",
                "Humpty Dumpty had a great fail.",
                "All the king's horses and all the king's men",
                "Coudn't put Humpty together again."};

        List<Document> docs = new ArrayList<>();

        for (String content : contents) {
            textField.setStringValue(content);
            doc.removeField("content");
            doc.add(textField);
            docs.add(doc);
            indexWriter.addDocument(doc);
        }
        indexWriter.commit();

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        QueryParser queryParser = new QueryParser("content", analyzer);
        Query query = queryParser.parse("humpty dumpty");

        TopDocs topDocs = indexSearcher.search(query, 2);
        log.info("Total hits: {}", topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            doc = indexReader.document(scoreDoc.doc);
            log.info("{}:{}", scoreDoc.score, doc.getField("content").stringValue());
        }
    }
}