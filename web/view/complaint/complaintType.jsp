<%-- 
    Document   : complaintType
    Created on : Jul 31, 2013, 9:59:08 AM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Complaint Type</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/table.js"></script>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" >
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
        <script type="text/javascript" language="javascript">

            jQuery(function(){
                $.ajax({url: "qtAdSubType1Cont.do", data: "advertising_type=OOH Display&q= ", success: function(response_data) {
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt        = document.createElement("option");
                            opt.text = arr[i];
                            opt.value = arr[i];
                            document.getElementById("ooh_ast1").options.add(opt);
                        }
                        if(i > 0) {
                            document.getElementById("ooh_ast1").value = "Hoarding";
                            ooh_fillAdSubType2();
                        }
                    }
                });
            });



            function ooh_fillAdSubType2() {
                var adSubType1 = document.getElementById("ooh_ast1").value;
                if(adSubType1 == 'Select') {
                    document.getElementById("ooh_ast1").focus();
                } else {
                    $.ajax({url: "qtAdSubType2Cont.do", async: false ,data: "advertising_type=OOH Display&adSubType1="
                            + adSubType1 + "&q= ", success: function(response_data) {
                            $("#ooh_ast2").html("");
                            var arr = response_data.split("&#;");
                            var i;
                            for(i = 0; i < arr.length - 1; i++) {
                                var opt        = document.createElement("option");
                                opt.text = arr[i];
                                opt.value = arr[i];
                                document.getElementById("ooh_ast2").options.add(opt);
                            }
                            document.getElementById("ooh_ast2").disabled = false;
                            if(i == 1) {
                                document.getElementById("ooh_ast2").value = arr[0];
                            }
                            if(adSubType1 == 'Hoarding') {
                                //  document.getElementById("ooh_ast2").disabled = true;
                            } else if(adSubType1 == 'Kiosks') {
                                document.getElementById("ooh_ast2").value = "Street Light Pole Kiosk";
                            }
                            // getComplainTypeList();
                        }
                    });
                }

            }
            function singleCheckUncheck(id){
                //  alert(document.getElementById('insertTable').rows.length);
                if ($('#check_complaint'+id).is(':checked')) {
                    $("#complaint_type_id"+id).val("1");
                }else{
                    $("#complaint_type_id"+id).val("0");
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
                    $('input[name=check_complaint]').attr("checked", true);
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_type_id"+i).val("1");
                    }
                    $('#checkUncheckAll').val('Uncheck All');
                    $("#addAllRecords").removeAttr("disabled");
                }else{
                    $('input[name=check_print]').attr("checked", false);
                    $('#checkUncheckAll').val('Check All');
                    $("#addAllRecords").attr("disabled", "disabled");
                    for(var i=1;i<rowCount;i++){
                        $("#complaint_type_id"+i).val("2");
                    }
                }
            }



            function verify() {
                try {
                    var result , complaint_type= "";
                    if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                        complaint_type = $.trim(document.getElementById("complaint_type").value);
                        if($.trim(complaint_type).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Complaint Type is Required...</b></td>");
                            document.getElementById("complaint_type").focus();
                            return false; // code to stop from submitting the form2.
                        }

                        if(result == false);
                        else result = true;
                        if(document.getElementById("clickedButton").value == 'Save AS New'){
                            result = confirm("Are you sure you want to save it as New record?")
                            return result;
                        }
                    } else result = confirm("Are you sure you want to delete this record?")
                    if(document.getElementById("complaint_type_id").value == 0){
                        addRow( complaint_type, $("#description").val());
                        $("#message").html("");
                        return false;
                    }
                }catch(e){
                    alert("is there any error"+e);
                }
                return result;
            }

            function addRow(complaint_type , description) {
                var table = document.getElementById('insertTable');
                var rowCount = table.rows.length;                // alert(rowCount);
                var row = table.insertRow(rowCount);
                var cell1 = row.insertCell(0);
                cell1.innerHTML = rowCount;
                var element1 = document.createElement("input");
                element1.type = "text";
                element1.name = "complaint_type_id";
                element1.id = "complaint_type_id"+rowCount;
                element1.size = 1;
                element1.value = 1;
                element1.readOnly = false;
                cell1.appendChild(element1);
                var element1 = document.createElement("input");
                element1.type = "checkbox";
                element1.name = "check_complaint";
                element1.id = "check_complaint"+rowCount;                //element1.size = 1;
                element1.value = "YesModify";
                element1.checked = true;
                element1.setAttribute('onclick', 'singleCheckUncheck('+rowCount+')');
                cell1.appendChild(element1);

                var cell2 = row.insertCell(1);
                var element2 = document.createElement("input");
                element2.type = "text";
                element2.name = "ooh_ast1";
                element2.id = "ooh_ast1"+rowCount;
                element2.value = $("#ooh_ast1").val();
                element2.readOnly = true;
                cell2.appendChild(element2);

                var cell2 = row.insertCell(2);
                var element3 = document.createElement("input");
                element3.type = "text";
                element3.name = "ooh_ast2";
                element3.id = "ooh_ast2"+rowCount;
                element3.value =  $("#ooh_ast2").val();
                element3.readOnly = true;
                cell2.appendChild(element3);

                var cell2 = row.insertCell(3);
                var element4 = document.createElement("input");
                element4.type = "text";
                element4.name = "complaint_type";
                element4.id = "complaint_type"+rowCount;
                element4.size = 30;
                element4.value = complaint_type;
                cell2.appendChild(element4);

                var cell3 = row.insertCell(4);
                var element5 = document.createElement("input");
                element5.type = "text";
                element5.name = "description";
                element5.id = "description"+rowCount;
                element5.value = description;
                element5.readOnly = true;
                cell3.appendChild(element5);

                var height = (rowCount * 40)+ 60;
                document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                document.getElementById("autoCreateTableDiv").style.height = height+'px';
                if(rowCount == 1){
                    $('#checkUncheckAll').attr('hidden', true);
                }else{
                    $('#checkUncheckAll').attr('hidden', false);
                }
            }
            function deleteRowWithoutResetForm() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                }catch(e) {
                    alert(e);
                }
            }
            function deleteRow() {
                try {
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    for(var i=1; i<rowCount; i++) {
                        table.deleteRow(1);
                    }
                    document.getElementById("autoCreateTableDiv").style.visibility = "visible";
                    document.getElementById("autoCreateTableDiv").style.height = '0px';
                    document.getElementById('form3').reset();
                }catch(e) {
                    alert(e);
                }
            }


            function makeEditable(id) {
                $("#ooh_ast1").attr("disabled", false);
                $("#ooh_ast2").attr("disabled", false);
                $("#complaint_type").attr("disabled", false);
                $("#description").attr("disabled", false);
                if(id == 'new') {
                    document.getElementById("complaint_type_id").value = '0';
                    document.getElementById("complaint_type").value = '';
                    document.getElementById("description").value = '';
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    ooh_fillAdSubType2();
                    setDefaultColor(document.getElementById("noOfRowsTraversed").value, 5);
                }
                if(id == 'edit') {
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;

                }
                document.getElementById("save").disabled = false;
                document.getElementById("message").innerHTML = "";
                $("#message").html("");
            }


            function setDefaultColor(noOfRowsTraversed, noOfColumns) {
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }



            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 5;
                var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
                columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
                var lowerLimit, higherLimit, rowNo = 0;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                    higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                    rowNo++;
                    if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                }
                var lower= lowerLimit;
                var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                var t2id = "t2c";
                deleteRowWithoutResetForm();
                setDefaultColor(noOfRowsTraversed, noOfColumns);
                // set default color of rows (i.e. of multiple coloumns).
                document.getElementById("complaint_type_id").value = document.getElementById("com_type_id" + rowNo).value;
                var ad_type1=document.getElementById(t1id +(lower+1)).innerHTML;
                $("#ooh_ast1 option" ).each(function()
                {
                    if($(this).val() == ad_type1){
                        $(this).attr('selected', true);
                    }
                });
                ooh_fillAdSubType2();
                var ad_type2=document.getElementById(t1id +(lower+2)).innerHTML;
                $("#ooh_ast2 option" ).each(function()
                {
                    if($(this).val() == ad_type2){
                        $(this).attr('selected', true);
                    }
                });
                document.getElementById("complaint_type").value = document.getElementById(t1id +(lower+3)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id +(lower+4)).innerHTML;
                for(var i = 0; i < noOfColumns; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled) {
                    document.getElementById("save_As").disabled = true;
                    // if save button is already enabled, then make edit, and delete button enabled too.
                    document.getElementById("delete").disabled = false;
                }
            }


            function setStatus(id) {
                if(id == 'save') {
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else if(id == 'edit') {
                    document.getElementById("clickedButton").value = "Edit";
                } else
                    document.getElementById("clickedButton").value = "Delete";;
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
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                        <form name="form1" method="post" action="complaintTypeCont.do" onsubmit="return verifyform()">
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complaint Type</td>
                                                <td><%@include file="/view/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                            <table id="table1" border="1" align="center" width="100%" class="reference">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Ad SubType1</th>
                                                    <th class="heading">Ad SubType2</th>
                                                    <th class="heading">Complaint Type</th>
                                                    <th class="heading">description</th>

                                                </tr>
                                                <c:forEach var="asso" items="${ComplaintStatusList}" varStatus="loopCounter">
                                                    <tr>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="com_type_id${loopCounter.count}" name="com_type_id${loopCounter.count}" value="${asso.complaint_type_id}">

                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.ad_sub_type1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.ad_sub_type2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.description}</td>

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
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">

                                            </table>
                                        </DIV>


                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <DIV id="autoCreateTableDiv"  STYLE="overflow: auto; visibility: hidden;height: 0px">
                                            <form name="form2"  action="" method="post" >
                                                <table id="parentTable" class="content" border="1"  align="center" width="100%">
                                                    <tr>
                                                        <td colspan="3">
                                                            <table id="insertTable" class="reference" border="1" align="center" width="100%">
                                                                <tr>
                                                                    <th class="heading1" >S.No.</th>
                                                                    <th class="heading1" >Ad subtype1</th>
                                                                    <th class="heading1" >Ad subtype2</th>
                                                                    <th class="heading1" >Complaint Type</th>
                                                                    <th class="heading1" >Description</th>

                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="8" align="center">
                                                            <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <input type="button" class="button" name="Cancel" value="Cancel" onclick="deleteRow();">
                                                            <input  type="hidden"  name="isComplaintReg" value="YesModify">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </DIV>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <DIV>
                                            <form id="form3" name="form3" method="POST" action="complaintTypeCont.do" onsubmit="return verify()">
                                                <table border="0"  id="table2" align="center"  class="content" >
                                                    <tr id="message">
                                                        <c:if test="${not empty message}">
                                                            <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                        </c:if>
                                                    </tr>
                                                    <tr>
                                                        <th class="heading1" >Ad Type1</th>
                                                        <td>
                                                            <select id="ooh_ast1" name="ooh_ast1"
                                                                    onchange="ooh_fillAdSubType2();"
                                                                    title="Select Advertising Sub Type1" disabled>
                                                            </select>
                                                        </td>
                                                        <th class="heading1" >Ad type2</th>
                                                        <td>
                                                            <select id="ooh_ast2"
                                                                    name="ooh_ast2"  title="Select Advertising Sub Type2"
                                                                    disabled>
                                                            </select>
                                                        </td>
                                                    </tr><tr>
                                                        <th class="heading1" >Complaint Type</th>
                                                        <td>
                                                            <input type="text" id="complaint_type" name="complaint_type" value="" size="30" disabled>
                                                        </td>
                                                        <th class="heading1" >Description</th>
                                                        <td>
                                                            <input type="text" id="description" name="description" value="" size="30" disabled>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align='center' colspan="4">
                                                            <input class="button"  type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id);setStatus(id);" disabled>
                                                            <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);"  disabled>
                                                            <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id); " disabled>
                                                            <input class="button" type="button" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                            <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                        </td>
                                                    </tr>
                                                    <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                    <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                    <input type="hidden" id="complaint_type_id" name="complaint_type_id" value="0">
                                                    <input type="hidden" id="clickedButton" value="">
                                                </table>
                                            </form>
                                        </DIV>

                                    </td>
                                </tr>
                            </table>
                        </form>

                    </div>

                </td>

            </tr>
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
