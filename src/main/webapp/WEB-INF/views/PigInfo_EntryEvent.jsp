<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<!-- ======== @Region: #content ======== -->
<div class="page-head">
          <h2><spring:message code='label.piginfo.entryeventform.piginformation'  text='Pig Information'/></h2>
        </div>
 <div class="cl-mcont" ng-controller="EntryEventController" ng-init="populateBarns(${CompanyId})">
          <div class="row">
		  <div class="col-sm-3 col-md-3"></div>
            <div class="col-sm-6 col-md-6">
              <div class="block-flat">
                <div class="header">
                  <h3><spring:message code='label.piginfo.entryeventform.entryevent'  text='Entry Event'/></h3>
                </div>
                <div class="content">
                  <form action="#" data-parsley-validate="" novalidate="">
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.pigid'  text='Pig Id'/></label>
                      <input type="text" name="nick" parsley-trigger="change" required="" placeholder="Enter user name" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.pen'  text='Pen'/></label>
                      <select class="form-control">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.barn'  text='Barn'/></label>
                      <select class="form-control">
                          <option value="-1"> -- Select -- </option>
                          <option ng-repeat="barnDto in barns" value="{{barnDto.id}}">{{barnDto.barnId}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sex'  text='Sex'/></label>
                      <select class="form-control">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sire'  text='Sire'/></label>
                      <select class="form-control">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.dam'  text='Dam'/></label>
                      <select class="form-control">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.origin'  text='Origin'/></label>
                      <input type="text" name="origin" parsley-trigger="change" required="" placeholder="Enter origin" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gline'  text='Gline'/></label>
                      <input type="email" name="email" parsley-trigger="change" required="" placeholder="Enter gline" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.gcompany'  text='GCompany'/></label>
                      <input type="email" name="email" parsley-trigger="change" required="" placeholder="Enter email" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.birthdate'  text='Birth Date'/></label>
                      <input type="text" data-mask="date" placeholder="DD/MM/YYYY" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.tattoo'  text='Tattoo'/></label>
                      <input type="text" name="tattoo" parsley-trigger="change" required="" placeholder="Enter tattoo" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.alternatetattoo'  text='Alternate Tattoo'/></label>
                      <input type="text" name="email" parsley-trigger="change" required="" placeholder="Enter alternate tattoo" class="form-control">
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.remarks'  text='Remarks'/></label>
                      <textarea class="form-control" placeholder="Enter remarks"></textarea>
                    </div>
                    <div class="form-group">
                      <label><spring:message code='label.piginfo.entryeventform.sowcondition'  text='Sow Condition'/></label>
                      <select class="form-control">
                          <option>1</option>
                          <option>2</option>
                          <option>3</option>
                          <option>4</option>
                          <option>5</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <button class="btn btn-default">Cancel</button>
                  </form>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-md-3">        
            </div>
          </div>
</div>


