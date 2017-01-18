package com.sloi.templates;

import java.util.Map;

import com.sloi.formatter.Formatter;
import com.sloi.templater.TemplaterException;

public class Func extends Block {

	private Map<String, Formatter> formatters;
	
	private String name;
	
	private Expr expr;
	
	public Map<String, Formatter> getFormatters() {
		return formatters;
	}

	public void setFormatters(Map<String, Formatter> formatters) {
		this.formatters = formatters;
	}

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
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		try {
			Object object = expr.getValue(scope);
			Formatter formatter = formatters.get(name);
			return formatter.doFormat(object);
		} catch (Exception e) {
			throw new TemplaterException(this, e.getMessage());
		}
	}

}
