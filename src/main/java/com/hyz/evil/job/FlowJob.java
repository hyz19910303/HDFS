package com.hyz.evil.job;
import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hyz.evil.Map.FlowMap;
import com.hyz.evil.Reduce.FlowReduce;
import com.hyz.evil.bean.Flow;
import com.hyz.evil.partitioner.FlowPartitioner;

public class FlowJob {
	
	public static void main(String[] args) throws Exception {
		//com.hyz.evil.job.FlowJob
		File file = new File("/home/evil/out/");
		if(file.exists()){
			
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				file2.delete();
			}
			file.delete();
		}
		Configuration conf= new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		//org.apache.hadoop.mapreduce.Job
		Job job = Job.getInstance(conf);
		
		
		
		job.setJarByClass(FlowJob.class);
		
		job.setMapperClass(FlowMap.class);
		job.setReducerClass(FlowReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Flow.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Flow.class);
		
		
		job.setPartitionerClass(FlowPartitioner.class);
		job.setNumReduceTasks(4);
		
		FileInputFormat.setInputPaths(job, new Path("/srcdata/"));
		FileOutputFormat.setOutputPath(job, new Path("/out/"));
		
		boolean iscomplete = job.waitForCompletion(true);
		System.exit(iscomplete?0:1);
		
		
	}

}







