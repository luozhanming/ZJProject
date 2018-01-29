package com.cndatacom.zjproject.entry;

/**
 * Created by cdc4512 on 2018/1/4.
 */

public class UserInfoEntry{


   // private String age;
  //  private String createDate;
    private String fullName;
    private String loginType;
    private String logonId;
    private String mobile;
    private String orgId;
    private String parentIds;
    private String parentNames;
    private String parentTypes;
    private String password;
    private String showOrder;
    private String title;
    private String userId;
    private String userName;
    private String photo;
    private SSOInfo infoOut;

//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
//
//    public String getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(String createDate) {
//        this.createDate = createDate;
//    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLogonId() {
        return logonId;
    }

    public void setLogonId(String logonId) {
        this.logonId = logonId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getParentNames() {
        return parentNames;
    }

    public void setParentNames(String parentNames) {
        this.parentNames = parentNames;
    }

    public String getParentTypes() {
        return parentTypes;
    }

    public void setParentTypes(String parentTypes) {
        this.parentTypes = parentTypes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public SSOInfo getInfoOut() {
        return infoOut;
    }

    public void setInfoOut(SSOInfo infoOut) {
        this.infoOut = infoOut;
    }

    public class SSOInfo{
        private String m_LoginID;
        private String m_OUID;
        private String m_ParentPID;
        private String m_SSOTokenKey;
        private String m_UserID;
        private String m_UserName;

        public String getM_LoginID() {
            return m_LoginID;
        }

        public void setM_LoginID(String m_LoginID) {
            this.m_LoginID = m_LoginID;
        }

        public String getM_OUID() {
            return m_OUID;
        }

        public void setM_OUID(String m_OUID) {
            this.m_OUID = m_OUID;
        }

        public String getM_ParentPID() {
            return m_ParentPID;
        }

        public void setM_ParentPID(String m_ParentPID) {
            this.m_ParentPID = m_ParentPID;
        }

        public String getM_SSOTokenKey() {
            return m_SSOTokenKey;
        }

        public void setM_SSOTokenKey(String m_SSOTokenKey) {
            this.m_SSOTokenKey = m_SSOTokenKey;
        }

        public String getM_UserID() {
            return m_UserID;
        }

        public void setM_UserID(String m_UserID) {
            this.m_UserID = m_UserID;
        }

        public String getM_UserName() {
            return m_UserName;
        }

        public void setM_UserName(String m_UserName) {
            this.m_UserName = m_UserName;
        }
    }

}
