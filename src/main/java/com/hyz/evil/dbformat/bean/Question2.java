package com.hyz.evil.dbformat.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class Question2 implements DBWritable,Writable{
	
	private String USER_ID;
	private String QUESTIONNAIRE_ID;
	private String TEST_QUESTIONS_ID;
	private String OPTIONS;
	
	
	//sql
	public void write(PreparedStatement statement) throws SQLException {
		statement.setString(1,USER_ID);
		statement.setString(2,QUESTIONNAIRE_ID);
		statement.setString(3,TEST_QUESTIONS_ID);
		statement.setString(4,OPTIONS);
	}
	//sql
	public void readFields(ResultSet resultSet) throws SQLException {
		this.USER_ID=resultSet.getString(1);
		this.QUESTIONNAIRE_ID=resultSet.getString(2);
		this.TEST_QUESTIONS_ID=resultSet.getString(3);
		this.OPTIONS=resultSet.getString(4);
	}
	
	//xuliehua
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.USER_ID);
		out.writeUTF(this.QUESTIONNAIRE_ID);
		out.writeUTF(this.TEST_QUESTIONS_ID);
//		if(this.OPTIONS==null){
//				System.out.println("----------"+this);
//		}
		out.writeUTF(this.OPTIONS==null?"NULL":this.OPTIONS);
	}
	//xuliehua
	public void readFields(DataInput in) throws IOException {
		this.USER_ID=in.readUTF();
		this.QUESTIONNAIRE_ID=in.readUTF();
		this.TEST_QUESTIONS_ID=in.readUTF();
		this.OPTIONS=in.readUTF();
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getQUESTIONNAIRE_ID() {
		return QUESTIONNAIRE_ID;
	}

	public void setQUESTIONNAIRE_ID(String qUESTIONNAIRE_ID) {
		QUESTIONNAIRE_ID = qUESTIONNAIRE_ID;
	}

	public String getTEST_QUESTIONS_ID() {
		return TEST_QUESTIONS_ID;
	}

	public void setTEST_QUESTIONS_ID(String tEST_QUESTIONS_ID) {
		TEST_QUESTIONS_ID = tEST_QUESTIONS_ID;
	}

	public String getOPTIONS() {
		return OPTIONS;
	}

	public void setOPTIONS(String oPTIONS) {
		OPTIONS = oPTIONS;
	}

	@Override
	public String toString() {
		return "Question2 [USER_ID=" + USER_ID + ", QUESTIONNAIRE_ID=" + QUESTIONNAIRE_ID + ", TEST_QUESTIONS_ID="
				+ TEST_QUESTIONS_ID + ", OPTIONS=" + OPTIONS + "]";
	}

	
	
	
	
	
	
	

}
