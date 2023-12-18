<%@page import="db.Member"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.List"%>
<%@page import="db.Db"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>근처 WIFI 정보 보기</title>
<%!// Java 코드 (메소드, 변수 선언 등)를 여기에 작성합니다.
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름 (단위: km)
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c;
	}%>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid #dcdada;
	height: 70px;
	text-align: center;
}

th {
	background-color: #04AA6D;
	color: white;
}

tr:nth-child(odd) {
	background-color: #f2f2f2;
}
</style>
<script>
	// 초기값을 설정
	var lat = '0.0';
	var lnt = '0.0';

	function clickBtn() {
		window.navigator.geolocation.getCurrentPosition(function(position) {
			// 얻은 값을 전역 변수에 저장
			lat = position.coords.latitude.toFixed(7).toString();
			lnt = position.coords.longitude.toFixed(7).toString();

			// 값을 입력하는 input 엘리먼트에 반영
			document.getElementById('latInput').value = lat;
			document.getElementById('lntInput').value = lnt;
		}, function(error) {
			alert("위치 정보를 가져오는 데 실패했습니다.");
		});
	}

	function redirectToNearWifi() {
		// 값을 그대로 전달
		window.location.href = './nearWifi.jsp?lat=' + lat + '&lnt=' + lnt;
	}

	function createTableHeader() {
		var headerRow = document.createElement("tr");

		var headers = [ "거리(Km)", "관리번호", "자치구", "와이파이명", "도로명주소", "상세주소",
				"설치위치(층)", "설치유형", "설치기관", "서비스구분", "망종류", "설치년도", "실내외구분",
				"WIFI접속환경", "X좌표", "Y좌표", "작업일자" ];

		for (var i = 0; i < headers.length; i++) {
			var th = document.createElement("th");
			th.textContent = headers[i];
			headerRow.appendChild(th);
		}

		return headerRow;
	}
	// 페이지 로드 시 초기값 설정
	document
			.addEventListener(
					'DOMContentLoaded',
					function() {
						// URL에서 lat 및 lnt 매개변수 가져오기
						var urlParams = new URLSearchParams(
								window.location.search);
						var latFromURL = urlParams.get('lat');
						var lntFromURL = urlParams.get('lnt');

						document.getElementById('latInput').value = latFromURL !== null ? latFromURL
								.toString()
								: '0.0';
						document.getElementById('lntInput').value = lntFromURL !== null ? lntFromURL
								.toString()
								: '0.0';
					});
</script>
</head>
<body>
	<%
	// calculateDistance 메소드 추가
	Db db = new Db();
	List<Member> memberList = db.list();

	// 현재 위치
	double currentLat = Double.parseDouble(request.getParameter("lat"));
	double currentLng = Double.parseDouble(request.getParameter("lnt"));

	// 거리를 계산하여 memberList를 정렬
	memberList.sort(Comparator.comparingDouble(member -> calculateDistance(currentLat, currentLng,
			Double.parseDouble(member.getLAT()), Double.parseDouble(member.getLNT()))));

	// 상위 20개만 추출
	List<Member> nearList = memberList.subList(0, Math.min(20, memberList.size()));
	System.out.println("정상 조회 완료");
	%>
	<h1>와이파이 정보 구하기</h1>
	<a href="./index.html">홈</a> |
	<a href="./history.jsp">위치 히스토리 목록</a>
	<a href="./info.html">Open API 와이파이 정보 가져오기</a>
	<br>
	<br> LAT:
	<input type="text" id="latInput" value="0.0">, LNT:
	<input type="text" id="lntInput" value="0.0">
	<button onclick="clickBtn()">내 위치 가져오기</button>
	<!-- 근처 WIFI 정보 보기를 눌렀을 때 위치 정보를 전달 -->
	<button onclick="redirectToNearWifi()">근처 WIFI 정보 보기</button>
	<br>
	<br>
	<table>
		<script>
			document.write(createTableHeader().outerHTML);
		</script>
		<tbody>
			<tr>
				<%
				for (Member member : nearList) {
					double distance = calculateDistance(currentLat, currentLng, Double.parseDouble(member.getLAT()),
					Double.parseDouble(member.getLNT()));
				%>
			
			<tr>
				<td><%=String.format("%.4f", distance)%></td>
				<td><%=member.getX_SWIFI_MGR_NO()%></td>
				<td><%=member.getX_SWIFI_WRDOFC()%></td>
				<td><%=member.getX_SWIFI_MAIN_NM()%></td>
				<td><%=member.getX_SWIFI_ADRES1()%></td>
				<td><%=member.getX_SWIFI_ADRES2()%></td>
				<td><%=member.getX_SWIFI_INSTL_FLOOR()%></td>
				<td><%=member.getX_SWIFI_INSTL_TY()%></td>
				<td><%=member.getX_SWIFI_INSTL_MBY()%></td>
				<td><%=member.getX_SWIFI_SVC_SE()%></td>
				<td><%=member.getX_SWIFI_CMCWR()%></td>
				<td><%=member.getX_SWIFI_CNSTC_YEAR()%></td>
				<td><%=member.getX_SWIFI_INOUT_DOOR()%></td>
				<td><%=member.getX_SWIFI_REMARS3()%></td>
				<td><%=member.getLAT()%></td>
				<td><%=member.getLNT()%></td>
				<td><%=member.getWORK_DTTM()%></td>
			</tr>
			<%
			}
			%>
			</tr>
		</tbody>
	</table>
</body>
</html>