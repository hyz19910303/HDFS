package com.hyz.evil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Before;
import org.junit.Test;

public class HDFSUtil {
	
	private FileSystem fileSystem;
	
	@Before
	public void init() throws IOException, InterruptedException, URISyntaxException{
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoopMaster10:9000");
		fileSystem = FileSystem.get(new URI("hdfs://hadoopMaster10:9000"), conf, "evil");
	}
	
	@Test
	public void upload(){
		try {
			fileSystem.copyFromLocalFile(new Path("/home/lsp/protobuf-2.5.0.tar.gz"), new Path("/"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void download(){
		try {
			fileSystem.copyToLocalFile( new Path("/protobuf-2.5.0.tar.gz"), new Path("/home/lsp/protobuf.tag.gz"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void mkdirs(){
		try {
			fileSystem.mkdirs(new Path("/aa/bb/cc"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void getFileList(){
		try {
			//显示
			RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(new Path("/"), true);
			while (listFiles.hasNext()) {
				 LocatedFileStatus file = listFiles.next();
				 String filename = file.getPath().getName();
				 System.out.println(filename);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("###########################################################################");
		
		try {
			FileStatus[] listStatus = fileSystem.listStatus(new Path("/"));
			for (FileStatus fileStatus : listStatus) {
				String filename = fileStatus.getPath().getName();
				System.out.println(filename);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}








