package me.pedrazas.fhr;

import junit.framework.TestCase;

public class PostCodeUtilsTest extends TestCase {
	
	public void testGetAreaCode() {
		String postcode01 = "NW12 3YN";
		String postcode02 = "AW109PO";
		String postcode03 = "e14 4hb";
		String nullCode = null;
		
		// valid upper case
		assertNotNull(PostCodeUtils.getAreaCode(postcode01));
		assertEquals(PostCodeUtils.getAreaCode(postcode01), "NW");
		// Not valid
		assertNull(PostCodeUtils.getAreaCode(postcode02));
		// valid lower case
		assertNotNull(PostCodeUtils.getAreaCode(postcode03));
		assertEquals(PostCodeUtils.getAreaCode(postcode03), "E");		
		// Not valid
		assertNull(PostCodeUtils.getAreaCode(nullCode));
	}

	public void testGetOutward() {
		String postcode01 = "NW12 3YN";
		String postcode02 = "AW109PO";
		String postcode03 = "e14 4hb";
		String nullCode = null;
		
		// valid upper case
		assertNotNull(PostCodeUtils.getOutward(postcode01));
		assertEquals(PostCodeUtils.getOutward(postcode01), "NW12");
		// Not valid
		assertNull(PostCodeUtils.getOutward(postcode02));
		// valid lower case
		assertNotNull(PostCodeUtils.getOutward(postcode03));
		assertEquals(PostCodeUtils.getOutward(postcode03), "E14");		
		// Not valid
		assertNull(PostCodeUtils.getOutward(nullCode));
		
	}

	public void testValidate() {
		String postcode01 = "PaO18 4SY";
		String postcode02 = "e14 4hb";
		String postcode03 = "AW109PO";
		String nullCode = null;
		assertFalse(PostCodeUtils.validate(postcode01));
		assertTrue(PostCodeUtils.validate(postcode02));
		assertFalse(PostCodeUtils.validate(postcode03));
		assertFalse(PostCodeUtils.validate(nullCode));
	}

}
