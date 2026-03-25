package model; // 代表這個類別放在 model 套件中

import java.time.LocalDateTime; // 匯入 LocalDateTime，用來記錄建立時間

public class TransactionRecord { // 宣告 TransactionRecord 類別，代表一筆農產品履歷紀錄

    private String recordId; // 紀錄編號，例如 T001
    private String productId; // 農產品編號，例如 P001
    private String productName; // 農產品名稱，例如 高麗菜
    private String batchId; // 批次編號，例如 BATCH20260325
    private String actionType; // 事件類型，例如 播種、施肥、採收、運送、上架
    private String description; // 詳細描述
    private String location; // 發生地點
    private Participant operator; // 操作者，也就是誰建立這筆紀錄
    private String timestamp; // 紀錄時間
    private boolean verified; // 是否已驗證，例如檢驗是否通過

    // 建構子：建立一筆交易紀錄時使用
    public TransactionRecord(String recordId, String productId, String productName, String batchId,
                             String actionType, String description, String location,
                             Participant operator, boolean verified) {
        this.recordId = recordId; // 設定紀錄編號
        this.productId = productId; // 設定產品編號
        this.productName = productName; // 設定產品名稱
        this.batchId = batchId; // 設定批次編號
        this.actionType = actionType; // 設定事件類型
        this.description = description; // 設定描述
        this.location = location; // 設定地點
        this.operator = operator; // 設定操作者
        this.timestamp = LocalDateTime.now().toString(); // 自動記錄建立時間
        this.verified = verified; // 設定是否已驗證
    }

    // 以下是各欄位的 getter / setter

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

    // 這個方法很重要：把交易紀錄轉成固定格式字串
    // 後面做 hash 或 Merkle Root 時會用到
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

    // 覆寫 toString 方法，方便印出查看
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