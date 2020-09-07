<%-- 
    Document   : healthStatusMapView
    Created on : Apr 6, 2015, 2:43:33 PM
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
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" language="javascript">

    jQuery(function(){
        $("#searchSwitchingPointName").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getSwitchingPointName"}
            }
        });

        $("#searchHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP1HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP2HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP3HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP1vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP2vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP3vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });

        $("#searchReadingDateFrom").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchReadingDateTo").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
    });

    function IsNumeric(id) {
        var strString=    document.getElementById(id).value;
        var strValidChars = "0123456789";
        var strChar;
        var blnResult = true;
        if (strString.length == 0) return false;
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                document.getElementById(id).value="";
                alert("Time should be Numeric");
                blnResult = false;
            }else{
                var time = document.getElementById(id).value;
                var timePeriodFrom = document.getElementById("time_from").value;
                var timePeriodTo = document.getElementById("time_to").value;
                if(id=="reading_time_from_hour"){
                    if(timePeriodFrom == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(timePeriodFrom == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else if(id=='reading_time_to_hour'){
                    if(timePeriodTo == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(timePeriodTo == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else{
                    if(time>59){
                        document.getElementById(id).value="";
                        alert("Min should Be less than 60");
                        return;
                    }
                    $("#message").html("");
                }

            }
        }
        return blnResult;
    }

    function verify(){
        var search = document.getElementById("searchIn").value;
        if(search == "Search"){
            var searchReadingDateFrom = document.getElementById("searchReadingDateFrom").value;
            var searchReadingDateTo = document.getElementById("searchReadingDateTo").value;
            var pattern = /(\d{2})-(\d{2})-(\d{4})/;//alert(pattern);
            var dateFrom = new Date(searchReadingDateFrom.replace(pattern, '$3-$2-$1'));//alert(dateFrom);
            var dateTo = new Date(searchReadingDateTo.replace(pattern, '$3-$2-$1'));//alert(dateTo);
            //var dateFrom = new Date(searchReadingDateFrom, "d"));alert(dateFrom);
            //var dateTo = new Date(searchReadingDateTo, ""));alert(dateTo);
            if(searchReadingDateFrom != "" && searchReadingDateTo != ""){
                if(dateFrom > dateTo){
                    document.getElementById("searchReadingDateFrom").value="";
                    document.getElementById("searchReadingDateTo").value="";
                    alert("Reading Date From should Be less than Reading Date To");
                    return false;
                }
            }
            var timePeriodFrom = document.getElementById("time_from").value;
            var timePeriodTo = document.getElementById("time_to").value;
            var hourFrom = document.getElementById("reading_time_from_hour").value;
            var hourTo = document.getElementById("reading_time_to_hour").value;
            var minFrom = document.getElementById("reading_time_from_min").value;
            var minTo = document.getElementById("reading_time_to_min").value;
            if((timePeriodFrom == "AM" && timePeriodTo == "AM") || (timePeriodFrom == "PM" && timePeriodTo == "PM")){
                if((hourFrom != "00" && hourFrom != "") && (hourTo != "00" && hourTo != "")){
                    if(hourFrom > hourTo){
                        document.getElementById("reading_time_from_hour").value="";
                        document.getElementById("reading_time_to_hour").value="";
                        alert("Time From should Be less than Time To");
                        return false;
                    }
                }else if((minFrom != "" && minFrom != "00") && (minTo != "" && minTo != "00")){
                    if(minFrom > minTo){
                        document.getElementById("reading_time_from_min").value="";
                        document.getElementById("reading_time_to_min").value="";
                        alert("Time From should Be less than Time To");
                        return false;
                    }
                }
            }else if(timePeriodFrom == "PM" && timePeriodTo == "AM"){
                document.getElementById("reading_time_from_hour").value="";
                document.getElementById("reading_time_to_hour").value="";
                document.getElementById("reading_time_from_min").value="";
                document.getElementById("reading_time_to_min").value="";
                alert("Time From should Be less than Time To");
                return false;
            }
        }
        return true;
    }

    function getHealthStatuslist(){
        var searchSwitchingPointName = document.getElementById("searchSwitchingPointName").value;
        var searchHealthStatus = document.getElementById("searchHealthStatus").value;
        var searchP1HealthStatus = document.getElementById("searchP1HealthStatus").value;
        var searchP2HealthStatus = document.getElementById("searchP2HealthStatus").value;
        var searchP3HealthStatus = document.getElementById("searchP3HealthStatus").value;
        var searchP1vcHealthStatus = document.getElementById("searchP1vcHealthStatus").value;
        var searchP2vcHealthStatus = document.getElementById("searchP2vcHealthStatus").value;
        var searchP3vcHealthStatus = document.getElementById("searchP3vcHealthStatus").value;
        var searchReadingDateFrom = document.getElementById("searchReadingDateFrom").value;
        var searchReadingDateTo = document.getElementById("searchReadingDateTo").value;
        //reading_time_from_hour=11&reading_time_from_min=00&time_from=AM&reading_time_to_hour=1&reading_time_to_min=00&time_to=PM
        var reading_time_from_hour = document.getElementById("reading_time_from_hour").value;
        var reading_time_from_min = document.getElementById("reading_time_from_min").value;
        var time_from = document.getElementById("time_from").value;
        var reading_time_to_hour = document.getElementById("reading_time_to_hour").value;
        var reading_time_to_min = document.getElementById("reading_time_to_min").value;
        var time_to = document.getElementById("time_to").value;
        var queryString = "task=generateHSReport&searchSwitchingPointName="+searchSwitchingPointName+"&searchHealthStatus="
            +searchHealthStatus+"&searchReadingDateFrom="+searchReadingDateFrom+"&searchReadingDateTo="+searchReadingDateTo+"&reading_time_from_hour="
            +reading_time_from_hour+"&reading_time_from_min="+reading_time_from_min+"&time_from="+time_from+"&reading_time_to_hour="+reading_time_to_hour+"&reading_time_to_min="
            +reading_time_to_min+"&time_to="+time_to+"&searchP1HealthStatus="+searchP1HealthStatus+"&searchP2HealthStatus="+searchP2HealthStatus+"&searchP3HealthStatus="+searchP3HealthStatus
            +"&searchP1vcHealthStatus="+searchP1vcHealthStatus+"&searchP2vcHealthStatus="+searchP2vcHealthStatus+"&searchP3vcHealthStatus="+searchP3vcHealthStatus;
        var url = "healthStatusMapCont.do?" + queryString;
        popupwin = openPopUp(url, "Health Status Map List", 600, 900);
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
        <title>Health Status Map</title>
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
                                            <td align="center" class="header_table" width="980"><b>Health Status Map</b></td>
                                            <td><%--<%@include file="/layout/org_menu.jsp" %>--%></td>
                                            <%-- <td>
                                                 <%@include file="/view/layout/org_menu.jsp" %>
                                             </td> --%>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td align="center"> <div id="search_div">
                                        <form name="form0" method="get" action="healthStatusMapCont.do" onsubmit="return verify()">
                                            <table align="center" width="995">
                                                <tr align="center">
                                                    <th align="center">
                                                        Switching Point Name &nbsp;&nbsp;<input class="input" type="text" id="searchSwitchingPointName" name="searchSwitchingPointName" value="${searchSwitchingPointName}" >
                                                    </th>
                                                    <th align="center" >
                                                        Date From &nbsp;&nbsp;<input class="input" type="text" name="searchReadingDateFrom" id="searchReadingDateFrom" value="${searchReadingDateFrom == '' ? '' : searchReadingDateFrom}">
                                                    </th>
                                                    <th align="center" >
                                                        Date To &nbsp;&nbsp;<input class="input" type="text" name="searchReadingDateTo" id="searchReadingDateTo" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}">
                                                    </th>
                                                    <th align="center">
                                                        OverAll Health&nbsp;&nbsp;<input class="input" type="text" id="searchHealthStatus" name="searchHealthStatus" value="${searchHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase1 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP1HealthStatus" name="searchP1HealthStatus" value="${searchP1HealthStatus}" >
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th align="center">
                                                        Phase2 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP2HealthStatus" name="searchP2HealthStatus" value="${searchP2HealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase3 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP3HealthStatus" name="searchP3HealthStatus" value="${searchP3HealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase1 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP1vcHealthStatus" name="searchP1vcHealthStatus" value="${searchP1vcHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase2 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP2vcHealthStatus" name="searchP2vcHealthStatus" value="${searchP2vcHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase3 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP3vcHealthStatus" name="searchP3vcHealthStatus" value="${searchP3vcHealthStatus}" >
                                                    </th>
                                                </tr>
                                                <tr align="center">
                                                    <th align="center" >
                                                        Time From &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_from_hour" name="reading_time_from_hour" Placeholder ="HH" value="${reading_time_from_hour}"
                                                                                            size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_from_min" name="reading_time_from_min"  value="${reading_time_from_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <select id="time_from" name="time_from" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${time_from eq 'PM'}">
                                                                    <option>AM</option>
                                                                    <option selected>PM</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option selected>AM</option>
                                                                    <option>PM</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </th>
                                                    <th align="center" >
                                                        Time To &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_to_hour" name="reading_time_to_hour" value="${reading_time_to_hour}"
                                                                                          size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_to_min" name="reading_time_to_min"  value="${reading_time_to_min}"
                                                                size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <select id="time_to" name="time_to" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${time_to eq 'PM'}">
                                                                    <option>AM</option>
                                                                    <option selected>PM</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option selected>AM</option>
                                                                    <option>PM</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </th>                                                    
                                                    <td  colspan="2" align="center">
                                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                        <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getHealthStatuslist()">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                            </tr>
                            <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
                            <tr>
                                <td align="center">

                                    <form name="form1" method="POST" action="healthStatusMapCont.do">
                                        <DIV class="content_div">
                                            <table id="table1" align="center" border="1" class="content" width="650">
                                                <tr>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading">Switching Point Name</th>
                                                    <th class="heading">Reading Date</th>
                                                    <th class="heading">Reading Time</th>
                                                    <th class="heading">Overall Health</th>
                                                    <th class="heading">Phase</th>
                                                    <th class="heading">Phase 1</th>
                                                    <th class="heading">Phase 2</th>
                                                    <th class="heading">Phase 3</th>
                                                    <th class="heading">Phase1 VC Status</th>
                                                    <th class="heading">Phase2 VC Status</th>
                                                    <th class="heading">Phase3 VC Status</th>
                                                </tr>
                                                <c:forEach var="healthStatusMapBean" items="${requestScope['healthStatusMapList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusMapBean.health_status_map_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusMapBean.health_status_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusMapBean.switching_point_detail_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusMapBean.switching_rev_no}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)"><a href="junctionDetailCont.do?searchJunction=${healthStatusMapBean.switching_point_name}&task=Search">${healthStatusMapBean.switching_point_name}</a></td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.reading_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.reading_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.health_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase1_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase2_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase3_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase1_vc_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase2_vc_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusMapBean.phase3_vc_status}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="12">
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
                                        <form name="form3"  action="healthStatusMapCont.do" method="POST" >
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
                                        <form name="form2" id="form2" method="POST" action="healthStatusMapCont.do" onsubmit="return verify()">
                                            <table border="0" id="table2" align="center" class="reference" width="650">
                                                <%--   <tr><td colspan="6"></td> </tr>
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
                                                --%>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
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
