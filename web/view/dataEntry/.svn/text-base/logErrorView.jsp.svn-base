<%--
    Document   : logErrorView
    Created on : Oct 20, 2015, 3:02:53 PM
    Author     : Navitus1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" src="JS/table.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
 <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
  <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
  <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
  <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>JSP Page</title>
        <script type="text/javascript">
           jQuery(function(){
                $("#searchJunctionName").autocomplete("logErrorCont", {
            extraParams: {
                action1: function() { return "searchJunction"}
                        }
        });
            $("#searchErrorMsg").autocomplete("logErrorCont", {
            extraParams: {
                action1: function() { return "searchErrorMessage"}
                        }
        });
         $("#searchReadingDateFrom").result(function(event, data, formatted){
                    $("#searchReadingDateTo").value = "";
                    $("#searchReadingDateTo").flushCache();
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
                $("#searchDate").datepicker({
                        minDate: -100,
                        showOn: "both",
                        buttonImage: "images/calender.png",
                        dateFormat: 'dd-mm-yy',
                        buttonImageOnly: true,
                        changeMonth: true,
                        changeYear: true
                });
           });
        function checkAllField()
        {
            
            var value=document.getElementById("checkfield").checked;            
            if(value==true)
                {
                  $('.allcheck').attr("checked",true);
                  document.getElementById("revisedbutton").disabled=false;
                  $('.allcheck').focus();
             
                }
                else{
                     $('.allcheck').attr("checked",false);
                     document.getElementById("revisedbutton").disabled=true;
                }
   
        }
      
         function checkField(id)
         {   
             var columnId = id;
             columnId = columnId.substring(3, id.length);            
             var t1id = "t1c";           
             var value=document.getElementById(t1id + columnId).checked;
             var colId = parseInt(columnId);
                      if(value==true)
                      {                      
                             document.getElementById(t1id+(colId+1)).value=document.getElementById(t1id+(colId-7)).innerHTML;
                             var recivemessage=document.getElementById(t1id + (colId-2)).innerHTML;
                             var imeino="imeiNo= ";
                             var emno="energyMeterNo= ";
                             var firstposition=recivemessage.indexOf("imeiNo");
                             var lastposition=recivemessage.indexOf("energyMeterNo");
                             var crcposition=recivemessage.indexOf("crc");
                             var imeinovalue=recivemessage.substring(firstposition+imeino.length , lastposition-1);                             
                             var emnovalue=recivemessage.substring(lastposition+emno.length, crcposition-1);                            
                             document.getElementById(t1id +(colId+2)).value=imeinovalue;
                             document.getElementById(t1id + (colId+3)).value=emnovalue;
                             document.getElementById("revisedbutton").disabled=false;
                         }
                         else
                             {  document.getElementById(t1id +(colId+1)).value=0;
                                document.getElementById(t1id +(colId+2)).value=0;
                                 document.getElementById(t1id + (colId+3)).value=0;
                                 document.getElementById("revisedbutton").disabled=true;
                             }               
                       }

         function fieldConfirmation()
         {           
             var value=$('.allcheck').attr('checked');
             if(value==false)
                 {
                     alert("Sorry,No One Field is Selected!!");
                     return false;
                 }else
                     {
                     var mConfirm=confirm("Do You Want to Update ?");
                     if(mConfirm==true)
                         {
                             return true;
                         }
                         else{return false;}
                     }
         }
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

     function showDate(){
            
                if($('input[name="monthType"]:checked').val()=='single'){
                    $("#duration").show();               
                    $("#duration1").hide();
                    $("#duration2").hide();
                    $("#radioSingle").attr("checked", true);
                    document.getElementById("searchDate").disabled=false;


                } else{
                    $("#duration1").show();
                    $("#duration2").show();                   
                    $("#duration").hide();                    
                    $("#radioPeriod").attr("checked", true);
                    document.getElementById("searchDate").disabled=true;
                }
            }

            jQuery(function() {
                $(document).ready(function () {
                        $("#duration1").show();
                        $("#duration2").show();
                        $("#duration").hide();
                        $("#radioPeriod").attr("checked", true);
                    
                });

            });

//           function callJunction(JName)
//           {
//
//                var action="task=logErrorView&searchJunction="+JName;
//                var url="junctionCont.do?"+action;
//                popup = popupWindow(url,"Junction");
//            }
//            function popupWindow(url,windowName)
//            {
//                var windowfeatures= "left=50, top=50, width=1000, height=500, resizable=no, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";
//                return window.open(url,windowName,windowfeatures)
//            }

    
//    $(document).ready(function(){
//    $("form").submit(function(){
//        var errmsg=document.getElementById("searchErrorMsg").value;
//        if(errmsg=='imeiNo not matched'){
//
//            alert("value="+document.getElementById("searchErrorMsg").value);
//           // var action="task=Errormsgview&searchErrorMsg="+errmsg;
//            //var url="errormsgcont.do?"+action;
//
//
//        }
//    });
//   });
    </script>
    </head>
    <body><table align="center" cellpadding="0" cellspacing="0" class="main">
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
                                        <td align="center" class="header_table" width="100%">Error Log DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" id="form0" method="POST" action="logErrorCont" onsubmit="return verify()">
                                        <table align="center" class="heading1" width="800">
                                            <tr>
                                                     <td align="left" style="background-color:thistle">Junction &nbsp;&nbsp; <input class="input" type="text" id="searchJunctionName" name="searchJunctionName" value="${searchJunction}" size="28" ></td>
                                                     <td align="left" style="background-color:thistle">Error Message<input class="input" type="text" id="searchErrorMsg" name="searchErrorMsg" value="${searchErrorMessage}" size="31"  ></td>
                                                     <td align="right"><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                     <td align="right" ><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                     </tr>

                                                     <tr> <td>
                                                    <font style="background-color:thistle">Date</font>
                                                    <input type="radio" name="monthType" id="radioSingle" value="single"  onchange="showDate()">single
                                                    <input type="radio" name="monthType" id="radioPeriod" value="period" onchange="showDate()">Period
                                                   </td></tr>

                                                   <tr><td >
                                                    <div id="duration" style="background-color:thistle">
                                                      Date <input class="input1" type="text" name="searchDate" id="searchDate" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}" disabled>
                                                      
                                                    </div></td></tr>
                                                   <tr><td><div  id="duration1" style="background-color:thistle">
                                                     Date From<input class="input1" type="text" name="searchReadingDateFrom" id="searchReadingDateFrom" value="${searchReadingDateFrom == '' ? '' : searchReadingDateFrom}">
                                                     </div> </td>
                                                    <td><div  id="duration2" style="background-color:thistle">
                                                     Date To<input class="input1" type="text" name="searchReadingDateTo" id="searchReadingDateTo" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}">
                                                    </div></td>
                                                   </tr>
<!--                                                <td style="display:none">Switching Point No<input class="input" type="text" id="searchSwitchNo" name="searchSwitchNo" value="${searchSwitchNo}" size="22" ></td>-->
                                    

                                    

                                               <tr align="center">
                                                    <th align="center" style="background-color:thistle">
                                                        Time From &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_from_hour" name="reading_time_from_hour" Placeholder ="HH" value="00"
                                                                                            size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_from_min" name="reading_time_from_min"  value="00" Placeholder ="MM"
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
                                                    <th align="center" style="background-color:thistle">
                                                        Time To &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_to_hour" name="reading_time_to_hour" value="00"
                                                                                          size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_to_min" name="reading_time_to_min"  value="00"
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
                                                    </th> </tr>
                                            </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="logErrorCont">
                                    <DIV class="content_div">
                                        
                                        <table id="table1" width="700"  border="1"  align="center" class="content">
                                              <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th style="white-space: normal" class="heading" >S.No.</th>
                                                <th style="white-space: normal" class="heading">Junction Id</th>
                                                <th style="white-space: normal" class="heading">Program Version No</th>
                                                <th style="white-space: normal" class="heading">Switching PointName</th>
                                                <th style="white-space: normal" class="heading">Error Message</th>
                                                <th style="white-space: normal" class="heading">Time Stamp</th>
                                                <th style="white-space: normal" class="heading">Received Data</th>
                                                <th style="white-space: normal" class="heading">Sent Data</th>
                                              <c:if test="${status eq 'true'}">  <th style="white-space: normal" class="heading">Check<br>
                                                      <input type="checkbox" id="checkfield" class="checkfield" onclick="checkAllField()"/></th>
                                              </c:if>
                                            </tr>                         
                                                                                     
                                               
                                             <c:forEach var="logBean" items="${requestScope['logList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${loopCounter.count+lowerLimit - noOfRowsTraversed}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)"  align="center">${logBean.junction_id}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${logBean.pro_ver_no}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" style="white-space: normal" align="center"><a href="junctionCont.do?searchName=${logBean.switching_name}" style="text-decoration: none" target="_blank">${logBean.switching_name}</a></td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="white-space: normal"  align="center">${logBean.error_mess}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="white-space: normal"  align="center">${logBean.timestamp}</td>
                                                   <td align="center">
                                                       <textarea cols="30" rows="3" name="receivedData" id="t1c${IDGenerator.uniqueID}" readonly>${logBean.recieved_data}</textarea>
                                                   </td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                   <textarea cols="15" rows="2" name="sentData" id="SentData">${logBean.sent_data}</textarea>
                                                   </td>
                                                   <c:if test="${status eq 'true'}">
                                                   <c:if test="${(logBean.status)=='true'}">
                                                   <td  align="center">                                                   
                                                       <input type="checkbox" name="check" class="allcheck" id="t1c${IDGenerator.uniqueID}" onfocus="checkField(id)" onclick="checkField(id)"/><br>
                                                    <input type="hidden" id="t1c${IDGenerator.uniqueID}" name="jId" value="0"/>
                                                    <input type="hidden" id="t1c${IDGenerator.uniqueID}" name="imeino" value="0"/>
                                                    <input type="hidden" id="t1c${IDGenerator.uniqueID}" name="emno" value="0" />
                                                   </td> 
                                                   </c:if>
                                                   </c:if>
                                                   </tr>
                                              </c:forEach>                                       
                                                  <tr>
                                                <td align='center' colspan="9" style="margin-left: 250px">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                
                                                    <c:if test="${status eq 'true'}">
                                                        <input class="button" id="revisedbutton" style="margin-left: 550px"  align="right" type="submit" name="buttonAction" value="Update" onclick="return fieldConfirmation()" disabled/></c:if></td>
                                               </tr>                                         
                                   <input type="hidden" name="lowerLimit" value="${lowerLimit}" id="lowerLimit"/>
                                   <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}"/>
                                   <input type="hidden" id="searchJunctionName" name="searchJunctionName" value="${searchJunction}"/>
                                   <input type="hidden" id="searchErrorMsg" name="searchErrorMsg" value="${searchErrorMessage}"/>                                   
                                   <input type="hidden" name="searchReadingDateFrom" id="searchReadingDateFrom" value="${searchReadingDateFrom }"/>
                                   <input type="hidden" name="searchReadingDateTo" id="searchReadingDateTo" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}"/>
                                  <input type="hidden" name="searchDate" id="searchDate" value="${searchDate }"/>
                                 </table>
                                    </DIV>
                                   
                                </form>
                            </td></tr></table></DIV>
          </td></table>

    </body>
</html>
