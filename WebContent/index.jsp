<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
<img src="phoneix.png" width="200" height="150" border="0"/></img>
<h1>SEARCH ENGINE</h1>
<h3>Enter Database Credentials here to connect:</h3>
</head>
<body>
<form action="MainApplication">
<br>Enter IP:<input type="text" name="ip"/><br>
<br>Enter Port:<input type="text" name="port"/><br>
<br>Enter Username:<input type="text" name="user"/><br>
<br>Enter Password:<input type="text" name="pass"/><br>
<br><br></n>
<h3>Use Web Crawler</h3>
Want to Crawl? :<input type="text" name="crawl"/><br>

<br><input type="hidden" name="flag" value="insert"/> 
<br><input type="submit" value="Submit"/>

</form>
</body>
</html>