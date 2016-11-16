package com.sloi.templater;

import java.util.List;
import java.util.Map;

public class Template extends Block {

	private List<Block> blocks;
	
	public List<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws ParsingException {
		StringBuffer buffer = new StringBuffer(4096);
		for (Block block : blocks) {
			buffer.append(block.doProcess(scope));
		}
		return buffer;
	}
	
}
