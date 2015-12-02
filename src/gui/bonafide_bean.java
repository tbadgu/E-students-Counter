package gui;

import java.sql.Date;

public class bonafide_bean {
	
	String username;
	Date applied_on;
	String reason;
	Date issued_on;
	int rollno;
	public int getRollno() {
		return rollno;
	}
	public void setRollno(int rollno) {
		this.rollno = rollno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getApplied_on() {
		return applied_on;
	}
	public void setApplied_on(Date applied_on) {
		this.applied_on = applied_on;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getIssued_on() {
		return issued_on;
	}
	public void setIssued_on(Date issued_on) {
		this.issued_on = issued_on;
	}
	
	

}
