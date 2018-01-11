package com.hyz.evil.job;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
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
		String base="/home/evil";
		base=null;
		String inPath=base==null?"/srcdata/":base+"/srcdata/";
		String outPath=base==null?"/out/":base+"/out/";
		
		Configuration conf= new Configuration();
		if(base==null){
			conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		}
		
		//删除输出位置
		Path path = new Path(outPath);
		FileSystem fileSystem = path.getFileSystem(conf);
		if(fileSystem.exists(path)){
			fileSystem.delete(path, true);
		}
		
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
		
		FileInputFormat.setInputPaths(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		
		boolean iscomplete = job.waitForCompletion(true);
		System.exit(iscomplete?0:1);
		
		
	}

}







