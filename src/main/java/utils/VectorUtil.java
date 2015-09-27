package utils;

/**
 * Created by Yoosan on 15/9/19.
 * All rights reserved @SYSUNLP GROUP
 */
public class VectorUtil {

    /**
     * Calculate the norm of a vector
     *
     * @param v, a float vector
     * @return norm
     */
    public static float vectorNorm(float[] v) {
        float result = 0;
        for (float aV : v) {
            result += aV * aV;
        }
        result = (float) Math.sqrt(result);
        return result;
    }

    /**
     * Calculate the dot-product result of two vectors.
     *
     * @param v1, vector 1
     * @param v2, vector 2
     * @return dot product
     */

    public static float dotProduct(float[] v1, float[] v2) {
        assert v1.length == v2.length;
        float result = 0;
        for (int i = 0; i < v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    /**
     * Calculate cosine distance of two vectors
     *
     * @param v1, vector 1
     * @param v2, vector 2
     * @return cosine distance
     */
    public static float cosDistance(float[] v1, float[] v2) {
        assert v1.length == v2.length;
        float dotProduct = dotProduct(v1, v2);
        float sumNorm = vectorNorm(v1) * vectorNorm(v2);
        return dotProduct / sumNorm;
    }

}
