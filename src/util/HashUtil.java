package util; // 宣告此類別位於 util 套件中

import java.nio.charset.StandardCharsets; // 匯入 UTF-8 編碼，確保中文資料能正確轉成 byte
import java.security.MessageDigest; // 匯入 MessageDigest，用來執行 SHA-256 雜湊運算

public class HashUtil { // 宣告 HashUtil 工具類別

    // 傳入一段字串，回傳該字串的 SHA-256 雜湊結果
    public static String applySHA256(String input) {
        try {
            // 建立 SHA-256 演算法物件
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 將輸入字串用 UTF-8 編碼轉成 byte 陣列，並進行 SHA-256 運算
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 用來組合最後的十六進位字串
            StringBuilder hexString = new StringBuilder();

            // 將每一個 byte 轉換成兩位數的十六進位字串
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // 將 byte 轉成十六進位字串

                // 如果轉出來只有一位數，前面補 0，確保格式一致
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex); // 將轉換後的十六進位字串加入結果
            }

            return hexString.toString(); // 回傳完整 SHA-256 Hash 字串

        } catch (Exception e) {
            // 如果 SHA-256 計算失敗，丟出 RuntimeException
            throw new RuntimeException("SHA-256 計算失敗：" + e.getMessage());
        }
    }
}