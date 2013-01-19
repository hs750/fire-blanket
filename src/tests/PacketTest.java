package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import anchovy.util.Packet;
import anchovy.util.Tag;

public class PacketTest {
	Packet a = null;
	
	@Before
	public void testSetup() {
		a = new Packet();
	}

	@Test
	public void testPutAndGet() {
		a.put(Tag.test1, new Integer(32));
		a.put(Tag.test2, new String("\"I'm a test\""));
		a.put(Tag.test3, new Integer(118));
		a.put(Tag.test3, new Integer(119));
		a.put(Tag.test3, new Integer(120));
		
		// System.out.println(a); // uncomment if you want to see how toString outputs Packet
		
		assertEquals("Result", (Integer) 120, (Integer) a.get(Tag.test3));
	}

	@Test
	public void testContainsTag() {
		a.put(Tag.test4, new Integer(32));
		
		assertTrue(a.containsTag(Tag.test4));
	}
}
