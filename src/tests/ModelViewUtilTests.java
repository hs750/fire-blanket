package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GameEngineTest.class, InfoPacketTest.class, PairTest.class,
		ParserTest.class })
public class ModelViewUtilTests {

}
