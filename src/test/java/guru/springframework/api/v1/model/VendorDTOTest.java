package guru.springframework.api.v1.model;

//import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import pl.pojo.tester.api.assertion.Method;
import org.junit.jupiter.api.Test;

import guru.springframework.domain.Vendor;

public class VendorDTOTest {
	
	@Test
	public void Should_Pass_All_Pojo_Tests_Using_All_Testers() {
	    // given
	    final Class<?> classUnderTest = Vendor.class;

	    // when

	    // then
//	    assertPojoMethodsForAll(classUnderTest).areWellImplemented();
	    
	    assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
	                                        .testing(Method.EQUALS)
	                                        .testing(Method.HASH_CODE)
	                                        .testing(Method.CONSTRUCTOR)
	                                        .areWellImplemented();
	}
}
