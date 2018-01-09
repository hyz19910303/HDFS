package com.hyz.evil.partitioner;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import com.hyz.evil.bean.Flow;

/**
 * 分类
 * @author evil
 *
 */
public class FlowPartitioner extends Partitioner<Text, Flow> {

	@Override
	public int getPartition(Text key, Flow value, int numPartitions) {
		int paration=Integer.parseInt(key.toString().substring(0, 3));
		if(paration<=133){
			return 0;
		}else if(paration<=137){
			return 1;
		}else if(paration<=150){
			return 2;
		}else {
			return 3;
		}
	}

}
