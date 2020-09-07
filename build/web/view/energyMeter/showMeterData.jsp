<%--
    Document   : wardView
    Created on : Jul 11, 2014, 10:09:08 AM
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
<script type="text/javascript" language="javascript" >
            jQuery(function(){

                $("#searchStatus").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchStatus" }
                    }
                });

                $("#searchDoc").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchDoc" }
                    }
                });

                $("#searchCaseNo").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchCaseNo" }
                    }
                });

                $("#searchClient").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchClient" }
                    }
                });

                $("#searchAdvocate").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchAdvocate" }
                    }
                });

                $("#searchTypist").autocomplete("caseDocsStatusCont.do", {
                    extraParams: {
                        action1: function() { return "getSearchTypist" }
                    }
                });

            });

            function checkUncheck(){
                var temp = document.getElementById("checkUncheckAll").checked;
                var items=document.getElementsByName('acs');
                if(temp){
                    for(var i=0; i<items.length; i++){
                        if(items[i].type=='checkbox'){
                            items[i].checked=true;
                        }
                        $("#meter_id"+i).val("1");
                       // alert("id= "+items[i]);
                    }

                }else{
                    for(var i=0;i<items.length;i++){
                        if(items[i].type=='checkbox'){
                            items[i].checked=false;
                        }
                        $("#meter_id"+i).val("0");
                       // alert("id= "+items[i]);
                    }
                }
            }



            function updateSelectedRecords(){

                var id = "";
                $('input[name="acs"]:checked').each(function(){
                    //if($(this).checked == true)
                    if(id == "")
                        id = this.value;

                    else
                        id = id + "," + this.value;

                });

                $("#meter_id").val(id);
                //alert(id);
            }

          function verify(){
              var result=true;
              if(document.getElementById("clickedButton").value == 'verify'){

                result = confirm("Are you sure you want verify record")
                return result;
            }else{

            }

          }

          function verify1(){
              var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Delete') {
            var division_name_m = document.getElementById("division_name_m").value;
            var a=document.getElementById("active").value;
            //    alert(a);
            if(myLeftTrim(division_name_m).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Ward No is required...</b></td>");
                document.getElementById("division_name_m").focus();
                return false; // code to stop from submitting the form2.
            }

            if(document.getElementById("active").value =='Revised' || document.getElementById("active").value =='Cancelled')
            {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on revised and cancelled record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to cancel this record?")
        return result;

          }

        </script>
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
                        <table align="center" width="100%">

                            <tr>
                                <td nowrap>
                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <div class="block1" style="width: 450px">
                                            <div><h1> Meter Details </h1></div>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <tr><td>
                                    <form name="form1" method="POST" action="ShowMeterDataCont.do">
                                        <DIV class="content_div">
                                            <table id="table1"  border="1" align="center" class="content">
                                                <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                                <tr>
<!--                                                    <th class="heading" >S.No.<input type="checkbox"    name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck()"/></th>-->
                                                    <th class="heading" >S.No.</th>
                                                    <th class="heading" >Meter Name</th>
                                                    <th class="heading" >Security Deposit</th>
                                                    <th class="heading" >SD_receipt_no</th>
                                                    <th class="heading" >Date</th>
                                                    <th class="heading" >Initial_reading</th>
                                                    <th class="heading" >City_name</th>
                                                    <th class="heading" >Meter_service_number</th>
                                                    <th class="heading" >Poll_no</th>
                                                    <th class="heading" >Organisation_name</th>
                                                    <th class="heading" >Org_office_code</th>
                                                    <th class="heading" >Switching_point</th>
                                                    <th class="heading" >Feeder_name</th>
                                                    <th class="heading" >Sanctioned_load_kw</th>
                                                    <th class="heading" >Reason</th>
                                                    <th class="heading" >Phase</th>
                                                    <th class="heading" >Accessed_load</th>
                                                    <th class="heading" >Effective_date</th>
                                                    <th class="heading" >Updated_date</th>
                                                    <th class="heading" >Calculated_load</th>
                                                    <th class="heading" >Description</th>
                                                    <th class="heading" >Tariff_code</th>
                                                    <th class="heading" >Msn_first_part</th>
                                                    <th class="heading" >Msn_sec_part</th>
                                                    <th class="heading" >Msn_third_part</th>
                                                    <th class="heading" >Msn_fourth_part</th>
                                                    <th class="heading" >Ivrs_no</th>
                                                    <th class="heading" >File_no</th>
                                                    <th class="heading" >Calculated_security_deposit</th>
                                                    <th class="heading" >Meter_name_auto</th>
                                                    <th class="heading" >Ward_no</th>
                                                    <th class="heading" >Bill_sanction_load</th>


                                                    <!--                                                    <th class="heading" >Audio</th>-->
                                                </tr>

                                                <c:forEach var="meterData" items="${requestScope['meterTypeList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center">
                                                            <input type="hidden" id="meter_id${loopCounter.count}" value="${meterData.meter_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="checkbox" id="rowcount${loopCounter.count}"  class="feeder_premises_check" value="${meterData.meter_id}" name="acs" onclick="updateSelectedRecords(id)">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.meter_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.security_deposit}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.sd_receipt_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.initial_reading}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.city_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.meter_service_number}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.poll_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.organisation_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.org_office_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.switching_point}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.feeder_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.sanctioned_load_kw}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.reason}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.accessed_load}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.effective_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.updated_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.calculated_load}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.description}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.tariff_code}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.msn_first_part}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.msn_sec_part}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.msn_third_part}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.msn_fourth_part}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.ivrs_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.file_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.calculated_security_deposit}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.meter_name_auto}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.ward_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" align="center" >${meterData.bill_sanction_load}</td>

                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td colspan="10" align="center">
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

                                            </table>
                                        </DIV>
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
<!--                                    <form name="form2" method="POST" action="ShowMeterDataCont.do" onsubmit="return verify1()" >-->
                                         <form name="form2" method="POST" action="ShowMeterDataCont.do">
                                        <table border="0"  id="table2" align="center" class="reference"  >

                                            <tr align="center">
                                                <td> <input class="button" type="submit" id="getStatus" name="task" value="Save_in_tube_well_detail" onclick="updateSelectedRecords()"></td>
                                                <td> <input class="button" type="submit" id="getStatus1" name="task" value="insertintometers" ></td>
                                                <td> <input class="button" type="submit" id="getStatus2" name="task" value="insertData" ></td>
                                                <td> <input class="button" type="submit" id="getStatus" name="task" value="Save_in_switching_point_detail" onclick="updateSelectedRecords()"></td>
                                            </tr>
                                            <input type="hidden" id="meter_id" name="meter_id" value="" >
<!--                                            <input type="hidden" id="check_typist_id" name="check_typist_id" value="" >-->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                        </table>
                                    </form>
                                </td>
                            </tr>

                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
