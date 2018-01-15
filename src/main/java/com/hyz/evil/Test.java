package com.hyz.evil;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String  test="ada**Kdksk**dasd**dsm";
//		if(test.contains("**")) {
//			String[] split = test.split("\\*\\*");
//			for (String string : split) {
//				System.out.println(string);
//			}
//		}
//		System.out.println("d7773b71-6986-4cd7-b885-f77e21bd1549".hashCode()%5);
//		System.out.println("a24a4001-6285-467b-a0d5-9be02379a3b0".hashCode()%5);
		byte[] by=new byte[]{51, 54, 100, 48, 54, 54, 55, 53, 45, 100, 48, 54, 49, 45, 52, 97, 49, 99, 45, 57, 51, 100, 102, 45, 49, 55, 49, 56, 102, 100, 50, 54, 55, 102, 102, 102, 64, 49, 52, 48, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		System.out.println(new String(by,"UTF-8"));
		
		
	}
	

}
