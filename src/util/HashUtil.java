package util; // 代表這個類別放在 util 套件中

import java.nio.charset.StandardCharsets; // 匯入標準字元編碼 UTF-8
import java.security.MessageDigest; // 匯入 MessageDigest，用來做 SHA-256

public class HashUtil { // 宣告 HashUtil 工具類別

    // 靜態方法：傳入字串後回傳 SHA-256 結果
    public static String applySHA256(String input) {
        try {
            // 建立 SHA-256 演算法物件
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 將輸入字串用 UTF-8 編碼轉成位元組，並進行雜湊運算
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 用來組合最後的十六進位字串
            StringBuilder hexString = new StringBuilder();

            // 把每個 byte 轉成兩位數的十六進位
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // 先轉成十六進位字串
                if (hex.length() == 1) {
                    hexString.append('0'); // 如果只有一位，前面補 0
                }
                hexString.append(hex); // 加入結果
            }

            return hexString.toString(); // 回傳最終 hash

        } catch (Exception e) {
            throw new RuntimeException("SHA-256 計算失敗：" + e.getMessage());
        }
    }
}