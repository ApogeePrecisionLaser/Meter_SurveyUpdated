<%---
    Document   : complaint
    Created on : Jul 26, 2013, 11:24:43 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--@taglib prefix="myfn" uri="http://myCustomTagFunctions" --%>
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

            var popupwin;
           
           jQuery(function(){
             $("#searchTypeOfComplaintNo").autocomplete("complaintAssingnedForCont.do", {
                    extraParams: {
                        action1: function() { return "getComplaintNoList";}
                    }
                });
             
                $("#assigned_for").autocomplete("complaintAssingnedForCont.do", {
                    extraParams: {
                        action1: function() { return "getAssignedForlist";}

                    }
                });
    
               $("#searchAssignedFor").autocomplete("complaintAssingnedForCont.do", {
                    extraParams: {
                        action1: function() { return "getAssignedForlist";}

                    }
                });


            });

          function assingedForList(id){
           $("#"+id).autocomplete("complaintAssingnedForCont.do", {
                    extraParams: {
                        action1: function() { return "getAssignedForlist";}

                    }
                });
    }
           
     
            function singleCheckAndUncheck(id){
                //   alert(document.getElementById('insertTable').rows.length);

                if ($('#checkPrint'+id).is(':checked')) {
                    $("#is_check"+id).val("1");

                }else{
                    $("#is_check"+id).val("0");
                }

            }
    function verify() {
            
                    try {

                     //   var result=true , feedback_type= "";
                        var assigned_comp,checkPrint;
                        if(document.getElementById("clickedButton").value == 'Save') {
                           assigned_comp = document.getElementById("assigned_comp").value;
                        if($.trim(assigned_comp).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Assigned For is required...</b></td>");
                          //  document.getElementById("assigned_comp").focus();
                            return false; // code to stop from submitting the form2.
                        }
                       checkPrint = document.getElementById("checkPrint").value;
                       if($.trim(checkPrint).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>CheckBox  is required...</b></td>");
                        //    document.getElementById("checkPrint").focus();
                            return false; // code to stop from submitting the form2.
                        }

                        }
                      //  else result = confirm("Are you sure you want to delete this record?")
                 
                    }catch(e){
                        alert(e);
                    }
                    return result;
           
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
            <%-- change here  ---%>
           <%--<c:if test="${isSelectPriv eq 'Y'}">
           <%-- change here  ---%>
            <tr>
                <td>

                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                  <%--      <form name="form1" method="post" action="complaintAssingnedForCont.do" onsubmit="return verifyform()">   --%>
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complaint Assinged For</td>
                                                <td><%@include file="/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                     <%--  <tr>    --%>
                            <tr>  <%-- Start Complaint type of search table --%>
                                <td  align="center">
                                    <form name="form0" method="POST" action="complaintAssingnedForCont.do">
                                        <table  align="center" width="800" class="heading1">
                                            <tr>
                                                <th>Assigned For </th>
                                                <td><input class="input" type="text" id="searchAssignedFor" name="searchAssignedFor" value="${searchAssignedFor}" size="25" ></td>
                                                <th>Complaint No</th>
                                                <td><input class="input" type="text" id="searchTypeOfComplaintNo" name="searchTypeOfComplaintNo" value="${searchTypeOfComplaintNo}" size="25" >                                                
                                                </td>
                                                <td><input  type="submit" name="task" id="search" value="Search"></td>
                                                <td><input  type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr <%--End Complaint type of search table --%>


                                <tr>
                                    <td align="center">
                                    <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                         <form name="form1" method="post" action="complaintAssingnedForCont.do" onsubmit="return verify()">
                                            <table id="table1" border="1" align="center" width="100%" class="reference">
                                                  <tr>
                                                        <c:if test="${not empty message}">
                                                            <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                        </c:if>
                                                    </tr>
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading1" >Complaint No.</th>
                                                    <th class="heading">Assigned for</th>
                                                    <th class="heading">Pole No.</th>
                                                    <th class="heading">Status</th>
                                                    <th class="heading">Coml. Type</th>
                                                    <th class="heading">Coml. Sub Type</th>
                                                    <th class="heading">Complaint Date</th>
                                                    <th class="heading">Posted By</th>                                                   
                                                    <th class="heading">Complition Date</th>
                                                    <th class="heading">Remark</th>
                                                   
                                                </tr>
                                                <c:forEach var="asso" items="${ComplaintRegisterList}" varStatus="loopCounter">
                                                    <tr>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="com_reg_id${loopCounter.count}" name="com_reg_id" value="${asso.complaint_reg_no}">
                                                           <input type="checkbox"  id="checkPrint${loopCounter.count}" name="checkPrint" value="" onclick="singleCheckAndUncheck(${loopCounter.count})">
                                                           <input type="hidden"  id="is_check${loopCounter.count}" name="is_check" value="">
                                                           <input type="hidden" id="compalint_rev_no${loopCounter.count}" name="compalint_rev_no" value="${asso.compalint_rev_no}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${asso.complaint_reg_no}</td>
                                                        <td> <input class="input"  id="assigned_for${loopCounter.count}" type="text" name="assigned_for" value="${asso.assigned_forM}"   onfocus="assingedForList(id)">  </td>
                                                   
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.site_nameM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.statusM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_typeM} </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_sub_typeM} </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_dateM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.posted_byM}</td>                                                       
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complition_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.remarkM}</td>
                                                       <%-- <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.assigned_forM}</td>  --%>
                                                  
                                                    </tr>
                                                </c:forEach>
                                                 <tr>
                                                    <td align='center' colspan="11">
                                                    <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);"style="display:<%--${myfn:isContainPrivileges(privilegeList, 'save') ? '' : 'none'}--%>">
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
                                                <input class="hidden" type="hidden" id="searchAssignedFor" name="searchAssignedFor" value="${searchAssignedFor}" size="25" >
                                                <%--   <input type="hidden" id="search" name="task" value="Search"> --%>
                                            <%--- <input type="hidden" id="search_compalint_date_from"name="search_compalint_date_from" value="${search_compalint_date_from}" size="15">
                                                  <input type="hidden" id="search_compalint_date_to"name="search_compalint_date_to" value="${search_compalint_date_to}" size="15">   --%>
                                               <input type="hidden" id="clickedButton" value="">
                                            </table>
                                         </form>
                                    </DIV>
                                   </td>
                                </tr>                                                 
                           </table>                           
                    </div>
                </td>
            </tr>
           <%-- </c:if>  --%>
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
