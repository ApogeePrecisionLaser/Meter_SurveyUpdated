<%-- 
    Document   : switchingMapView.jsp
    Created on : Sep 15, 2014, 11:49:22 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>

<script type="text/javascript" language="javascript">
   

    jQuery(function(){


        $("#searchIVRSNo").autocomplete("switchingPointMap.do", {

            extraParams: {
                action1: function() { return "getsearchIVRSNo"}
            }
        });
        $("#ivrs_no_survey").autocomplete("switchingPointMap.do", {

            extraParams: {
                action1: function() { return "getIVRSNo"}
            }
        });
        $("#ivrs_no_meter").autocomplete("switchingPointMap.do", {

            extraParams: {
                action1: function() { return "getivrs_meter"}
            }
        });
        $("#searchService_Conn_No").autocomplete("switchingPointMap.do", {

            extraParams: {
                action1: function() { return "getsearchService_Conn_No"}
            }
        });
        $("#searchZone").autocomplete("switchingPointMap.do", {

            extraParams: {
                action1: function() { return "getsearchZone"}
            }
        });
        $("#meter_detail").autocomplete("switchingPointMap.do", {
            extraParams: {
                action1: function() { return "getMeter_detail"},
                action2: function() { return  $("#feeder_detail").val();}
            }
        });
        $("#switching_pt_detail").autocomplete("switchingPointMap.do", {
            extraParams: {
                action1: function() { return "getSwitching_pt_detail"},
                action2: function() { return $("#meter_detail").val();}
            }
        });
        $("#meter_meter").autocomplete("switchingPointMap.do", {
            extraParams: {
                action1: function() { return "getMeter_meter"},
                action2: function() { return  $("#feeder_meter").val();}
            }
        });
        $("#switching_pt_meter").autocomplete("switchingPointMap.do", {
            extraParams: {
                action1: function() { return "getSwitching_pt_meter"},
                action2: function() { return $("#meter_meter").val()}
            }
        });    
    });
   
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save").disabled = true;
        document.getElementById("ivrs_no_meter").disabled = false;
        document.getElementById("ivrs_no_survey").disabled = false;

        if(id == 'new') {

            $("#message").html("");
            document.getElementById("update").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save").disabled = false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 8);
            document.getElementById("switching_point_map_id").value = "";
            document.getElementById("ivrs_no_survey").focus();
      
        }

    }
   
    function setStatus(id) { 
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
        else if(id == 'delete'){
            document.getElementById("clickedButton").value = "Delete";
        }
       
    }
    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Update') {
           var ivrs_no_survey = document.getElementById("ivrs_no_survey").value;
                var ivrs_no_meter = document.getElementById("ivrs_no_meter").value;

            if(myLeftTrim(ivrs_no_survey).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Survey IVRS No. is required...</b></td>");
                document.getElementById("ivrs_no_survey").focus();
                return false; // code to stop from submitting the form2.
            } 
          <%--  if(myLeftTrim(ivrs_no_meter).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Meter IVRS No. is required...</b></td>");
                document.getElementById("ivrs_no_meter").focus();
                return false; // code to stop from submitting the form2.
            }   --%>
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(document.getElementById("clickedButton").value == 'Update'){
                result = confirm("Are you sure you want to update it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to delete this record?")
        return result;
    }

    function fillColumns(id) {
       
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 8;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);
        var t1id = "t1c";  
       
        document.getElementById("switching_point_map_id").value = document.getElementById(t1id +(lowerLimit+0)).innerHTML;
        document.getElementById("ivrs_no_survey").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("ivrs_no_meter").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
       
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
       
        //  document.getElementById("message").innerHTML = "";
        $("#message").html("");
        makeEditable('');
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
            
    function IsNumeric(id) {
        var strString = document.getElementById(id).value;
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
                var  time = document.getElementById(id).value;
                if(id=="submision_time_hour" || id=='receipt_time_hour'){
                    if(time>12){
                        document.getElementById(id).value="";
                        alert("Hour should Be less than 12");
                        return;
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
    var popupwin = null;
    function displayMapList(){
       var searchIVRSNo =  $("#searchIVRSNo").val();
       var searchZone = $("#searchZone").val();
     var queryString = "task=generateMapReport&searchIVRSNo="+searchIVRSNo+"&searchZone="+searchZone;
        var url = "switchingPointMap.do?"+queryString;
        popupwin = openPopUp(url, "Survey-Meter Map", 500, 1000);

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
           


</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Switching Point Map</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body"  style="width:95%" class="maindiv" align="center" >
                    <table width="95%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">SURVEY - METER MAP</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="switchingPointMap.do">
                                        <table align="center" class="heading1" width="590">
                                            <tr>
                                                <td align="center">IVRS No.<input class="input" type="text" id="searchIVRSNo" name="searchIVRSNo" value="${searchIVRSNo}" size="20" ></td>
                                              <%--  <td align="center">Service Conn. No.<input class="input" type="text" id="searchService_Conn_No" name="searchService_Conn_No" value="${searchService_Conn_No}" size="20" ></td>   --%>
                                                <td align="center">Zone<input class="input" type="text" id="searchZone" name="searchZone" value="${searchZone}" size="20" ></td>

                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <DIV class="content_div">
                                    <form name="form00" method="POST" action="switchingPointMap.do">
                                        <table id="table1" align="center"  class="content" width="920">
                                            <tr>
                                                <th class="heading" style="width: 20px">S.No.</th>
                                        <!--        <th class="heading" style="width: 20px">Service Con. No (S)</th>
                                                <th class="heading" style="width: 20px">Service Con. No (M)</th>  -->
                                                <th class="heading" style="width: 20px">IVRS No. (S)</th>
                                                <th class="heading" style="width: 20px">IVRS No. (M)</th>
                                          <!--      <th class="heading" style="width: 20px">Swtchg. Pt. Name (S)</th>
                                                <th class="heading" style="width: 20px">Swtchg. Pt. Name (M)</th>   -->
                                                <th class="heading" style="width: 20px">Zone (S)</th>
                                                <th class="heading" style="width: 20px">Zone (M)</th>
                                          <!--      <th class="heading" style="width: 20px">Feeder (S)</th>
                                                <th class="heading" style="width: 20px">Feeder (M)</th>   -->
                                                <th class="heading" style="width: 20px; white-space: normal ">Measured Load KW (S)</th>
                                                <th class="heading" style="width: 20px; white-space: normal">Sanctioned Load KW (M)</th>
                                          <!--      <th class="heading" style="width: 20px">Address (S)</th>
                                                <th class="heading" style="width: 20px">Address (M)</th>   -->
                                            </tr>

                                            <c:forEach var="sw_point" items="${requestScope['sw_pointList']}" varStatus="loopCounter">
                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${sw_point.switching_point_map_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                             <%--   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.service_conn_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.sevice_conn_no_meter}</td>   --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.ivrs_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.ivrs_no_meter}</td>
                                             <%--   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.switching_point_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.switching_point_name_meter}</td>  --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.zone}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.zone_meter}</td>
                                             <%--   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.feeder}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.feeder_meter}</td>   --%>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.measured_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.sanctioned_load}</td>
                                             <%--   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.address}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.address_meter}</td>  --%>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="8">
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
                                                </td> </tr>
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" name="switching_point_filter" id="switching_point_filter" value="${switching_point_filter}" >
                                            <input type="hidden" name="location_filter" id="location_filter" value="${location_filter}" >
                                            <input type="hidden" name="feeder_filter" id="feeder_filter" value="${feeder_filter}" >
                                            <input type="hidden" name="zone_filter" id="zone_filter" value="${zone_filter}" >
                                            <input type="hidden" id="searchZone" name="searchZone" value="${searchZone}" size="20" >
                                            <input type="hidden" name="switching_pt_no_filter" id="switching_pt_no_filter" value="${switching_pt_no_filter}" >
                                            <input type="hidden" name="switching_point_type_filter" id="switching_point_type_filter" value="${switching_point_type_filter}" >
                                        </table>
                                    </form>
                                </DIV>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="switchingPointMap.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="520">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th bgcolor="#F0F8FF" colspan="2" align="center">SURVEY</th>

                                                <th bgcolor="#F0F8FF" colspan="2" align="center">METER</th>
                                            </tr>
                                            <tr>
                                                <td style="display: none"><input type="text" class="input" id="switching_point_map_id" name="switching_point_map_id" size="26" value=""></td>
                                                <th >IVRS No.</th><td><input type="text" class="input" id="ivrs_no_survey" name="ivrs_no_survey" size="32" disabled></td>
                                                <th >IVRS No.</th><td><input type="text" class="input" id="ivrs_no_meter" name="ivrs_no_meter" size="32" disabled></td>
                                            </tr>


                                        </table>
                                        <table>  <tr>
                                                <td align='center' colspan="6">
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">

                                            <input  type="hidden" id="addRowCount" name="addRowCount" value="0" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>

            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
