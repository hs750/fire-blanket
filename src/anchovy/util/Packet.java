package anchovy.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Class that enables a simple communication protocol. It stores pairs of names and values (e.g. "temperature", (Integer)320).
 *  It's worth noting that the value part is basically like a C++ pointer, it doesn't allocate memory for itself. Check out this
 *  usage example for further understanding:
 *  <pre>
 *  Packet a = new Packet();
 *  a.put("temperature", Integer(320));
 *  String.out.println("The temperature is: " + (String)a.get(Tag.reactorTemp));
 *  </pre>
 *  So basically you have to cast values when you 'get' them from a Packet, and allocate them when you 'put' them in one.
 *   That means you cannot use primitive types such as int or double, because they have no constructors. Use Integer or Double instead.
 *  
 * @author andrei
 * @see anchovy.sandbox.ui.UserInterface
 */
public class Packet {
	// Data
	HashMap<Tag, Object> store = null;
	
	// Operations
	/**
	 * This method retrieves a value form the packet; caveat: you must cast the resulting expression<br>
	 * e.g. String value = (String) get(Tag.name);
	 * @param tag	This is the key, the Tag we want to retrieve the value for
	 * @return		An Object value - must be casted to be used; return null if the key doesn't exist
	 */
	public Object get(Tag tag) {
		return this.store.get(tag);
	}
	
	/**
	 * Puts a tag, value pair in the Packet
	 * @param tag		Enum (Tag) denoting what the value represents
	 * @param value		a value - must be allocated!! (e.g. Integer(320))
	 * @return			if the key was already in the Packet it return the value of the previous object stored there; otherwise, null
	 */
	public Object put(Tag tag, Object value) {	
		return this.store.put(tag, value);
	}
	
	public Boolean containsTag(Tag tag) {
		return this.store.containsKey(tag);
	}
	
	public Set<Map.Entry<Tag, Object>> entrySet() {
		return this.store.entrySet();
	}
	
	// Constructors
	/**
	 * Simple default cons
	 */
	public Packet() {
		this.store = new HashMap<Tag, Object>();
	}
	
	/**
	 * Copy cons
	 * @param p a Packet that will be copied
	 */
	public Packet(Packet p) {
		this.store = new HashMap<Tag, Object>();
		
		for(Map.Entry<Tag, Object> i : p.entrySet()) {
			this.put(i.getKey(), i.getValue());
		}
	}
	
	/**
	 * Useful constructor for fast declaration of Packets with 1 element
	 */
	public Packet(Tag tag1, Object value1) {
		this.store = new HashMap<Tag, Object>();
		this.put(tag1, value1);
	}
	
	/**
	 * Useful constructor for fast declaration of Packets with 2 elements
	 */
	public Packet(Tag tag1, Object value1, Tag tag2, Object value2) {
		this.store = new HashMap<Tag, Object>();
		this.put(tag1, value1);
		this.put(tag2, value2);
	}
	
	/**
	 * Useful constructor for fast declaration of Packets with 3 elements
	 */
	public Packet(Tag tag1, Object value1, Tag tag2, Object value2, Tag tag3, Object value3) {
		this.store = new HashMap<Tag, Object>();
		this.put(tag1, value1);
		this.put(tag2, value2);
		this.put(tag3, value3);
	}
	
	// Util
	/**
	 *	Just a method to get a visual representation of what's in the Packet
	 *	@return the Packet in String form 
	 */
	@Override
	public String toString() {
		String out = "";
		Iterator<Map.Entry<Tag, Object>> it = this.store.entrySet().iterator();
		
		while(it.hasNext()) {
			out += it.next() + "\n";
		}
		
		return out;
	}
}
