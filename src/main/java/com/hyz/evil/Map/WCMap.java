package com.hyz.evil.Map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMap extends Mapper<LongWritable, Text, Text, LongWritable> {
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		long index=1;
		String[] values = value.toString().split(" ");
		for (String string : values) {
			context.write(new Text(string), new LongWritable(index));
		}
	}
	
}
