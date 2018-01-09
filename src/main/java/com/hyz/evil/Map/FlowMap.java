package com.hyz.evil.Map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.hyz.evil.bean.Flow;

public class FlowMap extends Mapper<LongWritable, Text, Text, Flow> {
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		String[] data = value.toString().split("\t");
		if(data.length>=3){
			Flow flow = new Flow();
			flow.setMobile(data[1]);
			long upflow=Long.parseLong(data[data.length-3]);
			long downflow=Long.parseLong(data[data.length-2]);
			flow.setUpFlow(upflow);
			flow.setDownFlow(downflow);
			flow.setSumFlow(upflow+downflow);
			context.write(new Text(data[1]), flow);
		}else{
			System.out.println(value);
		}
		
	}
	
}
