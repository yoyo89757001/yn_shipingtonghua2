package com.example.yinian.menkou.yn_shipingtonghua.beans;

public class LoginBean {


    /**
     * success : true
     * result : {"nurseName":"护士1","headImg":"http://118.190.246.182/group1/M00/00/00/rB-zyV7tg3yALv9wAABoawgSEOE774.jpg","phone":"18888888888","nurseCode":"123456","token":"eyJhbGciOiJIUzI1NiJ9.eyJzZXNzaW9uSWQiOiI2N2Q3MTlkMzRiNDU0OGI4ODQzMDFlZDY1OWU2MTM3OSIsInVzZXJOYW1lIjoiMTg4ODg4ODg4ODgiLCJleHAiOjE1OTI3NTM0NzMsInVzZXJJZCI6IjEwIn0.EoZ_7B-05S35S3lXcr-gxjXyL0gfLqSfu4Cxv-LBAsk"}
     * code : 1
     */

    private boolean success;
    private ResultBean result;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * nurseName : 护士1
         * headImg : http://118.190.246.182/group1/M00/00/00/rB-zyV7tg3yALv9wAABoawgSEOE774.jpg
         * phone : 18888888888
         * nurseCode : 123456
         * token : eyJhbGciOiJIUzI1NiJ9.eyJzZXNzaW9uSWQiOiI2N2Q3MTlkMzRiNDU0OGI4ODQzMDFlZDY1OWU2MTM3OSIsInVzZXJOYW1lIjoiMTg4ODg4ODg4ODgiLCJleHAiOjE1OTI3NTM0NzMsInVzZXJJZCI6IjEwIn0.EoZ_7B-05S35S3lXcr-gxjXyL0gfLqSfu4Cxv-LBAsk
         */

        private String nurseName;
        private String headImg;
        private String phone;
        private String nurseCode;
        private String token;

        public String getNurseName() {
            return nurseName;
        }

        public void setNurseName(String nurseName) {
            this.nurseName = nurseName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNurseCode() {
            return nurseCode;
        }

        public void setNurseCode(String nurseCode) {
            this.nurseCode = nurseCode;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
