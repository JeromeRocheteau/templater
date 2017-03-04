package com.github.jeromerocheteau.tests;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.jeromerocheteau.templater.Templater;

public class CollectionTestTest {

	private Templater templater;
	
    @Before
    public void setUp() throws Exception {
    	InputStream stream = this.getClass().getResourceAsStream("/collection-test-test.template");
    	Reader reader = new InputStreamReader(stream);
        templater = new Templater(reader);
    }
 
    @After
    public void tearDown() throws Exception {
    }
	
	@Test
	public void doList() throws Exception {
		List<String> words = new ArrayList<String>(2);
		words.add("Hello");
		words.add("World");
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("words", words);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("1=Hello 2=World!", buffer.toString().trim());
	}
	
	@Test
	public void doEmptyList() throws Exception {
		List<String> words = new ArrayList<String>(0);
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("words", words);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut tout le monde !", buffer.toString().trim());
	}
	
	@Test
	public void doUndefinedList() throws Exception {
		List<String> words = null;
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("words", words);
		StringBuffer buffer = templater.doProcess(scope);
		Assert.assertEquals("Salut tout le monde !", buffer.toString().trim());
	}
	
}
