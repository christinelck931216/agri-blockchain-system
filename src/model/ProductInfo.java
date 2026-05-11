package model; // 宣告此類別位於 model 套件中

/*
 * ProductInfo：
 * 用來儲存農產品基本資料。
 * 主要用途是提供前端產品詳細頁上半部顯示。
 */
public class ProductInfo { // 宣告 ProductInfo 類別

    private String productId; // 產品編號，系統內部使用
    private String traceCode; // 追溯碼，提供前端或消費者查詢使用
    private String productName; // 產品名稱，例如 高麗菜、芒果
    private String category; // 產品類別，例如 水果、蔬菜
    private String origin; // 產地，例如 屏東、雲林
    private String packageDate; // 包裝日期，先用 String 配合目前系統格式
    private String farmName; // 農場名稱
    private String producerName; // 生產者名稱
    private String contact; // 聯絡電話或聯絡方式

    // 建構子：建立 ProductInfo 物件時執行
    public ProductInfo(String productId, String traceCode, String productName,
                       String category, String origin, String packageDate,
                       String farmName, String producerName, String contact) {
        this.productId = productId; // 設定產品編號
        this.traceCode = traceCode; // 設定追溯碼
        this.productName = productName; // 設定產品名稱
        this.category = category; // 設定產品類別
        this.origin = origin; // 設定產地
        this.packageDate = packageDate; // 設定包裝日期
        this.farmName = farmName; // 設定農場名稱
        this.producerName = producerName; // 設定生產者名稱
        this.contact = contact; // 設定聯絡方式
    }

    // 取得產品編號
    public String getProductId() {
        return productId;
    }

    // 取得追溯碼
    public String getTraceCode() {
        return traceCode;
    }

    // 取得產品名稱
    public String getProductName() {
        return productName;
    }

    // 取得產品類別
    public String getCategory() {
        return category;
    }

    // 取得產地
    public String getOrigin() {
        return origin;
    }

    // 取得包裝日期
    public String getPackageDate() {
        return packageDate;
    }

    // 取得農場名稱
    public String getFarmName() {
        return farmName;
    }

    // 取得生產者名稱
    public String getProducerName() {
        return producerName;
    }

    // 取得聯絡方式
    public String getContact() {
        return contact;
    }

    // 覆寫 toString 方法，方便測試時印出產品基本資料
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