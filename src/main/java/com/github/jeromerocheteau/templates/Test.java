package com.github.jeromerocheteau.templates;

import java.util.Collection;
import java.util.Map;

import com.github.jeromerocheteau.templater.TemplaterException;

public class Test extends Block implements Templatable {

	private Boolean not;
	
	private Expr expr;
	
	private Template template;
	
	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public StringBuffer doProcess(Map<String, Object> scope) throws TemplaterException {
		Object value = expr.getValue(scope);
		if (value == null) {
			if (not) {
				return this.doForward(scope);
			} else {
				return new StringBuffer("");
			}
		} else if (value instanceof String && ((String) value).isEmpty()) {
			if (not) {
				return this.doForward(scope);
			} else {
				return new StringBuffer("");
			}
		} else if (value instanceof Boolean) {
			Boolean val = (Boolean) value;
			return this.doProcess(scope, val);
		} else if (value instanceof Collection<?>) {
			Collection<?> collection = (Collection<?>) value;
			return this.doProcess(scope, collection);
		} else if (value instanceof Map<?,?>) {
			Map<?,?> map = (Map<?,?>) value;
			return this.doProcess(scope, map);
		} else if (!not) {
			return this.doForward(scope);
		} else {
			return new StringBuffer("");
		}
	}

	private StringBuffer doForward(Map<String, Object> scope) throws TemplaterException {
		StringBuffer builder = new StringBuffer(2048);
		builder.append(template.doProcess(scope));
		return builder;
	}

	private StringBuffer doProcess(Map<String, Object> scope, Map<?, ?> map) throws TemplaterException {
		if (!not && !map.isEmpty()) {
			return doForward(scope);
		} else if (not && map.isEmpty()) {
			return doForward(scope);
		} else {
			return new StringBuffer("");
		}
	}

	private StringBuffer doProcess(Map<String, Object> scope, Collection<?> collection) throws TemplaterException {
		if (!not && !collection.isEmpty()) {
			return doForward(scope);
		} else if (not && collection.isEmpty()) {
			return doForward(scope);
		} else {
			return new StringBuffer("");
		}
	}

	private StringBuffer doProcess(Map<String, Object> scope, Boolean value) throws TemplaterException {
		if (!not && value.booleanValue()) {
			return doForward(scope);
		} else if (not && !value.booleanValue()) {
			return doForward(scope);
		} else {
			return new StringBuffer("");
		}
	}
	
}
