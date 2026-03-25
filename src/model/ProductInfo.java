package model; // 放在 model 套件

/*
 * ProductInfo：
 * 存「產品基本資料」
 * 👉 給前端 detail 頁上半部用
 */
public class ProductInfo { // 宣告類別

    private String productId; // 產品編號（內部用）
    private String traceCode; // 追溯碼（給前端）
    private String productName; // 產品名稱
    private String category; // 類別
    private String origin; // 產地
    private String packageDate; // 包裝日期（用 String，配合你現在系統）
    private String farmName; // 農場名稱
    private String producerName; // 生產者
    private String contact; // 聯絡電話

    public ProductInfo(String productId, String traceCode, String productName,
                       String category, String origin, String packageDate,
                       String farmName, String producerName, String contact) { // 建構子
        this.productId = productId; // 設定 productId
        this.traceCode = traceCode; // 設定 traceCode
        this.productName = productName; // 設定產品名稱
        this.category = category; // 設定類別
        this.origin = origin; // 設定產地
        this.packageDate = packageDate; // 設定包裝日期
        this.farmName = farmName; // 設定農場名稱
        this.producerName = producerName; // 設定生產者
        this.contact = contact; // 設定聯絡電話
    }

    public String getProductId() { return productId; } // 回傳 productId
    public String getTraceCode() { return traceCode; } // 回傳 traceCode
    public String getProductName() { return productName; } // 回傳名稱
    public String getCategory() { return category; } // 回傳類別
    public String getOrigin() { return origin; } // 回傳產地
    public String getPackageDate() { return packageDate; } // 回傳包裝日期
    public String getFarmName() { return farmName; } // 回傳農場
    public String getProducerName() { return producerName; } // 回傳生產者
    public String getContact() { return contact; } // 回傳電話

    @Override
    public String toString() {
        return "ProductInfo{" +
                "\n 產品名稱='" + productName + '\'' +
                ",\n 追溯碼='" + traceCode + '\'' +
                ",\n 類別='" + category + '\'' +
                ",\n 產地='" + origin + '\'' +
                ",\n 包裝日期='" + packageDate + '\'' +
                ",\n 農場='" + farmName + '\'' +
                ",\n 生產者='" + producerName + '\'' +
                ",\n 聯絡='" + contact + '\'' +
                "\n}";
    }
}