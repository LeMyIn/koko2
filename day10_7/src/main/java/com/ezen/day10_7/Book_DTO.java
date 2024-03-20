package com.ezen.day10_7;

public class Book_DTO {
String bname,writer,pday,company,image,summary;
public Book_DTO() {
	// TODO Auto-generated constructor stub
}
public Book_DTO(String bname, String writer, String pday, String company, String image, String summary) {
	super();
	this.bname = bname;
	this.writer = writer;
	this.pday = pday;
	this.company = company;
	this.image = image;
	this.summary = summary;
}

public String getBname() {
	return bname;
}

public void setBname(String bname) {
	this.bname = bname;
}

public String getWriter() {
	return writer;
}

public void setWriter(String writer) {
	this.writer = writer;
}

public String getPday() {
	return pday;
}

public void setPday(String pday) {
	this.pday = pday;
}

public String getCompany() {
	return company;
}

public void setCompany(String company) {
	this.company = company;
}

public String getImage() {
	return image;
}

public void setImage(String image) {
	this.image = image;
}

public String getSummary() {
	return summary;
}

public void setSummary(String summary) {
	this.summary = summary;
}


}
