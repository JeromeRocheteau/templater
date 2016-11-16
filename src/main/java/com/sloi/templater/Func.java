package com.sloi.templater;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Func extends Block {

	private String name;
	
	private Expr expr;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws ParsingException {
		StringBuffer buffer = expr.doProcess(scope);
		if (name.equals("cap")) {
			buffer.replace(0, 1, buffer.substring(0, 1).toUpperCase());
		} else if (name.equals("lc")) {
			buffer = new StringBuffer(buffer.toString().toLowerCase()); 
		} else if (name.equals("uc")) {
			buffer = new StringBuffer(buffer.toString().toUpperCase()); 
		} else if (name.equals("name")) {
			buffer = this.getName(buffer.toString()); 
		} else if (name.equals("id")) {
			buffer = this.getId(buffer.toString()); 
		} else if (name.equals("url")) {
			buffer = this.getPath(buffer.toString()); 
		} else if (name.equals("path")) {
			buffer = this.getPath(buffer.toString().replaceAll("\\.", File.separator)); 
		}
		return buffer;
	}

	private StringBuffer getName(String name) {
		StringBuffer buffer = new StringBuffer(64);
		boolean flag = true;
		for (char c : name.toCharArray()) {
			if (flag) {
				flag = false;
				buffer.append(Character.toUpperCase(c));				
			} else if (c == '-' || c == '_' || c == ' ') {
				flag = true;
			} else {
				buffer.append(c);
			}
		}
		return buffer;
	}
	
	private StringBuffer getId(String name) {
		StringBuffer buffer = new StringBuffer(64);
		for (char c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				if (buffer.length() > 0) {
					buffer.append('-');		
				}
			} 
			buffer.append(Character.toLowerCase(c));
		}
		return buffer;
	}

	private StringBuffer getPath(String name) {
		StringBuffer buffer = new StringBuffer(64);
		List<StringBuffer> buffers = new LinkedList<StringBuffer>();
		StringBuffer current = new StringBuffer(16);
		for (char c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				buffers.add(current);
				current = new StringBuffer(16);
			} 
			current.append(Character.toLowerCase(c));
		}
		buffers.add(current);
		for (int i = buffers.size() - 1; i >= 0; i--) {
			buffer.append('/');
			buffer.append(buffers.get(i));
		}
		return buffer;
	}

}
