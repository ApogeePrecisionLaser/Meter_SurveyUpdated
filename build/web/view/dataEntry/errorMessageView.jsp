<%-- 
    Document   : errorMessageView
    Created on : Nov 5, 2015, 12:01:46 PM
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
        <%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <title>JSP Page</title>
        <script type="text/javascript">
             jQuery(function(){
                $("#searchMessage").autocomplete("errorMessageCont.do", {
            extraParams: {
                action1: function() { return "searchMessage"}
                        }
        });
        });
        function verify()
        {
            var message=document.getElementById("message").value;
            var remark=document.getElementById("remark").value;
            var createdby=document.getElementById("createdby").value;
            var active=document.getElementById("active").value;
            if(message==null||message==""||message.length==0)
                {
                alert("Sorry,Message Field is Required!!");
                return false;
                }else
                    { if(active=='Select.....' || active==null)
                            {
                       alert("Sorry, Active Field is Required!!");
                          return false;
                            }
                    }
                     if(document.getElementById("message_id").value == 0)
                      {
                        addRow(message,remark,createdby,active);
                        document.getElementById('ffmm').reset();
                        return  false;
                    }
        }
       function fillColumns(id)
       {
           
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 7;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed;i++)
        {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            if((columnId>= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

      document.getElementById("message_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
      document.getElementById("message").value= document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
    
      document.getElementById("remark").value= document.getElementById(t1id + (lowerLimit + 3)).innerHTML;      
      document.getElementById("createdby").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
      document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;

        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        document.getElementById("edit").disabled = false;

        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and delete button enabled too.
        {
            document.getElementById("saveasnew").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save").disabled=true;

        }
          //document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "pink";     // set the default color.
            }
        }
    }

        function activeField(id)
        {          
        document.getElementById("new").disabled = false;       
        if(id =='new')
        {   
            document.getElementById("delete").disabled = true;
            document.getElementById("save").disabled=false;
            document.getElementById("edit").disabled = true;
            document.getElementById("saveasnew").disabled=true;           
            document.getElementById("message").disabled=false;
            document.getElementById("remark").disabled=false;
            document.getElementById("createdby").disabled=false;
            document.getElementById("active").disabled=false;
            document.getElementById("message_id").value=0;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 7);
          }
        if(id == 'save')
            {
                document.getElementById("delete").disabled=false;
                document.getElementById("save").disabled=true;
            }
        if(id == 'edit')
        {            
              document.getElementById("delete").disabled = false;
              document.getElementById("save").disabled=false;
              document.getElementById("saveasnew").disabled=false;
              document.getElementById("edit").disabled = false;
              document.getElementById("new").disabled = false;
              document.getElementById("message").disabled=false;
              document.getElementById("remark").disabled=false;
              document.getElementById("createdby").disabled=false;
              document.getElementById("active").disabled=false;
        }
    }
         function addRow(message,remark,createdby,active) {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;

                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "hidden";
                element1.name = "message_id";
                element1.id = "message_id"+rowCount;
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
                cell3.innerHTML = $.trim(message);
                var element3 = document.createElement("input");
                element3.type = "hidden";
                element3.name = "message";
                element3.id = "message"+rowCount;
                element3.size = 20;
                element3.value = message;
                cell3.appendChild(element3);

                var cell4 = row.insertCell(2);
                cell4.innerHTML = $.trim(remark);
                var element4 = document.createElement("input");
                element4.type = "hidden";
                element4.name = "remark";
                element4.id = "remark"+rowCount;
                element4.size = 20;
                element4.value = remark;
                cell4.appendChild(element4);

                 var cell5 = row.insertCell(3);
                 cell5.innerHTML = $.trim(createdby);
                 var element5 = document.createElement("input");
                element5.type = "hidden";
                element5.name = "created";
                element5.id = "createdby"+rowCount;
                element5.size = 20;
                element5.value = createdby;
                cell5.appendChild(element5);
                  var cell6 = row.insertCell(4);
                 cell6.innerHTML = $.trim(active);
                 var element6 = document.createElement("input");
                element6.type = "hidden";
                element6.name = "active";
                element6.id = "active"+rowCount;
                element6.size = 20;
                element6.value = active;
                cell6.appendChild(element6);

                var height = (rowCount * 25)+ 60;
                document.getElementById("autoCreateTable").style.visibility = "visible";
                document.getElementById("autoCreateTable").style.height = height+'px';
                $("#autoCreateTable").show();
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
                    $("#autoCreateTable").hide();
                    //document.getElementById('form3').reset();
                    document.getElementById("message").focus();
                }catch(e) {
                    alert(e);
                }
            }
            function singleCheckUncheck(id){
                // alert(document.getElementById('insertTable').rows.length);
                if ($('#check_print'+id).is(':checked')) {
                    $("#message_id"+id).val("1");
                }else{
                    $("#message_id"+id).val("0");
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
                        $("#message_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#message_id"+i).val("2");
                    }
                }
            }

        </script>

        <style type="text/css">
            .r_set{border:inset;border-color: black}
        </style>
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
                                        <td align="center" class="header_table" width="100%">Error Message DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <td align="center">
                            <form action="errorMessageCont.do" method="post">
                                <font color="black" size="+1"> Error Message</font> &ensp;<input type="text" name="searchMessage" id="searchMessage" size="30" class="r_set" placeholder="Search...." value="${searchMessage}">
                              <input class="button" type="submit" value="SEARCH" id="searchType" name="task" >
                              <input class="button" type="submit" name="task"  id="searchAllType" value="Show All Data">
                            </form>
                        </td>
                         <tr>
                            <td align="center"><div align="center" id="content_div" class="content_div">
                                <form name="form1" method="POST" action="errorMessageCont.do">
                                        <table id="table1" width="700"  border="1"  align="center" class="content">
                                            <tr>
                                               <th style="white-space: normal; width: 20px" class="heading" >S.No.</th>
                                                <th style="white-space: normal; width: 150px" class="heading">Message</th>
                                                <th style="white-space: normal; width:40px" class="heading">Remark</th>
                                                <th style="white-space: normal" class="heading">TimeStamp</th>
                                                <th style="white-space: normal" class="heading">Created By</th>
                                                <th style="white-space: normal;width: 20px" class="heading">Active</th>
                                            </tr>
                                             <c:forEach var="messageBean" items="${requestScope['messageList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display:none" align="center">${messageBean.error_message_id}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${loopCounter.count+lowerLimit - noOfRowsTraversed}</td>                                                   
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${messageBean.message} </td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${messageBean.remark}  </td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${messageBean.timestamp}</td>
                                                   <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${messageBean.createdBy}</td>
                                                  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)"  align="center">${messageBean.active}</td>
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
                                                </tr>
                                                <input type="hidden" name="lowerLimit" id="lowerLimit" value="${lowerLimit}" />
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}"/>
                                            </table>
                                     </form>
                                </div>
                           </table>
                               <DIV id="autoCreateTable" STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form4"  action="errorMessageCont.do" method="POST" >
                                            <table id="parentTable" class="content" border="1"  align="center" width="70%">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" class="content" border="1" align="center" width="100%">
                                                              <tr><th class="heading">S.No</th>
                                                                 <th class="heading">Message</th>
                                                                 <th class="heading">Remark</th>
                                                                 <th class="heading">Created By</th>
                                                                 <th class="heading">active</th>
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
                                                                          
                                    
                                     <br>
                                  <form id="ffmm" action="errorMessageCont.do" method="post" onsubmit="return verify()">
                              <table align="center"  border="1" class="content" width="700">
                                  <tr>
                                      <th bgcolor="#F0FFFF" colspan="4">Result :&ensp;
                                   <c:if test="${not empty message}">
                                     <b>${message}</b>        </c:if>
                                   </th>
                                  </tr>
                                 <tr>
                                    <th  style="white-space: normal">Error Message  </th><td><input type="text" name="message" id="message" class="r_set" size="40" disabled></td>
                                    <th>Remark  </th><td><input type="text" name="remark" id="remark" class="r_set" disabled></td>
                                </tr>
                                <tr>
                                       <th>Created By  </th><td>
                                           <input type="hidden" name="message_id" id="message_id">
                                           <input type="text" name="created" id="createdby" class="r_set" size="40" disabled></td>
                                       <th>Active  </th><td><select name="active" id="active" disabled>
                                               <option disabled selected>Select.....</option>
                                               <option>Y</option>
                                               <option>N</option>
                                           </select>
                                       </td>
                                </tr>
                                <tr>
                                    <td align="center" colspan="4"><input class="button" type="submit" value="Save" name="task" id="save" disabled>
                                        <input class="button" type="button" value="New"  name="task" id="new" onclick="activeField(id)">
                                        <input class="button" type="button" value="Edit" name="task" id="edit" onclick="activeField(id)" disabled>
                                        <input class="button" type="submit" value="Delete" name="task" id="delete" disabled>
                                        <input class="button" type="submit" value="Save As New" name="task" id="saveasnew" disabled>
                                    </td>
                                </tr>
                            </table>
                        </form>                                          
              </DIV>
          </td>
            <input type="hidden" id="lowerLimit" name="lowerLimit" value="${lowerLimit}">
            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
       </table>                                            
    </body>
</html>
