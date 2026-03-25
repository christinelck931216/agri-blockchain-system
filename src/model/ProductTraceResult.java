package model; // 放在 model 套件

import java.util.ArrayList; // 匯入 ArrayList
import java.util.List; // 匯入 List

/*
 * ProductTraceResult：
 * 👉 前端 detail 頁「整包資料」
 */
public class ProductTraceResult {

    private ProductInfo productInfo; // 產品基本資料
    private boolean valid; // 是否驗證成功
    private int blockNumber; // 區塊編號
    private String blockHash; // 區塊 Hash
    private String chainTime; // 上鏈時間
    private List<TraceStep> timeline; // 時間軸

    public ProductTraceResult() { // 建構子
        this.timeline = new ArrayList<>(); // 初始化時間軸
    }

    public void setProductInfo(ProductInfo productInfo) { this.productInfo = productInfo; } // 設定基本資料
    public void setValid(boolean valid) { this.valid = valid; } // 設定驗證結果
    public void setBlockNumber(int blockNumber) { this.blockNumber = blockNumber; } // 設定區塊編號
    public void setBlockHash(String blockHash) { this.blockHash = blockHash; } // 設定 hash
    public void setChainTime(String chainTime) { this.chainTime = chainTime; } // 設定時間
    public void setTimeline(List<TraceStep> timeline) { this.timeline = timeline; } // 設定 timeline

    public ProductInfo getProductInfo() { return productInfo; } // 取得基本資料
    public boolean isValid() { return valid; } // 取得驗證結果
    public int getBlockNumber() { return blockNumber; } // 取得區塊編號
    public String getBlockHash() { return blockHash; } // 取得 hash
    public String getChainTime() { return chainTime; } // 取得時間
    public List<TraceStep> getTimeline() { return timeline; } // 取得時間軸
    @Override // 覆寫 toString 方法
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