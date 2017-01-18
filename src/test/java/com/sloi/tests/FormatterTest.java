package com.sloi.tests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sloi.formatters.LowerCaseStringFormatter;
import com.sloi.formatters.UpperCaseStringFormatter;
import com.sloi.templater.Templater;
import com.sloi.utils.Message;

public class FormatterTest {

	private Templater templater;
	
    @Before
    public void setUp() throws Exception {
    	InputStream stream = this.getClass().getResourceAsStream("/formatter-test.template");
    	Reader reader = new InputStreamReader(stream);
        templater = new Templater(reader);
        templater.addFormatter("lowercase", new LowerCaseStringFormatter());
        templater.addFormatter("uppercase", new UpperCaseStringFormatter());
    }
 
    @After
    public void tearDown() throws Exception {
    }
	
	@Test
	public void doProcess() throws Exception {
		Message message = new Message("Hello", "World");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals(buffer.toString(), "hello WORLD!");
	}
	
}
