package data_structures;

public class CacheNode {
	private String key;
	private String value;
	
	private CacheNode prev;
	private CacheNode next;
	
	public CacheNode(String key, String value) {
		this.setKey(key);
		this.setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CacheNode getPrev() {
		return prev;
	}

	public void setPrev(CacheNode prev) {
		this.prev = prev;
	}

	public CacheNode getNext() {
		return next;
	}

	public void setNext(CacheNode next) {
		this.next = next;
	}
}
