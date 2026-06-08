package logic;

import data_structures.CacheNode;
import data_structures.ClockNode;
import data_structures.ExpirationHeap;
import data_structures.SecureHashMap;

public class LruCache {
	private final int capacity;
	private int currentSize;
	private final SecureHashMap map;
	private final ExpirationHeap clockHeap;

	private final CacheNode head;
	private final CacheNode tail;

	public LruCache(int capacity) {
		this.capacity = capacity;
		this.currentSize = 0;
		this.map = new SecureHashMap(capacity * 2);
		this.clockHeap = new ExpirationHeap();

		this.head = new CacheNode("HEAD_DUMMY", "", 0);
		this.tail = new CacheNode("TAIL_DUMMY", "", 0);

		head.setNext(tail);
		tail.setPrev(head);
	}

	public String get(String key) {
		evictExpiredItems();

		CacheNode node = map.get(key);
		if (node == null) {
			return null;
		}

		moveToHead(node);
		return node.getValue();
	}

	public void put(String key, String value, long ttlMs) {
		evictExpiredItems();

		long absoluteExpiry = System.currentTimeMillis() + ttlMs;
		CacheNode existingNode = map.get(key);

		if (existingNode != null) {
			existingNode.setValue(value);
			existingNode.setExpireTime(absoluteExpiry);
			moveToHead(existingNode);
			clockHeap.insert(key, absoluteExpiry);
		} else {
			CacheNode newNode = new CacheNode(key, value, absoluteExpiry);
			map.put(key, newNode);
			addNodeToHead(newNode);
			clockHeap.insert(key, absoluteExpiry);
			currentSize++;

			if (currentSize > capacity) {
				evictLeastRecentlyUsed();
			}
		}
	}

	private void evictExpiredItems() {
		long now = System.currentTimeMillis();

		while (!clockHeap.isEmpty() && clockHeap.peekMin().getExpireTime() <= now) {
			ClockNode expiredToken = clockHeap.extractMin();
			CacheNode targetNode = map.get(expiredToken.getKey());

			if (targetNode != null && targetNode.getExpireTime() <= now) {
				System.out.println(
						"Item [" + expiredToken.getKey() + "] has exceeded its TTL limit. Evicting...");
				removeNode(targetNode);
				map.remove(expiredToken.getKey());
				currentSize--;
			}
		}
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

	private void moveToHead(CacheNode node) {
		removeNode(node);
		addNodeToHead(node);
	}

	private void evictLeastRecentlyUsed() {
		CacheNode lruNode = tail.getPrev();
		System.out.println("Cache is full. Removing LRU item: " + lruNode.getKey());
		removeNode(lruNode);
		map.remove(lruNode.getKey());
		currentSize--;
	}
}
