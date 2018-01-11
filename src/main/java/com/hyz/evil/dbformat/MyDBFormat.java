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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.StringUtils;

import com.hyz.evil.dbformat.bean.Question2;
import com.hyz.evil.dbformat.partitioner.DBPartitioner;

public class MyDBFormat {
	//com.hyz.evil.dbformat.MyDBFormat
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		String base="/home/evil";
		String out="/out1/";
		String out2="/out2/";
//		base=null;
		String outPath=base==null?out:base+out;
		String outPath2=base==null?out2:base+out2;
		Configuration conf = new Configuration();
		if(base==null){
			conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		}
		Path path = new Path(outPath);
		FileSystem fileSystem = path.getFileSystem(conf);
		if(fileSystem.exists(path)){
			fileSystem.delete(path, true);
		}
		
		//
		conf.set(DBConfiguration.DRIVER_CLASS_PROPERTY, "oracle.jdbc.driver.OracleDriver");
		conf.set(DBConfiguration.URL_PROPERTY, "jdbc:oracle:thin:@192.168.159.133:1521/orcl");
		conf.set(DBConfiguration.USERNAME_PROPERTY, "CXDC");
		conf.set(DBConfiguration.PASSWORD_PROPERTY, "neusoft");
		//DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.159.133:3306/test","root","YHtz19910303");
		//DBConfiguration.configureDB(conf, "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.159.133:1521/orcl","CXDC","neusoft");
		// 设置一个job名称
		Job job =Job.getInstance(conf,MyDBFormat.class.getSimpleName());
		
		///添加所需的jar包
		//job.addFileToClassPath(new Path(""));
		
		
		job.setJarByClass(MyDBFormat.class);
		job.setInputFormatClass(DBInputFormat.class);
		
		//DBInputFormat.setInput(job, Question.class, "select * from tb_question", "select count(1) from tb_question");
		DBInputFormat.setInput(job, Question2.class, "select * from TB_QUESTIONNAIRES_RECORD", "select count(1) from TB_QUESTIONNAIRES_RECORD");
		
		job.setMapperClass(DBMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		
		job.setPartitionerClass(DBPartitioner.class);
		job.setNumReduceTasks(1);
		
		job.setReducerClass(DBReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		job.setOutputFormatClass(TextOutputFormat.class);
//		job.setOutputFormatClass(FileOutputFormat.class);
		if(job.waitForCompletion(true)) {
			
			Job instance = Job.getInstance();
			//instance.setJarByClass(MyDBFormat.class);
			instance.setOutputKeyClass(Text.class);
			instance.setOutputValueClass(LongWritable.class);
			instance.setMapperClass(OptionMapper.class);
			instance.setReducerClass(OptionReduce.class);
			FileInputFormat.setInputPaths(instance, new Path(outPath));
			FileOutputFormat.setOutputPath(instance, new Path(outPath2));
			instance.setOutputFormatClass(TextOutputFormat.class);
			System.exit(instance.waitForCompletion(true)?0:1);
		}
		System.exit(job.waitForCompletion(true)?0:1);
	}
	
	/**
	 * map
	 * @author evil
	 *
	 */
	public static class DBMapper extends Mapper<LongWritable, Question2, Text, Text>{
		
		@Override
		protected void map(LongWritable key, Question2 value,Context context)
				throws IOException, InterruptedException {
			context.write(new Text(value.getUSER_ID()), new Text(value.getOPTIONS()==null?"NULL":value.getOPTIONS()));
		}
		
	}
	
	/**
	 * reduce
	 * @author evil
	 *
	 */
	public static class DBReduce extends Reducer<Text, Text, Text, Text>{
		
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			StringBuilder build = new StringBuilder();
			String join = StringUtils.join(",",values);
			build.append(join);
			context.write(key, new Text(build.toString()));
		}
	}
	
	public static class OptionMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] split = value.toString().split("\t");
//			String key1=split[0];
			String[] options=split[1].split(",");
			for (String string : options) {
				context.write(new Text(string), new LongWritable(1));
			}
		}
	}
	
	public static class OptionReduce extends Reducer<Text, LongWritable, Text, LongWritable>{
		
		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Context context) throws IOException, InterruptedException {
			long count=0;
			for (LongWritable value : values) {
				count+=value.get();
			}
			context.write(key, new LongWritable(count));
		}
	}
}
