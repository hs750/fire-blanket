package anchovy.old;

import java.util.ArrayList;

public class TestingPointers {
	ArrayList<TestingPointers> connections = null;
	protected static Integer nextID = 0;
	protected Integer ID;
	
	public TestingPointers() {
		ID = nextID++;
		this.connections = new ArrayList<TestingPointers>();
	}
	
	public void addConnection(TestingPointers c) {
		this.connections.add(c);
	}
	
	@Override
	public String toString() {
		return new String("I am number " + this.ID + "and my connections are:\n" + this.connections.toString());
	}
	
	public static void main(String[] args) {
		TestingPointers tp1 = new TestingPointers();
		TestingPointers tp2 = new TestingPointers();
		TestingPointers tp3 = new TestingPointers();
		TestingPointers tp4 = new TestingPointers();
		
		tp1.addConnection(tp2);
		tp1.addConnection(tp3);
		tp1.addConnection(tp4);
		
		System.out.println(tp1);
	}
}
