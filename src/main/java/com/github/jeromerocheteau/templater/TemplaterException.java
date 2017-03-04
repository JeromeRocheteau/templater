package com.github.jeromerocheteau.templater;

import com.github.jeromerocheteau.templates.Block;

public class TemplaterException extends Exception {

	private static final long serialVersionUID = 20150522001L;

	private String message;
	
	private Block block;
	
	public TemplaterException(Block block, String message) {
		this.block = block;
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "[" + block.getStart() + "-" + block.getStop() + "] " + message;
	}
	
}
