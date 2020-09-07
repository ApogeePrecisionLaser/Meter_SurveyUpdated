<%-- 
    Document   : roadView
    Created on : Jul 10, 2014, 2:42:25 PM
    Author     : JPSS
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
        $("#searchRoadNameType").autocomplete("roadTypeCont", {
            extraParams: {
                action1: function() { return "getRoadType"}
            }
        });
         $("#searchRoadUseType").autocomplete("roadTypeCont", {
            extraParams: {
                action1: function() { return "getRoadUseType"}
                  }
        });
         $("#searchRoadCatType").autocomplete("roadTypeCont", {
            extraParams: {
                action1: function() { return "getRoadCatType"}
                  }
        });
    });
    jQuery(function(){
        $("#road_use").autocomplete("roadTypeCont", {
            extraParams: {
                action1: function() { return "getRoadUseType"}
            }
        });

        $("#category_name").autocomplete("roadTypeCont", {
            extraParams: {
                action1: function() { return "getRoadCatType"}
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
        document.getElementById("road_name").disabled = false;
        document.getElementById("road_use").disabled = false;
        document.getElementById("category_name").disabled = false;
        document.getElementById("start_landmark").disabled = false;
        document.getElementById("end_landmark").disabled = false;
        document.getElementById("created_by").disabled = true;
        document.getElementById("created_date").disabled = true;
        document.getElementById("remark").disabled = false;
        document.getElementById("active").disabled = false;
        document.getElementById("approx_length").disabled = false;
        document.getElementById("central_light").disabled = false;
     //   document.getElementById("save").disabled = false;
        document.getElementById("revise").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save_As").disabled =false;
        document.getElementById("save").disabled = true;
        if(id == 'new') {
             document.getElementById("created_date").disabled = true;
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
         //  document.getElementById("edit").disabled = true;
            document.getElementById("update").disabled = true;
            document.getElementById("revise").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
           
           
           /* document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true; */
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 12);
            document.getElementById("road_name").focus();

        }
       /* if(id == 'edit'){
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        }  */
    }
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
  
       else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }
       else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else document.getElementById("clickedButton").value = "Delete";
    }
    function verify() {
   var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New'|| document.getElementById("clickedButton").value == 'Update' || document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Delete') {
            var road_name = document.getElementById("road_name").value;
                var road_use =document.getElementById("road_use").value;
                var category_name = document.getElementById("category_name").value;
                var start_landmark = document.getElementById("start_landmark").value;
                var end_landmark  =  document.getElementById("end_landmark").value;
      // var a=  document.getElementById("clickedButton").value;
       //  alert(a);
       //var regEx = /^\d{2}\.\d{2}$/;
          if(myLeftTrim(road_name).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Road Name is required...</b></td>");
                document.getElementById("road_name").focus();
                return false; // code to stop from submitting the form2.
            }
             
             if(myLeftTrim(start_landmark).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Start Landmark is required...</b></td>");
                document.getElementById("start_landmark").focus();
                return false; // code to stop from submitting the form2.
            }

            if(myLeftTrim(end_landmark).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>End Landmar is required...</b></td>");
                document.getElementById("end_landmark").focus();
                return false; // code to stop from submitting the form2.
            }

            if(myLeftTrim(road_use).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Road Use is required...</b></td>");
                document.getElementById("road_use").focus();
                return false; // code to stop from submitting the form2.
            }
            if(myLeftTrim(category_name).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Road Category Name is required...</b></td>");
                document.getElementById("category_name").focus();
                return false; // code to stop from submitting the form2.
            }                        
             if(document.getElementById("active").value =='N')
            {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>You can not perform any operation on inactive record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            var regex = /^[1-9]\d*(\.\d*)?$/
            if(!document.getElementById("approx_length").value.match(regex)){
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Please insert valid Numeric Value in Approximate Length...</b></td>");
                return false; // code to stop from submitting the form2.
            }
           
           if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }
    
            if(document.getElementById("clickedButton").value == 'Save AS New'){
                //    alert(a);
               result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
           else if(document.getElementById("clickedButton").value == 'Update'){
                result = confirm("Are you sure you want to update this record?")
                return result;
            }
            else if(document.getElementById("clickedButton").value == 'Revise'){
                result = confirm("Are you sure you want to revise this record?")
                return result;
            } 
         } if(document.getElementById("clickedButton").value == 'Delete'){
             result = confirm("Are you sure you want to Cancelled this record?")
           return result;
    // }
          }
        return result;
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

function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 12;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("road_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("road_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("start_landmark").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("end_landmark").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("road_use").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("category_name").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("central_light").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("road_rev_no").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        //document.getElementById("created_date").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
        document.getElementById("approx_length").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
      /*  document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("delete").disabled = false;
        } */
         makeEditable('');
         if(document.getElementById("active").value == 'Cancelled' || document.getElementById("active").value == 'Revised')
          {
        document.getElementById("road_id").value= "";
        document.getElementById("road_name").value = "";
        document.getElementById("start_landmark").value = "";
        document.getElementById("end_landmark").value = "";
        document.getElementById("road_use").value = "";
        document.getElementById("category_name").value = "";
        document.getElementById("central_light").value = "";
        document.getElementById("road_rev_no").value = "";
        //document.getElementById("created_date").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        document.getElementById("remark").value = "";
        document.getElementById("active").value = "";
        document.getElementById("approx_length").value = "";
    makeEditable('');
    /*    document.getElementById("edit").disabled = true;
        document.getElementById("save").disabled = true;
        document.getElementById("delete").disabled = true;  */
          }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
    }
    var popupwin = null;
            function displayMapList(){

          var  searchRoadNameType=document.getElementById("searchRoadNameType").value;
          var  searchRoadUseType=  document.getElementById("searchRoadUseType").value;
          var searchRoadCatType=document.getElementById("searchRoadCatType").value;
             
             
             var queryString = "task=generateMapReport&searchRoadNameType="+searchRoadNameType+"&searchRoadUseType="+searchRoadUseType+"&searchRoadCatType="+searchRoadCatType;


               var url = "roadTypeCont?"+queryString;

              popupwin = openPopUp(url, "Road Type Map Details", 500, 1000);

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
        <title>Road Page</title>
    </head>
    <body>
           <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">ROAD DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="roadTypeCont">
                                        <table align="center" class="heading1" width="600">
                                            <tr>

                                                <td>Road Name<input class="input" type="text" id="searchRoadNameType" name="searchRoadNameType" value="${searchRoadNameType}" size="35" ></td>
                                                <td>Road Use<input class="input" type="text" id="searchRoadUseType" name="searchRoadUseType" value="${searchRoadUseType}" size="20" ></td>
                                                <td>Road Category<input class="input" type="text" id="searchRoadCatType" name="searchRoadCatType" value="${searchRoadCatType}" size="20" ></td>
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
                                <form name="form1" method="POST" action="roadTypeCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Road Name</th>
                                                <th class="heading">Start LandMark</th>
                                                <th class="heading">End LandMark</th>
                                                <th class="heading">Road Use</th>
                                                <th class="heading">Road Category</th>
                                                <th class="heading">Central Light</th>
                                                <th class="heading">Road R.No.</th>
                                               <!-- <th class="heading">Created By</th>-->
                                          <!--      <th class="heading">Created Date</th>-->
                                                <th class="heading">Remark</th>
                                                <th class="heading">Active</th>
                                                <th class="heading">Approximate Length</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="roadTypeBean" items="${requestScope['roadTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                   
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${roadTypeBean.road_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.road_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.start_landmark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.end_landmark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.road_use}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.category_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.central_light}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.road_rev_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.active}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${roadTypeBean.approx_length}</td>
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
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchRoadNameType" name="searchRoadNameType" value="${searchRoadNameType}" >
                                            <input  type="hidden" id="searchRoadUseType" name="searchRoadUseType" value="${searchRoadUseType}" >
                                            <input  type="hidden" id="searchRoadCatType" name="searchRoadCatType" value="${searchRoadCatType}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="roadTypeCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Road Name</th>
                                                <td>
                                                    <input class="input" type="hidden" id="road_id" name="road_id" value="$(searchRoadNameType)" >
                                                    <input class="input" type="text" id="road_name" name="road_name" value="" size="25" disabled>
                                                </td>
                                          
                                                <th class="heading1">Start LandMark</th>
                                                <td><input class="input" type="text" id="start_landmark" name="start_landmark" value="" size="25" disabled></td>
                                          
                                                <th class="heading1">End LandMark</th>
                                                <td><input class="input" type="text" id="end_landmark" name="end_landmark" value="" size=25" disabled></td>
                                            </tr>

                                             <tr>
                                                <th class="heading1">Road Use</th>
                                                <td><input class="input" type="text" id="road_use" name="road_use" value="" size="25" disabled></td>
                                            
                                                <th class="heading1">Road Category</th>
                                                <td><input class="input" type="text" id="category_name" name="category_name" value="" size="25" disabled></td>
                                           
                                                <th class="heading1">Central light</th>
                                              <%--  <td><input class="input" type="text" id="central_light" name="central_light" value="" size="25" disabled></td>   --%>
                                              <td>
                                                    <select name="central_light" id="central_light" disabled>
                                                            <option>Y</option>
                                                            <option selected>N</option>
                                                    </select>
                                                </td>
                                             </tr>
                                            <tr style="display:none">
                                                <th class="heading1">Road Rev No</th>
                                                <td><input class="input" type="text" id="road_rev_no" name="road_rev_no" value="" size="25" ></td>
                                            </tr>
                                            <tr style="display:none">
                                                <th class="heading1">Created By</th>
                                                <td><input class="input" type="text" id="created_by" name="created_by" value="" size="25" disabled></td>
                                            </tr>
                                            <tr style="display:none">
                                                <th class="heading1">Created Date</th><td><input class="input" type="text" id="created_date" name="created_date" value="" size="25" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Approximate Length</th><td><input class="input" type="text" id="approx_length" name="approx_length" value="" size="25" disabled></td>
                                                <th class="heading1">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="25" disabled></td>
                                            </tr>
                                            <tr style="display:none">
                                                <th class="heading1">Active</th>
                                               <td><input class="input" type="text" id="active" name="active" value="" size="25" ></td>
                                              <!--  <td>
                                                    <select name="active" id="active" disabled>
                                                            <option>Y</option>
                                                            <option>N</option>
                                                    </select>
                                                </td>-->

                                            </tr>
                                            <tr>
                                                <td align='center' colspan="6">
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>
                                                    <input style="display:none" class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Cancel" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input  type="hidden" name="searchRoadNameType" value="${searchRoadNameType}" >
                                            <input  type="hidden" name="searchRoadUseType" value="${searchRoadUseType}" >
                                            <input  type="hidden" name="searchRoadCatType" value="${searchRoadCatType}" >
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
