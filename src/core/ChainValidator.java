package core; // 宣告此類別位於 core 套件中

import java.util.List; // 匯入 List，用來操作區塊鏈中的區塊清單

import model.Block; // 匯入 Block 類別
import model.TransactionRecord; // 匯入 TransactionRecord 類別
import util.HashUtil; // 匯入 HashUtil，用來測試交易資料是否能正常進行 SHA-256 運算
import util.MerkleUtil; // 匯入 MerkleUtil，用來重新計算 Merkle Root

public class ChainValidator { // 宣告 ChainValidator 類別，負責驗證區塊鏈是否合法

    // 驗證整條區塊鏈是否合法
    public static boolean isChainValid(Blockchain blockchain) {

        List<Block> chain = blockchain.getChain(); // 從 Blockchain 物件取得整條鏈

        // 如果區塊鏈為 null，或區塊鏈中沒有任何區塊，代表此鏈不合法
        if (chain == null || chain.isEmpty()) {

            // 注意：
            // 後端串接版本不能輸出文字
            // 否則 Node.js 的 JSON.parse(stdout) 會失敗

            return false;
        }

        // 從第 0 個區塊開始逐一驗證到最後一個區塊
        for (int i = 0; i < chain.size(); i++) {

            Block currentBlock = chain.get(i); // 取得目前正在驗證的區塊

            // 1. 驗證目前區塊的 Merkle Root 是否正確
            // 重新根據目前區塊內的交易資料計算 Merkle Root
            String recalculatedMerkleRoot =
                    MerkleUtil.getMerkleRoot(
                            currentBlock.getTransactions()
                    );

            // 如果重新計算的 Merkle Root 和區塊內原本儲存的不一致，代表交易資料可能被竄改
            if (!currentBlock.getMerkleRoot().equals(recalculatedMerkleRoot)) {

                return false;
            }

            // 2. 驗證目前區塊的 Hash 是否正確
            // 根據區塊目前的 index、timestamp、previousHash、merkleRoot、nonce 重新計算 Hash
            String recalculatedHash =
                    currentBlock.calculateHash();

            // 如果重新計算的 Hash 和原本儲存的 Hash 不一致，代表區塊資料可能被修改
            if (!currentBlock.getHash().equals(recalculatedHash)) {

                return false;
            }

            // 3. 驗證 previousHash 是否正確連接
            // 創世區塊是第 0 個區塊，沒有前一個區塊，所以從 i > 0 才開始檢查
            if (i > 0) {

                Block previousBlock = chain.get(i - 1); // 取得前一個區塊

                // 檢查目前區塊的 previousHash 是否等於前一個區塊的 Hash
                if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {

                    return false;
                }
            }

            // 4. 驗證目前區塊內每一筆交易資料是否合法
            if (!areTransactionsValid(
                    currentBlock.getTransactions(),
                    i
            )) {

                return false;
            }
        }

        // 如果所有檢查都通過，代表整條區塊鏈有效
        return true;
    }

    // 檢查某個區塊內的所有交易紀錄是否合法
    private static boolean areTransactionsValid(
            List<TransactionRecord> transactions,
            int blockIndex
    ) {

        // 如果交易清單是 null，代表交易資料不合法
        if (transactions == null) {

            return false;
        }

        // 一筆一筆檢查 TransactionRecord
        for (int i = 0; i < transactions.size(); i++) {

            TransactionRecord record = transactions.get(i); // 取得目前交易紀錄

            // 檢查重要欄位是否為 null 或空字串
            // 如果重要欄位缺失，代表此交易資料不完整
            if (isNullOrEmpty(record.getRecordId()) ||
                isNullOrEmpty(record.getProductId()) ||
                isNullOrEmpty(record.getProductName()) ||
                isNullOrEmpty(record.getBatchId()) ||
                isNullOrEmpty(record.getActionType()) ||
                isNullOrEmpty(record.getDescription()) ||
                isNullOrEmpty(record.getLocation()) ||
                record.getOperator() == null ||
                isNullOrEmpty(record.getTimestamp())) {

                return false;
            }

            // 額外檢查：確認這筆交易資料可以正常轉成字串並計算 SHA-256
            try {

                HashUtil.applySHA256(
                        record.toRecordString()
                );

            } catch (Exception e) {

                // 如果計算 Hash 時發生錯誤，代表交易資料有問題
                return false;
            }
        }

        // 所有交易資料都檢查通過
        return true;
    }

    // 判斷字串是否為 null 或空字串
    private static boolean isNullOrEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }
}