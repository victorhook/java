<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INSANELY COOL WEBSITE</title>
</head>
<body>
	<h1>CHRISTMAS IS ALMOST HERE!</h1>
	<%out.println("Your ip is:" + request.getRemoteAddr()); %>
    <p>Name: ${name} </p>
    <p>Number of items: ${itemCount} </p>

</body>
</html>