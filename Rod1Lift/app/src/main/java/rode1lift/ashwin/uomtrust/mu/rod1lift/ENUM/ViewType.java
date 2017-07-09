package rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM;

import java.util.HashMap;
import java.util.Map;

public enum ViewType {
	PROFILE_PICTURE(1),
	CARS_PICTURES(2),
	DATA(3);

	private int value ;
	private static final Map<Integer, ViewType> map = new HashMap<>();


	ViewType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	static {
		for (ViewType item : values()) {
			map.put(item.getValue(), item);
		}
	}

	public static ViewType valueFor(int ref) {
		return map.get(ref);
	}
}