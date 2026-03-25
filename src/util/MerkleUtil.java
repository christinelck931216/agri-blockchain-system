package util; // 代表這個類別放在 util 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來建立可變動清單
import java.util.List; // 匯入 List 介面
import model.TransactionRecord; // 匯入 TransactionRecord，因為 Merkle Root 會用到交易紀錄

public class MerkleUtil { // 宣告 MerkleUtil 工具類別

    // 根據交易紀錄清單計算 Merkle Root
    public static String getMerkleRoot(List<TransactionRecord> transactions) {

        // 如果交易清單是空的，直接回傳空字串
        if (transactions == null || transactions.isEmpty()) {
            return "";
        }

        // 第一步：先把每一筆交易紀錄轉成 hash，作為樹的最底層葉節點
        List<String> currentLayer = new ArrayList<>();

        for (TransactionRecord record : transactions) {
            // 將每筆交易紀錄轉成固定格式字串，再做 SHA-256
            String transactionHash = HashUtil.applySHA256(record.toRecordString());
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
                // 如果剛好是奇數個，最後一個就複製自己
                String right;
                if (i + 1 < currentLayer.size()) {
                    right = currentLayer.get(i + 1);
                } else {
                    right = left;
                }

                // 將左右兩個 hash 串接後再做一次 SHA-256，形成上一層節點
                String parentHash = HashUtil.applySHA256(left + right);

                // 加入下一層
                nextLayer.add(parentHash);
            }

            // 更新目前層，繼續往上做
            currentLayer = nextLayer;
        }

        // 最後只剩下一個節點時，那個值就是 Merkle Root
        return currentLayer.get(0);
    }
}