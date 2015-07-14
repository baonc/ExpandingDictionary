package com.bigdata.vccorp.googleword2vecjava;

import org.junit.rules.ExpectedException;

/**
 * Class test Word2VecJava library
 * 
 * @created 14 / 7 / 2015
 * @author baonc
 * @github https://github.com/baonc/ExpandingDictionary
 */
public class Word2VecTest {
	public ExpectedException expected = ExpectedException.none();
	
	// cleanup after test run
	public void after() {
		Thread.interrupted();
	}
	
	
}
