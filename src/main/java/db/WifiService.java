package db;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class WifiService {
    static String apiKey = "424f576258716c61313039426b425777";
    static String dataType = "json";
    static String serviceName = "TbPublicWifiInfo";

    static String X_SWIFI_MGR_NO = "";
    static String X_SWIFI_WRDOFC = "";
    static String X_SWIFI_MAIN_NM = "";
    static String X_SWIFI_ADRES1 = "";
    static String X_SWIFI_ADRES2 = "";
    static String X_SWIFI_INSTL_FLOOR = "";
    static String X_SWIFI_INSTL_TY = "";
    static String X_SWIFI_INSTL_MBY = "";
    static String X_SWIFI_SVC_SE = "";
    static String X_SWIFI_CMCWR = "";
    static String X_SWIFI_CNSTC_YEAR = "";
    static String X_SWIFI_INOUT_DOOR = "";
    static String X_SWIFI_REMARS3 = "";
    static String LAT = "";
    static String LNT = "";
    static String WORK_DTTM = "";

    public static void main(String[] args) throws Exception {
        int start = 1;
        int end = 1000;
        int totalCount;

        do {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
            urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode(dataType, "UTF-8"));
            urlBuilder.append("/" + URLEncoder.encode(serviceName, "UTF-8"));
            urlBuilder.append("/" + Integer.toString(start));

            end = start + 999;
            urlBuilder.append("/" + Integer.toString(end));

            // 추가 내용
//        urlBuilder.append("/" + URLEncoder.encode("중랑구", "UTF-8"));

            // fetchDataFromApi로 url 넘기기
            String apiUrl = urlBuilder.toString();
            System.out.println(apiUrl);
            String jsonData = fetchDataFromApi(apiUrl);
            System.out.println(jsonData);


            // JSON 파싱(Gson 이용)

            Gson gson = new Gson();
            DataModel data = gson.fromJson(jsonData, DataModel.class);
            totalCount = data.tbPublicWifiInfo.getList_total_count();

            // 데이터 가져오는 부분 수정
            List<Row> rows = data.getRows();

            for (Row row : rows) {
            	Member member = new Member();
                Db db = new Db();
                X_SWIFI_MGR_NO = row.getX_SWIFI_MGR_NO();
                X_SWIFI_WRDOFC = row.getX_SWIFI_WRDOFC();
                X_SWIFI_MAIN_NM = row.getX_SWIFI_MAIN_NM();
                X_SWIFI_ADRES1 = row.getX_SWIFI_ADRES1();
                X_SWIFI_ADRES2 = row.getX_SWIFI_ADRES2();
                X_SWIFI_INSTL_FLOOR = row.getX_SWIFI_INSTL_FLOOR();
                X_SWIFI_INSTL_TY = row.getX_SWIFI_INSTL_TY();
                X_SWIFI_INSTL_MBY = row.getX_SWIFI_INSTL_MBY();
                X_SWIFI_SVC_SE = row.getX_SWIFI_SVC_SE();
                X_SWIFI_CMCWR = row.getX_SWIFI_CMCWR();
                X_SWIFI_CNSTC_YEAR = row.getX_SWIFI_CNSTC_YEAR();
                X_SWIFI_INOUT_DOOR = row.getX_SWIFI_INOUT_DOOR();
                X_SWIFI_REMARS3 = row.getX_SWIFI_REMARS3();
                LAT = row.getLAT();
                LNT = row.getLNT();
                WORK_DTTM = row.getWORK_DTTM();
                db.dbInsert(member);
                start = end + 1;
            }
        } while (start <= totalCount) ;
    }

    private static String fetchDataFromApi(String apiUrl) throws Exception {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setConnectTimeout(30000);
        con.setReadTimeout(30000);

        con.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            con.disconnect();
        }
        return sb.toString();
    }

    public class DataModel {
        @SerializedName("TbPublicWifiInfo")
        private TbPublicWifiInfo tbPublicWifiInfo;

        public String getX_SWIFI_MGR_NO() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_MGR_NO();
            }
            return null;
        }
        public String getX_SWIFI_WRDOFC() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_WRDOFC();
            }
            return null;
        }
        public String getX_SWIFI_MAIN_NM() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_MAIN_NM();
            }
            return null;
        }
        public String getX_SWIFI_ADRES1() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_ADRES1();
            }
            return null;
        }
        public String getX_SWIFI_ADRES2() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_ADRES2();
            }
            return null;
        }
        public String getX_SWIFI_INSTL_FLOOR() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_INSTL_FLOOR();
            }
            return null;
        }
        public String getX_SWIFI_INSTL_TY() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_INSTL_TY();
            }
            return null;
        }
        public String getX_SWIFI_INSTL_MBY() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_INSTL_MBY();
            }
            return null;
        }
        public String getX_SWIFI_SVC_SE() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_SVC_SE();
            }
            return null;
        }
        public String getX_SWIFI_CMCWR() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_CMCWR();
            }
            return null;
        }
        public String getX_SWIFI_CNSTC_YEAR() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_CNSTC_YEAR();
            }
            return null;
        }
        public String getX_SWIFI_INOUT_DOOR() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_INOUT_DOOR();
            }
            return null;
        }
        public String getX_SWIFI_REMARS3() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getX_SWIFI_REMARS3();
            }
            return null;
        }
        public String getLAT() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getLAT();
            }
            return null;
        }
        public String getLNT() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getLNT();
            }
            return null;
        }
        public String getWORK_DTTM() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return tbPublicWifiInfo.getRow()[0].getWORK_DTTM();
            }
            return null;
        }
        public List<Row> getRows() {
            if (tbPublicWifiInfo != null && tbPublicWifiInfo.getRow() != null && tbPublicWifiInfo.getRow().length > 0) {
                return Arrays.asList(tbPublicWifiInfo.getRow());
            }
            return Collections.emptyList();
        }
    }

    class TbPublicWifiInfo {
        private int list_total_count;
        private Result RESULT;
        private Row[] row;
        // Getter 및 Setter 메서드들 추가


        public int getList_total_count() {
            return list_total_count;
        }

        public void setList_total_count(int list_total_count) {
            this.list_total_count = list_total_count;
        }

        public Result getRESULT() {
            return RESULT;
        }

        public void setRESULT(Result RESULT) {
            this.RESULT = RESULT;
        }

        public Row[] getRow() {
            return row;
        }

        public void setRow(Row[] row) {
            this.row = row;
        }
    }

    class Result {
        private String CODE;
        private String MESSAGE;

        // Getter 및 Setter 메서드들 추가

        public String getCODE() {
            return CODE;
        }

        public void setCODE(String CODE) {
            this.CODE = CODE;
        }

        public String getMESSAGE() {
            return MESSAGE;
        }

        public void setMESSAGE(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }
    }

    class Row {
        // 1. 관리번호
        @SerializedName("X_SWIFI_MGR_NO")
        private String X_SWIFI_MGR_NO;

        public String getX_SWIFI_MGR_NO() {
            return X_SWIFI_MGR_NO;
        }

        public void setX_SWIFI_MGR_NO(String X_SWIFI_MGR_NO) {
            this.X_SWIFI_MGR_NO = X_SWIFI_MGR_NO;
        }

        // 2. 자치구
        @SerializedName("X_SWIFI_WRDOFC")
        private String X_SWIFI_WRDOFC;

        public String getX_SWIFI_WRDOFC() {
            return X_SWIFI_WRDOFC;
        }

        public void setX_SWIFI_WRDOFC(String X_SWIFI_WRDOFC) {
            this.X_SWIFI_WRDOFC = X_SWIFI_WRDOFC;
        }

        // 3. 와이파이명
        @SerializedName("X_SWIFI_MAIN_NM")
        private String X_SWIFI_MAIN_NM;

        public String getX_SWIFI_MAIN_NM() {
            return X_SWIFI_MAIN_NM;
        }

        public void setX_SWIFI_MAIN_NM(String X_SWIFI_MAIN_NM) {
            this.X_SWIFI_MAIN_NM = X_SWIFI_MAIN_NM;
        }

        // 4. 도로명주소
        @SerializedName("X_SWIFI_ADRES1")
        private String X_SWIFI_ADRES1;

        public String getX_SWIFI_ADRES1() {
            return X_SWIFI_ADRES1;
        }

        public void setX_SWIFI_ADRES1(String X_SWIFI_ADRES1) {
            this.X_SWIFI_ADRES1 = X_SWIFI_ADRES1;
        }

        // 5. 상세주소
        @SerializedName("X_SWIFI_ADRES2")
        private String X_SWIFI_ADRES2;

        public String getX_SWIFI_ADRES2() {
            return X_SWIFI_ADRES2;
        }

        public void setX_SWIFI_ADRES2(String X_SWIFI_ADRES2) {
            this.X_SWIFI_ADRES2 = X_SWIFI_ADRES2;
        }

        // 6. 설치위치(층)
        @SerializedName("X_SWIFI_INSTL_FLOOR")
        private String X_SWIFI_INSTL_FLOOR;

        public String getX_SWIFI_INSTL_FLOOR() {
            return X_SWIFI_INSTL_FLOOR;
        }

        public void setX_SWIFI_INSTL_FLOOR(String X_SWIFI_INSTL_FLOOR) {
            this.X_SWIFI_INSTL_FLOOR = X_SWIFI_INSTL_FLOOR;
        }

        // 7. 설치유형
        @SerializedName("X_SWIFI_INSTL_TY")
        private String X_SWIFI_INSTL_TY;

        public String getX_SWIFI_INSTL_TY() {
            return X_SWIFI_INSTL_TY;
        }

        public void setX_SWIFI_INSTL_TY(String X_SWIFI_INSTL_TY) {
            this.X_SWIFI_INSTL_TY = X_SWIFI_INSTL_TY;
        }

        // 8. 설치기관
        @SerializedName("X_SWIFI_INSTL_MBY")
        private String X_SWIFI_INSTL_MBY;

        public String getX_SWIFI_INSTL_MBY() {
            return X_SWIFI_INSTL_MBY;
        }

        public void setX_SWIFI_INSTL_MBY(String X_SWIFI_INSTL_MBY) {
            this.X_SWIFI_INSTL_MBY = X_SWIFI_INSTL_MBY;
        }

        // 9. 서비스구분
        @SerializedName("X_SWIFI_SVC_SE")
        private String X_SWIFI_SVC_SE;

        public String getX_SWIFI_SVC_SE() {
            return X_SWIFI_SVC_SE;
        }

        public void setX_SWIFI_SVC_SE(String X_SWIFI_SVC_SE) {
            this.X_SWIFI_SVC_SE = X_SWIFI_SVC_SE;
        }

        // 10. 망종류
        @SerializedName("X_SWIFI_CMCWR")
        private String X_SWIFI_CMCWR;

        public String getX_SWIFI_CMCWR() {
            return X_SWIFI_CMCWR;
        }

        public void setX_SWIFI_CMCWR(String X_SWIFI_CMCWR) {
            this.X_SWIFI_CMCWR = X_SWIFI_CMCWR;
        }

        // 11. 설치년도
        @SerializedName("X_SWIFI_CNSTC_YEAR")
        private String X_SWIFI_CNSTC_YEAR;

        public String getX_SWIFI_CNSTC_YEAR() {
            return X_SWIFI_CNSTC_YEAR;
        }

        public void setX_SWIFI_CNSTC_YEAR(String X_SWIFI_CNSTC_YEAR) {
            this.X_SWIFI_CNSTC_YEAR = X_SWIFI_CNSTC_YEAR;
        }

        // 12. 실내외구분
        @SerializedName("X_SWIFI_INOUT_DOOR")
        private String X_SWIFI_INOUT_DOOR;

        public String getX_SWIFI_INOUT_DOOR() {
            return X_SWIFI_INOUT_DOOR;
        }

        public void setX_SWIFI_INOUT_DOOR(String X_SWIFI_INOUT_DOOR) {
            this.X_SWIFI_INOUT_DOOR = X_SWIFI_INOUT_DOOR;
        }

        // 13. wifi접속환경
        @SerializedName("X_SWIFI_REMARS3")
        private String X_SWIFI_REMARS3;

        public String getX_SWIFI_REMARS3() {
            return X_SWIFI_REMARS3;
        }

        public void setX_SWIFI_REMARS3(String X_SWIFI_REMARS3) {
            this.X_SWIFI_REMARS3 = X_SWIFI_REMARS3;
        }

        // 14. Y좌표
        @SerializedName("LAT")
        private String LAT;

        public String getLAT() {
            return LAT;
        }

        public void setLAT(String LAT) {
            this.LAT = LAT;
        }

        // 15. X좌표
        @SerializedName("LNT")
        private String LNT;

        public String getLNT() {
            return LNT;
        }

        public void setLNT(String LNT) {
            this.LNT = LNT;
        }

        // 16. 작업일자
        @SerializedName("WORK_DTTM")
        private String WORK_DTTM;

        public String getWORK_DTTM() {
            return WORK_DTTM;
        }

        public void setWORK_DTTM(String WORK_DTTM) {
            this.WORK_DTTM = WORK_DTTM;
        }
    }
}