package com.example.elasticsearch.common;


import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 동기화 데이터. Map<String, Object>
 */
public class StringKeyMap extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = -6360367764912729772L;

	public StringKeyMap() {
		super();
	}

	public StringKeyMap(Map<? extends String, ? extends Object> m) {
		super(m);
	}

	public String getString(String key) {
		if (key == null) {
			return null;
		} else if (super.get(key) == null) {
			return null;
		} else {
			Object obj = super.get(key);
			if (obj instanceof Double) {
				return String.valueOf(new BigDecimal((Double) obj).longValue());
			}
			return String.valueOf(super.get(key));
		}
	}
	
}
