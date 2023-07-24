<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 목록</title>
</head>
<body>
	<p>다운로드 가능한 파일 목록</p>
	<ul>
		<c:forEach var="filename" items="${ filelist }">
			<c:url var="url" value="./download">
				<c:param name="filename">${ filename }</c:param>
			</c:url>
			<li><a href="${ url }">${ filename }</a></li>
		</c:forEach>		
	</ul>
</body>
</html>