 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<html ng-app="pigTrax" lang="${pageContext.response.locale}">
  <head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="resources/assets/img/favicon.png">
    <title><spring:message code="label.application.title"  text="PigTrax"/>&nbsp;<spring:message code="label.application.system.title"  text="System"/></title>
    <link href="resources/assets/css/googlefonts.css" rel="stylesheet" type="text/css">
    <link href="resources/assets/lib/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="resources/assets/lib/font-awesome/css/font-awesome.min.css"> 
    <!--if lt IE 9script(src='https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js')
    -->
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/jquery.nanoscroller/css/nanoscroller.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/jquery.gritter/css/jquery.gritter.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/bootstrap.switch/css/bootstrap3/bootstrap-switch.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/jquery.select2/select2.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/bootstrap.slider/css/bootstrap-slider.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/intro.js/introjs.css">
	
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/bootstrap.datetimepicker/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/bootstrap.daterangepicker/daterangepicker-bs3.css">
    <link rel="stylesheet" type="text/css" href="resources/assets/lib/jquery.icheck/skins/square/blue.css">
    <link href="resources/assets/css/polyglot-language-switcher-2.css" rel="stylesheet">
    <link href="resources/assets/css/style.css" rel="stylesheet">    
	
	
	<script src="resources/assets/lib/jquery/jquery.min.js"></script>
	<!-- Include Angular JS files -->
	<script src="resources/lib/angular/angular.min.js"></script>
	<script src="resources/lib/angular/angular-cookies.js"></script>
	<script src="resources/lib/angular/angular-resource.js"></script>
	<script src="resources/assets/lib/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="resources/assets/lib/bootstrap.switch/js/bootstrap-switch.js"></script>
	<script src="resources/lib/angular-ui/0.11.2/ui-bootstrap-tpls-0.11.2.min.js"></script>
	<script type="text/javascript" src="resources/lib/angular-dropdown-multiselect/angularjs-dropdown-multiselect.min.js"></script>
	<script type="text/javascript" src="resources/lib/angular-dropdown-multiselect/underscore-min.js"></script>
	<script src="resources/js/app.js"></script>
	<script src="resources/js/directive.js"></script>
	<script src="resources/js/services/ngMask.min.js"></script>
	<script src="resources/js/services/date.js"></script>
	<script src="resources/js/services/restServices.js"></script>
	<script src="resources/js/services/DateUtils.js"></script> 
	<script src="resources/js/services/angular-confirm.js"></script>
	<script src="resources/js/controllers/menuController.js"></script>
	<script src="resources/js/controllers/employeeController.js"></script>
	<script src="resources/js/controllers/companyController.js"></script>
	<script src="resources/js/controllers/entryEventController.js"></script>
	<script src="resources/js/controllers/breedingEventController.js"></script>		
	<script src="resources/js/controllers/employeeGroupController.js"></script>
	<script src="resources/js/controllers/addCompanyController.js"></script>
	<script src="resources/js/controllers/addPremisesController.js"></script>
	<script src="resources/js/controllers/addBarnController.js"></script>
	<script src="resources/js/controllers/addRoomController.js"></script>
	<script src="resources/js/controllers/addSiloController.js"></script>
	<script src="resources/js/controllers/addPenController.js"></script>
	<script src="resources/js/controllers/premisesController.js"></script>
	<script src="resources/js/controllers/barnController.js"></script>
	<script src="resources/js/controllers/roomController.js"></script>
	<script src="resources/js/controllers/siloController.js"></script>
	<script src="resources/js/controllers/penController.js"></script>
	<script src="resources/js/controllers/transportTrailerTruckController.js"></script>
	<script src="resources/js/controllers/addTransportTrailerTruckController.js"></script>
	<script src="resources/js/controllers/transportDestinationController.js"></script>
	<script src="resources/js/controllers/addTransportDestinationController.js"></script>
	<script src="resources/js/controllers/pregnancyEventController.js"></script>
	<script src="resources/js/controllers/farrowEventController.js"></script>
	<script src="resources/js/controllers/groupEventController.js"></script>
	<script src="resources/js/controllers/pigletEventController.js"></script>
	<script src="resources/js/controllers/addGroupEventDetailController.js"></script>
	<script src="resources/js/controllers/transportJourneyController.js"></script>		
	<script src="resources/js/controllers/pigletStatusEventController.js"></script>
	<script src="resources/js/controllers/moveToAnotherGroupController.js"></script>
	<script src="resources/js/controllers/feedEventController.js"></script>
	<script src="resources/js/controllers/addFeedEventDetailController.js"></script>
	<script src="resources/js/controllers/removalEventController.js"></script>		
	<script src="resources/js/controllers/removalExceptSalesController.js"></script>
	<script src="resources/js/controllers/openSelectBoxController.js"></script>
	<script src="resources/js/controllers/addSalesEventController.js"></script>	
	<script src="resources/js/controllers/companyTargetController.js"></script>
	<script src="resources/js/controllers/changePigIdController.js"></script>
	<script src="resources/js/controllers/productionLogController.js"></script>
	<script src="resources/js/controllers/addEmployeeController.js"></script>
	<script src="resources/js/controllers/passwordController.js"></script>
	<script src="resources/js/controllers/originController.js"></script>
	<script src="resources/js/controllers/rationController.js"></script>
	<script src="resources/js/controllers/massUploadController.js"></script>
	<script src="resources/js/controllers/massUploadController.js"></script>
	<script src="resources/js/controllers/sowMovementController.js"></script>
	<script src="resources/js/controllers/editSowMovementController.js"></script>	
	
	<script src="resources/lib/angular/smart-table.js"></script> 
	<script src="resources/lib/angular/smart-table.min.js"></script>
	<script type="text/javascript" src="resources/assets/lib/jquery/jquery.min.js"></script>
	<script src="resources/js/services/jquery-polyglot.language.switcher.js"></script>
	
  </head>
  <body>
  
  <jsp:include page="header.jsp"/>
    <div id="cl-wrapper" class="fixed-menu">
            <!--Sidebar item function-->
            <!--Sidebar sub-item function-->
            <%
            if(request.getRemoteUser() != null)
            {
            %>
            <jsp:include page="leftmenu.jsp"/>
            <%
            }
            %>
      <div id="pcont" class="container-fluid">
        <jsp:include page="${contentUrl}"/>
      </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
