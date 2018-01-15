package com.hyz.evil.dbformat.map;

import java.util.Set;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;

public class MyMap extends MapWritable{
	
	@Override
	public String toString() {
		Set<Writable> keySet = this.keySet();
		StringBuilder build = new StringBuilder("{");
		for (Writable key : keySet) {
			Writable value = this.get(key);
			build.append(key.toString()+"="+value.toString()+",");
		}
		build.append("}");
		return build.toString();
	}
}
