package com.tow.skills.lucene;

import org.apache.lucene.analysis.Analyzer;
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

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();

        Directory directory = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        Document doc = new Document();
        String text = "Lucene is an Information Retrieval library written in Java. Lucene";
        doc.add(new TextField("Content", text, Field.Store.YES));

        indexWriter.addDocument(doc);
        indexWriter.close();

        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        QueryParser queryParser = new QueryParser("Content", analyzer);
        Query query = queryParser.parse("Lucene");

        int hitsPerPage =10;
        TopDocs docs =indexSearcher.search(query,hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        long end = Math.min(docs.totalHits, hitsPerPage);
        System.out.println("Total Hits: "+docs.totalHits);
        System.out.println("Results: ");

        for (int i=0;i<end;i++){
            Document d = indexSearcher.doc(hits[i].doc);
            System.out.println("Content: "+d.get("Content"));
        }

//        System.out.println(indexSearcher.doc(0));
//        System.out.println(indexSearcher.);
    }

}
