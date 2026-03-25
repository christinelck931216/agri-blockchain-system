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
        // 創世區塊沒有前一個區塊，所以 previousHash 先設為 0
        // 交易紀錄先給一個空清單
        List<TransactionRecord> genesisTransactions = new ArrayList<>();

        // 建立創世區塊
        Block genesisBlock = new Block(0, genesisTransactions, "0");

        // 創世區塊也可以進行簡單挖礦，讓格式一致
        genesisBlock.mineBlock(difficulty);

        return genesisBlock;
    }

    // 取得目前整條鏈最後一個區塊
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // 新增一個新區塊
    public void addBlock(List<TransactionRecord> transactions) {
        // 新區塊的 index = 目前鏈長度
        int newIndex = chain.size();

        // 新區塊要連到前一個區塊的 hash
        String previousHash = getLatestBlock().getHash();

        // 建立新區塊
        Block newBlock = new Block(newIndex, transactions, previousHash);

        // 做簡單挖礦
        newBlock.mineBlock(difficulty);

        // 加入鏈中
        chain.add(newBlock);
    }

    // 印出整條區塊鏈
    public void printChain() {
        System.out.println("\n================ 區塊鏈內容 ================");
        for (Block block : chain) {
            block.printBlock();
        }
        System.out.println("===========================================");
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

    // 設定挖礦難度
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}