package com.indexingAndSearching;

import java.util.Map;
import org.apache.commons.math3.linear.RealVectorFormat;
import org.apache.commons.math3.linear.OpenMapRealVector;

public class DocVector {

    public Map<String,Integer> terms;
    public OpenMapRealVector vector;
    
    public DocVector(Map<String,Integer> terms) {
        this.terms = terms;
        this.vector = new OpenMapRealVector(terms.size());        
    }

    public void setEntry(String term, int freq) {
        if (terms.containsKey(term)) {
            int pos = (int) terms.get(term);
            vector.setEntry(pos, (double) freq);
        }
    }

    public void normalize() {
        double sum = vector.getL1Norm();
        vector = (OpenMapRealVector) vector.mapDivide(sum);
    }

    public String toString() {
        RealVectorFormat formatter = new RealVectorFormat();
        return formatter.format(vector);
    }
}
