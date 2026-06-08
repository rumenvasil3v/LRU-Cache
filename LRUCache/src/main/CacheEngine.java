package main;

import data_structures.CacheNode;
import data_structures.SecureHashMap;
import logic.LruCache;

public class CacheEngine {
	
	public static void main(String[] args) {
		System.out.println("Secure Cache Initialization");
		
		LruCache cache = new LruCache(3);

        System.out.println("\nLoading first 3 videos into user session memory cache...");
        cache.put("video_10", "Matrix_Trailer_1080p.mp4");
        cache.put("video_20", "Inception_Clip_4k.mp4");
        cache.put("video_30", "Interstellar_Teaser.mp4");
        
        System.out.println("\nUser watches 'video_10' again...");
        cache.get("video_10");
        
        System.out.println("\nUser clicks on a 4th item 'video_40'...");
        cache.put("video_40", "Avatar_Deleted_Scene.mp4");
        
        System.out.println("\n--- Cache Verification Report ---");
        System.out.println("video_40: " + (cache.get("video_40") != null ? "found" : "miss"));
        System.out.println("video_10: " + (cache.get("video_10") != null ? "found" : "miss"));
        System.out.println("video_30: " + (cache.get("video_30") != null ? "found" : "miss"));
        
        System.out.println("video_20: " + (cache.get("video_20") != null ? "found" : "evicted video"));
	}
}
