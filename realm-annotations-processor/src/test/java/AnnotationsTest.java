package io.realm.annotations;

import org.junit.Before;
import org.junit.Test;

public class AnnotationsTest {

  @Before
  public void setUp() throws Exception {
	System.out.println("TEST SETUP");
  }

  @Test
  public void testBasic() throws Exception {
	System.out.println("TEST BASIC");
  }
}
