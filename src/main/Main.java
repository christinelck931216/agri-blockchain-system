package main; // 代表這個類別放在 main 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來建立交易清單
import java.util.List; // 匯入 List 介面

import core.Blockchain; // 匯入 Blockchain 類別
import core.ChainValidator; // 匯入 ChainValidator 類別
import model.Participant; // 匯入 Participant 類別
import model.TransactionRecord; // 匯入 TransactionRecord 類別

public class Main { // 主程式類別

    public static void main(String[] args) { // Java 程式執行入口

       
        // 1. 建立供應鏈參與者
      

        Participant farmer = new Participant(
                "F001", // 參與者編號
                "王小明", // 參與者名稱
                "農民", // 角色
                "0912-345678", // 聯絡資訊
                "雲林" // 所在地
        );

        Participant packagingCenter = new Participant(
                "P001",
                "雲林包裝場",
                "包裝商",
                "05-1234567",
                "雲林"
        );

        Participant retailer = new Participant(
                "R001",
                "台北安心超市",
                "零售商",
                "02-9876543",
                "台北"
        );

     
        // 2. 建立第一批交易紀錄（第一個正式區塊）
        

        List<TransactionRecord> block1Transactions = new ArrayList<>();

        TransactionRecord record1 = new TransactionRecord(
                "T001", // 紀錄編號
                "A001", // 產品編號
                "高麗菜", // 產品名稱
                "BATCH001", // 批次編號
                "播種", // 事件類型
                "於 3 月初完成播種作業", // 描述
                "雲林農地 A 區", // 地點
                farmer, // 操作者
                true // 是否驗證
        );

        TransactionRecord record2 = new TransactionRecord(
                "T002",
                "A001",
                "高麗菜",
                "BATCH001",
                "施肥",
                "完成第一次有機肥施作",
                "雲林農地 A 區",
                farmer,
                true
        );

        TransactionRecord record3 = new TransactionRecord(
                "T003",
                "A001",
                "高麗菜",
                "BATCH001",
                "採收",
                "完成採收並準備送往包裝場",
                "雲林農地 A 區",
                farmer,
                true
        );

        block1Transactions.add(record1); // 將第 1 筆交易加入區塊 1 清單
        block1Transactions.add(record2); // 將第 2 筆交易加入區塊 1 清單
        block1Transactions.add(record3); // 將第 3 筆交易加入區塊 1 清單

       
        // 3. 建立第二批交易紀錄（第二個正式區塊）
       

        List<TransactionRecord> block2Transactions = new ArrayList<>();

        TransactionRecord record4 = new TransactionRecord(
                "T004",
                "A001",
                "高麗菜",
                "BATCH001",
                "包裝",
                "完成分級與真空包裝",
                "雲林包裝場",
                packagingCenter,
                true
        );

        TransactionRecord record5 = new TransactionRecord(
                "T005",
                "A001",
                "高麗菜",
                "BATCH001",
                "上架",
                "產品送達台北安心超市並上架販售",
                "台北安心超市",
                retailer,
                true
        );

        block2Transactions.add(record4); // 將第 4 筆交易加入區塊 2 清單
        block2Transactions.add(record5); // 將第 5 筆交易加入區塊 2 清單

        // 4. 建立區塊鏈
      

        Blockchain blockchain = new Blockchain(3); // 建立區塊鏈，難度設為 3

       
        // 5. 將兩批交易分別加入成兩個新區塊
       
        blockchain.addBlock(block1Transactions); // 加入第一個正式區塊
        blockchain.addBlock(block2Transactions); // 加入第二個正式區塊

        
        // 6. 印出整條鏈
       

        blockchain.printChain(); // 顯示整條區塊鏈的所有內容

       
        // 7. 驗證區塊鏈是否合法
       

        System.out.println("\n=== 驗證區塊鏈 ===");
        boolean isValidBeforeTamper = ChainValidator.isChainValid(blockchain); // 檢查鏈是否合法
        System.out.println("竄改前驗證結果：" + isValidBeforeTamper); // 印出驗證結果

        
        // 8. 模擬竄改資料
       

        boolean demoMode = true; // 控制是否模擬攻擊

        if (demoMode) {
            System.out.println("\n=== 模擬竄改資料 ===");

            TransactionRecord tamperedRecord =
                blockchain.getChain().get(1).getTransactions().get(1);

            tamperedRecord.setDescription("測試資料異常");
        }

        
        // 9. 再次驗證區塊鏈
       

        System.out.println("\n=== 再次驗證區塊鏈 ===");
        boolean isValidAfterTamper = ChainValidator.isChainValid(blockchain); // 再檢查一次
        System.out.println("竄改後驗證結果：" + isValidAfterTamper); // 印出驗證結果
    
     // 10. 查詢某個產品履歷
    

        System.out.println("\n=== 查詢產品履歷 ==="); // 印出提示文字

     // 建立 QueryService 物件
     service.QueryService queryService = new service.QueryService(blockchain);

     // 先註冊產品基本資料（這很重要，不然前端資料會是 null）
     model.ProductInfo productInfo = new model.ProductInfo(
             "A001", // productId
             "AGRI-2026-001", // traceCode
             "高麗菜", // 名稱
             "蔬菜", // 類別
             "雲林縣", // 產地
             "2026-03-20", // 包裝日期
             "幸福農場", // 農場名稱
             "王小明", // 生產者
             "0912-345-678" // 聯絡電話
     );

     // 把產品資料註冊進 QueryService
     queryService.registerProductInfo(productInfo);

     // 查詢產品履歷（回傳一整包結果）
     model.ProductTraceResult result = queryService.getTraceByProductId("A001");

     // 印出結果（先測試用）
     System.out.println(result);
    }
}