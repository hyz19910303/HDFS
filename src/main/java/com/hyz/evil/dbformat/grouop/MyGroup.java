package com.hyz.evil.dbformat.grouop;

import org.apache.hadoop.io.WritableComparator;

public class MyGroup extends WritableComparator {

	
	@Override
	public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
		String key = new String(b1,s1,l1);
		String key2 = new String(b2,s2,l2);
		byte[] bytes1 = key.toString().split("@")[0].getBytes();
		byte[] bytes2 = key2.toString().split("@")[0].getBytes();
		return WritableComparator.compareBytes(bytes1, 0, bytes1.length, bytes2, 0, bytes2.length);
//		int compareBytes = WritableComparator.compareBytes(b1, s1, l1, b2, s2, l2);
//		return compareBytes;
	}
	
}
