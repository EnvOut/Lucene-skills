package com.tow.skills.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new WhitespaceAnalyzer();

        Directory directory = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, config);


        Document doc = new Document();
        String text = "Lucene is an Information Retrieval library written in Java.";
        doc.add(new TextField("fieldName", text, Field.Store.YES));

        indexWriter.addDocument(doc);
        indexWriter.close();
    }
}
