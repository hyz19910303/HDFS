package com.hyz.evil.log;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LogMap extends Mapper<LongWritable, Text, Text, LongWritable> {
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		long index=1;
		String[] split = value.toString().split(" ");
		String string = split[split.length-2];
		context.write(new Text(string), new LongWritable(index));
	}
	
}
