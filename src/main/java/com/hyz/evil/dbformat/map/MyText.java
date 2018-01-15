package com.hyz.evil.dbformat.map;

import org.apache.hadoop.io.Text;

public class MyText extends Text{
	
	@Override
	public boolean equals(Object o) {
		if(o  instanceof MyText) {
			MyText mytext=(MyText)o;
			return this.equals(mytext);
		}
		return false;
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	public MyText() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyText(byte[] utf8) {
		super(utf8);
		// TODO Auto-generated constructor stub
	}
	public MyText(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}
	public MyText(Text utf8) {
		super(utf8);
		// TODO Auto-generated constructor stub
	}
	
	
	
}
