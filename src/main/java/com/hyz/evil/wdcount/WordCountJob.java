package com.hyz.evil.wdcount;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountJob {
	
	public static void main(String[] args) throws Exception {
		Configuration conf= new Configuration();
		//conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		
		Job job = Job.getInstance(conf);
		
		
		job.setJarByClass(WordCountJob.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setMapperClass(WCMap.class);
		job.setReducerClass(WCReduce.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("/home/evil/data/"));
		FileOutputFormat.setOutputPath(job, new Path("/home/evil/outs/"));
		
		job.waitForCompletion(true);
		
		
	}

}







