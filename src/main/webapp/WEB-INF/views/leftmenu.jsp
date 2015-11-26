<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- -Left menu -->
<div data-position="right" data-step="1" class="cl-sidebar">
              <div class="cl-toggle"><i class="fa fa-bars"></i></div>
              <div class="cl-navblock">
                <div class="menu-space">
                  <div class="content">
                    <div class="side-user">
                     
                    </div>
                     <ul class="cl-vnavigation">
                            <li><a href="home"><i class="fa fa-home"></i><span><spring:message code="label.leftmenu.dashboard.link"  text="Dashboard"/></span></a>
                             
                            </li>
                            
                    			
                    			<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) || 
                              		  request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue())) {%>
                    			
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.listoptions.link"  text="List Options"/></a>
                    			  <ul class="sub-menu">
                    			   <li><a href="origin"><spring:message code="label.leftmenu.managemasterdata.origin.link"  text="Origin"/></a></li>
                    			   <li><a href="ration"><spring:message code="label.leftmenu.managemasterdata.rartion.link"  text="Ration Id"/></a></li>
                    			  </ul>
                    			  </li>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="company"><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a></li>
                    			 	 </ul>                            			 
                    			  </li>
                    			   <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee Data/Roles"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="employee"><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee Data/Roles"/></a></li>
                    			 	 </ul>                               			  
                    			  </li>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.transportation.link"  text="Transportation"/></a>
                    			  <ul class="sub-menu">
                    			   <li><a href="company"><spring:message code="label.leftmenu.managemasterdata.truckandtrailer.link"  text="Truck & Trailer"/></a></li>
                    			   <li><a href="company"><spring:message code="label.leftmenu.managemasterdata.destination.link"  text="Destination"/></a></li>
                    			  </ul>
                    			  </li>
                    			   <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Targets"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="goToCompanyTarget"><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Targets"/></a></li>
                    			 	 </ul>                               			  
                    			  </li>
                    			  <%}else {%>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.listoptions.link"  text="List Options"/></a>
                    			  <ul class="sub-menu">
                    			   <li><a href="origin"><spring:message code="label.leftmenu.managemasterdata.origin.link"  text="Origin"/></a></li>
                    			   <li><a href="ration"><spring:message code="label.leftmenu.managemasterdata.rartion.link"  text="Ration Id"/></a></li>
                    			  </ul>
                    			  </li>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="loadPremises"><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a></li>
                    			 	 </ul>                            			 
                    			  </li>
                    			   <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee Data/Roles"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="employee"><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee Data/Roles"/></a></li>
                    			 	 </ul>                               			  
                    			  </li>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.transportation.link"  text="Transportation"/></a>
                    			  <ul class="sub-menu">
                    			   <li><a href="transportTrailerAndTruck"><spring:message code="label.leftmenu.managemasterdata.truckandtrailer.link"  text="Truck & Trailer"/></a></li>
                    			   <li><a href="transportDestination"><spring:message code="label.leftmenu.managemasterdata.destination.link"  text="Destination"/></a></li>
                    			  </ul>
                    			  </li>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Targets"/></a>
                    			     <ul class="sub-menu">
                    			   		<li><a href="goToCompanyTarget"><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Targets"/></a></li>
                    			 	 </ul>                               			  
                    			  </li>
                    			  <%} %>
                            			  
                            <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><span></span><spring:message code="label.leftmenu.managepigevents.link"  text="Manage Pig Events"/></span></a>
                                      	<ul class="sub-menu">
                                         	<li><a href="pigEntryEvent"><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></a></li>
                                         	<li><a href="pigBreedingEvent"><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></a></li>
                                         	<li><a href="pigPregnancyEvent"><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></a></li>
                                         	<li><a href="pigFarrowEvent"><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></a></li>
                                         	<li><a href="pigGroupEvent"><spring:message code="label.leftmenu.managepigevents.groupEvent.link"  text="Group Event"/></a></li>
                                         	<li><a href="pigletStatusEvent"><spring:message code="label.leftmenu.managepigevents.pigletstatus.link"  text="Piglet Status Event"/></a></li>
                                         	<li><a href="pigFeedEvent"><spring:message code="label.leftmenu.managepigevents.feedevent.link"  text="Feed Event"/></a></li>
											<li><a href="pigRemovalEvent"><spring:message code="label.leftmenu.managepigevents.removalevent.link"  text="Pig Movement"/></a></li>
											<li><a href="toChangeId"><spring:message code="label.leftmenu.managepigevents.changeid.link"  text="Change Id"/></a></li>
                                         	<li><a href="loadPigletEvent"><spring:message code="label.leftmenu.managepigevents.individualpiglet.link"  text="Individual Piglet"/></a></li>
                                         	<li><a href="getProductionLogs"><spring:message code="label.leftmenu.managepigevents.productionlog.link"  text="Production Log"/></a></li>
                                         </ul>
                                      </li>
                             <li><a href="#"><i class="glyphicon glyphicon-stats"></i><span><spring:message code="label.leftmenu.analyticreports.link"  text="Analytic Reports"/></span></a>
                             	<ul class="sub-menu">
                                         	<li><a href="reportGeneration"><spring:message code="label.leftmenu.reports.link"  text="Ferrow Report"/></a></li>
                                </ul>
                              </li>
                              
                              
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i><span><spring:message code="label.leftmenu.settings.link"  text="Settings"/></span></a>
                            <ul class="sub-menu">
                              <% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) || 
                                    		  request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue())) { %>
                                         	<li><a href="company"><spring:message code="label.leftmenu.managemasterdata.companydata.link"  text="Company Data"/></a></li>
                                      <%} else {%>
                                      <li><a href="companyDetail"><spring:message code="label.leftmenu.managemasterdata.companydetail.link"  text="Company Detail"/></a></li>
                                      <%}  %>
                               </ul>
                            </li>           
                            <li><a href="massupload"><i class="glyphicon glyphicon-cog"></i><span>Mass Upload</span></a></li>    
                              </ul>
                  </div>
                </div>
                <div class="search-field collapse-button">
                  <input type="text" placeholder="Search..." class="form-control search">
                  <button id="sidebar-collapse" class="btn btn-default"><i class="fa fa-angle-left"></i>
                  </button>
                </div>
              </div>
            </div>