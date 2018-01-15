package com.hyz.evil.dbformat.partitioner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;



// 分区  根据试卷ID
public class DBPartitioner extends Partitioner<Text, Text> {

	private Map<String,Integer> unionQuestionID=new ConcurrentHashMap<>();
	
	private volatile AtomicInteger partition=new AtomicInteger(0);
	
	@Override
	public int getPartition(Text key, Text value, int numPartitions) {
		
		String[] split = key.toString().split("@");
		String questiondid=split[0];
		//分区  根据试卷ID
		int paratiorn=0;
		if(unionQuestionID.containsKey(questiondid)) {
			paratiorn = unionQuestionID.get(questiondid);
		}else {
			unionQuestionID.put(questiondid, partition.getAndIncrement());
		}
		return paratiorn;
		
	}
	
	
}
