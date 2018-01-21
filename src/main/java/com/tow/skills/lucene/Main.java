package com.tow.skills.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

//@Slf4j
public class Main {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(""));
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearch = new IndexSearcher(directoryReader);

        Query query = new WildcardQuery(new Term("content", "alpha"));
        SortField sortField = new SortField("name", SortField.Type.STRING);

        Sort sort = new Sort(sortField);

        TopDocs topDocs = indexSearch.search(query, 100, sort);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = directoryReader.document(scoreDoc.doc);
            System.out.println(doc);
        }
    }
}