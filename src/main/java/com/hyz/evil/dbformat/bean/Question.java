package com.hyz.evil.dbformat.bean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class Question implements DBWritable,Writable{
	
	private String id;
	private String errorCode;
	private String questionAssortment;
	private String assortmentCode;
	private String QuestionDetail;
	private String questionDescription;
	private String answer;
	
	
	
	public void write(PreparedStatement statement) throws SQLException {
		statement.setString(1,id);
		statement.setString(2,errorCode);
		statement.setString(3,questionAssortment);
		statement.setString(4,assortmentCode);
		statement.setString(5,QuestionDetail);
		statement.setString(6,questionDescription);
		statement.setString(7,answer);
	}

	public void readFields(ResultSet resultSet) throws SQLException {
		this.id=resultSet.getString(1);
		this.errorCode=resultSet.getString(2);
		this.questionAssortment=resultSet.getString(3);
		this.assortmentCode=resultSet.getString(4);
		this.QuestionDetail=resultSet.getString(5);
		this.questionDescription=resultSet.getString(6);
		this.answer=resultSet.getString(7);
	}
	
	
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.id);
		out.writeUTF(this.errorCode);
		out.writeUTF(this.questionAssortment);
		out.writeUTF(this.assortmentCode);
		out.writeUTF(this.QuestionDetail);
		out.writeUTF(this.questionDescription);
		out.writeUTF(this.answer);
	}

	public void readFields(DataInput in) throws IOException {
		this.id=in.readUTF();
		this.errorCode=in.readUTF();
		this.questionAssortment=in.readUTF();
		this.assortmentCode=in.readUTF();
		this.QuestionDetail=in.readUTF();
		this.questionDescription=in.readUTF();
		this.answer=in.readUTF();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getQuestionAssortment() {
		return questionAssortment;
	}

	public void setQuestionAssortment(String questionAssortment) {
		this.questionAssortment = questionAssortment;
	}

	public String getAssortmentCode() {
		return assortmentCode;
	}

	public void setAssortmentCode(String assortmentCode) {
		this.assortmentCode = assortmentCode;
	}

	public String getQuestionDetail() {
		return QuestionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		QuestionDetail = questionDetail;
	}

	public String getQuestionDescription() {
		return questionDescription;
	}

	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", errorCode=" + errorCode + ", questionAssortment=" + questionAssortment
				+ ", assortmentCode=" + assortmentCode + ", QuestionDetail=" + QuestionDetail + ", questionDescription="
				+ questionDescription + ", answer=" + answer + "]";
	}
	
	
	
	

}
