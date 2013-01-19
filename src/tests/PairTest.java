package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import anchovy.Pair;
import anchovy.Pair.Label;

public class PairTest {
	private Label testFirst = Label.temp;
	private Double testSecond = 123.456;
	private String testString  = testFirst + " " + testSecond;
	@Test
	public void testFirst() {
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testFirst.toString(), testPair.first());
				
	}
	
	@Test
	public void testSecond(){
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testSecond, testPair.second());
	}
	
	@Test
	public void testString(){
		Pair<Double> testPair = new Pair<Double>(testFirst, testSecond);
		assertEquals(testString, testPair.toString());
	}

}

