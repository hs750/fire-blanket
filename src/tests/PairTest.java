package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import anchovy.Pair;
import anchovy.Pair.Label;

public class PairTest {
	private Label testFirst = Label.temp;
	private Double testSecond = 123.456;
	private String testString  = testFirst + " " + testSecond;
	
	/**
	 * Test whether the first item in the pair returns the correct value
	 */
	@Test
	public void testFirst() {
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testFirst.toString(), testPair.first());
				
	}
	/**
	 * Test whether the second item in the pair returns the correct value
	 */
	@Test
	public void testSecond(){
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testSecond, testPair.second());
	}
	
	/**
	 * Test whether the pair returns the correct string relating to the values of its first and second values.
	 */
	@Test
	public void testString(){
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testString, testPair.toString());
	}

}

