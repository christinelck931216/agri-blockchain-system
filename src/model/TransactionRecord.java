package model; // 宣告此類別位於 model 套件中

import java.time.LocalDateTime; // 匯入 LocalDateTime，用來自動記錄交易建立時間

public class TransactionRecord { // 宣告 TransactionRecord 類別，代表一筆農產品履歷交易紀錄

    private String recordId; // 紀錄編號，例如 T001
    private String productId; // 農產品編號或追溯碼，例如 P001 或 A010
    private String productName; // 農產品名稱，例如 高麗菜、芒果
    private String batchId; // 批次編號，例如 BATCH20260325
    private String actionType; // 事件類型，例如 播種、施肥、採收、運送、上架、資料上鏈
    private String description; // 此事件的詳細描述
    private String location; // 事件發生地點
    private Participant operator; // 操作者，也就是建立這筆紀錄的供應鏈參與者
    private String timestamp; // 交易紀錄建立時間
    private boolean verified; // 是否已驗證，例如檢驗是否通過

    // 原本建構子：建立交易紀錄時，自動使用目前時間
    // 適合第一次建立新資料時使用
    public TransactionRecord(String recordId, String productId, String productName, String batchId,
                             String actionType, String description, String location,
                             Participant operator, boolean verified) {
        this.recordId = recordId; // 設定紀錄編號
        this.productId = productId; // 設定產品編號或追溯碼
        this.productName = productName; // 設定產品名稱
        this.batchId = batchId; // 設定批次編號
        this.actionType = actionType; // 設定事件類型
        this.description = description; // 設定事件描述
        this.location = location; // 設定事件地點
        this.operator = operator; // 設定操作者
        this.timestamp = LocalDateTime.now().toString(); // 自動記錄目前時間
        this.verified = verified; // 設定是否驗證通過
    }

    // 新增建構子：允許外部傳入固定 timestamp
    // 這個建構子很重要，因為之後要重新計算 Hash 做比對時，timestamp 必須和第一次上鏈時完全一樣
    public TransactionRecord(String recordId, String productId, String productName, String batchId,
                             String actionType, String description, String location,
                             Participant operator, boolean verified, String timestamp) {
        this.recordId = recordId; // 設定紀錄編號
        this.productId = productId; // 設定產品編號或追溯碼
        this.productName = productName; // 設定產品名稱
        this.batchId = batchId; // 設定批次編號
        this.actionType = actionType; // 設定事件類型
        this.description = description; // 設定事件描述
        this.location = location; // 設定事件地點
        this.operator = operator; // 設定操作者
        this.timestamp = timestamp; // 使用外部傳入的固定時間
        this.verified = verified; // 設定是否驗證通過
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Participant getOperator() {
        return operator;
    }

    public void setOperator(Participant operator) {
        this.operator = operator;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    // 將交易紀錄轉成固定格式字串
    // Merkle Root 會依據這個字串計算
    // 只要產品名稱、地點、操作者、時間等任何欄位改變，最後算出的 Hash 就會不同
    public String toRecordString() {
        return recordId + "|" +
               productId + "|" +
               productName + "|" +
               batchId + "|" +
               actionType + "|" +
               description + "|" +
               location + "|" +
               operator.getParticipantId() + "|" +
               operator.getParticipantName() + "|" +
               operator.getRole() + "|" +
               timestamp + "|" +
               verified;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "recordId='" + recordId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", batchId='" + batchId + '\'' +
                ", actionType='" + actionType + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", operator=" + operator +
                ", timestamp='" + timestamp + '\'' +
                ", verified=" + verified +
                '}';
    }
}