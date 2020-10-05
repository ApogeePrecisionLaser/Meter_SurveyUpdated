<%-- 
    Document   : primary_survey
    Created on : Sep 11, 2020, 4:33:53 PM
    Author     : user
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
      
        $("#meter_name_search1").autocomplete("CircuitSurveyController", {
            extraParams: {
                action1: function() { return "getmeter_name_search"}
            }
        });
       $("#irvs_no_search1").autocomplete("CircuitSurveyController", {
            extraParams: {
                action1: function () {
                    return "getirvs_search"
                }
            }
        });
        
         $("#circuit_search1").autocomplete("CircuitSurveyController", {
            extraParams: {
                action1: function () {
                    return "getcircuit_search"
                }

            }
        });
    });
  
// jQuery(function(){
//        $("#city_name").autocomplete("areaTypeCont", {
//            extraParams: {
//                action1: function() { return "searchCity"}
//            }
//        });
//
//        $("#ward_no").autocomplete("areaTypeCont", {
//            extraParams: {
//                action1: function() { return "searchWard"},
//                action2: function() { return  $("#searchCity").val();}
//            }
//        });
//
//  });
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {

        document.getElementById("area_name").disabled = false;
        document.getElementById("ward_no").disabled = false;
//        document.getElementById("ward_name").disabled = false;
        document.getElementById("remark").disabled = false;

        document.getElementById("save").disabled = false;
        if (id == 'new') {
            $("#message").html("");
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("area_name").focus();
        }
        if (id == 'edit') {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        }
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        //alert(noOfRowsTraversed);
        var noOfColumns = 5;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;
            higherLimit = (i + 1) * noOfColumns;
            rowNo++;
            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }
        //setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        for (var i = 0; i < noOfColumns; i++) {
            // set the background color of clicked/selected row to yellow.
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }
        // Now get clicked row data, and set these into the below edit table.
        //alert(document.getElementById("district_id"+rowNo).value);
        document.getElementById("area_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("area_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
//        document.getElementById("ward_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("ward_no").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;

        // Now enable/disable various buttons.
        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = true;
        }
        $("#message").html("");
    }
    function setStatus(id) {
        if (id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        } else if (id == 'save_As') {
            document.getElementById("clickedButton").value = "Save AS New";
        } else {
            document.getElementById("clickedButton").value = "Delete";
            ;
        }
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var ward_no = document.getElementById("ward_no").value;
            var city_name = document.getElementById("city_name").value;
            if (myLeftTrim(city_name).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>City  Name  is required...</b></td>");
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }

            if (myLeftTrim(ward_no).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Ward No is required...</b></td>");
                document.getElementById("ward_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var area_name = document.getElementById("area_name").value;
            if (myLeftTrim(area_name).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Area Name is required...</b></td>");
                document.getElementById("area_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }

        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }

    var popupwin = null;
    function displayMapList() {

        var queryString = "task=areaDetailReport";
        var url = "areaTypeCont?" + queryString;
        popupwin = openPopUp(url, "Area Details", 500, 1000);

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }
  
      function viewFirstPoleImage(circiut_id)
    {
        debugger;
        var queryString = "task=viewFirstPoleImage&circiut_id=" + circiut_id;

        var url = "CircuitSurveyController?" + queryString;
  

        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
    }
    function viewLastPoleImage(circiut_id)
    {

        debugger;
        var queryString = "task=viewlastPoleImage&circiut_id=" + circiut_id;

        var url = "CircuitSurveyController?" + queryString;
       alert(url);

        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
    }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Detail</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td align="center"  class="header_table"  width="100%">Circuit Survey Detail </td>
                                            <td>
                                                <div class="tab">
                                                    <a href="#"></a>

                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <div>
                                        <form name="form0" method="POST" action="CircuitSurveyController" >
                                            <table class="heading1" align="center" width="600">
                                                <tr>
                                                    <td>Meter Name<input  type="text" name="meter_name_search1" id="meter_name_search1" value="${meter_name_search}" size="20"></td>
                                                    <td>Irvs No<input  type="text" name="irvs_no_search1" id="irvs_no_search1" value="${irvs_search}" size="20"></td>

                                                    <td>Circuit No<input  type="text" name="circuit_search1" id="circuit_search1" value="${circuit_search}" size="20"></td>

                                                    <td>
                                                        <input class="button" type="submit" id="search" name="search" value="SEARCH">

                                                    </td>
                                                    <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>

                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="CircuitSurveyController">
                                        <DIV class="content_div" >
                                            <table  border="1" id="table1" align="center" width="600" class="content">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Circuit Name </th>
                                                    <th class="heading">Irvs No</th>
                                                    <th class="heading">Circuit No</th>
                                                    <th class="heading">Survey Time</th>

                                                    <th class="heading">Is Child</th>
                                                    <th class="heading">Parent</th>

                                                    <th class="heading">Time</th>
                                                    <th class="heading">Sync Status</th>

                                                    <th class="heading">Latitude First Pole</th>
                                                    
                                                    <th class="heading">Longitude First Pole</th>
                                                    
                                                    <th class="heading">Altitude First Pole</th>
                                                    
                                                    <th class="heading">Accuracy First Pole</th>
                                                    
                                                    <th class="heading">Latitude Last Pole</th>
                                                    
                                                    <th class="heading">Longitude Last Pole</th>
                                                    
                                                    <th class="heading">Altitude First Pole</th>
                                                    
                                                    <th class="heading">Accuracy Last Pole</th>
                                                    
                                                    <th class="heading">Image First Pole</th>
                                                    
                                                    <th class="heading">Image Last Pole</th>

                                                </tr>
                                                <c:forEach var="area" items="${requestScope['dataTypeList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${area.circuit_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.circuit_name}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.irvs_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.circuitno}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.is_child}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.parent}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.timestamptime}</td>

                                                        
                                                        
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.sync_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.lattitudefirstpole}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.longitudefirstpole}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.altitudefirstpole}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.accuracyfirstpole}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.lattitudelasttpole}</td>
                                                         <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.longitudelasttpole}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.altitudelastpole}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.accuracylasttpole}</td>
                                                        <td><center><input class="button" type="button" id="viewFirstPole1" value="viewFirstPole" onclick="viewFirstPoleImage(${area.circuit_id})"></center></td>

                                                        <td><center><input class="button" type='button' id="viewLastPole1" value ='viewLastPole' onclick="viewLastPoleImage(${area.circuit_id})"></center></td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="12">
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
                                                <input type="hidden" name="meter_name_search" value="${meter_name_search}">
                                                <input type="hidden" name="irvs_search" value="${irvs_search}">
                                                <input type="hidden" name="circuit_search" value="${circuit_search}">

                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <!--                            <tr>
                                                            <td align="center">
                                                                <DIV>
                                                                    <form name="form2" method="POST" action="areaTypeCont" onsubmit="return verify()">
                                                                        <table  border="0" id="table2" align="center" width="600" class="content">
                                                                            <tr id="message">
                            <c:if test="${not empty message}">
                                <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                            </c:if>
                        </tr>
                        <tr>
                            <th class="heading1">Area Name</th>
                            <td><input type="hidden" id="area_id" name="area_id" value="" >
                                <input class="input" type="text" id="area_name" name="area_name" size="30" value="" disabled>
                            </td>
                             <th class="heading1">Ward No</th>
                            <td>
                                <input class="input" type="text" id="ward_no" name="ward_no" size="30" value="" disabled>
                            </td>
                        </tr>
                            <tr>
                            
                            <th class="heading1">Remark</th>
                          <td><input class="input" type="text" id="remark" name="remark" value="" size="30" disabled></td>
                        </tr>
                       
                        
                        
                      
                        <tr>
                            <td align='center' colspan="4">
                                <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                            </td>
                        </tr>-->
                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                            <input type="hidden" id="clickedButton" value="">
                            <input type="hidden" name="searchWardNo" value="${ward_no}">
                            <input type="hidden" name="searchAreaName" value="${area_name}">
<!--                                                <input type="hidden" name="searchWardName" value="${ward_name}">-->
                        </table>

                        </form>
                    </DIV>
                </td>
            </tr>
        </table>

    </DIV>
</td></tr>
<tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
</table>
</body>
</html>

