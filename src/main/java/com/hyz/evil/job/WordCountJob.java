package com.hyz.evil.job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.hyz.evil.Map.WCMap;
import com.hyz.evil.Reduce.WCReduce;

public class WordCountJob {
	
	public static void main(String[] args) throws Exception {
		Configuration conf= new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		
		Job job = Job.getInstance(conf);
		
		
		job.setJarByClass(WordCountJob.class);
		
		job.setMapperClass(WCMap.class);
		job.setReducerClass(WCReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("/src/"));
		FileOutputFormat.setOutputPath(job, new Path("/outs/"));
		
		job.waitForCompletion(true);
		
		
	}

}







