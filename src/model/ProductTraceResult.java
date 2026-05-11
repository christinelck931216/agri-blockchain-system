package model; // 宣告此類別位於 model 套件中

import java.util.ArrayList; // 匯入 ArrayList，用來初始化時間軸清單
import java.util.List; // 匯入 List，用來儲存多個 TraceStep

/*
 * ProductTraceResult：
 * 用來封裝前端產品詳細頁需要的完整資料。
 * 包含產品基本資料、區塊鏈驗證結果、區塊資訊與供應鏈時間軸。
 */
public class ProductTraceResult {

    private ProductInfo productInfo; // 產品基本資料
    private boolean valid; // 區塊鏈驗證是否成功
    private int blockNumber; // 區塊編號
    private String blockHash; // 區塊 Hash
    private String chainTime; // 上鏈時間
    private List<TraceStep> timeline; // 供應鏈時間軸

    // 建構子：建立 ProductTraceResult 物件時執行
    public ProductTraceResult() {
        this.timeline = new ArrayList<>(); // 初始化時間軸，避免 null 錯誤
    }

    // 設定產品基本資料
    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    // 設定區塊鏈驗證結果
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    // 設定區塊編號
    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    // 設定區塊 Hash
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    // 設定上鏈時間
    public void setChainTime(String chainTime) {
        this.chainTime = chainTime;
    }

    // 設定供應鏈時間軸
    public void setTimeline(List<TraceStep> timeline) {
        this.timeline = timeline;
    }

    // 取得產品基本資料
    public ProductInfo getProductInfo() {
        return productInfo;
    }

    // 取得區塊鏈驗證結果
    public boolean isValid() {
        return valid;
    }

    // 取得區塊編號
    public int getBlockNumber() {
        return blockNumber;
    }

    // 取得區塊 Hash
    public String getBlockHash() {
        return blockHash;
    }

    // 取得上鏈時間
    public String getChainTime() {
        return chainTime;
    }

    // 取得供應鏈時間軸
    public List<TraceStep> getTimeline() {
        return timeline;
    }

    // 覆寫 toString 方法，方便測試時印出完整追溯結果
    @Override
    public String toString() {
        return "ProductTraceResult{" +
                "\n productInfo=" + productInfo +
                ",\n valid=" + valid +
                ",\n blockNumber=" + blockNumber +
                ",\n blockHash='" + blockHash + '\'' +
                ",\n chainTime='" + chainTime + '\'' +
                ",\n timeline=" + timeline +
                "\n}";
    }
}