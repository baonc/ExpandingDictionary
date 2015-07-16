package com.bigdata.vccorp.word2vec;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.core.io.ClassPathResource;

/**
 * Class using Deep Learning 4 java library to generate vector for each word in corpus
 * 
 * @created 15 / 7 / 2015
 * @author baonc
 * @github https://github.com/baonc/ExpandingDictionary
 */
public class Word2Vec {
	private static final String INPUT_PATH = "NewsWordTraininng.txt";
	private static final String OUTPUT_PATH = "src/main/resources/NewsWordTrainingVector.txt";
	
	private org.deeplearning4j.models.word2vec.Word2Vec vec;
	
	/**
	 * Get word2vec model
	 * 
	 * @return	: vec of word2vec library
	 */
	public org.deeplearning4j.models.word2vec.Word2Vec getVec() {
		return vec;
	}
	
	/**
	 * Building word2vec model and set vector for each word
	 * 
	 * @param batchSize			: the amount of words process at any one time.
	 * @param minWordFrequency	: the floor on the number of times a word must appear in the corpus
	 * @param useAdaGrad		: Adagrad creates a different gradient for each feature.
	 * @param layerSize			: specifies the number of features in the word vector
	 * @param iterations		: the number of times allow the net to update its coefficients for 
	 * 							  one batch of the data. Too few iterations mean it many not have 
	 * 							  time to learn all it can; too many will make the net’s training 
	 * 							  longer.
	 * @param learningRate		: the step size for each update of the coefficients, as words are 
	 * 							  repositioned in the feature space
	 */
	public void word2Vec(int batchSize, int minWordFrequency, boolean useAdaGrad, int layerSize, 
			int iterations, double learningRate) throws Exception {
		Nd4j.getRandom().setSeed(133);
		
		System.out.println("Load data...");
		ClassPathResource resources = new ClassPathResource(INPUT_PATH);
		SentenceIterator iter = new LineSentenceIterator(resources.getFile());
		iter.setPreProcessor(new SentencePreProcessor() {
			private static final long serialVersionUID = 1L;

			@Override
			public String preProcess(String sentence) {
				return sentence.toLowerCase();
			}
		});
		
		System.out.println("Tokenizer data...");
		final EndingPreProcessor preProcessor = new EndingPreProcessor();
		TokenizerFactory tokenizer = new DefaultTokenizerFactory();
		tokenizer.setTokenPreProcessor(new TokenPreProcess() {
			
			@Override
			public String preProcess(String token) {
				token = token.toLowerCase();
				String base = preProcessor.preProcess(token);
				return base;
			}
		});
		
		System.out.println("Build model...");
		vec = new org.deeplearning4j.models.word2vec
				.Word2Vec.Builder().batchSize(batchSize).sampling(1e-5)
				.minWordFrequency(minWordFrequency).useAdaGrad(useAdaGrad).layerSize(layerSize)
				.iterations(iterations).learningRate(learningRate).minLearningRate(1e-2)
				.negativeSample(0).iterate(iter).tokenizerFactory(tokenizer).build();
		vec.fit();
		
		System.out.println("Save vector:");
		WordVectorSerializer.writeWordVectors(vec, OUTPUT_PATH);
	}
	
	/**
	 * Test function
	 * 
	 * @param args			: main args
	 * @throws Exception	: IOException
	 */
	public static void main(String args[]) throws Exception {
		Word2Vec word2Vec = new Word2Vec();
		word2Vec.word2Vec(1000, 5, false, 300, 30, 0.25);
	}
}