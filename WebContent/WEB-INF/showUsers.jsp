<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Users</title>
</head>
<body>
<h1>This are the users from the database </h1>

	<hr>
	<c:choose>
		<c:when test="${userList == null or userList.isEmpty()}">
		    The List is empty.
		</c:when>
		<c:otherwise>
		    <table border=1>
		    
		    <tr><th>Username</th><th>Password</th></tr>
			<c:forEach var="user" items="${userList}">
	        <tr><td>${user.username}</td><td>${user.password}</td></tr>
			</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<hr>
	<a href='index.html'>Main Page</a>
	<br>

</body>
</html>