package com.indexingAndSearching;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

public class IndexOpener {
    public static final String INDEX = "src/main/resources/Index/";
	public static IndexReader GetIndexReader() throws IOException {
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX)));
        return indexReader;
    }

    //Returns the total number of documents in the index
    public static Integer TotalDocumentInIndex() throws IOException {
        Integer maxDoc = GetIndexReader().maxDoc();
        GetIndexReader().close();
        return maxDoc;
    }
}
