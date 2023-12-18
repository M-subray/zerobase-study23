package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
	public List<Member> list() { //select
		
		List<Member> memberList = new ArrayList<>();
		
		String url = "jdbc:mariadb://localhost/wifi?characterEncoding=UTF-8";
		String dbUserId = "root";
		String dbPassword = "zerobase";

		 // 1. 드라이버 로드
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		// 2. 커넥션 객체 생성
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			// 3. 스테이트먼트 객체 생성
			statement = connection.createStatement();

			String sql = " select *" + " from wifi";
			rs = statement.executeQuery(sql);

			int rowCount = 0; // 현재까지 처리한 행의 개수
			int maxRowCount = 1; // 최대 출력할 행의 개수

			while (rs.next() && rowCount < maxRowCount) {
				Member member = new Member();
				member.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
				member.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
				member.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
				member.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
				member.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
				member.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
				member.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
				member.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
				member.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
				member.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
				member.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
				member.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
				member.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
				member.setLAT(rs.getString("LNT")); // 위도 경도 API 에서 반대로 저장 됨
				member.setLNT(rs.getString("LAT"));
				member.setWORK_DTTM(rs.getString("WORK_DTTM"));
				
				memberList.add(member);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			try {
				if (statement != null && !statement.isClosed()) {
					statement.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return memberList;
	}

	public void dbInsert(Member member) {
	    String url = "jdbc:mariadb://localhost/wifi?characterEncoding=UTF-8";
	    String dbUserId = "root";
	    String dbPassword = "zerobase";

	    // 1. 드라이버 로드
	    try {
	        Class.forName("org.mariadb.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        throw new RuntimeException(e);
	    }
	    // 2. 커넥션 객체 생성
	    Connection connection = null;
	    PreparedStatement prepareedStatement = null;

	    try {
	        connection = DriverManager.getConnection(url, dbUserId, dbPassword);

	        String sql = "INSERT INTO wifi (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1,"
	                + "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY,"
	                + "X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR,"
	                + "X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        prepareedStatement = connection.prepareStatement(sql);
	        prepareedStatement.setString(1, member.getX_SWIFI_MGR_NO());
	        prepareedStatement.setString(2, member.getX_SWIFI_WRDOFC());
	        prepareedStatement.setString(3, member.getX_SWIFI_MAIN_NM());
	        prepareedStatement.setString(4, member.getX_SWIFI_ADRES1());
	        prepareedStatement.setString(5, member.getX_SWIFI_ADRES2());
	        prepareedStatement.setString(6, member.getX_SWIFI_INSTL_FLOOR());
	        prepareedStatement.setString(7, member.getX_SWIFI_INSTL_TY());
	        prepareedStatement.setString(8, member.getX_SWIFI_INSTL_MBY());
	        prepareedStatement.setString(9, member.getX_SWIFI_SVC_SE());
	        prepareedStatement.setString(10, member.getX_SWIFI_CMCWR());
	        prepareedStatement.setString(11, member.getX_SWIFI_CNSTC_YEAR());
	        prepareedStatement.setString(12, member.getX_SWIFI_INOUT_DOOR());
	        prepareedStatement.setString(13, member.getX_SWIFI_REMARS3());
	        prepareedStatement.setString(14, member.getLAT());
	        prepareedStatement.setString(15, member.getLNT());
	        prepareedStatement.setString(16, member.getWORK_DTTM());

	        int affected = prepareedStatement.executeUpdate();

	        if (affected > 0) {
	            System.out.println("저장 성공");
	        } else {
	            System.out.println("저장 실패");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (prepareedStatement != null && !prepareedStatement.isClosed()) {
	                prepareedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}