package com.bigdata.vccorp.googleword2vecjava;

import java.util.Collection;

import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Class test Word2Vec Deep Learning 4 Java library
 * 
 * @created 14 / 7 / 2015
 * @author baonc
 * @github https://github.com/baonc/ExpandingDictionary
 */
public class Word2VecTest {
	private static Logger log = LoggerFactory.getLogger(Word2VecTest.class);
	
	public static void main(String args[]) throws Exception {
		int batchSize = 1000;
		int iterations = 1;
		int layerSize = 300;
		
		Nd4j.getRandom().setSeed(300);
		
		log.info("Load data...");
		ClassPathResource resource = new ClassPathResource("raw_sentences.txt");
		SentenceIterator iter = new LineSentenceIterator(resource.getFile());
		iter.setPreProcessor(new SentencePreProcessor() {
			private static final long serialVersionUID = 1L;

			@Override
			public String preProcess(String sentence) {
				return sentence.toLowerCase();
			}
		});
		
		log.info("tokenizer data...");
		final EndingPreProcessor preProcessor = new EndingPreProcessor();
		TokenizerFactory tokenizer = new DefaultTokenizerFactory();
		tokenizer.setTokenPreProcessor(new TokenPreProcess() {
			
			@Override
			public String preProcess(String token) {
				token = token.toLowerCase();
				String base = preProcessor.preProcess(token);
				base = base.replaceAll("\\d", "d");
				if(base.endsWith("ly") || base.endsWith("ing")) {
					System.out.println();
				}
				
				return base;
			}
		});
		
		log.info("Build model...");
		Word2Vec vec = new Word2Vec.Builder().batchSize(batchSize).sampling(1e-5)
				.minWordFrequency(5).useAdaGrad(false).layerSize(layerSize).iterations(iterations)
				.learningRate(0.025).minLearningRate(1e-2).negativeSample(0).iterate(iter)
				.tokenizerFactory(tokenizer).build();
		vec.fit();
		
		InMemoryLookupTable table = (InMemoryLookupTable) vec.lookupTable();
		table.getSyn0().diviRowVector(table.getSyn0().norm2(0));
		
		log.info("Evaluate model...");
		double sim = vec.similarity("people", "money");
		log.info("similarity between people and money is: " + sim);
		Collection<String> similar = vec.wordsNearest("day", 20);
		log.info("Similar words to 'day' : " + similar);
		log.info("save vector...");
		WordVectorSerializer.writeWordVectors(vec, "words.txt");
		
		log.info("--------------------------- finish -----------------------");
	}
}