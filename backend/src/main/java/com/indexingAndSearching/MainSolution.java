package com.indexingAndSearching;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainSolution {
//	public static void main(String[] args) throws IOException {
//		Indexer.index();
////		VectorGenerator vectorGenerator = new VectorGenerator();
////		vectorGenerator.GetAllTerms();       
////		DocVector[] docVector = vectorGenerator.GetDocumentVectors(); // getting document vectors
////		double cosineSimilarity1 = Similarity.CosineSimilarity(docVector[0], docVector[1]);
////		System.out.println("Cosine Similarity between document 1 and document 2 = " + cosineSimilarity1);
////		
////		double dotProduct = Similarity.DotProduct(docVector[0], docVector[1]);
////		System.out.println("DotProduct between document 1 and document 2 = " + dotProduct);
////		
////		double euclideanDIstance = Similarity.EuclideanDistance(docVector[0], docVector[1]);
////		System.out.println("EuclideanDistance between document 1 and document 2 = " + euclideanDIstance);
//
//
//
//		///////////////////PART B /////////////////////////
//
//		String indexPath = ".\\Index\\";
//		String queryString = "She is a sunny girl";
//		try {
//			searchQuery(indexPath, queryString);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

	public static List<Map<String, Object>> searchQuery(String indexPath, String queryString, int numberOfRes) throws IOException, ParseException {
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
		IndexSearcher searcher = new IndexSearcher(reader);
		searcher.setSimilarity(new BM25Similarity());
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("contents", analyzer);
		Query query = parser.parse(queryString);

		TopScoreDocCollector collector = TopScoreDocCollector.create(numberOfRes);
		searcher.search(query, collector);
		
		List<Map<String, Object>> relevantDocs = new ArrayList<Map<String, Object>>();
		ScoreDoc[] docs = collector.topDocs().scoreDocs;
		for (int i = 0; i < docs.length; i++) {
			Map<String, Object> relevantDoc = new HashMap<String, Object>();
			Document doc = searcher.doc(docs[i].doc);
			relevantDoc.put("title", doc.get("title"));
			relevantDoc.put("score", docs[i].score);
			relevantDoc.put("content", doc.get("contents"));
			relevantDocs.add(relevantDoc);
		}
		reader.close();
		return relevantDocs;
	}
}