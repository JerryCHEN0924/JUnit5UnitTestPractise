package com.idv.hanjichen.software.unitTestPractice;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TestParameterized {

	@ParameterizedTest
	@DisplayName("參數化測試int")
	@ValueSource(ints = { 1,2,3,4,5 })
	void testParameterizedint(int i) {
		System.out.println(i);
	}
	
	@ParameterizedTest
	@DisplayName("參數化測試Method")
	@MethodSource("stringProvider")
	void testParameterizedMethod(String s) {
		System.out.println(s);
	}
	
	static Stream<String> stringProvider(){
		return Stream.of("apple","banana","Cherry");
		
	}
	
}
