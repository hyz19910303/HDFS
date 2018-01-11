package com.hyz.evil.dbformat.partitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class DBPartitioner extends Partitioner<Text, Text> {

	
	@Override
	public int getPartition(Text key, Text value, int numPartitions) {
		return key.hashCode();
	}

	
	
}
