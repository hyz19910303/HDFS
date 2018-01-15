package com.hyz.evil.wdcount;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMap extends Mapper<Text, Text, Text, LongWritable> {
	
	@Override
	protected void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {
		
		long index=1;
		System.out.println(key.toString());
		String[] values = value.toString().split(" ");
		for (String string : values) {
			context.write(new Text(string), new LongWritable(index));
		}
	}
	
}
