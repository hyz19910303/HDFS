package com.hyz.evil.log;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class LogJob {
	
	public static void main(String[] args) throws Exception {
		Configuration conf= new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
//		conf.set("fs.defaultFS", "hdfs://192.168.1.102:9000");
		//org.apache.hadoop.mapreduce.Job
		Job job = Job.getInstance(conf);
		
		
		job.setJarByClass(LogJob.class);
		
		job.setMapperClass(LogMap.class);
		job.setReducerClass(LogReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("/srcdata/"));
		FileOutputFormat.setOutputPath(job, new Path("/out2/"));
		
		job.waitForCompletion(true);
		
		
	}

}







