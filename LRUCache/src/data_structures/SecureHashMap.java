package data_structures;

import java.util.Random;

public class SecureHashMap {
	private Entry[] buckets;
	private int capacity;
	private final long hashSeed;
	
	public SecureHashMap(int capacity) {
		this.capacity = capacity;
		this.buckets = new Entry[capacity];
		
		// generating random seed at runtime
		// preventing from Hash Flooding DoS attacks
		Random random = new Random();
		this.hashSeed = random.nextLong();
	}
	
	private int getBucketIndex(String key) {
		if (key == null) {
			return 0;
		}
		
		long hash = hashSeed;
		
		for (int i = 0; i < key.length(); i++) {
			hash = (hash * 31) + key.charAt(i);
		}
		
		return Math.abs((int) hash) % capacity;
	}
	
	public void put(String key, CacheNode node) {
		int index = getBucketIndex(key);
		Entry head = buckets[index];
		
		Entry current = head;
		while (current != null) {
			if (current.getKey().equals(key)) {
				current.setCacheNode(node);
				return;
			}
			current = current.getNext();
		}
		
		Entry newEntry = new Entry(key, node);
		newEntry.setNext(head);
		buckets[index] = newEntry;
	}
	
	public CacheNode get(String key) {
		int index = getBucketIndex(key);
        Entry current = buckets[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getCacheNode();
            }
            current = current.getNext();
        }
        return null;
	}
	
	public void remove(String key) {
        int index = getBucketIndex(key);
        Entry current = buckets[index];
        Entry prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev != null) {
                    prev.setNext(current.getNext());
                } else {
                    buckets[index] = current.getNext();
                }
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }
}
