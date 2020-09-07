<%--
    Document   : starterType
    Created on : Dec 30, 2015, 5:59:05 PM
    Author     : Navitus2
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
                $("#searchstartertype").autocomplete("startertypeCont.do", {
                    extraParams: {
                    action1: function(){ return "getStarterType"}
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
        // alert(id);

        document.getElementById("starter_type").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;
        if(id == 'new') {
            $("#message").html("");
            document.getElementById("remark").disabled = false;
            document.getElementById("starter_type").disabled = false;
            document.getElementById("edit").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("starter_type").focus();
        }
        if(id == 'edit') {
            document.getElementById("save_As").disabled = false;
            document.getElementById("delete").disabled = false;
        }
     }
      function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 3;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;
        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;
            higherLimit = (i + 1) * noOfColumns;
            rowNo++;
            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }
        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        for(var i = 0; i < noOfColumns; i++) {
            // set the background color of clicked/selected row to yellow.
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }
        // Now get clicked row data, and set these into the below edit table.
        //alert(document.getElementById("district_id"+rowNo).value);
        document.getElementById("starter_id").value= document.getElementById("starter_id"+rowNo).value;
        document.getElementById("starter_type").value = document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;

        // Now enable/disable various buttons.
        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
          document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = true;
        }
        $("#message").html("");
        }

    function setStatus(id) {
        if(id == 'save') {
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else {
            document.getElementById("clickedButton").value = "Delete";;
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
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var starter_type = document.getElementById("starter_type").value;

           if(myLeftTrim(starter_type).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Starter type is required...</b></td>");
                document.getElementById("starter_type").focus();
                return false; // code to stop from submitting the form2.
            }


//            var remark = document.getElementById("remark").value;
//            if(myLeftTrim(remark).length == 0) {
//                $("#message").html("<td colspan='2' bgcolor='coral'><b>remark is required...</b></td>");
//                document.getElementById("remark").focus();
//                return false; // code to stop from submitting the form2.
//            }
            if(result == false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }

        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Starter Type</title>
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
                                            <td align="center"  class="header_table"  width="100%">Starter Type</td>
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
                                        <form name="form0" method="POST" action="startertypeCont.do" >
                                            <table class="heading1" align="center" width="600">
                                                <tr>
                                                    <th class="heading1">Starter Type</th>
                                                    <td><input  type="text" name="searchstartertype" id="searchstartertype" value="${searchstartertype}" size="25"></td>

                                                    <td><input class="button" type="submit" id="search" name="search" value="SEARCH"></td>
                                                     <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>

                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="startertypeCont.do">
                                        <DIV class="content_div" >
                                            <table  border="1" id="table1" align="center" width="600" class="content">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Starter Type</th>
                                                    <th class="heading">Remark</th>


                                                </tr>
                                                <c:forEach var="list" items="${requestScope['starterTypeList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            <input type="hidden" id="starter_id${loopCounter.count}" value="${list.starter_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${list.starter_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${list.remark}</td>

                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="6">
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
                                                <input type="hidden" name="searchstartertype" value="${searchstartertype}">


                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>

                            <tr>
                                <td align="center">
                                    <DIV>
                                        <form name="form2" method="POST" action="startertypeCont.do" onsubmit="return verify()">
                                            <table  border="0" id="table2" align="center" width="600" class="content">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Starter Type</th>
                                                    <td><input type="hidden" id="starter_id" name="starter_id" value="" >
                                                        <input class="input" type="text" id="starter_type" name="starter_type" size="30" value="" disabled>
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
                                                </tr>
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                                <input type="hidden" name="searchstartertype" value="${searchstartertype}">

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

