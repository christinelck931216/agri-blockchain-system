package model; // 放在 model 套件

/*
 * TraceStep：
 * 👉 給前端 timeline（時間軸）用
 */
public class TraceStep {

    private String time; // 時間（用 String）
    private String actionType; // 動作
    private String description; // 描述
    private String location; // 地點
    private String operatorName; // 操作者
    private String role; // 角色

    public TraceStep(String time, String actionType, String description,
                     String location, String operatorName, String role) { // 建構子
        this.time = time; // 設定時間
        this.actionType = actionType; // 設定動作
        this.description = description; // 設定描述
        this.location = location; // 設定地點
        this.operatorName = operatorName; // 設定操作者
        this.role = role; // 設定角色
    }

    public String getTime() { return time; } // 回傳時間
    public String getActionType() { return actionType; } // 回傳動作
    public String getDescription() { return description; } // 回傳描述
    public String getLocation() { return location; } // 回傳地點
    public String getOperatorName() { return operatorName; } // 回傳操作者
    public String getRole() { return role; } // 回傳角色
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