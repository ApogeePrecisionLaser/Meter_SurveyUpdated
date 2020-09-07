<%-- 
    Document   : logHistoryView
    Created on : Apr 14, 2015, 4:40:26 PM
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
        /*$("#searchSwitchingPointName").autocomplete("healthStatusMapCont.do", {
                    extraParams: {
                        action1: function() { return "getSwitchingPointName"}
                    }
                });

                $("#searchHealthStatus").autocomplete("healthStatusMapCont.do", {
                    extraParams: {
                        action1: function() { return "getHealthStatus"}
                    }
                });*/

        $("#searchLoginDateFrom").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchLoginDateTo").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchLogoutDateFrom").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchLogoutDateTo").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
    });

    function IsNumericForLogin(id) {
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
                var login_time_from = document.getElementById("login_time_from").value;
                var login_time_to = document.getElementById("login_time_to").value;
                if(id=="login_time_from_hour"){
                    if(login_time_from == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(login_time_from == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else if(id=='login_time_to_hour'){
                    if(login_time_to == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(login_time_to == "PM"){
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
    function IsNumericForLogout(id) {
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
                var logout_time_from = document.getElementById("logout_time_from").value;
                var logout_time_to = document.getElementById("logout_time_to").value;
                if(id=="logout_time_from_hour"){
                    if(logout_time_from == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(logout_time_from == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else if(id=='logout_time_to_hour'){
                    if(logout_time_to == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(logout_time_to == "PM"){
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
            var searchLoginDateFrom = document.getElementById("searchLoginDateFrom").value;
            var searchLoginDateTo = document.getElementById("searchLoginDateTo").value;
            var searchLogoutDateFrom = document.getElementById("searchLogoutDateFrom").value;
            var searchLogoutDateTo = document.getElementById("searchLogoutDateTo").value;
            var pattern = /(\d{2})-(\d{2})-(\d{4})/;//alert(pattern);
            var loginDateFrom = new Date(searchLoginDateFrom.replace(pattern, '$3-$2-$1'));
            var loginDateTo = new Date(searchLoginDateTo.replace(pattern, '$3-$2-$1'));
            var logoutDateFrom = new Date(searchLogoutDateFrom.replace(pattern, '$3-$2-$1'));
            var logoutDateTo = new Date(searchLogoutDateTo.replace(pattern, '$3-$2-$1'));
            //var dateFrom = new Date(searchLoginDate, "d"));alert(dateFrom);
            //var dateTo = new Date(searchLogoutDate, ""));alert(dateTo);
            if(searchLoginDateFrom != "" && searchLoginDateTo != ""){
                if(loginDateFrom > loginDateTo){
                    document.getElementById("searchLoginDateFrom").value="";
                    document.getElementById("searchLoginDateTo").value="";
                    alert("Login Date From should Be less than Login Date To");
                    return false;
                }
            }
            if(searchLogoutDateFrom != "" && searchLogoutDateTo != ""){
                if(logoutDateFrom > logoutDateTo){
                    document.getElementById("searchLogoutDateFrom").value="";
                    document.getElementById("searchLogoutDateTo").value="";
                    alert("Logout Date From should Be less than Logout Date To");
                    return false;
                }
            }
            var login_time_from = document.getElementById("login_time_from").value;
            var login_time_to = document.getElementById("login_time_to").value;
            var logout_time_from = document.getElementById("logout_time_from").value;
            var logout_time_to = document.getElementById("logout_time_to").value;
            var hourFrom = document.getElementById("login_time_from_hour").value;
            var hourTo = document.getElementById("login_time_to_hour").value;
            var minFrom = document.getElementById("login_time_from_min").value;
            var minTo = document.getElementById("login_time_to_min").value;
            if((login_time_from == "AM" && login_time_to == "AM") || (login_time_from == "PM" && login_time_to == "PM")){
                if((hourFrom != "00" && hourFrom != "") && (hourTo != "00" && hourTo != "")){
                    if(hourFrom > hourTo){
                        document.getElementById("login_time_from_hour").value="";
                        document.getElementById("login_time_to_hour").value="";
                        alert("Login Time From should Be less than Login Time To");
                        return false;
                    }else if(minFrom > minTo){
                        document.getElementById("login_time_from_min").value="";
                        document.getElementById("login_time_to_min").value="";
                        alert("Login Time From should Be less than Login Time To");
                        return false;
                    }
                }else if((minFrom != "" && minFrom != "00") && (minTo != "" && minTo != "00")){
                    if(minFrom > minTo){
                        document.getElementById("login_time_from_min").value="";
                        document.getElementById("login_time_to_min").value="";
                        alert("Login Time From should Be less than Login Time To");
                        return false;
                    }
                }
            }else if(login_time_from == "PM" && login_time_to == "AM"){
                document.getElementById("login_time_from_hour").value="";
                document.getElementById("login_time_to_hour").value="";
                document.getElementById("login_time_from_min").value="";
                document.getElementById("login_time_to_min").value="";
                alert("Login Time From should Be less than Login Time To");
                return false;
            }
                  
            hourFrom = document.getElementById("logout_time_from_hour").value;
            hourTo = document.getElementById("logout_time_to_hour").value;
            minFrom = document.getElementById("logout_time_from_min").value;
            minTo = document.getElementById("logout_time_to_min").value;
            if((logout_time_from == "AM" && logout_time_to == "AM") || (logout_time_from == "PM" && logout_time_to == "PM")){
                if((hourFrom != "00" && hourFrom != "") && (hourTo != "00" && hourTo != "")){
                    if(hourFrom > hourTo){
                        document.getElementById("logout_time_from_hour").value="";
                        document.getElementById("logout_time_to_hour").value="";
                        alert("Logout Time From should Be less than Logout Time To");
                        return false;
                    }else if(minFrom > minTo){
                        document.getElementById("logout_time_from_min").value="";
                        document.getElementById("logout_time_to_min").value="";
                        alert("Logout Time From should Be less than Logout Time To");
                        return false;
                    }
                }else if((minFrom != "" && minFrom != "00") && (minTo != "" && minTo != "00")){
                    if(minFrom > minTo){
                        document.getElementById("logout_time_from_min").value="";
                        document.getElementById("logout_time_to_min").value="";
                        alert("Logout Time From should Be less than Logout Time To");
                        return false;
                    }
                }
            }else if(logout_time_from == "PM" && logout_time_to == "AM"){
                document.getElementById("logout_time_from_hour").value="";
                document.getElementById("logout_time_to_hour").value="";
                document.getElementById("logout_time_from_min").value="";
                document.getElementById("logout_time_to_min").value="";
                alert("Logout Time From should Be less than Logout Time To");
                return false;
            }
        }
        return true;
    }

    function getLogHistorylist(){
        var searchSwitchingPointName = document.getElementById("searchSwitchingPointName").value;
        var searchHealthStatus = document.getElementById("searchHealthStatus").value;
        var searchLoginDate = document.getElementById("searchLoginDate").value;
        var searchLogoutDate = document.getElementById("searchLogoutDate").value;
        //reading_time_from_hour=11&reading_time_from_min=00&time_from=AM&reading_time_to_hour=1&reading_time_to_min=00&time_to=PM
        var reading_time_from_hour = document.getElementById("reading_time_from_hour").value;
        var reading_time_from_min = document.getElementById("reading_time_from_min").value;
        var time_from = document.getElementById("time_from").value;
        var reading_time_to_hour = document.getElementById("reading_time_to_hour").value;
        var reading_time_to_min = document.getElementById("reading_time_to_min").value;
        var time_to = document.getElementById("time_to").value;
        var queryString = "task=generateHSReport&searchSwitchingPointName="+searchSwitchingPointName+"&searchHealthStatus="+searchHealthStatus+"&searchLoginDate="+searchLoginDate+"&searchLogoutDate="+searchLogoutDate+"&reading_time_from_hour="+reading_time_from_hour+"&reading_time_from_min="+reading_time_from_min+"&time_from="+time_from+"&reading_time_to_hour="+reading_time_to_hour+"&reading_time_to_min="+reading_time_to_min+"&time_to="+time_to;
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
        <title>Log History</title>
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
                                            <td align="center" class="header_table" width="980"><b>Log History</b></td>
                                            <td><%--<%@include file="/layout/org_menu.jsp" %>--%></td>
                                            <%-- <td>
                                                 <%@include file="/view/layout/org_menu.jsp" %>
                                             </td> --%>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td align="center"> <div id="search_div">
                                        <form name="form0" method="get" action="logHistoryCont.do" >
                                            <table align="center" width="995">
                                                <tr align="center">
                                                    <th align="center" >
                                                        Login Date From <input class="input" type="text" name="searchLoginDateFrom" id="searchLoginDateFrom" value="${searchLoginDateFrom}" style="width: 35%;">
                                                    </th>
                                                    <th align="center" colspan="2">
                                                        Login Date To <input class="input" type="text" name="searchLoginDateTo" id="searchLoginDateTo" value="${searchLoginDateTo}" style="width: 35%;">
                                                    </th>
                                                    <th align="center" >
                                                        Logout Date From <input class="input" type="text" name="searchLogoutDateFrom" id="searchLogoutDateFrom" value="${searchLogoutDateFrom}" style="width: 35%;">
                                                    </th>
                                                    <th align="center" >
                                                        Logout Date To <input class="input" type="text" name="searchLogoutDateTo" id="searchLogoutDateTo" value="${searchLogoutDateTo}" style="width: 35%;">
                                                    </th>
                                                </tr>
                                                <tr align="center">
                                                    <th align="center" colspan="">
                                                        Login Time From <input  type="text" id="login_time_from_hour" name="login_time_from_hour" Placeholder ="HH" value="${login_time_from_hour}"
                                                                                size="2" onchange="IsNumericForLogin(id)" onkeyup="IsNumericForLogin(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="login_time_from_min" name="login_time_from_min"  value="${login_time_from_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumericForLogin(id)" onkeyup="IsNumericForLogin(id)">
                                                        <select id="login_time_from" name="login_time_from" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${login_time_from eq 'PM'}">
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
                                                    <th align="center" colspan="2">
                                                        Login Time To <input  type="text" id="login_time_to_hour" name="login_time_to_hour" Placeholder ="HH" value="${login_time_to_hour}"
                                                                              size="2" onchange="IsNumericForLogin(id)" onkeyup="IsNumericForLogin(id)(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="login_time_to_min" name="login_time_to_min"  value="${login_time_to_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumericForLogin(id)" onkeyup="IsNumericForLogin(id)">
                                                        <select id="login_time_to" name="login_time_to" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${login_time_to eq 'PM'}">
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
                                                        Logout Time From <input  type="text" id="logout_time_from_hour" name="logout_time_from_hour" Placeholder ="HH" value="${logout_time_from_hour}"
                                                                                 size="2" onchange="IsNumericForLogout(id)" onkeyup="IsNumericForLogout(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="logout_time_from_min" name="logout_time_from_min"  value="${logout_time_from_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumericForLogout(id)" onkeyup="IsNumericForLogout(id)">
                                                        <select id="logout_time_from" name="logout_time_from" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${logout_time_from eq 'PM'}">
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
                                                        Logout Time To <input  type="text" id="logout_time_to_hour" name="logout_time_to_hour" Placeholder ="HH" value="${logout_time_to_hour}"
                                                                               size="2" onchange="IsNumericForLogout(id)" onkeyup="IsNumericForLogout(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="logout_time_to_min" name="logout_time_to_min"  value="${logout_time_to_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumericForLogout(id)" onkeyup="IsNumericForLogout(id)">
                                                        <select id="logout_time_to" name="logout_time_to" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${logout_time_to eq 'PM'}">
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
                                                </tr>
                                                <tr>
                                                    <th align="right" colspan="3">
                                                        Error State &nbsp;&nbsp;
                                                        <select id="searchError_state" name="searchError_state" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${searchError_state eq 'Yes'}">
                                                                    <option>All</option>
                                                                    <option selected>Yes</option>
                                                                    <option>No</option>
                                                                </c:when>
                                                                <c:when test="${searchError_state eq 'No'}">
                                                                    <option>All</option>
                                                                    <option>Yes</option>
                                                                    <option selected>No</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option selected>All</option>
                                                                    <option>Yes</option>
                                                                    <option>No</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </th>
                                                    <td  colspan="2" align="left">
                                                        <input class="button" type="submit" name="task" id="searchIn" value="Search" onclick="return verify()">
                                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                        <input type="hidden" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getHealthStatuslist()">                                                      
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                            </tr>
                            <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
                            <tr>
                                <td align="center">

                                    <form name="form1" method="POST" action="logHistoryCont.do">
                                        <DIV class="content_div">
                                            <table id="table1" align="center" border="1" class="content" width="650">
                                                <tr>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading">Junction Name</th>
                                                    <th class="heading">City Name</th>
                                                    <th class="heading">IP Address</th>
                                                    <th class="heading">Port</th>
                                                    <th class="heading">Login Date</th>
                                                    <th class="heading">Login Time</th>
                                                    <th class="heading">Logout Date</th>
                                                    <th class="heading">Logout Time</th>
                                                    <th class="heading">Status</th>
                                                    <th class="heading">Error State</th>
                                                </tr>
                                                <c:forEach var="history" items="${requestScope['historyList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="junction_id_gen${loopCounter.count}" value="${history.junction_id}">
                                                            <input type="hidden" id="program_version_no_gen${loopCounter.count}" value="${history.program_version_no}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.city_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.ip_address}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.port}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.login_timestamp_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.login_timestamp_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.logout_timestamp_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.logout_timestamp_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.status eq true?"Yes":"No"}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${history.error_state}</td>
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
                                                <input type="hidden"  name="searchError_state"  value="${searchError_state}">
                                               
                                                <input  type="hidden" name="searchLoginDateFrom"  value="${searchLoginDateFrom}">
                                                <input  type="hidden" name="searchLoginDateTo"  value="${searchLoginDateTo}">
                                                <input  type="hidden" name="searchLogoutDateFrom" id="searchLogoutDateFrom" value="${searchLogoutDateFrom}">
                                                <input type="hidden" name="searchLogoutDateTo" id="searchLogoutDateTo" value="${searchLogoutDateTo}">


                                                <input  type="hidden" name="login_time_from_hour" Placeholder ="HH" value="${login_time_from_hour}">
                                                <input  type="hidden" name="login_time_from_min"  value="${login_time_from_min}" Placeholder ="MM">
                                                <input  type="hidden" name="login_time_from" value="${login_time_from}">

                                                <input type="hidden" name="login_time_to_hour" Placeholder ="HH" value="${login_time_to_hour}">
                                                <input  type="hidden" name="login_time_to_min"  value="${login_time_to_min}" Placeholder ="MM">
                                                <input  type="hidden" name="login_time_to"  value="${login_time_to}">

                                                <input  type="hidden" name="logout_time_from_hour" Placeholder ="HH" value="${logout_time_from_hour}">
                                                <input  type="hidden" name="logout_time_from_min"  value="${logout_time_from_min}" Placeholder ="MM">
                                                <input  type="hidden" name="logout_time_from" value="${logout_time_from}">

                                                <input type="hidden" name="logout_time_to_hour" Placeholder ="HH" value="${logout_time_to_hour}">
                                                <input  type="hidden" name="logout_time_to_min"  value="${logout_time_to_min}" Placeholder ="MM">
                                                <input  type="hidden" name="logout_time_to"  value="${logout_time_to}">
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>
                            <%--</c:if>--%>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv" STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="logHistoryCont.do" method="POST" >
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
                                        <form name="form2" id="form2" method="POST" action="logHistoryCont.do" onsubmit="return verify()">
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
