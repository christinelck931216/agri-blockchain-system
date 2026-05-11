package main; // 宣告此類別位於 main 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來建立交易紀錄清單
import java.util.List; // 匯入 List，用來儲存多筆交易紀錄
import java.nio.charset.StandardCharsets; // 匯入 UTF-8 編碼，處理中文資料
import java.util.Base64; // 匯入 Base64，用來解碼後端傳入的參數

import model.Block; // 匯入 Block 類別，用來建立區塊
import model.Participant; // 匯入 Participant 類別，代表供應鏈參與者
import model.TransactionRecord; // 匯入 TransactionRecord 類別，代表上鏈交易紀錄

public class Main { // Java 程式進入點

    public static void main(String[] args) {

        // 設定 Java 執行時的字元編碼為 UTF-8
        // 用來降低中文亂碼發生機率
        System.setProperty("file.encoding", "UTF-8");

        /*
         * 新版參數設計：
         *
         * args[0] = traceCode，追溯碼，Base64
         * args[1] = productName，產品名稱，Base64
         * args[2] = producerName，生產者名稱，Base64
         * args[3] = location，地點，Base64
         * args[4] = recordTimestamp，交易紀錄時間，Base64
         * args[5] = blockTimestamp，區塊建立時間，Base64
         * args[6] = blockIndex，區塊編號，不用 Base64
         * args[7] = previousHash，前一個區塊 Hash，不用 Base64
         *
         * 為什麼要多傳 recordTimestamp 和 blockTimestamp：
         * 因為之後查詢驗證時，要重新計算 Hash。
         * 如果時間每次都重新產生，Hash 一定會不同，無法比對。
         */
        if (args.length < 8) {
            System.out.println("{\"success\":false,\"message\":\"參數不足\"}");
            return;
        }

        // 將後端傳入的 Base64 參數解碼成原本的中文字串
        String traceCode = decodeBase64(args[0]); // 追溯碼
        String productName = decodeBase64(args[1]); // 產品名稱
        String producerName = decodeBase64(args[2]); // 生產者名稱
        String location = decodeBase64(args[3]); // 地點

        // 這兩個時間必須由後端傳入並存進資料庫
        // 之後查詢驗證時要使用同一組時間重新計算 Hash
        String recordTimestamp = decodeBase64(args[4]); // 交易紀錄時間
        String blockTimestamp = decodeBase64(args[5]); // 區塊建立時間

        // 區塊編號由後端控制
        // 第一次上鏈時可以用資料庫目前區塊數量決定
        int blockIndex = Integer.parseInt(args[6]);

        // previousHash 由後端傳入
        // 如果是第一筆正式資料，可以先用創世區塊 Hash 或固定值
        String previousHash = args[7];

        // 建立供應鏈參與者
        // 目前角色固定為農民
        Participant farmer = new Participant(
                "F001", // 參與者編號
                producerName, // 參與者名稱，也就是生產者名稱
                "農民", // 角色
                "無", // 聯絡資訊，目前先用無
                location // 所在地
        );

        // 建立交易紀錄清單
        // 一個區塊可以包含多筆 TransactionRecord
        List<TransactionRecord> transactions = new ArrayList<>();

        // 建立一筆農產品履歷交易紀錄
        // 注意這裡使用的是可以傳入 timestamp 的建構子
        TransactionRecord record = new TransactionRecord(
                "T001", // 交易紀錄編號，目前先固定
                traceCode, // 產品編號，這裡目前使用 traceCode
                productName, // 產品名稱
                "BATCH001", // 批次編號，目前先固定
                "資料上鏈", // 事件類型
                "後端傳入農產品資料並建立區塊鏈紀錄", // 事件描述
                location, // 事件發生地點
                farmer, // 操作者，也就是生產者
                true, // 是否驗證通過
                recordTimestamp // 固定交易時間，由後端傳入
        );

        // 將交易紀錄加入交易清單
        transactions.add(record);

        // 建立區塊
        // 注意這裡使用的是可以傳入 blockTimestamp 的建構子
        Block block = new Block(
                blockIndex, // 區塊編號
                transactions, // 此區塊內的交易資料
                previousHash, // 前一個區塊 Hash
                blockTimestamp // 固定區塊時間，由後端傳入
        );

        // 執行挖礦
        // difficulty = 3，代表 Hash 前面需要有 3 個 0
        block.mineBlock(3);

        // 組合要回傳給 Node.js 後端的 JSON 字串
        // Java 只能輸出這一行 JSON，避免 Node.js JSON.parse 發生錯誤
        String json = "{"
                + "\"success\":true,"
                + "\"traceCode\":\"" + escapeJson(traceCode) + "\","
                + "\"productName\":\"" + escapeJson(productName) + "\","
                + "\"producerName\":\"" + escapeJson(producerName) + "\","
                + "\"location\":\"" + escapeJson(location) + "\","
                + "\"recordTimestamp\":\"" + escapeJson(recordTimestamp) + "\","
                + "\"blockTimestamp\":\"" + escapeJson(blockTimestamp) + "\","
                + "\"blockIndex\":" + blockIndex + ","
                + "\"blockHash\":\"" + block.getHash() + "\","
                + "\"previousHash\":\"" + block.getPreviousHash() + "\","
                + "\"merkleRoot\":\"" + block.getMerkleRoot() + "\","
                + "\"nonce\":" + block.getNonce()
                + "}";

        // 輸出 JSON 給 Node.js 後端接收
        System.out.println(json);
    }

    // 將字串中的特殊符號轉成 JSON 可接受格式
    // 避免文字中有反斜線或雙引號時造成 JSON 格式錯誤
    private static String escapeJson(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("\\", "\\\\") // 處理反斜線
                .replace("\"", "\\\""); // 處理雙引號
    }

    // 將 Base64 字串解碼回 UTF-8 中文字串
    private static String decodeBase64(String text) {
        return new String(
                Base64.getDecoder().decode(text), // Base64 解碼
                StandardCharsets.UTF_8 // 使用 UTF-8 還原中文
        );
    }
}