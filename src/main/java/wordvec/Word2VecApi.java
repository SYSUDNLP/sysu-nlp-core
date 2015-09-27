package wordvec;

import utils.VectorUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Yoosan on 15/9/19.
 * All rights reserved @SYSUNLP GROUP
 */

/**
 * The API of Word2Vec model.
 * The Main() given a example how to use it.
 */
public class Word2VecApi {

    private WordToVec wordToVec = new WordToVec();
    private static String modelPath;
    private static Word2VecApi _wordVecApi = null;
    private static final float FLOAT_NULL = -2;

    public static void main(String[] args) {
        Word2VecApi api = Word2VecApi.getInstance("/Users/node/Downloads/trunk/vectors.bin");
        WordToVec toVec = api.getWordToVec();
        Set<WordToVec.WordEntry> entries = toVec.distance("break");
        for (WordToVec.WordEntry e : entries) {
            System.out.println(e.toString());
        }

        float distance = api.word2wordDistance("break", "break");
        System.out.println("The distance between 'hello' and 'helloworld' is :" + distance);
    }

    private Word2VecApi(String modelPath) {
        try {
            System.out.println("[INFO] Loading vectors.bin, waiting for this process...");
            wordToVec.loadModel(modelPath);
            System.out.println("[INFO] Load vectors.bin successfully.");
        } catch (IOException e) {
            System.out.println("[ERROR] The model file does'n exist, please check it.");
            e.printStackTrace();
        }
    }

    public static Word2VecApi getInstance(String _modelPath) {
        if (_wordVecApi == null) {
            _wordVecApi = new Word2VecApi(_modelPath);
        }
        return _wordVecApi;
    }

    public float words2wordsDistance(List<String> wordList_A, List<String> wordList_B) {
        float sum = 0;
        int size = 0;
        for (String wa : wordList_A) {
            float dis = words2wordDistance(wordList_B, wa);
            if (dis == FLOAT_NULL) continue;
            size++;
            sum += dis;
        }
        return sum / size;
    }

    public float words2wordDistance(List<String> wordList, String word) {
        float[] word2vec = wordToVec.getWordVector(word);
        if (word2vec == null) return FLOAT_NULL;
        return words2vecDistance(wordList, word2vec);
    }

    public float words2vecDistance(List<String> wordList, float[] wordVec) {
        float sum = 0;
        int size = 0;
        for (String w : wordList) {
            float[] wordV = wordToVec.getWordVector(w);
            if (wordV != null) {
                float cosDis = VectorUtil.cosDistance(wordV, wordVec);
                sum += cosDis;
                size++;
            }
        }
        return sum / size;
    }

    public float word2wordDistance(String wordA, String wordB) {
        float[] wordAvec = wordToVec.getWordVector(wordA);
        float[] wordBvec = wordToVec.getWordVector(wordB);
        if (wordAvec == null || wordBvec == null) return FLOAT_NULL;
        return VectorUtil.cosDistance(wordAvec, wordBvec);
    }

    public float wordsRelation(List<String> words) {
        float sum = 0;
        int length = words.size();
        int size = 0;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                sum += word2wordDistance(words.get(i), words.get(j));
                size++;
            }
        }
        return sum / size;
    }

    public WordToVec getWordToVec() {
        return wordToVec;
    }

    public String getModelPath() {
        return modelPath;
    }

}
