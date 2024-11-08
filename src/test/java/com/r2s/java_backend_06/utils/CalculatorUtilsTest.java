package com.r2s.java_backend_06.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculatorUtilsTest {

	@BeforeEach
	 void setup() {
		System.err.println("Before all");
	}

	@AfterEach
	 void teardown() {
		System.out.println("After all");
	}

	@Test
	void divideTestWithHappyCase() {
		// given
		int a = 10;
		int b = 5;

		// when
		double result = CalculatorUtil.divide(a, b);

		// then
		assertEquals(2d, result);
	}

	@Test
	void divideTestWithZero() {
		// given
		int a = 10;
		int b = 0;

		// when
		Executable result = () -> CalculatorUtil.divide(a, b);

		// then
		Exception e = assertThrows(ArithmeticException.class, result);
		assertEquals("b la so 0!", e.getMessage());
	}

	@Test
	@DisplayName("test divide two number with one negative number")
	void divideTestWithNegativeNumber() {
		// given
		int a = 10;
		int b = -5;

		// when
		double result = CalculatorUtil.divide(a, b);

		// then
		assertEquals(-2d, result);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7 })
	void divideTestWithNumber(int b) {
		// given
		int a = 10;
//		int b = -5;

		// when
		double result = CalculatorUtil.divide(a, b);

		// then
		assertEquals(1.0 * a / b, result);
	}

	@ParameterizedTest
//	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7 })
	@CsvSource(value = { "1, 2", "3, 4", "6, 5", "10, 5" })
	void divideTestWith2Number(int a, int b) {
		// given
//		int a = 10;
//		int b = -5;

		// when
		double result = CalculatorUtil.divide(a, b);

		// then
		assertEquals(1.0 * a / b, result);
	}
}
