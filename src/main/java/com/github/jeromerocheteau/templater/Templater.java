package com.github.jeromerocheteau.templater;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;

import com.github.jeromerocheteau.formatter.Formatter;
import com.github.jeromerocheteau.templates.Block;
import com.github.jeromerocheteau.templates.Expr;
import com.github.jeromerocheteau.templates.Func;
import com.github.jeromerocheteau.templates.Loop;
import com.github.jeromerocheteau.templates.StartLoop;
import com.github.jeromerocheteau.templates.StartTest;
import com.github.jeromerocheteau.templates.StopLoop;
import com.github.jeromerocheteau.templates.StopTest;
import com.github.jeromerocheteau.templates.Templatable;
import com.github.jeromerocheteau.templates.Template;
import com.github.jeromerocheteau.templates.Test;
import com.github.jeromerocheteau.templates.Text;

public class Templater extends Template {

	private static final Pattern PATTERN = Pattern.compile("\\$\\{(\\w|\\-|\\.|:|\\s)+?\\}");
	private static final Pattern EXPR = Pattern.compile("(\\w|\\-|\\.)+");
	private static final Pattern LOOP = Pattern.compile("for\\s+(\\w+)\\s*:\\s*(\\w|\\.)+");
	private static final Pattern TEST = Pattern.compile("if\\s+(not\\s+)?(\\w|\\-|\\.)+");
	private static final Pattern FUNC = Pattern.compile("\\s*(\\w|\\.)+\\s*:\\s*\\w+");
	private static final Pattern DONE = Pattern.compile("for");
	private static final Pattern END = Pattern.compile("if");
	private static final Pattern WS = Pattern.compile("\\s*\\n\\r?(.*)");

	private Map<String, Formatter> formatters;
	
	public <T> void addFormatter(String name, Formatter formatter) {
		formatters.put(name, formatter);
	}
	
	private Templater() {
		formatters = new HashMap<String, Formatter>();
	}
	
	public Templater(Reader reader) throws Exception {
		this();
		String template = IOUtils.toString(reader);
		this.doLoad(new StringBuffer(template));
	}
	
	public void doProcess(Map<String, Object> scopes, Writer writer) throws TemplaterException, IOException {
		StringBuffer buffer = this.doProcess(scopes);
		IOUtils.copy(new StringReader(buffer.toString()), writer);
	}
	
	private void doLoad(StringBuffer buffer) throws Exception {
		this.setStart(0);
		this.setStop(buffer.length());
		this.setBlocks(new LinkedList<Block>());
		this.doFind(buffer, 0, buffer.length());
		this.doFill(buffer, 0, buffer.length());
	    this.doOrder();
	    this.doPrun(WS, null, this.getBlocks());
	}

	private void doFind(StringBuffer buffer, int inf, int sup) {
		Matcher m = PATTERN.matcher(buffer.substring(inf, sup));
	    while (m.find()) {
	    	int start = m.start();
	    	int stop = m.end();
	        String key = m.group().substring(2,m.group().length()-1);
	        if (END.matcher(key).matches()) {
	        	StopTest test = new StopTest();
	        	test.setStart(start);
	        	test.setStop(stop);
	        	this.getBlocks().add(test);	        	
	        } else if (DONE.matcher(key).matches()) {
	        	StopLoop loop = new StopLoop();
	        	loop.setStart(start);
	        	loop.setStop(stop);
	        	this.getBlocks().add(loop);	        	
	        } else if (TEST.matcher(key).matches()) {
	        	String cond = key.substring(2).trim();
	        	String[] path = null;
	        	Boolean not = null;
	        	if (cond.startsWith("not")) {
	        		path = cond.substring(3).trim().split("\\.");
	        		not = Boolean.TRUE;
	        	} else {
	        		not = Boolean.FALSE;
	        		path = cond.split("\\.");
	        	}
	        	StartTest test = new StartTest();
	        	test.setNot(not);
	        	test.setStart(start);
	        	test.setStop(stop);
	        	Expr iter = new Expr();
	        	iter.setPath(path);
	        	test.setExpr(iter);
	        	this.getBlocks().add(test);
	        } else if (LOOP.matcher(key).matches()) {
	        	String[] cond = key.substring(3).split(":");
	        	if (cond.length == 2) {
	        		String var = cond[0].trim();
	        		String prop = cond[1].trim();
	        		StartLoop loop = new StartLoop();
	        		loop.setStart(start);
	        		loop.setStop(stop);
	        		loop.setVariable(var);
	        		Expr iter = new Expr();
	        		iter.setPath(prop.split("\\."));
	        		loop.setIterator(iter);
	        		this.getBlocks().add(loop);
	        	} else {
		        	throw new NullPointerException(m.group());
		        } 
	        } else if (EXPR.matcher(key).matches()) {
	        	String[] path = key.split("\\.");
	        	Expr expr = new Expr();
	        	expr.setStart(start);
	        	expr.setStop(stop);
	        	expr.setPath(path);
	        	this.getBlocks().add(expr);
	        } else if (FUNC.matcher(key).matches()) {
	        	String[] items = key.split(":");
	        	if (items.length == 2) {
	        		String name = items[1].trim();
	        		String path = items[0].trim();
	        		Expr expr = new Expr();
	        		expr.setPath(path.split("\\."));
			    	Func func = new Func();
			    	func.setFormatters(formatters);
			    	func.setStart(start);
			    	func.setStop(stop);
			    	func.setName(name);
			    	func.setExpr(expr);
			    	this.getBlocks().add(func);
	        	} else {
	        		throw new NullPointerException(m.group());	
	        	}
	        } else {
		    	throw new NullPointerException(m.group());
	        }
	    }
	    Collections.sort(this.getBlocks());
	}

	private void doFill(StringBuffer buffer, int start, int stop) {
		List<Text> texts = new LinkedList<Text>();
		int begin = start;
		for (Block block : this.getBlocks()) {
			int end = block.getStart();
			if (end > begin) {
				Text text = new Text();
				text.setValue(buffer.substring(begin, end));
				text.setStart(begin);
				text.setStop(end);
				texts.add(text);
			} 
			begin = block.getStop();
		}
		if (begin < stop) {
			Text text = new Text();
			text.setValue(buffer.substring(begin, stop));
			text.setStart(begin);
			text.setStop(stop);
			texts.add(text);
		}
		this.getBlocks().addAll(texts);
		Collections.sort(this.getBlocks());
	}
	
	private void doOrder() {
		Stack<Templatable> loops = new Stack<Templatable>();
		List<Block> add = new LinkedList<Block>();
		List<Block> del = new LinkedList<Block>();
		for (Block block : this.getBlocks()) {
			if (block instanceof StartLoop) {
				StartLoop start = (StartLoop) block;
				del.add(block);
				Loop loop = new Loop();
				loop.setStart(block.getStart());
				loop.setVariable(start.getVariable());
				loop.setIterator(start.getIterator());
				Template tpl = new Template();
				tpl.setBlocks(new LinkedList<Block>());
				loop.setTemplate(tpl);
				loops.push(loop);
			} else if (block instanceof StopLoop) {
				del.add(block);
				Loop loop = (Loop) loops.pop();
				loop.setStop(block.getStop());
				if (loops.empty()) { 
					add.add(loop);	
				} else {
					loops.peek().getTemplate().getBlocks().add(loop);
				}
				Collections.sort(loop.getTemplate().getBlocks());
			} else if (block instanceof StartTest) {
				StartTest start = (StartTest) block;
				del.add(block);
				Test test = new Test();
				test.setNot(start.getNot());
				test.setStart(block.getStart());
				test.setExpr(start.getExpr());
				Template tpl = new Template();
				tpl.setBlocks(new LinkedList<Block>());
				test.setTemplate(tpl);
				loops.push(test);
			} else if (block instanceof StopTest) {
				del.add(block);
				Test loop = (Test) loops.pop();
				loop.setStop(block.getStop());
				if (loops.empty()) { 
					add.add(loop);	
				} else {
					loops.peek().getTemplate().getBlocks().add(loop);
				}
				Collections.sort(loop.getTemplate().getBlocks());
			} else if (loops.empty()) {
				
			} else {
				loops.peek().getTemplate().getBlocks().add(block);
				del.add(block);
			}
		}
		this.getBlocks().removeAll(del);
		this.getBlocks().addAll(add);
		Collections.sort(this.getBlocks());
	}
	
	private void doPrun(Pattern pattern, Block parent, List<Block> blocks) {
		int size = blocks.size();
		for (int i = 0; i < size; i++) {
			Block block = blocks.get(i);
			if (i == 0) {
				if (parent != null) this.doPrun(pattern, block);
			}
			if (block instanceof Loop) {
				Loop loop = (Loop) block;
				List<Block> innerBlocks = loop.getTemplate().getBlocks();
				this.doPrun(pattern, loop, innerBlocks);
			} else if (block instanceof Test) {
				Test test = (Test) block;
				List<Block> innerBlocks = test.getTemplate().getBlocks();
				this.doPrun(pattern, test, innerBlocks);
			}
		}
	}

	private void doPrun(Pattern pattern, Block block) {
		if (block instanceof Text) {
			Text text = (Text) block;
			String value = text.getValue();
			Matcher matcher = pattern.matcher(value);
			if (matcher.matches()) {
				int count = matcher.groupCount();
				if (count == 1) {
					text.setValue(matcher.group(1));
				}
			}
			
		}
	}
		
}
