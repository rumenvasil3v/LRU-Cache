package logic;

import data_structures.CacheNode;
import data_structures.SecureHashMap;

public class LruCache {
	private final int capacity;
	private int currentSize;
	private final SecureHashMap map;
	
	private final CacheNode head;
	private final CacheNode tail;
	
	public LruCache(int capacity) {
		this.capacity = capacity;
		this.currentSize = 0;
		this.map = new SecureHashMap(capacity * 2);
		
		this.head = new CacheNode("head_dummy", "");
		this.tail = new CacheNode("tail_dummy", "");
		
		head.setNext(tail);
		tail.setPrev(head);
	}
	
	public String get(String key) {
		CacheNode node = map.get(key);
		if (node == null) {
			return null;
		}
		
		moveToHead(node);
		return node.getValue();
	}
	
	public void put(String key, String value) {
		CacheNode existingNode = map.get(key);
		
		if (existingNode != null) {
			existingNode.setValue(value);
			moveToHead(existingNode);
		} else {
			CacheNode newNode = new CacheNode(key, value);
            map.put(key, newNode);
            addNodeToHead(newNode);
            currentSize++;
            
            if (currentSize > capacity) {
                evictLeastRecentlyUsed();
            }
		}
	}
	
	private void moveToHead(CacheNode node) {
		removeNode(node);
		addNodeToHead(node);
	}
	
	private void addNodeToHead(CacheNode node) {
        node.setPrev(head);
        node.setNext(head.getNext());

        head.getNext().setPrev(node);
        head.setNext(node);
    }
	
	private void removeNode(CacheNode node) {
		CacheNode prevNode = node.getPrev();
		CacheNode nextNode = node.getNext();
		
		prevNode.setNext(nextNode);
		nextNode.setPrev(prevNode);
	}
	
	private void evictLeastRecentlyUsed() {
		CacheNode lruNode = tail.getPrev();
		
		removeNode(lruNode);
		map.remove(lruNode.getKey());
		currentSize--;
	}
	
	public int getCurrentSize() {
		return this.currentSize;
	}
}
