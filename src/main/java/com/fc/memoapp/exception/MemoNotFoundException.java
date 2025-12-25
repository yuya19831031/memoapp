package com.fc.memoapp.exception;

public class MemoNotFoundException extends RuntimeException  {
	public MemoNotFoundException(String message) {
		super(message);
	}
}
