package data_structures;

public class CacheNode {
	private String key;
	private String value;
	private long expireTime;
	
	private CacheNode prev;
	private CacheNode next;
	
	public CacheNode(String key, String value, long expireTime) {
		this.setKey(key);
		this.setValue(value);
		this.setExpireTime(expireTime);
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

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
}
