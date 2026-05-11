package model; // 宣告此類別位於 model 套件中

import java.time.LocalDateTime; // 匯入 LocalDateTime，用來取得目前時間
import java.util.List; // 匯入 List，因為一個區塊可以存放多筆交易紀錄

import util.HashUtil; // 匯入 HashUtil，負責 SHA-256 雜湊運算
import util.MerkleUtil; // 匯入 MerkleUtil，負責計算 Merkle Root

public class Block { // 宣告 Block 類別，代表區塊鏈中的一個區塊

    private int index; // 區塊編號，表示這是第幾個區塊
    private String timestamp; // 區塊建立時間
    private List<TransactionRecord> transactions; // 區塊內包含的多筆農產品履歷交易紀錄
    private String previousHash; // 前一個區塊的 Hash，用來把區塊串起來
    private String merkleRoot; // 區塊內所有交易紀錄計算後得到的 Merkle Root
    private int nonce; // 挖礦用的數值，會不斷增加直到 Hash 符合難度
    private String hash; // 目前區塊自己的 Hash

    // 原本建構子：建立區塊時，自動使用目前時間
    // 適合第一次建立新區塊時使用
    public Block(int index, List<TransactionRecord> transactions, String previousHash) {
        this.index = index; // 設定區塊編號
        this.timestamp = LocalDateTime.now().toString(); // 自動設定區塊建立時間
        this.transactions = transactions; // 設定此區塊包含的交易紀錄
        this.previousHash = previousHash; // 設定前一個區塊的 Hash
        this.merkleRoot = MerkleUtil.getMerkleRoot(transactions); // 根據交易紀錄計算 Merkle Root
        this.nonce = 0; // nonce 一開始從 0 開始
        this.hash = calculateHash(); // 建立區塊時先計算一次 Hash
    }

    // 新增建構子：允許外部傳入固定 timestamp
    // 這個建構子用於重新計算 Hash 比對
    // 因為查詢驗證時，區塊時間必須和第一次上鏈時完全相同
    public Block(int index, List<TransactionRecord> transactions, String previousHash, String timestamp) {
        this.index = index; // 設定區塊編號
        this.timestamp = timestamp; // 使用外部傳入的固定區塊時間
        this.transactions = transactions; // 設定此區塊包含的交易紀錄
        this.previousHash = previousHash; // 設定前一個區塊的 Hash
        this.merkleRoot = MerkleUtil.getMerkleRoot(transactions); // 根據交易紀錄計算 Merkle Root
        this.nonce = 0; // nonce 一開始從 0 開始
        this.hash = calculateHash(); // 建立區塊時先計算一次 Hash
    }

    // 計算目前區塊的 Hash
    public String calculateHash() {
        // 將區塊中的重要欄位串接起來
        // 只要其中任何一個欄位改變，最後算出的 Hash 就會不同
        String dataToHash = index +
                            timestamp +
                            previousHash +
                            merkleRoot +
                            nonce;

        // 使用 SHA-256 計算 Hash 並回傳
        return HashUtil.applySHA256(dataToHash);
    }

    // 模擬簡單版工作量證明 Proof of Work
    // difficulty 代表 Hash 前面需要幾個 0
    public void mineBlock(int difficulty) {

        // 建立目標字串
        // 例如 difficulty = 3 時，target 會是 "000"
        String target =
                new String(new char[difficulty])
                .replace('\0', '0');

        // 只要目前 Hash 的開頭不符合 target，就持續挖礦
        while (!hash.substring(0, difficulty).equals(target)) {

            nonce++; // nonce 加 1

            hash = calculateHash(); // 使用新的 nonce 重新計算 Hash
        }

        // 注意：
        // 後端串接版本不能輸出文字
        // 否則 Node.js 的 JSON.parse(stdout) 會失敗
    }

    // 顯示區塊內容
    // 目前主要用於本機測試，不建議在後端串接時呼叫
    public void printBlock() {
        System.out.println("{ 區塊資訊 }");
        System.out.println("區塊編號: " + index);
        System.out.println("建立時間: " + timestamp);
        System.out.println("前一個 Hash: " + previousHash);
        System.out.println("Merkle Root: " + merkleRoot);
        System.out.println("Nonce: " + nonce);
        System.out.println("目前 Hash: " + hash);
        System.out.println("{ 區塊內交易紀錄 }");

        for (TransactionRecord record : transactions) {
            System.out.println(record);
        }

        System.out.println("============================");
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.hash = calculateHash(); // index 改變時，重新計算 Hash
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        this.hash = calculateHash(); // timestamp 改變時，重新計算 Hash
    }

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRecord> transactions) {
        this.transactions = transactions;
        this.merkleRoot = MerkleUtil.getMerkleRoot(transactions); // 交易資料改變時，重新計算 Merkle Root
        this.hash = calculateHash(); // Merkle Root 改變後，重新計算區塊 Hash
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
        this.hash = calculateHash(); // previousHash 改變時，重新計算 Hash
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
        this.hash = calculateHash(); // Merkle Root 改變時，重新計算 Hash
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
        this.hash = calculateHash(); // nonce 改變時，重新計算 Hash
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}