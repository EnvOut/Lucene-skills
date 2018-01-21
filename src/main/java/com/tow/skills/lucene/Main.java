package com.tow.skills.lucene;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
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

    }
}