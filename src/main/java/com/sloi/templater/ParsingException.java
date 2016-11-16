package com.sloi.templater;

public class ParsingException extends Exception {

	private static final long serialVersionUID = 20150522001L;

	private String message;
	
	private Block block;
	
	public ParsingException(Block block, String message) {
		this.block = block;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "[" + block.getStart() + "-" + block.getStop() + "] " + message;
	}
	
}
