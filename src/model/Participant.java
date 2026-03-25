package model; // 代表這個類別放在 model 套件中

public class Participant { // 宣告 Participant 類別，代表供應鏈中的參與者

    private String participantId; // 參與者編號，例如 F001、R001
    private String participantName; // 參與者名稱，例如 王小明、台北超市
    private String role; // 角色，例如 農民、包裝商、運輸商、零售商
    private String contactInfo; // 聯絡資訊，例如電話或 Email
    private String location; // 所在地，例如 雲林、台中

    // 建構子：建立 Participant 物件時使用
    public Participant(String participantId, String participantName, String role, String contactInfo, String location) {
        this.participantId = participantId; // 設定參與者編號
        this.participantName = participantName; // 設定參與者名稱
        this.role = role; // 設定角色
        this.contactInfo = contactInfo; // 設定聯絡資訊
        this.location = location; // 設定所在地
    }

    // 取得參與者編號
    public String getParticipantId() {
        return participantId;
    }

    // 設定參與者編號
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    // 取得參與者名稱
    public String getParticipantName() {
        return participantName;
    }

    // 設定參與者名稱
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    // 取得角色
    public String getRole() {
        return role;
    }

    // 設定角色
    public void setRole(String role) {
        this.role = role;
    }

    // 取得聯絡資訊
    public String getContactInfo() {
        return contactInfo;
    }

    // 設定聯絡資訊
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // 取得所在地
    public String getLocation() {
        return location;
    }

    // 設定所在地
    public void setLocation(String location) {
        this.location = location;
    }

    // 覆寫 toString 方法，讓物件印出時更容易閱讀
    @Override
    public String toString() {
        return "Participant{" +
                "participantId='" + participantId + '\'' +
                ", participantName='" + participantName + '\'' +
                ", role='" + role + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}