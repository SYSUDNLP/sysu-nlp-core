package wordvec;

import java.io.*;
import java.util.*;

/**
 * Created by Yoosan on 15/9/19.
 * All rights reserved @SYSUNLP GROUP
 */

/**
 * WordToVec is the class for loading the trained word2vec model 'vectors.bin'
 */
public class WordToVec {

    private static final int MAX_SIZE = 50;
    private Map<String, float[]> wordMap = new HashMap<String, float[]>();
    private int size;
    private int words;
    private int topNSize = 40;

    public static float readFloats(InputStream is) throws IOException {
        byte[] bytes = new byte[4];
        is.read(bytes);
        return getFloat(bytes);
    }

    public static float getFloat(byte[] bytes) {
        int accum = 0;
        accum = accum | (bytes[0] & 0xff);
        accum = accum | (bytes[1] & 0xff) << 8;
        accum = accum | (bytes[2] & 0xff) << 16;
        accum = accum | (bytes[3] & 0xff) << 24;
        return Float.intBitsToFloat(accum);
    }

    private static String readString(DataInputStream dis) throws IOException {
        byte[] bytes = new byte[MAX_SIZE];
        byte b = dis.readByte();
        int i = -1;
        StringBuffer buffer = new StringBuffer();
        while (b != 32 && b != 10) {
            i++;
            bytes[i] = b;
            b = dis.readByte();
            if (i == MAX_SIZE - 1) {
                buffer.append(new String(bytes));
                i = -1;
                bytes = new byte[MAX_SIZE];
            }
        }
        buffer.append(new String(bytes, 0, i + 1));
        return buffer.toString();
    }

    /**
     *
     * @param path the path of vectors.bin
     * @return if loading successful then true, else false
     * @throws IOException
     */
    public boolean loadModel(String path) throws IOException {
        DataInputStream dis = null;
        BufferedInputStream bis = null;
        double norm;
        float vector;
        bis = new BufferedInputStream(new FileInputStream(path));
        dis = new DataInputStream(bis);
        words = Integer.parseInt(readString(dis));
        size = Integer.parseInt(readString(dis));
        System.out.println("[Words] -- " + words);
        System.out.println("[Embedding dimension] -- " + size);
        String word;
        float[] vectors;
        for (int i = 0; i < words; i++) {
            word = readString(dis);
            vectors = new float[size];
            norm = 0;
            for (int j = 0; j < size; j++) {
                vector = readFloats(dis);
                norm += vector * vector;
                vectors[j] = vector;
            }
            norm = Math.sqrt(norm);

            for (int j = 0; j < size; j++) {
                vectors[j] = (float) (vectors[j] / norm);
            }

            wordMap.put(word, vectors);
            dis.read();
        }
        bis.close();
        dis.close();
        return true;
    }

    /**
     * Get the top-N nearest words of the given word
     * @param word
     * @return Sets of wordentry
     */
    public Set<WordEntry> distance(String word) {
        float[] wordVec = getWordVector(word);
        if (wordVec == null) return null;
        Set<Map.Entry<String, float[]>> entries = wordMap.entrySet();
        float[] tempVec;
        List<WordEntry> wordEntries = new ArrayList<WordEntry>(topNSize);
        String name;
        for (Map.Entry<String, float[]> entry : entries) {
            name = entry.getKey();
            if (name.equals(word)) continue;
            float dist = 0;
            tempVec = entry.getValue();
            for (int i = 0; i < wordVec.length; i++) {
                dist += wordVec[i] * tempVec[i];
            }
            insertTopN(name, dist, wordEntries);
        }
        return new TreeSet<WordEntry>(wordEntries);
    }

    public TreeSet<WordEntry> analogy(String word1, String word2, String word3) {
        float[] w1Vec = getWordVector(word1);
        float[] w2Vec = getWordVector(word2);
        float[] w3Vec = getWordVector(word3);

        if (w1Vec == null || w2Vec == null || w3Vec == null) return null;

        float[] resVector = new float[size];
        for (int i = 0; i < size; i++) {
            resVector[i] = w1Vec[i] + w2Vec[i] - w3Vec[i];
        }
        float[] tempVec;
        String name;
        List<WordEntry> wordEntries = new ArrayList<WordEntry>(topNSize);
        Set<Map.Entry<String, float[]>> entrySet = wordMap.entrySet();
        for (Map.Entry entry : entrySet) {
            name = (String) entry.getKey();
            if (name.equals(word1) || name.equals(word2) || name.equals(word3)) continue;
            float dist = 0;
            tempVec = (float[]) entry.getValue();
            for (int i = 0; i < tempVec.length; i++) {
                dist += resVector[i] * tempVec[i];
            }
            insertTopN(name, dist, wordEntries);
        }
        return new TreeSet<WordEntry>(wordEntries);
    }

    public void insertTopN(String name, float score, List<WordEntry> wordEntries) {
        if (wordEntries.size() < topNSize) {
            wordEntries.add(new WordEntry(name, score));
            return;
        }
        float min = Float.MAX_VALUE;
        int minIndex = 0;
        for (int i = 0; i < topNSize; i++) {
            WordEntry wordEntry = wordEntries.get(i);
            if (min > wordEntry.score) {
                min = wordEntry.score;
                minIndex = i;
            }
        }

        if (score > min) {
            wordEntries.set(minIndex, new WordEntry(name, score));
        }
    }

    public float[] getWordVector(String word) {
        return wordMap.get(word);
    }

    public int getTopNsize() {
        return topNSize;
    }

    public void setTopNSize(int topNSize) {
        this.topNSize = topNSize;
    }

    public Map<String, float[]> getWordMap() {
        return wordMap;
    }

    public int getWords() {
        return words;
    }

    public int getSize() {
        return size;
    }

    public class WordEntry implements Comparable<WordEntry> {
        public String name;
        public float score;

        public WordEntry(String name, float score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.name + "\t" + this.score;
        }

        public int compareTo(WordEntry o) {
            if (this.score > o.score) {
                return -1;
            } else {
                return 1;
            }
        }
    }

}
