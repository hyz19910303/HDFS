package com.hyz.evil.flow;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class Flow implements Writable{
	
	private String mobile;
	
	private long upFlow;
	
	private long downFlow;
	
	private long sumFlow;

	
	

	public void write(DataOutput out) throws IOException {
//		this.mobile.toCharArray();
		out.writeUTF(this.mobile);
		out.writeLong(this.upFlow);
		out.writeLong(this.downFlow);
		out.writeLong(this.sumFlow);
		
	}

	public void readFields(DataInput in) throws IOException {
		this.mobile=in.readUTF();
		this.upFlow=in.readLong();
		this.downFlow=in.readLong();
		this.sumFlow=in.readLong();
	}

	@Override
	public String toString() {
		return "上传的：" + upFlow + ", 下载的" + downFlow + ", 总的：" + sumFlow;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public long getUpFlow() {
		return upFlow;
	}

	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}

	public long getDownFlow() {
		return downFlow;
	}

	public void setDownFlow(long downFlow) {
		this.downFlow = downFlow;
	}

	public long getSumFlow() {
		return sumFlow;
	}

	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}

	public Flow() {
		super();
	}

	public Flow(String mobile, long upFlow, long downFlow, long sumFlow) {
		super();
		this.mobile = mobile;
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = sumFlow;
	}
	
	
	
}
