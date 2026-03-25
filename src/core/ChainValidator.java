package core; // 代表這個類別放在 core 套件中

import java.util.List; // 匯入 List 介面

import model.Block; // 匯入 Block 類別
import model.TransactionRecord; // 匯入 TransactionRecord 類別
import util.HashUtil; // 匯入 HashUtil，用來重新計算交易 hash
import util.MerkleUtil; // 匯入 MerkleUtil，用來重新計算 Merkle Root

public class ChainValidator { // 宣告 ChainValidator 類別，專門負責驗證鏈

    // 驗證整條區塊鏈是否合法
    public static boolean isChainValid(Blockchain blockchain) {
        List<Block> chain = blockchain.getChain(); // 取得整條鏈

        // 如果鏈不存在或完全沒有區塊，直接視為無效
        if (chain == null || chain.isEmpty()) {
            System.out.println("區塊鏈不存在或為空。");
            return false;
        }

        // 從第 0 個到最後一個區塊逐一檢查
        for (int i = 0; i < chain.size(); i++) {
            Block currentBlock = chain.get(i); // 目前正在檢查的區塊

            // 1. 驗證目前區塊的 Merkle Root 是否正確
            String recalculatedMerkleRoot = MerkleUtil.getMerkleRoot(currentBlock.getTransactions());
            if (!currentBlock.getMerkleRoot().equals(recalculatedMerkleRoot)) {
                System.out.println("第 " + i + " 個區塊驗證失敗：Merkle Root 不一致。");
                return false;
            }

            // 2. 驗證目前區塊的 hash 是否正確
            String recalculatedHash = currentBlock.calculateHash();
            if (!currentBlock.getHash().equals(recalculatedHash)) {
                System.out.println("第 " + i + " 個區塊驗證失敗：Hash 不一致。");
                return false;
            }

            // 3. 如果不是創世區塊，還要驗證 previousHash 是否正確連接
            if (i > 0) {
                Block previousBlock = chain.get(i - 1);

                if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                    System.out.println("第 " + i + " 個區塊驗證失敗：previousHash 與前一區塊 Hash 不一致。");
                    return false;
                }
            }

            // 4. 額外檢查：區塊內交易紀錄是否有空值欄位
            if (!areTransactionsValid(currentBlock.getTransactions(), i)) {
                return false;
            }
        }

        System.out.println("區塊鏈驗證成功：整條鏈合法。");
        return true;
    }

    // 檢查某個區塊內的交易紀錄是否合法
    private static boolean areTransactionsValid(List<TransactionRecord> transactions, int blockIndex) {

        // 如果交易清單是 null，視為不合法
        if (transactions == null) {
            System.out.println("第 " + blockIndex + " 個區塊驗證失敗：交易清單為 null。");
            return false;
        }

        // 一筆一筆檢查交易紀錄
        for (int i = 0; i < transactions.size(); i++) {
            TransactionRecord record = transactions.get(i);

            // 檢查重要欄位是否為空
            if (isNullOrEmpty(record.getRecordId()) ||
                isNullOrEmpty(record.getProductId()) ||
                isNullOrEmpty(record.getProductName()) ||
                isNullOrEmpty(record.getBatchId()) ||
                isNullOrEmpty(record.getActionType()) ||
                isNullOrEmpty(record.getDescription()) ||
                isNullOrEmpty(record.getLocation()) ||
                record.getOperator() == null ||
                isNullOrEmpty(record.getTimestamp())) {

                System.out.println("第 " + blockIndex + " 個區塊中的第 " + i + " 筆交易驗證失敗：存在空欄位。");
                return false;
            }

            // 額外檢查：交易紀錄字串是否能正常轉成 hash
            try {
                HashUtil.applySHA256(record.toRecordString());
            } catch (Exception e) {
                System.out.println("第 " + blockIndex + " 個區塊中的第 " + i + " 筆交易驗證失敗：交易 hash 計算錯誤。");
                return false;
            }
        }

        return true;
    }

    // 判斷字串是否為 null 或空字串
    private static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}