package com.sloi.templater;

import java.util.Map;

public abstract class Block implements Comparable<Block> {

	private int start;
	
	private int stop;
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

	// @Override
	public int compareTo(Block block) {
		int diff = this.start - block.start;
		if (diff == 0) {
			return block.stop - this.stop;
		} else {
			return diff;
		}
	}

	public abstract StringBuffer doProcess(Map<String, Object> scope) throws ParsingException;
	
}
