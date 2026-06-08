package main;

import data_structures.CacheNode;
import data_structures.SecureHashMap;

public class CacheEngine {
	
	public static void main(String[] args) {
		System.out.println("Secure Cache Initialization");
		
		SecureHashMap map = new SecureHashMap(10);
	
		map.put("user_101", new CacheNode("user_101", "{\"name\": \"Alice\", \"tier\": \"premium\"}"));
        map.put("user_102", new CacheNode("user_102", "{\"name\": \"Bob\", \"tier\": \"free\"}"));
        
        CacheNode retrieved = map.get("user_101");
        if (retrieved != null) {
            System.out.println("Securely retrieved payload for user_101!");
            System.out.println("Data: " + retrieved.getValue());
        } else {
            System.out.println("Cache mapping error.");
        }
        
        CacheNode missing = map.get("user_999");
        System.out.println("Querying nonexistent data returned: " + (missing == null ? "null (Correct Cache Miss)" : "Object"));
	}
}
