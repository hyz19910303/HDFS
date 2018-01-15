package com.hyz.evil.dbformat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import com.hyz.evil.dbformat.bean.Question2;
import com.hyz.evil.dbformat.bean.Question2.QuestionComparator;
import com.hyz.evil.dbformat.grouop.MyGroup;
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
		Path path2 = new Path(outPath2);
		FileSystem fileSystem2 = path2.getFileSystem(conf);
		if(fileSystem2.exists(path2)){
			fileSystem2.delete(path2, true);
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
		
		String sql="select * from TB_QUESTIONNAIRES_RECORD ";
//				+ " where questionnaire_id='36d06675-d061-4a1c-93df-1718fd267fff'";
		DBInputFormat.setInput(job, Question2.class, 
				sql,
				"select count(1) from TB_QUESTIONNAIRES_RECORD");
		
		job.setMapperClass(DBMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		//分区 在map的最后阶段 在shuffle过程 进行
		job.setPartitionerClass(DBPartitioner.class);
		job.setNumReduceTasks(1);
		// 设置排序规则 在map到reduce 过程会调用 以及reduce 也会调用
		job.setSortComparatorClass(QuestionComparator.class);
		//分组 在reduce 中调用
		job.setGroupingComparatorClass(MyGroup.class);
	
		job.setReducerClass(DBReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		
		
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		job.setOutputFormatClass(TextOutputFormat.class);
//		job.setOutputFormatClass(FileOutputFormat.class);
//		if(job.waitForCompletion(true)) {
//			
//			Job instance = Job.getInstance();
//			//instance.setJarByClass(MyDBFormat.class);
//			instance.setOutputKeyClass(Text.class);
//			instance.setOutputValueClass(LongWritable.class);
//			instance.setMapperClass(OptionMapper.class);
//			instance.setReducerClass(OptionReduce.class);
//			FileInputFormat.setInputPaths(instance, new Path(outPath));
//			FileOutputFormat.setOutputPath(instance, new Path(outPath2));
//			instance.setOutputFormatClass(TextOutputFormat.class);
//			System.exit(instance.waitForCompletion(true)?0:1);
//		}
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
			String test_QUESTIONS_ID = value.getTEST_QUESTIONS_ID();
			String questionnaire_ID = value.getQUESTIONNAIRE_ID();
			//选择
			String options = value.getOPTIONS();
			//填空
			if(options!=null && options.contains("**")) {
				String[] tiankong = options.split("\\*\\*");
				for (String option : tiankong) {
					context.write(new Text(questionnaire_ID+"@"+test_QUESTIONS_ID), new Text(option));
				}
			}else {
				context.write(new Text(questionnaire_ID+"@"+test_QUESTIONS_ID), new Text(options==null?"NULL":options));
			}
		}
		
	}
	
	/**
	 * reduce
	 * @author evil
	 *
	 */
	public static class DBReduce extends Reducer<Text, Text, Text, Text>{
		
		
		//private MapWritable mapw= new MapWritable();
		// 1  list<option>
		@Override
		protected void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			Map<String,Integer> map = new HashMap<>();
			
			for (Text option : values) {
				String op_str = option.toString();
				if(map.containsKey(op_str)) {
					Integer integer = map.get(op_str);
					Integer newValue = integer+1;
					map.put(op_str, newValue);
				}else {
					map.put(op_str, new Integer(1));
				}
			}
			context.write(new Text(key.toString().split("@")[1]), new Text(map.toString()));
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
