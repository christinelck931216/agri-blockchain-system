package core; // 代表這個類別放在 core 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來存放區塊
import java.util.List; // 匯入 List 介面

import model.Block; // 匯入 Block 類別
import model.TransactionRecord; // 匯入 TransactionRecord 類別

public class Blockchain { // 宣告 Blockchain 類別，代表整條區塊鏈

    private List<Block> chain; // 用來存放整條鏈上的所有區塊
    private int difficulty; // 挖礦難度，代表 hash 前面要幾個 0

    // 建構子：建立區塊鏈時使用
    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>(); // 初始化區塊鏈清單
        this.difficulty = difficulty; // 設定挖礦難度
        this.chain.add(createGenesisBlock()); // 一建立就先加入創世區塊
    }

    // 建立創世區塊（第一個區塊）
    private Block createGenesisBlock() {
        List<TransactionRecord> genesisTransactions = new ArrayList<>();

        Block genesisBlock = new Block(0, genesisTransactions, "0");

        genesisBlock.mineBlock(difficulty);

        return genesisBlock;
    }

    // 取得目前整條鏈最後一個區塊
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // 新增一個新區塊
    public void addBlock(List<TransactionRecord> transactions) {
        int newIndex = chain.size();

        String previousHash = getLatestBlock().getHash();

        Block newBlock = new Block(newIndex, transactions, previousHash);

        newBlock.mineBlock(difficulty);

        chain.add(newBlock);

        // 注意：
        // 這裡不要 System.out.println()
        // 因為後端 Node.js 會用 JSON.parse(stdout)
        // Java 只能輸出 Main.java 最後那一行 JSON
    }

    // 印出整條區塊鏈
    public void printChain() {
        // 後端串接版本不要輸出任何文字
        // 原本內容先保留註解，避免影響 JSON.parse
        /*
        System.out.println("\n================ 區塊鏈內容 ================");
        for (Block block : chain) {
            block.printBlock();
        }
        System.out.println("===========================================");
        */
    }

    // 回傳整條鏈
    public List<Block> getChain() {
        return chain;
    }

    // 設定整條鏈
    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    // 取得挖礦難度
    public int getDifficulty() {
        return difficulty;
    }

    // 設定挖礦難[簡單來說就是強制HASH開頭要是00(自訂)如果算出來不是就在重新算一次] 
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}