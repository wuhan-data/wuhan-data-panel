package com.wuhan_data.tools;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapValueComparator implements Comparator<Map.Entry<String, Float>>{

	@Override
	public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
		// TODO Auto-generated method stub
		return o2.getValue().compareTo(o1.getValue());
	}

}
