package com.example.yinian.menkou.yn_shipingtonghua.beans;

import java.util.List;

public class JiGouBean {


    /**
     * success : true
     * result : {"page":1,"rows":1000,"totalRecord":6,"data":[{"id":20,"orgName":"测试企业","orgType":2,"contactPhone":"","contactName":"d","province":"广东省/广州市/天河区","city":"","area":"440000,440100,440106","address":"444号","saleUser":"","createTime":1606388566000,"status":1,"loginName":"test23","levelCode":"","visitNotice":"","coverImg":"","creditCode":"k555","showStatus":1,"userName":"test23","userPwd":"123456a"},{"id":19,"orgName":"湖北秭归颐年医养院","orgType":2,"contactPhone":"18888888888","contactName":"李四","province":"","city":"","area":"","address":"","saleUser":"","createTime":1598511867000,"status":1,"loginName":"zhigui","parentId":0,"levelCode":"19"},{"id":18,"orgName":"湖北武穴颐年医养院","orgType":2,"contactPhone":"13642730363","contactName":"李四","province":"","city":"","area":"","address":"","saleUser":"","createTime":1598511767000,"status":1,"loginName":" wuxue","parentId":0,"levelCode":"18"},{"id":13,"orgName":"广州颐年天河养老院","orgType":3,"contactPhone":"18888888999","contactName":"刘院长","province":"","city":"","area":"","address":"天河颐年养老院","saleUser":"","saleTime":"2020-07-07 00:00:00","createTime":1595557342000,"status":1,"loginName":"yinian-tianhe","parentId":0,"levelCode":"13","userName":""},{"id":8,"orgName":"广州颐年石溪养老院","orgType":2,"contactPhone":"1999999999","contactName":"张杰","province":"","city":"","area":"","address":"","saleUser":"","saleTime":"2020-07-15 00:00:00","createTime":1594773221000,"status":1,"loginName":"1999999999","parentId":0,"levelCode":"8"},{"id":2,"orgName":"广州颐年海珠养老院","orgType":2,"contactPhone":"138888888","contactName":"王五","province":"广东省/广州市/海珠区/赤岗西路","city":"广州市","area":"440000,440100,440105,820012","address":"赤岗35-37号","saleUser":"","createTime":1591000932000,"status":1,"loginName":"yinian","parentId":0,"levelCode":"2","visitNotice":"拜访须知、、、、、","coverImg":"https://images.jiahubao.net/group1/M00/00/29/rBoolV2RVPqAEvSPAASjSrtRVrY630.png","latitude":"23.092733","longitude":"113.326023","creditCode":"hgfhf5566565","showStatus":1,"userName":"yinian","userPwd":"123456"}],"pageCount":6,"totalPage":1,"prePage":1,"nextPage":1,"firstPage":true,"lastPage":true}
     * code : 1
     */

    private boolean success;
    private ResultDTO result;
    private int code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class ResultDTO {
        /**
         * page : 1
         * rows : 1000
         * totalRecord : 6
         * data : [{"id":20,"orgName":"测试企业","orgType":2,"contactPhone":"","contactName":"d","province":"广东省/广州市/天河区","city":"","area":"440000,440100,440106","address":"444号","saleUser":"","createTime":1606388566000,"status":1,"loginName":"test23","levelCode":"","visitNotice":"","coverImg":"","creditCode":"k555","showStatus":1,"userName":"test23","userPwd":"123456a"},{"id":19,"orgName":"湖北秭归颐年医养院","orgType":2,"contactPhone":"18888888888","contactName":"李四","province":"","city":"","area":"","address":"","saleUser":"","createTime":1598511867000,"status":1,"loginName":"zhigui","parentId":0,"levelCode":"19"},{"id":18,"orgName":"湖北武穴颐年医养院","orgType":2,"contactPhone":"13642730363","contactName":"李四","province":"","city":"","area":"","address":"","saleUser":"","createTime":1598511767000,"status":1,"loginName":" wuxue","parentId":0,"levelCode":"18"},{"id":13,"orgName":"广州颐年天河养老院","orgType":3,"contactPhone":"18888888999","contactName":"刘院长","province":"","city":"","area":"","address":"天河颐年养老院","saleUser":"","saleTime":"2020-07-07 00:00:00","createTime":1595557342000,"status":1,"loginName":"yinian-tianhe","parentId":0,"levelCode":"13","userName":""},{"id":8,"orgName":"广州颐年石溪养老院","orgType":2,"contactPhone":"1999999999","contactName":"张杰","province":"","city":"","area":"","address":"","saleUser":"","saleTime":"2020-07-15 00:00:00","createTime":1594773221000,"status":1,"loginName":"1999999999","parentId":0,"levelCode":"8"},{"id":2,"orgName":"广州颐年海珠养老院","orgType":2,"contactPhone":"138888888","contactName":"王五","province":"广东省/广州市/海珠区/赤岗西路","city":"广州市","area":"440000,440100,440105,820012","address":"赤岗35-37号","saleUser":"","createTime":1591000932000,"status":1,"loginName":"yinian","parentId":0,"levelCode":"2","visitNotice":"拜访须知、、、、、","coverImg":"https://images.jiahubao.net/group1/M00/00/29/rBoolV2RVPqAEvSPAASjSrtRVrY630.png","latitude":"23.092733","longitude":"113.326023","creditCode":"hgfhf5566565","showStatus":1,"userName":"yinian","userPwd":"123456"}]
         * pageCount : 6
         * totalPage : 1
         * prePage : 1
         * nextPage : 1
         * firstPage : true
         * lastPage : true
         */

        private int page;
        private int rows;
        private int totalRecord;
        private int pageCount;
        private int totalPage;
        private int prePage;
        private int nextPage;
        private boolean firstPage;
        private boolean lastPage;
        private List<DataDTO> data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getPrePage() {
            return prePage;
        }

        public void setPrePage(int prePage) {
            this.prePage = prePage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public static class DataDTO {
            /**
             * id : 20
             * orgName : 测试企业
             * orgType : 2
             * contactPhone :
             * contactName : d
             * province : 广东省/广州市/天河区
             * city :
             * area : 440000,440100,440106
             * address : 444号
             * saleUser :
             * createTime : 1606388566000
             * status : 1
             * loginName : test23
             * levelCode :
             * visitNotice :
             * coverImg :
             * creditCode : k555
             * showStatus : 1
             * userName : test23
             * userPwd : 123456a
             * parentId : 0
             * saleTime : 2020-07-07 00:00:00
             * latitude : 23.092733
             * longitude : 113.326023
             */

            private int id;
            private String orgName;
            private int orgType;
            private String contactPhone;
            private String contactName;
            private String province;
            private String city;
            private String area;
            private String address;
            private String saleUser;
            private long createTime;
            private int status;
            private String loginName;
            private String levelCode;
            private String visitNotice;
            private String coverImg;
            private String creditCode;
            private int showStatus;
            private String userName;
            private String userPwd;
            private int parentId;
            private String saleTime;
            private String latitude;
            private String longitude;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public int getOrgType() {
                return orgType;
            }

            public void setOrgType(int orgType) {
                this.orgType = orgType;
            }

            public String getContactPhone() {
                return contactPhone;
            }

            public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getSaleUser() {
                return saleUser;
            }

            public void setSaleUser(String saleUser) {
                this.saleUser = saleUser;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getLevelCode() {
                return levelCode;
            }

            public void setLevelCode(String levelCode) {
                this.levelCode = levelCode;
            }

            public String getVisitNotice() {
                return visitNotice;
            }

            public void setVisitNotice(String visitNotice) {
                this.visitNotice = visitNotice;
            }

            public String getCoverImg() {
                return coverImg;
            }

            public void setCoverImg(String coverImg) {
                this.coverImg = coverImg;
            }

            public String getCreditCode() {
                return creditCode;
            }

            public void setCreditCode(String creditCode) {
                this.creditCode = creditCode;
            }

            public int getShowStatus() {
                return showStatus;
            }

            public void setShowStatus(int showStatus) {
                this.showStatus = showStatus;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPwd() {
                return userPwd;
            }

            public void setUserPwd(String userPwd) {
                this.userPwd = userPwd;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getSaleTime() {
                return saleTime;
            }

            public void setSaleTime(String saleTime) {
                this.saleTime = saleTime;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }
        }
    }
}
