<%-- 
    Document   : healthStatus
    Created on : Apr 3, 2015, 12:34:37 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">

            jQuery(function(){
                $("#searchHealthStatus").autocomplete("healthStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getHealthStatus"}
                    }
                });
            });
            
            function makeEditable(id) {
               document.getElementById("save_As").disabled = false;
                document.getElementById("update").disabled = false;
                document.getElementById("delete").disabled = false;
                document.getElementById("save").disabled = true;

                document.getElementById("health_status").disabled = false;
                document.getElementById("health_status_value").disabled = false;
                document.getElementById("remark").disabled = false;
                if(id == 'new') {
                    // document.getElementById("message").innerHTML = "";      // Remove message
                    var  message="";
                    $("#message").html(message);     // Remove message
                    document.getElementById("update").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("save").disabled = false;
                    document.getElementById("health_status_id").value = '';
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value,4);
                    document.getElementById("health_status").focus();
                }

            }
            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }
            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 4;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowCount = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowCount++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                for(var i = 0; i < noOfColumns; i++) {
                    // set the background color of clicked/selected row to yellow.
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
                }

                document.getElementById("health_status_id").value = document.getElementById("health_status_id_gen" + rowCount).value;
                document.getElementById("health_status").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
                document.getElementById("health_status_value").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
                document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dagfd";        // set the background color of clicked row to yellow.
                }

                // document.getElementById("message").innerHTML = "";      // Remove message
                var  message="";
                $("#message").html(message);     // Remove message
                makeEditable('');
            }
            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'update'){
                    document.getElementById("clickedButton").value = "Update";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else {
                    document.getElementById("clickedButton").value = "Delete";
                }
            }
            function myLeftTrim(str) {
                var beginIndex = 0;
                for(var i = 0; i < str.length; i++) {
                    if(str.charAt(i) == ' ')
                        beginIndex++;
                    else break;
                }
                return str.substring(beginIndex, str.length);
            }
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                    var health_status = document.getElementById("health_status").value;
                    var health_status_value = document.getElementById("health_status_value").value;
                    var remark = document.getElementById("remark").value;
                    
                    if(myLeftTrim(health_status).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Division name is required...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Health Status is required...</b></td>");
                        document.getElementById("health_status").focus();
                        return false; // code to stop from submitting the form2.
                    }
                    /*if(myLeftTrim(health_status_value).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='2' bgcolor='coral'><b>Zone is Manadotory...</b></td>";
                        $("#message").html("<td colspan='2' bgcolor='coral'><b>Health Status Value is required...</b></td>");
                        document.getElementById("health_status_value").focus();
                        return false; // code to stop from submitting the form2.
                    }*/
                    if(result == false) {
                        // if result has value false do nothing, so result will remain contain value false.
                    } else {
                        result = true;
                    }

                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else {
                    result = confirm("Are you sure you want to delete this record?");
                }
               if(document.getElementById("health_status_id").value == 0){
                        addRow( health_status, health_status_value, remark );
                        document.getElementById('form2').reset();
                        document.getElementById("health_status").focus();
                        return false;
                    }
                return result;
            }
            function addRow(data2, data3, data4) {
                var  message="";
                $("#message").html(message);
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "health_status_id";
                element1.id = "health_status_id"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);

                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_print";
                element1.id = "check_print"+rowCount;                //element1.size = 1;
                element1.value = "Yes";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell3 = row.insertCell(1);
                cell3.innerHTML = $.trim(data2);
                var element3 = document.createElement("input");
                element3.type = "hidden";
                element3.name = "health_status";
                element3.id = "health_status"+rowCount;
                element3.size = 20;
                element3.value = data2;
                cell3.appendChild(element3);

                var cell4 = row.insertCell(2);
                cell4.innerHTML = $.trim(data3);
                var element4 = document.createElement("input");
                element4.type = "hidden";
                element4.name = "health_status_value";
                element4.id = "health_status_value"+rowCount;
                element4.size = 20;
                element4.value = data3;
                cell4.appendChild(element4);

                var cell5 = row.insertCell(3);
                cell5.innerHTML = $.trim(data4);
                var element5 = document.createElement("input");
                element5.type = "hidden";
                element5.name = "remark";
                element5.id = "remark"+rowCount;
                element5.size = 20;
                element5.value = data4;
                cell5.appendChild(element5);

                var height = (rowCount * 25)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
                $("#autoCreateTableDiv").show();
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden', true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
            }
            function deleteRow() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                   // document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                   // document.getElementById("autoCreateTableDiv").style.height = '0px';
                    $("#autoCreateTableDiv").hide();
                    //document.getElementById('form3').reset();
                    document.getElementById("health_status").focus();
                }catch(e) {
                    alert(e);
                }
            }
            function singleCheckUncheck(id){
                // alert(document.getElementById('insertTable').rows.length);
                if ($('#check_print'+id).is(':checked')) {
                    $("#health_status_id"+id).val("1");
                }else{
                    $("#health_status_id"+id).val("0");
                }

                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }
            }

            function checkUncheck(id){
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;
                if(id == 'Check All'){
                    $('input[name=check_print]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#state_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#health_status_id"+i).val("2");
                    }
                }
            }


            function getHealthStatuslist(){
                var searchHealthStatus = document.getElementById("searchHealthStatus").value;
                var queryString = "task=generateHSReport&searchHealthStatus="+searchHealthStatus;
                var url = "healthStatusCont.do?" + queryString;
                popupwin = openPopUp(url, "Health Status List", 600, 900);
            }


            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }

</script>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Health Status</title>
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                  <td><%@include file="/layout/menu.jsp" %></td>  
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                            <tr><tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="980"><b>Health Status</b></td>
                                      <td><%--<%@include file="/layout/org_menu.jsp" %>--%></td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="healthStatusCont.do">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <th class="heading1">Health Status</th>
                                                <td>
                                                    <input class="input" type="hidden" id="s_n" name="s_n" value="" >
                                                    <input class="input" type="text" id="searchHealthStatus" name="searchHealthStatus" value="${searchHealthStatus}" >
                                                </td>
                                               <!-- <th class="heading1">Country Name</th>
                                                <td>
                                                    <input class="input" type="text" id="searchcompany" name="searchcompany" value="${searchCompany}" >
                                                </td>-->
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                   <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getHealthStatuslist()"></td>
                                            </tr>
                                        </table>
                                    </form>
                                </div>
                         </tr>
                            <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
                            <tr>
                                <td align="center">

                                    <form name="form1" method="POST" action="healthStatusCont.do">
                                        <DIV class="content_div">
                                            <table id="table1" align="center" border="1" class="content" width="650">
                                                <tr>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading">Health Status</th>
                                                    <th class="heading">Health Status Value</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="healthStatusBean" items="${requestScope['healthStatusList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusBean.health_status_id}">

                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.health_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.health_status_value}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="4">
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
                                                    </td>  </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>
                            <%--</c:if>--%>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv" STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="healthStatusCont.do" method="POST" >
                                            <table id="parentTable" class="content" border="1"  align="center" width="500">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" class="content" border="1" align="center" width="100%">
                                                            <tr>
                                                                <th class="heading" style="width: 50px">S.No.</th>
                                                                 <th class="heading">Health Status</th>
                                                                 <th class="heading">Health Status Value</th>
                                                                 <th class="heading">Remark</th>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                        <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </DIV>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                        <div >
                                    <form name="form2" id="form2" method="POST" action="healthStatusCont.do" onsubmit="return verify()">
                                        <table border="0" id="table2" align="center" class="reference" width="650">
                                            <tr><td colspan="6"></td> </tr>
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Health Status</th>
                                                <td><input type="hidden" id="health_status_id" name="health_status_id" value="0">
                                                    <input class="input" type="text" id="health_status" name="health_status" size="20" value="" disabled>
                                                </td>
                                                <th class="heading1">Health Status Value</th>
                                                <td>
                                                    <input class="input" type="text" id="health_status_value" name="health_status_value" size="20" value="" disabled>
                                                </td>
                                                <th class="heading1">Remark</th>
                                                <td>
                                                    <input class="input" type="text" id="remark" name="remark" size="20" value="" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="6">
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden"  name="searchHealthStatus" value="${searchHealthStatus}" >
                                            <input type="hidden" id="clickedButton" value="">
                                        </table>
                                    </form>
                                  </div>
                                </td>
                            </tr>
                        </table>
                    </div></td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
