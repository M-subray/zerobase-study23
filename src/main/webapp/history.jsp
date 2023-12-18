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
	function createTableHeader() {
		var headerRow = document.createElement("tr");

		var headers = [ "ID", "X좌표", " Y좌표", "조회일자", "비고" ];

		for (var i = 0; i < headers.length; i++) {
			var th = document.createElement("th");
			th.textContent = headers[i];
			headerRow.appendChild(th);
		}

		return headerRow;
	}
</script>
</head>
<body>
	<%
	// calculateDistance 메소드 추가
	Db db = new Db();
	List<Member> memberList = db.list();
	%>
	<h1>위치 히스토리 목록</h1>
	<a href="./index.html">홈</a> |
	<a href="./history.jsp">위치 히스토리 목록</a>
	<a href="./info.html">Open API 와이파이 정보 가져오기</a>
	<table>
		<script>
			document.write(createTableHeader().outerHTML);
		</script>
	</table>
</body>
</html>