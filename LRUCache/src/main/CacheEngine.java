package main;

import data_structures.CacheNode;
import data_structures.SecureHashMap;
import logic.LruCache;

public class CacheEngine {
    public static void main(String[] args) {
        System.out.println("--- TTL Cache Simulation ---");
        
        LruCache sessionCache = new LruCache(5);

        System.out.println("\nStoring API authorization tokens with specific TTLs...");
        sessionCache.put("token_A", "user_alice_session_granted", 300000);
        
        sessionCache.put("token_B", "one_time_password_used", 500);

        System.out.println("\n--- Instant Query Check ---");
        System.out.println("token_A Lookup: " + sessionCache.get("token_A"));
        System.out.println("token_B Lookup: " + sessionCache.get("token_B"));

        System.out.println("\nSimulating a 1-second background thread delay...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }

        System.out.println("\n--- Stale Query Verification Check ---");
        System.out.println("token_A Lookup: " + (sessionCache.get("token_A") != null ? "alive" : "expired"));
        
        // token_B should register as an automatic cache miss because our heap clock dropped it!
        System.out.println("token_B Lookup: " + (sessionCache.get("token_B") != null ? "alive" : "expired"));
    }
}
