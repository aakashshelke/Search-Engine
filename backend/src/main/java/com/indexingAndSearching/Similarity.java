package com.indexingAndSearching;

public class Similarity {
	@SuppressWarnings("deprecation")
	public static double CosineSimilarity(DocVector d1,DocVector d2) {
        double cosinesimilarity;
        try {
            cosinesimilarity = (d1.vector.dotProduct(d2.vector))
                    / (d1.vector.getNorm() * d2.vector.getNorm());
        } catch (Exception e) {
            return 0.0;
        }
        return cosinesimilarity;
    }
	@SuppressWarnings("deprecation")
	public static double DotProduct(DocVector d1,DocVector d2) {
        double dotProduct;
        try {
        	dotProduct = d1.vector.dotProduct(d2.vector);
        } catch (Exception e) {
            return 0.0;
        }
        return dotProduct;
    }
	public static double EuclideanDistance(DocVector d1,DocVector d2) {
        double euclideanDist;
        double sim;
        try {
        	euclideanDist = d1.vector.getNorm() * d2.vector.getNorm();
        	sim = 1/(1+euclideanDist);
        } catch (Exception e) {
            return 0.0;
        }
        return sim;
    }
}
