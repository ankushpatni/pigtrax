<%@ page import="com.pigtrax.usermanagement.enums.RoleType" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- -Left menu -->
<div data-position="right" data-step="1" class="cl-sidebar" ng-controller="MenuController">
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
                    		
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.listoptions.link"  text="Master data lists"/></a>
                    			  <ul class="sub-menu">
								  <li><a href="company"><spring:message code="label.leftmenu.managemasterdata.destination.link"  text="Destination"/></a></li>
                    			  <li><a href="employee"><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee"/></a></li>
								  <li><a href="ration"><spring:message code="label.leftmenu.managemasterdata.rartion.link"  text="Ration Id"/></a></li> 
                    			  <li><a href="origin"><spring:message code="label.leftmenu.managemasterdata.origin.link"  text="Genetic Origin"/></a></li>
                    			   
								  <li><a href="company"><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a></li>                    			   
                    			    
									<li><a href="goToCompanyTarget"><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Parameters"/></a></li>
								  <li><a href="company"><spring:message code="label.leftmenu.managemasterdata.truckandtrailer.link"  text="Truck & Trailer"/></a></li>
                    			  </ul>
                    			  </li>
                    			  
                    			  
                    			  <%}else if(request.isUserInRole(RoleType.DataManager.getRoleValue())){%>
                    			  <li><a href="#"><i class="glyphicon glyphicon-tasks"></i><spring:message code="label.leftmenu.managemasterdata.listoptions.link"  text="Master data lists"/></a>
                    			  <ul class="sub-menu">
								  <li><a href="transportDestination"><spring:message code="label.leftmenu.managemasterdata.destination.link"  text="Destination"/></a></li>
                    			   <li><a href="employee"><spring:message code="label.leftmenu.managemasterdata.employeedata.link"  text="Employee"/></a></li>
								   <li><a href="ration"><spring:message code="label.leftmenu.managemasterdata.rartion.link"  text="Ration Id"/></a></li>
                    			   <li><a href="origin"><spring:message code="label.leftmenu.managemasterdata.origin.link"  text="Genetic Origin"/></a></li>
                    			  
                    			   <li><a href="loadPremises"><spring:message code="label.leftmenu.managemasterdata.premisedata.link"  text="Premise Data"/></a></li>
                    			   
								    <li><a href="goToCompanyTarget"><spring:message code="label.leftmenu.managepigevents.targets.link"  text="Parameters"/></a></li>
                    			   <li><a href="transportTrailerAndTruck"><spring:message code="label.leftmenu.managemasterdata.truckandtrailer.link"  text="Truck & Trailer"/></a></li>
                    			  </ul> 
                    			  </li>                    			 
                    			  <%} %>
                            	<% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) || 
                              		  request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue()) || request.isUserInRole(RoleType.DataManager.getRoleValue())) {%>		  
                            	<li><a href="#"><i class="glyphicon glyphicon-tasks"></i><span></span><spring:message code="label.leftmenu.managepigevents.link"  text="Pig Events"/></span></a>
                                      	<ul class="sub-menu">
                                         	<li><a href="pigEntryEvent"><spring:message code="label.leftmenu.managepigevents.entryevent.link"  text="Entry Event"/></a></li>
											<li><a href="pigBreedingEvent"><spring:message code="label.leftmenu.managepigevents.breedingevent.link"  text="Breeding Event"/></a></li>
                                         	<li><a href="pigPregnancyEvent"><spring:message code="label.leftmenu.managepigevents.pregnancyevent.link"  text="Pregnancy Event"/></a></li>
                                         	<li><a href="pigFarrowEvent"><spring:message code="label.leftmenu.managepigevents.farrowevent.link"  text="Farrow Event"/></a></li>
                                         	<li><a href="pigletStatusEvent"><spring:message code="label.leftmenu.managepigevents.pigletstatus.link"  text="Piglet Status Event"/></a></li>
                                         	<li><a href="pigGroupEvent"><spring:message code="label.leftmenu.managepigevents.groupEvent.link"  text="Group Event"/></a></li>
                                         	<li><a href="pigRemovalEvent"><spring:message code="label.leftmenu.managepigevents.removalevent.link"  text="Pig Movement"/></a></li>                                         	
                                         	<li><a href="pigFeedEvent"><spring:message code="label.leftmenu.managepigevents.feedevent.link"  text="Feed Event"/></a></li>											
											<li><a href="toChangeId"><spring:message code="label.leftmenu.managepigevents.changeid.link"  text="Change Id"/></a></li>
                                         	<li><a href="loadPigletEvent"><spring:message code="label.leftmenu.managepigevents.individualpiglet.link"  text="Individual Piglet"/></a></li>
                                         	<li><a href="getProductionLogs"><spring:message code="label.leftmenu.managepigevents.productionlog.link"  text="Production Log"/></a></li>
											<li><a href="sowMomentEvent"><spring:message code="label.leftmenu.managepigevents.sowMomentEvent.link"  text="Sow Movement Event"/></a></li>
                                         </ul>
                                      </li>
                                      <%}%>
                             <li><a href="#"><i class="glyphicon glyphicon-stats"></i><span><spring:message code="label.leftmenu.analyticreports.link"  text="Analytical reports"/></span></a>
                             	<ul class="sub-menu">
											<li><a href=""><spring:message code="label.leftmenu.reports.managementLists"  text="Management Lists"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.reports.trackingReports"  text="Tracking Reports"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.reports.performanceMonitoring"  text="Performance Monitoring"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.reports.dataExtraction"  text="Data Extraction"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.reports.statisticalReports"  text="Statistical Reports"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.reports.sowCards"  text="Sow Cards"/></a></li>
											<li><a href="reportGeneration"><spring:message code="label.leftmenu.Charts.inventoryStats"  text="Inventory Stats"/></a></li>
											<li><a href="reportGenerationSow"><spring:message code="label.leftmenu.Charts.sowReport"  text="Sow Report"/></a></li>
											<li><a href="reportGenerationGroup"><spring:message code="label.leftmenu.Charts.groupReport"  text="Group Report"/></a></li>
											<li><a href="reportGenerationActionList"><spring:message code="label.leftmenu.Charts.actionListReport"  text="Action List Report"/></a></li>
											<li><a href="reportInventoryStatus"><spring:message code="label.leftmenu.Charts.inventoryStatusReport"  text="Inventory Status Report"/></a></li>
											<li><a href="reportLactationLength"><spring:message code="label.leftmenu.Charts.lactationLengthReport"  text="Lactation Length Report"/></a></li>
											<li><a href="reportPigletMortality"><spring:message code="label.leftmenu.Charts.pigletMortalityReport"  text="Piglet Mortality Report"/></a></li>
											<li><a href="prodEventLog"><spring:message code="label.leftmenu.reports.prodEventLog"  text="Production Event Log"/></a></li>
											<li><a href="removalReport"><spring:message code="label.leftmenu.reports.removalReport"  text="Removal Report"/></a></li>
											<li><a href="targetReport"><spring:message code="label.leftmenu.reports.targetReport"  text="Target Report"/></a></li>
  
                                </ul>
                              </li>
                              
                              <li><a href="#"><i class="glyphicon glyphicon-stats"></i><span><spring:message code="label.leftmenu.Charts"  text="Charts"/></span></a>
                             	<ul class="sub-menu">
                                         	
											<li><a href=""><spring:message code="label.leftmenu.Charts.performanceMonitor"  text="Performance Monitor"/></a></li>
											<li><a href=""><spring:message code="label.leftmenu.Charts.simpleStats"  text="Simple Stats"/></a></li>
											
                                </ul>
                              </li>
                              
                                <% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) || 
                              		  request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue()) || request.isUserInRole(RoleType.DataManager.getRoleValue())) {%> 
                            <li><a href="#"><i class="glyphicon glyphicon-cog"></i><span><spring:message code="label.leftmenu.settings.link"  text="Settings"/></span></a>
                            <ul class="sub-menu">
                          				 <% if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) || 
                              		  request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue())) {%> 
                          
                                         	<li><a href="company"><spring:message code="label.leftmenu.managemasterdata.companydata.link"  text="Company Data"/></a></li>
                                      <%} else {%>
                                      <li><a href="companyDetail"><spring:message code="label.leftmenu.managemasterdata.companydetail.link"  text="Company Detail"/></a></li>
                                      <%}  %>
                                      <li><a href="#"><spring:message code="label.leftmenu.settings.dateformat"  text="Date Format"/> :<br/> <a href="#"><button id="format-btn"  ng-model="myDateFormat" label="dd/MM/yyyy" ng-value="false" class="btn btn-warning"  ng-click="toggle()">dd/MM/yyyy</button></a>
                                      </li> 
                                       <li><a href="#"><spring:message code="label.leftmenu.settings.weightunits"  text="Weight Units : Kg"/></a>
                                      </li> 
                                      <li><a href="#"><spring:message code="label.leftmenu.settings.mandatoryfields"  text="Mandatory fields : *"/></a>
                                      </li>                                    
                                     
                               </ul>
                            </li>           
                            <li><a href="massupload"><i class="fa fa-upload"></i><span>Mass Upload</span></a></li>    
                            <%} %>
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