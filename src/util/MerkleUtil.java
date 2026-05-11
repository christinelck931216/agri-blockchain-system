package util; // 宣告此類別位於 util 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來建立可變動清單
import java.util.List; // 匯入 List 介面

import model.TransactionRecord; // 匯入 TransactionRecord，因為 Merkle Root 是根據交易紀錄計算

public class MerkleUtil { // 宣告 MerkleUtil 工具類別

    // 根據交易紀錄清單計算 Merkle Root
    public static String getMerkleRoot(List<TransactionRecord> transactions) {

        // 如果交易清單為 null 或沒有任何交易，直接回傳空字串
        if (transactions == null || transactions.isEmpty()) {
            return "";
        }

        // 第一步：建立 Merkle Tree 的最底層，也就是葉節點
        // 每一筆交易紀錄會先轉成固定字串，再進行 SHA-256
        List<String> currentLayer = new ArrayList<>();

        for (TransactionRecord record : transactions) {
            // 將交易紀錄轉成固定格式字串，然後計算 SHA-256
            String transactionHash = HashUtil.applySHA256(record.toRecordString());

            // 將交易 Hash 加入目前這一層
            currentLayer.add(transactionHash);
        }

        // 第二步：只要目前這一層還有超過 1 個節點，就持續往上合併
        while (currentLayer.size() > 1) {

            // 建立下一層節點清單
            List<String> nextLayer = new ArrayList<>();

            // 每兩個節點合併一次
            for (int i = 0; i < currentLayer.size(); i += 2) {

                // 取得左邊節點
                String left = currentLayer.get(i);

                // 取得右邊節點
                // 如果節點數量是奇數，最後一個節點就複製自己
                String right;
                if (i + 1 < currentLayer.size()) {
                    right = currentLayer.get(i + 1);
                } else {
                    right = left;
                }

                // 將左右兩個 Hash 串接，再做一次 SHA-256
                // 形成上一層的父節點
                String parentHash = HashUtil.applySHA256(left + right);

                // 將父節點加入下一層
                nextLayer.add(parentHash);
            }

            // 將下一層變成目前層，繼續往上合併
            currentLayer = nextLayer;
        }

        // 當最後只剩下一個節點時，該節點就是 Merkle Root
        return currentLayer.get(0);
    }
}