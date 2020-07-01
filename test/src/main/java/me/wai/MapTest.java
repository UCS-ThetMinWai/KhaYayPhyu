package me.wai;

import java.util.HashMap;

public class MapTest {

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		map.put("wai", "Thet min wai");
		System.out.print(map.get("wai"));
		System.out.println(map.get("thura"));
	}
}
