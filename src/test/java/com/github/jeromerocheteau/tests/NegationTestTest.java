package com.github.jeromerocheteau.tests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.jeromerocheteau.templater.Templater;
import com.github.jeromerocheteau.utils.Message;

public class NegationTestTest {

	private Templater templater;
	
    @Before
    public void setUp() throws Exception {
    	InputStream stream = this.getClass().getResourceAsStream("/negation-test-test.template");
    	Reader reader = new InputStreamReader(stream);
        templater = new Templater(reader);
    }
 
    @After
    public void tearDown() throws Exception {
    }
	
	@Test
	public void doBothUndefined() throws Exception {
		Message message = new Message(null, null);
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut le monde!", buffer.toString().trim());
	}
	
	@Test
	public void doBothEmpty() throws Exception {
		Message message = new Message("", "");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut le monde!", buffer.toString().trim());
	}

	@Test
	public void doFirstEmpty() throws Exception {
		Message message = new Message("", "World");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut World!", buffer.toString().trim());
	}

	@Test
	public void doLastEmpty() throws Exception {
		Message message = new Message("Hello", "");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Hello le monde!", buffer.toString().trim());
	}
	
	@Test
	public void doDefined() throws Exception {
		Message message = new Message ("Hello", "World");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Hello World!", buffer.toString().trim());
	}
	
	@Test
	public void doUndefined() throws Exception {
		Message message = null;
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("message", message);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut tout le monde !", buffer.toString().trim());
	}
	
}
