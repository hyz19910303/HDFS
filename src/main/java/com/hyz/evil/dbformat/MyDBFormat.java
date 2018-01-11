package com.hyz.evil.dbformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

import com.hyz.evil.dbformat.bean.Question2;

public class MyDBFormat {
	//com.hyz.evil.dbformat.MyDBFormat
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		String base="/home/evil";
		base=null;
		String outPath=base==null?"/out/":base+"/out/";
		
		Configuration conf = new Configuration();
		if(base==null){
			conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		}
		
		Path path = new Path(outPath);
		FileSystem fileSystem = path.getFileSystem(conf);
		if(fileSystem.exists(path)){
			fileSystem.delete(path, true);
		}
		
		
		//DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.159.133:3306/test","root","YHtz19910303");
		DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.159.133:1521/orcl","CXDC","neusoft");
		// 设置一个job名称
		Job job =Job.getInstance(conf,MyDBFormat.class.getSimpleName());
		
		job.setJarByClass(MyDBFormat.class);
		job.setInputFormatClass(DBInputFormat.class);
		
		//DBInputFormat.setInput(job, Question.class, "select * from tb_question", "select count(1) from tb_question");
		DBInputFormat.setInput(job, Question2.class, "select * from TB_QUESTIONNAIRES_RECORD", "select count(1) from TB_QUESTIONNAIRES_RECORD");
		
		job.setMapperClass(DBMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Question2.class);
		
		job.setPartitionerClass(HashPartitioner.class);
		job.setNumReduceTasks(1);
		
		job.setReducerClass(DBReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Question2.class);

		
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		job.setOutputFormatClass(TextOutputFormat.class);
//		job.setOutputFormatClass(FileOutputFormat.class);
		
		
		job.waitForCompletion(true);
	}
	
	/**
	 * map
	 * @author evil
	 *
	 */
	public static class DBMapper extends Mapper<LongWritable, Question2, Text, Question2>{
		
		@Override
		protected void map(LongWritable key, Question2 value,Context context)
				throws IOException, InterruptedException {
			
			context.write(new Text(value.getUSER_ID()), value);
		}
		
	}
	
	/**
	 * reduce
	 * @author evil
	 *
	 */
	public static class DBReduce extends Reducer<Text, Question2, Text, Question2>{
		
		@Override
		protected void reduce(Text key, Iterable<Question2> values, Context context)
				throws IOException, InterruptedException {
			for (Question2 question : values) {
				context.write(key, question);
			}
		}
		
		
	}
}
