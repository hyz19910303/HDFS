package com.hyz.evil.Reduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.hyz.evil.bean.Flow;

public class FlowReduce extends Reducer<Text, Flow, Text, Flow> {
	
	@Override
	protected void reduce(Text text, Iterable<Flow> values, Context context)
			throws IOException, InterruptedException {
		long upflows=0;
		long downflows=0;
		long sumflows=0;
		for (Flow flow : values) {
			upflows+=flow.getUpFlow();
			downflows+=flow.getDownFlow();
			sumflows+=flow.getSumFlow();
		}
		Flow fw= new Flow(text.toString(), upflows, downflows, sumflows);
		context.write(text, fw);
		
	}
	
	
}
