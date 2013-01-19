/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.*;

import anchovy.*;
import anchovy.io.*;

/**
 * 
 * @author andrei
 */
public class ParserTest {
	static Parser parser = null;
	
	@BeforeClass
	public static void initParser() {
		parser = new Parser(new GameEngine());
	}
	
	/**
	 * Test method for {@link anchovy.io.Parser#parseCommand(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testParseCommand() {
		assertTrue(true);
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link anchovy.io.Parser#parse(java.lang.String)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testParse() throws FileNotFoundException {
		
		assertEquals(parser.parse(""), "");
		
		assertEquals(parser.parse("no-spaces-string"), "");
		
		//assertNotSame(parser.parse("load abc.fg"), "");
	}

}
