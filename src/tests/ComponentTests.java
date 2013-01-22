package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	GeneratorTest.class,
	InfrastructureTest.class,
	PumpTest.class,
	ReactorTest.class,
	TurbineTest.class,
	ValveTest.class,
	CondenserTest.class
	
})
public class ComponentTests {

}
