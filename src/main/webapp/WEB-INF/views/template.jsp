<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html ng-app="pigTrax">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PigTrax</title>

<!--  Refer bootstrap CSS -->
<link href="resources/lib/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet"> 
<link href="resources/css/custom-style.css" rel="stylesheet"> 
<link href="resources/css/font-awesome.min.css" rel="stylesheet">
 <link href="resources/css/theme-style.min.css" rel="stylesheet">

<!-- Include Angular JS files -->
<script src="resources/lib/angular/angular.min.js"></script>
<script src="resources/lib/angular/angular-resource.js"></script>
<script src="resources/lib/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="resources/lib/angular-ui/0.11.2/ui-bootstrap-tpls-0.11.2.min.js"></script>
<script src="resources/js/app.js"></script>
<script src="resources/js/services/restServices.js"></script>
<script src="resources/js/controllers/employeeController.js"></script>

</head>
<body>

<div id="header" > 
	   <jsp:include page="header.jsp"/>
</div>
<div id="content">
  	<jsp:include page="${contentUrl}"/> 
</div> 
<br><br><br>
<div id="navigation" class="wrapper"> 
   <jsp:include page="footer.jsp"/>
</div>

</body>
</html>