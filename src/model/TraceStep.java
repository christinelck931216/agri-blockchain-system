package model; // 宣告此類別位於 model 套件中

/*
 * TraceStep：
 * 用來表示供應鏈時間軸中的其中一個步驟。
 * 例如：播種、施肥、採收、包裝、運輸、上架。
 */
public class TraceStep {

    private String time; // 此步驟發生的時間
    private String actionType; // 動作類型，例如 採收、包裝、運輸
    private String description; // 此步驟的詳細描述
    private String location; // 此步驟發生的地點
    private String operatorName; // 操作者名稱
    private String role; // 操作者角色，例如 農民、包裝商、運輸商

    // 建構子：建立 TraceStep 物件時執行
    public TraceStep(String time, String actionType, String description,
                     String location, String operatorName, String role) {
        this.time = time; // 設定時間
        this.actionType = actionType; // 設定動作類型
        this.description = description; // 設定描述
        this.location = location; // 設定地點
        this.operatorName = operatorName; // 設定操作者名稱
        this.role = role; // 設定操作者角色
    }

    // 取得時間
    public String getTime() {
        return time;
    }

    // 取得動作類型
    public String getActionType() {
        return actionType;
    }

    // 取得描述
    public String getDescription() {
        return description;
    }

    // 取得地點
    public String getLocation() {
        return location;
    }

    // 取得操作者名稱
    public String getOperatorName() {
        return operatorName;
    }

    // 取得操作者角色
    public String getRole() {
        return role;
    }

    // 覆寫 toString 方法，方便測試時印出時間軸節點資料
    @Override
    public String toString() {
        return "\n---\n時間: " + time +
               "\n動作: " + actionType +
               "\n描述: " + description +
               "\n地點: " + location +
               "\n操作者: " + operatorName +
               "\n角色: " + role;
    }
}