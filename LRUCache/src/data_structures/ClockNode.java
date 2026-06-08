package data_structures;

public class ClockNode {
	private String key;
    private long expireTime;

    public ClockNode(String key, long expireTime) {
        this.setKey(key);
        this.setExpireTime(expireTime);
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
}
