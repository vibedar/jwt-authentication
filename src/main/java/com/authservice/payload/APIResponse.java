package com.authservice.payload;

public class APIResponse<T> {
	private String message;
	private int status;
	private T date;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getDate() {
		return date;
	}
	public void setDate(T date) {
		this.date = date;
	}
	
}
