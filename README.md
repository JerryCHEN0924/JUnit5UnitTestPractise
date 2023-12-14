# 單元測試
## 為什麼要寫單元測試?
確保程式的運作邏輯正確!
寫單元測試的效益，起初可能不明顯，但它可以很大程度降低後續維護成本。
搭配 CI 可以做到自動化測試並分析程式涵蓋率。
若發現程式寫不了單元測試，很有可能表示程式沒有盡可能符合物件導向設計原則（SRP、OCP、LSP、ISP、DIP）導致。
**單元測試 != 整合測試**
單元測試關注的是**測試程式本身邏輯**，所以必須要把外部依賴（Database、File System IO）全部排除掉才有辦法往下進行！
其次，寫單元測試也能確定自己的程式沒有問題，遇到協作或其他相關聯系統如有出BUG，也能最大程度的說明非自己程式所造成的問題(前提是測試覆蓋率剛好有覆蓋到與他人重疊的部分)。
## 常見問題
1. 怎麼讓UI, Service, Data Access平行開發？
Unit Test中使用mock object，達到關注點分離
2. 要到真實環境方能測試程式無誤
Unit Test使用mock object來模擬外部回傳的資料
3. 頁面發生錯誤，到底是誰錯了？
把input值當做test case，跑一次Unit Test
4. 交付的程式，到底測過哪些東西了？
交付的程式，包括Unit Test程式碼
5. 我改了這支程式，會不會害別的程式掛掉？
改完程式就跑一次Unit Test吧!

## 單元測試定義與基本原則
1. 一個測試案例只測一種方法
2. 最小的測試單位
3. 不與外部（包括檔案、資料庫、網路、服務、物件、類別）直接相依
4. 不具備邏輯
5. 測試案例之間相依性為零

## 單元測試的特性FIRST
1. Fast：快速。
2. Independent：獨立。
3. Repeatable：可重複。
4. Self-Validating：可反應驗證結果。單元測試不論成功或失敗，都應該要從測試的reporting直接瞭解其意義或失敗原因。
5. Timely：及時。單元測試應該恰好在使其通過的production code之前撰寫。

## 單元測試的範圍
單元測試通常就只關注在測試的目標物件上，而不管目標物件以外的東西，例如：目標物件所相依的實體物件、相依服務、相依資源、相依環境等等...

單元測試來模擬外部如何使用這個目標物件，或是如何與這個目標物件互動。所以我們所撰寫的單元測試程式，就是模擬與目標物件互動的程式。
測試案例，就是該互動下的情境。接著驗證物件的行為是否符合我們預期。
因此，單元測試程式，既然是模擬外部如何使用目標物件，所以也只會針對目標物件對外開放的方法。
而基本上，單元測試透過哪些方式去驗證物件的行為符合預期呢？簡單來說有三種：
1.驗證目標物件的回傳值
2.驗證目標物件的狀態改變
3.驗證目標物件與外部相依介面的互動方式

# JUnit
JUnit是JAVA環境中最常使用的單元測試工具。
JUnit 5 由 JUnit Platform、JUnit Jupiter、JUnit Vintage
1. Platform
JUnit平台 用於 JVM上啟動的服務，用於支持測試時候的程式
2. Jupiter
核心引擎JUnit 5 新的 Compile 和 Extends 模組，主要用於讀寫測試程式語法
3. Vintage：
用於兼容 JUnit 4 和 JUnit 3 版本的模組
![](https://hackmd.io/_uploads/r1iCGz8oh.png)

需在專案POM檔中加入依賴才能使用JUnit，4.0以後的版本才能使用注釋。
`<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.8.1</version>
    <scope>test</scope>
</dependency>
`
4.0版本起的JUnit可使用annotation注釋，SpringBoot使用5.0版本的JUnit。
如果Spring boot版本為2.4以前，但想要使用jUnit5的功能，則必須另外在POM檔案中加入jUnit4 vintage的依賴。

Spring Boot整合jUnit後，編寫測試方法:@Test標註，且jUnit具有Spring的功能如@Autowired,@Transactional標註還會在測試結束後自動Roll Back。

測試通常會再開一個test的package
裡頭新建jut package後再建立junit檔，勾選setUp與tearDown選項
檔案中有三個方法分別為@Before、@After、@Test標示，@Test為測試方法。
執行測試方法的步驟為:點選方法名稱→右鍵Run as JUnit Test
當執行測試方法時，@Before會最先被執行，然後@Test，最後@After

## JUnit的四大核心:
1. TestCase / 測試案例
Test Case是JUnit單元測試的核心，掌管單元測試流程的生命週期，其中關鍵的@Annotation有:
@Test / 其中包含常用的屬性 timeout & except
在要測試的Test Function加上@Test，JUint就會自動執行單元測試，其中常用的屬性timeout代表這個Test Function執行只能在低於timeout時間內才算成功，而except代表預期執行的方法會丟出指定的Exception。

@Before — 在每個Test Method之前都會執行一次。
@After — 在每個Test Method之後都會執行一次。
@BeforeClass — 每個Test Class建構者執行後都會執行一次。
@AfterClass — 每個Test Class建構者執行後都會執行一次。
@Ignore — 忽略此單元測試，(若在單元測試class中方法沒加上Ignore，JUnit會執行失敗)
2. TestResult / 測試結果
3. TestSuite / 測試組合
4. Assert / 斷言
Assert是JUnit中用來**判定結果是否符合開發者預期的API**，
其中包含比較常用的API有:
```
assertEquals(基本型別 expected, 基本型別 actual) / 檢查兩者是否相同，如果是物件比較只看value (Call by value)
assertNotEquals(Object expected, Object actual) / 檢查兩者是否不相同
assertSame(Object expected, Object actual) / 檢查兩者是否相同，會多看"是否為相同記憶體位置" (Call by reference)
assertFalse(boolean condition) / 是否為false
assertTrue(boolean condition) / 是否為true
```
流程:
*如果沒有特別撰寫Suite則直接從TestCase開始
TestSuite(找Case) -> TestCase(執行Case方法) -> Assert (判定Case成功與否)-> TestResult(總結Case案例)

Suite因為現在已經有許多套件輔助管理TestCase所以不常見，TestCase則是測試的心臟，測試邏輯都寫在裡面，Assert則是驗證測試的結果是否符合預期，TestResult也已經被許多套件內嵌，主要是讓開發者查看整組的測試結果。
其實本篇文章有個很核心的概念沒提到，就是單元測試的測試方法如何命名，因為每個團隊都有自己的規範，
那到新團隊在使用其規範就好，不過還是提一下**最簡易的命名方法，簡單規則就是test+測試方法+預期回傳結果**。

## 前置條件assumptions 
JUnit5中的前置條件assumptions (假設)，類似斷言，但不同的地方在於**斷言若是不滿足條件會使測試方法失敗**，而**前置條件則是只會使測試方法中止**。
前置條件可以視為測試方法的**前提**，當沒有滿足前提條件之前，則該測試方法沒有執行的必要。

## 參數化測試
參數化測試是JUnit5很重要的特性，它使得不同的參數多次執行測試成為了可能。
利用`@ValueSource`等註解指定入參，我們將可以使用不同的參數進行多次單元測試，再也不需要每新增一個參數就要寫一個新的單元測試。

```
@ValueSource 為參數化測試指定入參來源，支持八大基礎類與String類,Class類
@NullSource 為參數化測試指定Null入參
@EnumSource 為參數化測試指定枚舉入參
@CsvFileSource 為參數化測試指定CSV文件內容入參
@MethodSource 表示讀取指定方法的返回值作為參數化測試入參(注意方法返回必須是一個流且是靜態的-Stream)

```

# Mockito
Mockito主要是用來模擬實際程式運行的狀況，適合用來寫測Test Double，JUnit則適合用來作模組類程式的測試，而Mockito適合用來測試實際的Service類程式。
在介紹 Mockito 之前，需要先了解何謂 Test Double?
1. SUT(System Under Test 或 Software Under Test):
可簡單想作SUT就是一個開發者想拿來作測試的Function或method，在SpringBoot專案中就是Service類的類別程式。
2. DOC(Depended-on Component):
扮演著Collaborator的角色。是SUT執行時會用到的模組類程式。在SpringBoot 專案中就是各式DAO或Module 類的程式。
作單元測試的時候，Mockito使用來測試的對象通常是SUT本身，畢竟測試模組類程式是較沒有意義的事情，測試本身必須符合專案的業務邏輯，在此前提之下的測試才有意義。
但在SUT中必然會存在著使用到諸多的DOC，這些DOC內的方法在測試上也必須加以排除，才能確保SUT的測試是沒有受到任何DOC影響的。

### 如何單獨驗證 SUT，而不需要真正使用到 DOC 成為了第一個問題
如果你願意手刻一個新的DOC，且僅為測試用，就會衍生出第二個問題，測試時候我們不需要真的去跑動那些太複雜的DOC邏輯，我們可以假定一些參數或者回應，然而如果每一個測試都需要手動更動剛剛建立的測試用DOC程式，又會拉慢整體測試的速度，更遑論一個開發者絕對沒有充足的時間可以進行測試的動作。
### 如何快速驗證 SUT 成為了第二個問題
為此，Test Double的方式解決了這兩個問題。
Test Double主要作為測試替身，盡可能地在SUT中替代了那些DOC方法，並且能夠更好的被更動和調整，促使測試的速度可以更快更穩定。
Test Double 一共有五種
1. Dummy Object
不包含實作的物件 (Null也算)，在測試中需要傳入但卻不會被用到的參數，產生完全沒有實作 DOC 的 Test Double。
2. Test Stub
回傳固定值的實作，產生僅回傳特定回傳值的DOC實作的 Test Double，多用來作State Verification(狀態驗證)。
3. Test Spy
類似 Stub，但會記錄自身被呼叫的成員變數，以此確認SUT與它 (被Test Spy的DOC對象)的互動是否正確。
類似 Stub，但會記錄成員變數的DOC實作的Test Double，多用來作Behavior Verification(行為驗證)。
4. Mock Object
由 Mock 程式庫動態建立，提供類似 Dummy、Stub、Spy的功能。
開發人員看不到實作的程式碼，只能夠設定 Mock 要提供的回傳值、預期要呼叫使用的成員變數等。
集合 Dummy、Stub 與 Spy 功能於一身的 Test Double，
5. Fake Object
提供接近原始物件但較簡單的實作，非常接近原始 DOC 方法的 Test Double，其差異在於會採用較簡單的方式處理DOC類程式的邏輯。




參考資料:
https://medium.com/bucketing/java-test-java%E6%B8%AC%E8%A9%A6%E5%AD%B8%E7%BF%92%E5%9C%B0%E5%9C%96-45c5de71c1ef
https://www.youtube.com/watch?v=X60MOsSfSTk&list=PLmOn9nNkQxJFKh2PMfWbGT7RVuMowsx-u&index=71
https://hackmd.io/@KaiChen/rJ6Al2WKI
https://hackmd.io/g3YGfarYSA6iu8IBfyq1Gg
https://zxuanhong.medium.com/%E5%A6%82%E4%BD%95%E6%9C%89%E6%95%88%E6%92%B0%E5%AF%AB%E9%AB%98%E5%93%81%E8%B3%AA%E5%96%AE%E5%85%83%E6%B8%AC%E8%A9%A6-da40a4957f50



