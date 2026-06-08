package data_structures;

public class Entry {
	private String key;
	private CacheNode cacheNode;
	private Entry next;
	
	public Entry(String key, CacheNode cacheNode) {
		this.setKey(key);
		this.setCacheNode(cacheNode);
	}

	public Entry getNext() {
		return next;
	}

	public void setNext(Entry next) {
		this.next = next;
	}

	public CacheNode getCacheNode() {
		return cacheNode;
	}

	public void setCacheNode(CacheNode cacheNode) {
		this.cacheNode = cacheNode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
