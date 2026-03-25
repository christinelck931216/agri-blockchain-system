package model; // 代表這個類別放在 model 套件中

import java.time.LocalDateTime; // 匯入 LocalDateTime，用來取得目前時間
import java.util.List; // 匯入 List，因為一個區塊會存多筆交易紀錄

import util.HashUtil; // 匯入 HashUtil，負責做 SHA-256
import util.MerkleUtil; // 匯入 MerkleUtil，負責計算 Merkle Root

public class Block { // 宣告 Block 類別，代表區塊鏈中的一個區塊

    private int index; // 區塊編號，第幾個區塊
    private String timestamp; // 建立區塊的時間
    private List<TransactionRecord> transactions; // 區塊內包含的多筆農產品履歷紀錄
    private String previousHash; // 前一個區塊的 hash
    private String merkleRoot; // 區塊內所有交易紀錄計算後得到的 Merkle Root
    private int nonce; // 隨機數 / 計數值，未來可用來擴充簡單挖礦或工作量證明
    private String hash; // 目前區塊自己的 hash

    // 建構子：建立區塊時使用
    public Block(int index, List<TransactionRecord> transactions, String previousHash) {
        this.index = index; // 設定區塊編號
        this.timestamp = LocalDateTime.now().toString(); // 設定區塊建立時間
        this.transactions = transactions; // 設定區塊內的交易紀錄
        this.previousHash = previousHash; // 設定前一個區塊的 hash
        this.merkleRoot = MerkleUtil.getMerkleRoot(transactions); // 根據交易紀錄計算 Merkle Root
        this.nonce = 0; // 一開始先設為 0
        this.hash = calculateHash(); // 建立區塊時先計算一次自己的 hash
    }

    // 計算目前區塊的 hash
    public String calculateHash() {
        // 把區塊重要欄位串接起來，作為 hash 的輸入內容
        String dataToHash = index +
                            timestamp +
                            previousHash +
                            merkleRoot +
                            nonce;

        // 回傳 SHA-256 結果
        return HashUtil.applySHA256(dataToHash);
    }

    // 這個方法可模擬簡單版工作量證明
    // difficulty 代表前面要幾個 0
    public void mineBlock(int difficulty) {
        // 建立目標字串，例如 difficulty=3 時，目標為 "000"
        String target = new String(new char[difficulty]).replace('\0', '0');

        // 持續調整 nonce，直到 hash 前面符合指定數量的 0
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++; // nonce 加 1
            hash = calculateHash(); // 重新計算 hash
        }

        System.out.println("資料成功上鏈，Hash: " + hash);
    }

    // 顯示區塊內容
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

    // 以下是 getter / setter

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionRecord> transactions) {
        this.transactions = transactions;
        this.merkleRoot = MerkleUtil.getMerkleRoot(transactions); // 交易被改時，同步更新 Merkle Root
        this.hash = calculateHash(); // 同步重新計算 hash
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
        this.hash = calculateHash(); // previousHash 改變時，重新計算 hash
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
        this.hash = calculateHash(); // Merkle Root 改變時，重新計算 hash
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
        this.hash = calculateHash(); // nonce 改變時，重新計算 hash
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}