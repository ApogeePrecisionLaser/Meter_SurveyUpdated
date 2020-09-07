<%-- 
    Document   : personHierarchyView
    Created on : Jul 2, 2015, 6:13:17 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Complaint Register</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/table.js"></script>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" >
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>

        <script type="text/javascript" language="javascript">

  jQuery(function(){
                $("#office_type").autocomplete("personHierarchyCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficeTypeList";}
                    }
                });
                $("#designated_person").autocomplete("personHierarchyCont.do", {
                    extraParams: {
                        action1: function() { return "getDesignatedPersonList";},
                        action2: function() { return $("#office_type").val();}
                    }
                });
                $("#searchDesignatedPerson").autocomplete("personHierarchyCont.do", {
                    extraParams: {
                        action1: function() { return "getDesignatedPersonList";},
                        action2: function() { return $("#searchOfficeType").val();}
                    }
                });
                $("#higher_designated_person").autocomplete("personHierarchyCont.do", {
                    extraParams: {
                        action1: function() { return "getHigherDesignatedPersonList";},
                        action2: function() { return $("#office_type").val();},
                        action3: function() { return $("#designated_person").val();}
                    }
                });
                $("#searchOfficeType").autocomplete("personHierarchyCont.do", {
                    extraParams: {
                        action1: function() { return "getOfficeTypeList";}
                    }
                });
                /*$("#designated_person").result(function(event, data, formatted){
                    if(document.getElementById("searchTypeOfComplaint").value == 'No Such  Type of Tax Exists.'){
                        document.getElementById("searchTypeOfComplaint").value = "";
                    }
                });*/
                $("#designated_person").result(function(event, data, formatted){
                    $.ajax({url: "personHierarchyCont.do?action1=getOffice", data: "designated_person="+ data +"&q=", success: function(response_data) {
                        //var id = response_data.split("_")[0];
                        //var name = response_data.split("_")[1];
                        //var i;
                        $("#office_type").val(response_data);
                        //$("#sp_id").val(id.split("#")[0]);
                       // $("#switching_point").val(id);
                        //selectDivisionZoneFeeder();

                    }
                });
                });
            });

            function singleCheckUncheck(id){
                //  alert(document.getElementById('insertTable').rows.length);
                if ($('#check_complaint'+id).is(':checked')) {
                    $("#complaint_type_id"+id).val("1");
                }else{
                    $("#complaint_type_id"+id).val("0");
                }
                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }


            }

            function checkUncheckAll(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_complaint]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_type_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_complaint]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_type_id"+i).val("2");
                    }
                }
            }

            function verify() {debugger;
               try {
                    var result , office_type = "", designated_person= "", higher_designated_person="";

                   if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                        office_type = $.trim(document.getElementById("office_type").value);
                        designated_person = $.trim(document.getElementById("designated_person").value);
                        higher_designated_person = $.trim(document.getElementById("higher_designated_person").value);
                        if($.trim(office_type).length == 0) {
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Office Type is Required...</b></td>");
                            document.getElementById("office_type").focus();
                            return false; // code to stop from submitting the form2.
                        }
                       if($.trim(designated_person).length == 0) {
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Designated Person is Required...</b></td>");
                            document.getElementById("designated_person").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if($.trim(higher_designated_person).length == 0) {
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Higher Designated Person is Required...</b></td>");
                            document.getElementById("higher_designated_person").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        if(designated_person == higher_designated_person) {
                            $("#message").html("<td colspan='6' bgcolor='coral'><b>Designated Person and Higher Designated Person must be different...</b></td>");
                            document.getElementById("designated_person").focus();
                            return false; // code to stop from submitting the form2.
                        }

                        if(result == false);
                        else result = true;
                        if(document.getElementById("clickedButton").value == 'Save AS New'){
                            result = confirm("Are you sure you want to save it as New record?")
                            return result;
                        }
                        if(document.getElementById("clickedButton").value == 'Update'){
                            result = confirm("Are you sure you want to Update record?")
                            return result;
                        }
                    } else result = confirm("Are you sure you want to delete this record?")
                  if(document.getElementById("person_hierarchy_id").value == 0){
                        addRow(office_type, designated_person, higher_designated_person, $("#description").val(), $("#remark").val());
                        $("#message").html("");
                        return false;
                    }
                }catch(e){
                    alert(e);
                }
                return result;

       }

            function addRow(office_type, designation, higher_designation, description, remark) {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "person_hierarchy_id";
                element1.id = "person_hierarchy_id"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);
                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_designation";
                element1.id = "check_designation"+rowCount;                //element1.size = 1;
                element1.value = "YesModify";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell2 = row.insertCell(1);
                var element2 = document.createElement("input");
                element2.type = "text";
                element2.name = "office_type";
                element2.id = "office_type"+rowCount;
                element2.readOnly = true;
                element2.size = 30;
                element2.value = office_type;
                cell2.appendChild(element2);

                var cell3 = row.insertCell(2);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.name = "designated_person";
                element3.id = "designated_person"+rowCount;
                element3.readOnly = true;
                element3.size = 30;
                element3.value = designation;
                cell3.appendChild(element3);

                var cell4 = row.insertCell(3);
                var element4 = document.createElement("input");
                element4.type = "text";
                element4.name = "higher_designated_person";
                element4.id = "higher_designated_person"+rowCount;
                element4.readOnly = true;
                element4.size = 30;
                element4.value = higher_designation;
                cell4.appendChild(element4);

                var cell5 = row.insertCell(4);
                var element5 = document.createElement("input");
                element5.type = "text";
                element5.name = "description";
                element5.id = "description"+rowCount;
                element5.readOnly = true;
                element5.value = description;
                cell5.appendChild(element5);

                var cell6 = row.insertCell(5);
                var element6 = document.createElement("input");
                element6.type = "text";
                element6.name = "remark";
                element6.id = "remark"+rowCount;
                element6.readOnly = true;
                element6.value = remark;
                cell6.appendChild(element6);

                var height = (rowCount * 40)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden', true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
            }

            function deleteRowWithoutResetForm() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                }catch(e) {
                    alert(e);
                }
            }

            function deleteRow() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                    document.getElementById('form3').reset();
                }catch(e) {
                    alert(e);
                }
            }

            function makeEditable(id) {
                document.getElementById("office_type").disabled = false;
                document.getElementById("designated_person").disabled = false;
                document.getElementById("higher_designated_person").disabled = false;
                document.getElementById("description").disabled = false;
                document.getElementById("remark").disabled = false;
                document.getElementById("save_As").disabled = false;
                document.getElementById("update").disabled = false;
                document.getElementById("delete").disabled = false;
                document.getElementById("save").disabled = true;
               // document.getElementById("save").disabled = false;
                if(id == 'new') {
                    document.getElementById("person_hierarchy_id").value = '0';
                    document.getElementById("office_type").value = '';
                    document.getElementById("designated_person").value = '';
                    document.getElementById("higher_designated_person").value = '';
                    //document.getElementById("complaint_type_eng").value = '';
                    document.getElementById("description").value = '';
                    document.getElementById("remark").value = '';
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("update").disabled = true;
                    document.getElementById("save").disabled = false;

                $("#message").html("");

               // document.getElementById("edit").disabled = true;
                //    document.getElementById("delete").disabled = true;
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 8);
                }
               /*
               if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;

                }
              */

             //   document.getElementById("message").innerHTML = "";

             //   $("#message").html("");
            }

            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }

            function fillColumns(id) {debugger;
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
           var noOfColumns = 8;
           var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of      table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                var t2id = "t2c";
                deleteRowWithoutResetForm();
                setDefaultColor(noOfRowsTraversed, noOfColumns);
                // set default color of rows (i.e. of multiple coloumns).
                document.getElementById("person_hierarchy_id").value = document.getElementById("person_hierarchy_id" + rowNo).value;
                document.getElementById("office_type").value = document.getElementById(t1id +(lower+1)).innerHTML;
                document.getElementById("designated_person").value = document.getElementById(t1id +(lower+2)).innerHTML;
                document.getElementById("higher_designated_person").value = document.getElementById(t1id +(lower+4)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id +(lower+6)).innerHTML;
                document.getElementById("remark").value = document.getElementById(t1id +(lower+7)).innerHTML;
                //document.getElementById("complaint_type_eng").value = document.getElementById(t1id +(lower+3)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                makeEditable('');

               /* document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled) {
                    document.getElementById("save_As").disabled = true;
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                } */
            }

            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                 else if(id == 'update') {
                    document.getElementById("clickedButton").value = "Update";
                }
             /*   else if(id == 'edit') {
                    document.getElementById("clickedButton").value = "Edit";
                }*/ else
                    document.getElementById("clickedButton").value = "Delete";;
            }

        </script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main" style="table-layout: auto">

                  <tr>

                <td></td>

            </tr>
            <%--<tr>
                <td><%@include file="/view/layout/header_layout.jsp" %> </td>

            </tr>--%>
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                    <%--    <form name="form1" method="post" action="complaintTypesCont.do" onsubmit="return verifyform()">   --%>
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">

                                                                  <tr>
                                                                      <td align="center" class="header_table" width="90%">Person Hierarchy</td>
                                                                      <td><%@include file="/layout/complaint_menu.jsp" %></td>
                                                                  </tr>

                                        </table>
                                    </td>
                                </tr>
                                <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
                                <tr>  <%-- Start Complaint type of search table --%>
                                <td  align="center">
                                    <form name="form0" method="POST" action="personHierarchyCont.do">
                                        <table  align="center" width="850" class="heading1">
                                            <tr>
                                                <th>Office Name</th>
                                                <td><input class="input" type="text" id="searchOfficeType" name="searchOfficeType" value="${searchOfficeType}" size="30" ></td>
                                                <th>Designated Person</th>
                                                <td><input class="input" type="text" id="searchDesignatedPerson" name="searchDesignatedPerson" value="${searchDesignatedPerson}" size="30" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchInOfficeType" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                                </tr <%--End Complaint type of search table --%>
                                <tr>
                                    <td align="center">
                                        <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                           <form name="form1" method="post" action="personHierarchyCont.do" onsubmit="return verifyform()">  <%--change --%>
                                            <table id="table1" border="1" align="center"  class="reference">
                                                <tr>
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Office Name</th>
                                                    <th class="heading" >Designated Person</th>
                                                    <th class="heading" >Designation</th>
                                                    <th class="heading" >Higher Designated Person</th>
                                                    <th class="heading" >Higher Designation</th>
                                                    <th class="heading" >Description</th>
                                                    <th class="heading" >Remark</th>

                                                </tr>
                                                <c:forEach var="phList" items="${PersonHierarchyList}" varStatus="loopCounter">
                                                    <tr>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="person_hierarchy_id${loopCounter.count}" name="person_hierarchy_id${loopCounter.count}" value="${phList.person_hierarchy_id}">

                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.office_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.designated_person}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.designation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.higher_designated_person}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.higher_designation}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.description}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${phList.remark}</td>

                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="11">
                                                <c:choose>
                                                    <c:when test="${showFirst eq 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='First'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showPrevious == 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showNext eq 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Next'>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${showLast == 'false'}">
                                                        <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input class="button" type='submit' name='buttonAction' value='Last'>
                                                    </c:otherwise>
                                                </c:choose>
                                                </td>
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" name="searchTypeOfComplaint" value="${searchTypeOfComplaint}"  >
                                            </table>
                                       </form>
                                        </DIV>
                                    </td>
                                </tr>
                                <%--</c:if>--%>
                                <tr>
                                    <td align="center">
                                        <DIV id="autoCreateTableDiv"  STYLE="overflow: auto; visibility: hidden;height: 0px">
                                            <form name="form2"  action="" method="post" >
                                                <table id="parentTable" class="content" border="1"  align="center" >
                                                    <tr>
                                                        <td colspan="3">
                                                            <table id="insertTable" class="reference" border="1" align="center" width="100%">
                                                                <tr>
                                                                    <th class="heading1" >S.No.</th>
                                                                    <th class="heading1" >Office Name</th>
                                                                    <th class="heading1" >Designated Person</th>
                                                                    <th class="heading1" >Higher Designated Person</th>
                                                                    <th class="heading1" >Description</th>
                                                                    <th class="heading1" >Remark</th>

                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="8" align="center">
                                                            <input type="button" class="button" name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                            <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <input type="button" class="button" name="Cancel" value="Cancel" onclick="deleteRow();">
                                                            <input  type="hidden"  name="isComplaintReg" value="YesModify">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </DIV>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">

                                        <DIV style="<%--${!empty(privilegeList) ? '' : 'display:none'}--%>">
                                            <form id="form3" name="form3" method="POST" action="personHierarchyCont.do" onsubmit="return verify()">
                                                <table border="0"  id="table2" align="center"  class="content" >
                                                    <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                    </tr>
                                                    <tr>
                                                        <th class="heading1" >Office Name</th>
                                                        <td>
                                                            <input type="text" id="office_type" name="office_type" value="" disabled>
                                                                <%--<c:forEach var="office" items="${officeTypeList}">
                                                                    <option class="" value="${office.key}">${office.value}</option>
                                                                </c:forEach>
                                                            </select>--%>
                                                        </td>
                                                        <th class="heading1" >Designated Person</th>
                                                        <td>
                                                            <input type="text" id="designated_person" name="designated_person" value="" disabled>
                                                                <%--<c:forEach var="designate" items="${DesignationList}">
                                                                    <option class="" value="${designate.key}">${designate.value}</option>
                                                                </c:forEach>
                                                            </select>--%>
                                                        </td>
                                                        <th class="heading1" >Higher Designated Person</th>
                                                        <td>
                                                            <input type="text" id="higher_designated_person" name="higher_designated_person" value="" disabled>
                                                                <%--<c:forEach var="higherDesignate" items="${DesignationList}">
                                                                    <option class="" value="${higherDesignate.key}">${higherDesignate.value}</option>
                                                                </c:forEach>
                                                            </select>--%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th class="heading1" >Description</th>
                                                        <td>
                                                            <input type="text" id="description" name="description" value="" disabled>
                                                        </td>
                                                        <th class="heading1" >Remark</th>
                                                        <td>
                                                            <input type="text" id="remark" name="remark" value="" disabled>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align='center' colspan="6">
                                                            <input class="button"  type="submit" name="task" id="update" value="Update" onclick="setStatus(id);" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'update') ? '' : 'none'}--%>"disabled>
                                                            <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'save') ? '' : 'none'}--%>"  disabled >
                                                            <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id); " style="display:<%--${myfn:isContainPrivileges(privilegeList, 'save_As') ? '' : 'none'}--%>"disabled>
                                                            <input class="button" type="button" name="new" id="new" value="New" onclick="makeEditable(id)" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'new') ? '' : 'none'}--%>">
                                                            <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'delete') ? '' : 'none'}--%>"disabled>
                                                        </td>
                                                    </tr>
                                                    <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                    <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                    <input type="hidden" id="person_hierarchy_id" name="person_hierarchy_id" value="0">
                                                    <input  type="hidden" name="searchTypeOfComplaint" value="${searchTypeOfComplaint}"  >
                                                    <input type="hidden" id="clickedButton" value="">
                                                </table>
                                            </form>
                                        </DIV>

                                    </td>
                                </tr>
                            </table>
                     <%--   </form>  --%>

                    </div>

                </td>

            </tr>

             <tr>
                 <td>
                     <jsp:include page="calendar_view"/>

                </td>
            </tr>

            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>

        </table>
    </body>
</html>
