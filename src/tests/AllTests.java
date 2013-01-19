package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	GameEngineTest.class,
	GeneratorTest.class,
	InfoPacketTest.class,
	InfrastructureTest.class,
	PairTest.class,
	ParserTest.class,
	PumpTest.class,
	ReactorTest.class,
	TurbineTest.class,
	ValveTest.class
	
})
public class AllTests {

}
