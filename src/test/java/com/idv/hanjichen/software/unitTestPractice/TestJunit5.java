package com.idv.hanjichen.software.unitTestPractice;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

//@SpringBootTest或@ExtendWith可以整合其他測試類

@DisplayName("junit5功能測試類")
public class TestJunit5 {

	//為測試類或測試方法設置展示的名稱
	@DisplayName("測試displayname註解")
	@Test
	void testDisplayName() {
		System.out.println(1);
	}
	
	@DisplayName("測試方法2")
	@Test
	void test2() {
		System.out.println(2);
	}
	
	/**
	 * @Timeout-規範測試方法運行超過指定時間會出異常
	 * @Disabled-可以將測試方法禁用
	 */
	@Disabled
	@DisplayName("測試方法3用來做timeout")
	@Timeout(value = 3,unit = TimeUnit.SECONDS)
	@Test
	void testTimeOut() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println(3);
	}
	
	/**
	 * 導入斷言的包:import static org.junit.Assert.assertEquals;
	 * 斷言的情況下,若前面已有失敗的斷言,則後面所有code不會再繼續執行
	 */
	@DisplayName("測試簡單斷言")
	@Test
	void testSimpleAssertions() {
		int cal = cal(2,3);
		//前者為期望答案,後者為傳入的真實運算結果
		assertEquals(5, cal);
		//加入顯示訊息
//		assertEquals("測試業務邏輯失敗", 6, cal);
		
		//測試是否為相同物件
		Object obj1 = new Object();
		Object obj2 = new Object();
		assertSame(obj1, obj2,"兩個目標不是相同的物件");
	}
	
	/**
	 * 陣列判斷是否相等assertArrayEquals
	 */
	@Test
	@DisplayName("陣列斷言")
	void array() {
		assertArrayEquals(new int[] {1,2}, new int[] {1,2});
	}
	
	/**
	 * 組合斷言:所有的斷言都判定成功,才算成功
	 */
	@Test
	@DisplayName("組合斷言")
	void all() {
		assertAll("組合斷言test",
				()-> assertTrue(true && true),
				()-> assertEquals(1, 1));
	}
	
	/**
	 * 異常斷言
	 */
	@Test
	@DisplayName("異常斷言")
	void testException() {
		//這邊的意思是:我斷言此業務邏輯一定會出異常exception
		//如果該案例會出異常->表示斷言成功,但如果不會出異常->斷言失敗
		assertThrows(ArithmeticException.class, ()->{int i=10/2;},"業務邏輯居然正常運作");
	}
	
	/**
	 * 快速失敗
	 */
	@Test
	@DisplayName("快速失敗")
	void testFail() {
		if(2 == 2)
		fail("測試失敗");
	}
	
	/*
	 * 測試前置條件
	 */
	@DisplayName("測試前置條件")
	@Test
	void testAssumptions() {
//		Assumptions.assumeTrue(true,"前置條件不滿足,結果不是True");
		Assumptions.assumeTrue(false,"前置條件不滿足,結果不是True");
		System.out.println("前置條件有滿足!結果是true");
	}
	
	
	int cal(int i,int j) {
		return i + j;
	}
	
	@DisplayName("測試方法4-用於重複測試")
	@RepeatedTest(value = 2)
	@Test
	void test4()  {
		System.out.println("我會重新被執行2次");
	}
	
	//在"每個測試"開始前先執行
	@BeforeEach
	void testBeforeEach() {
		System.out.println("測試即將開始!");
	}
	
	//在"每個測試"結束後先執行
	@AfterEach
	void testAfterEach() {
		System.out.println("測試結束了!");
	}
	
	//在所有測試開始前先執行，且執行一次。
	//注意!因為需要在每個方法執行前調用此方法且只會調用一次，所以要static
	@BeforeAll
	static void testBeforeAll() {
		System.out.println("所有測試即將開始!");
	}
	
	//在所有測試結束後先執行，且執行一次。
	//注意!因為需要在每個方法執行前調用此方法且只會調用一次，所以要static
	@AfterAll
	static void testAfterAll() {
		System.out.println("所有測試結束了!");
	}
	
}
