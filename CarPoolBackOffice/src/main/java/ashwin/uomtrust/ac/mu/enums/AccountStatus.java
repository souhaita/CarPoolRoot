package ashwin.uomtrust.ac.mu.enums;

import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {
	ACTIVE(0),
	DESACTIVE(1);
	
	private int value ;
	private static final Map<Integer, AccountStatus> map = new HashMap<>();

	AccountStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	static {
		for (AccountStatus item : values()) {
			map.put(item.getValue(), item);
		}
	}

	public static AccountStatus valueFor(int ref) {
		return map.get(ref);
	}
}