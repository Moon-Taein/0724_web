<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="jdbc.DBCPInitListener"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.connection.ConnectionProvider"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커넥션 테스트</title>
</head>
<body>

<%
	// 드라이버만 로드 해주니까 저번주에 안되던거 다 되네;;;;
	try(Connection conn = ConnectionProvider.getInstance().getConnection()){
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from coffee order by name");
		while(rs.next()){
			String name = rs.getString("name");
			System.out.println(name);
		}
		out.println("커넥션 연결 성공함");
	} catch(SQLException ex){
		out.println("커넥션 연결 실패함 : " + ex.getMessage());
		application.log("커넥션 연결 실패", ex);
	}
%>

</body>
</html>