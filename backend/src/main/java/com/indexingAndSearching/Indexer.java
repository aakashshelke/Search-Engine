package com.indexingAndSearching;

import java.io. *;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	public static final String SOURCE_DIRECTORY = "src/main/resources/Doc";
    public static final String INDEX_DIRECTORY = "src/main/resources/Index";
    
    public static void index () throws IOException {
    	final Path docDir = Paths.get(SOURCE_DIRECTORY);
    	Directory dir = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
    	List<String> stopWords = Arrays.asList("");
		CharArraySet stopSet=new CharArraySet(stopWords, true);
    	Analyzer analyzer= new StandardAnalyzer(stopSet);
    	IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
    	iwc.setOpenMode(OpenMode.CREATE);
    	final IndexWriter writer = new IndexWriter(dir, iwc);
    	Files.walkFileTree(docDir, new SimpleFileVisitor<Path>() {
    		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    			try (InputStream stream = Files.newInputStream(file)) {
    				Document doc = new Document();
   			      	FieldType fieldType = new FieldType ();
   			      	IndexOptions indexOptions = IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS;
   			      	fieldType.setStored (true);
   			      	fieldType.setStoreTermVectors (true);
   			      	fieldType.setTokenized (true);
   			      	fieldType.setIndexOptions (indexOptions);

   			      	BufferedReader br=new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
   			      	String response = new String();
   			      	for (String line; (line = br.readLine()) != null; response += line);
   			      	Field contentField = new Field ("contents", response, fieldType);
   			      	String title = file.getFileName().toString().replace(".txt", "");
   			      	Field titleField = new Field ("title", title, fieldType);
   			      	doc.add (contentField);
   			      	doc.add (titleField);
   			      	if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
   			      		System.out.println("indexing " + file);
   			      		writer.addDocument(doc);
   			      	} 
   			 	} catch (Exception e) {
   			 		e.printStackTrace();
   			 	}
   			 	return FileVisitResult.CONTINUE;
    		}
   		});
   	    writer.close();
	}
    public String getAllText (File f) throws FileNotFoundException, IOException {
        String textFileContent = "";

        for (String line: Files.readAllLines (Paths.get (f.getAbsolutePath ()))) {
            textFileContent = textFileContent+line;
        }
        return textFileContent;
    }
}