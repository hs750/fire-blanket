package anchovy.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ InfoPacketTest.class, PairTest.class, GameEngineTest.class, ValveTest.class, GeneratorTest.class, PumpTest.class })
public class AllTests {

}
