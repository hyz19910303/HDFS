package com.hyz.evil.Reduce;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class LogReduce extends Reducer<Text, LongWritable, Text, LongWritable> {
	
	@Override
	protected void reduce(Text text, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
		Iterator<LongWritable> iterator = values.iterator();
		long value=0;
		while(iterator.hasNext()){
			value+=iterator.next().get();
		}
		context.write(text, new LongWritable(value));
	}
	
}
