package com.idv.hanjichen.software.unitTestPractice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("嵌套測試")
public class TestingAStackDemo {


	    Stack<Object> stack;

	    @Test
	    @DisplayName("is instantiated with new Stack()")
	    void isInstantiatedWithNew() {
	        new Stack<>();
	        //嵌套測試情況下，外層的Nest不能驅動內層的Before(After) Each/All之類的方法提前/之後執行
	        assertNull(stack);
	    }

	    @Nested
	    @DisplayName("when new")
	    class WhenNew {

	        @BeforeEach
	        void createNewStack() {
	            stack = new Stack<>();
	        }

	        @Test
	        @DisplayName("is empty")
	        void isEmpty() {
	            assertTrue(stack.isEmpty());
	        }

	        @Test
	        @DisplayName("throws EmptyStackException when popped")
	        void throwsExceptionWhenPopped() {
	            assertThrows(EmptyStackException.class, stack::pop);
	        }

	        @Test
	        @DisplayName("throws EmptyStackException when peeked")
	        void throwsExceptionWhenPeeked() {
	            assertThrows(EmptyStackException.class, stack::peek);
	        }
	        
	        /*
	         * 在嵌套測試中，內層的Test可以驅動外層的before/after each/all
	         */
	        @Nested
	        @DisplayName("after pushing an element")
	        class AfterPushing {

	            String anElement = "an element";

	            @BeforeEach
	            void pushAnElement() {
	                stack.push(anElement);
	            }

	            @Test
	            @DisplayName("it is no longer empty")
	            void isNotEmpty() {
	                assertFalse(stack.isEmpty());
	            }

	            @Test
	            @DisplayName("returns the element when popped and is empty")
	            void returnElementWhenPopped() {
	                assertEquals(anElement, stack.pop());
	                assertTrue(stack.isEmpty());
	            }

	            @Test
	            @DisplayName("returns the element when peeked but remains not empty")
	            void returnElementWhenPeeked() {
	                assertEquals(anElement, stack.peek());
	                assertFalse(stack.isEmpty());
	            }
	        }
	    }
	}
