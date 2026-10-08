# LRU-Cache

A least-recently-used cache with per-item expiry, where the hash map and the heap are written by hand instead of borrowed from `java.util`.

I wanted to see what a cache actually has to do, so this one is a few questions and their answers.

**How does it find an entry fast?**
Through `SecureHashMap`, my own chained hash table. The table is sized at twice the cache capacity and never resizes. The hash gets a random seed when the cache is created, which is meant to make it harder for someone to craft keys that all land in one bucket.

**How does it know what was used least recently?**
Entries also sit in a doubly linked list with dummy nodes at each end. Every `get` or `put` moves the entry to the front, so the back of the list is always the eviction candidate. When the cache goes over capacity, the node at the back is dropped.

**How does expiry work?**
`put(key, value, ttlMs)` stores an absolute expiry time, and the key goes into a min-heap ordered by that time (`ExpirationHeap`). Before every `get` and `put`, the cache looks at the top of the heap and evicts anything whose time has passed. Nothing runs in the background; expired entries disappear the next time anyone touches the cache.

**What happens when a key is stored twice?**
The old heap entry isn't removed, since that would be expensive. A second one is pushed instead, and when a stale one surfaces the cache compares it against the node's current expiry and ignores it if they don't match.

## Try it

```
cd LRUCache
javac -d bin src/main/*.java src/data_structures/*.java src/logic/*.java
java -cp bin main.CacheEngine
```

The demo stores two tokens, one with a five-minute TTL and one with 500 ms, sleeps for a second and looks them up again:

```
Item [token_B] has exceeded its TTL limit. Evicting...
token_A Lookup: alive
token_B Lookup: expired
```

## Limits

Not thread-safe, keys and values are both `String`, and the table size is fixed at construction. It's a study project, not something to put in front of real traffic.
