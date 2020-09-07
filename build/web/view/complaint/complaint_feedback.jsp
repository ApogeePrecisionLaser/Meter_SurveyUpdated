<%--
    Document   : complaint_status
    Created on : Jul 30, 2013, 1:04:42 PM
    Author     : Shruti
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>

        <script type="text/javascript" language="javascript">


            jQuery(function(){
                $("#searchTypeOfComplaintNo").autocomplete("complaintFeedbackCont.do", {
                    extraParams: {
                        action1: function() { return "getComplaintNoList";}
                    }
                });

                $("#searchAssignedFor").autocomplete("complaintFeedbackCont.do", {
                    extraParams: {
                        action1: function() { return "getAssignedForlist";}

                    }
                });

                $("#complaintFeedbackDate").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#feedback_date").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });

            });


            function FillComplaintNODetails(id) {
                var complaint_no = document.getElementById("complaint_no").value;
                //  alert(complaint_no);
                if(complaint_no == 'Select') {
                    document.getElementById("complaint_no").focus();
                } else {
                    $.ajax({url: "complaintFeedbackCont.do", async: false ,data: "action1=getComplaintDetail&complaint_no_details=OOH DisplayComplaint_no&complaint_no="  + complaint_no , success: function(response_data) {
                                      
                            $("#complaint_no_details").html("");
                            //    var arr= response_data("&#;");
                            // alert(arr);
                            var  complaint_no_detail= response_data;
                            //   alert(complaint_no_detail);
                            document.getElementById("complaint_no_details").value =complaint_no_detail;
                        }});
                }
            }
            function fill_ComplaintNo() {
                var assigned_complaint = document.getElementById("assigned_comp").value;
                if(assigned_complaint == 'Select') {
                    document.getElementById("assigned_comp").focus();
                } else {
                    $.ajax({url: "complaintFeedbackCont.do", data: "action1=getAssignedComplaintDetail&assigned_complaint="
                            + assigned_complaint , success: function(response_data) {
                          //  alert(response_data);
                            $("#complaint_no").html("");
                            var arr = response_data.split(",");
                            var i;
                            for(i = 1; i < arr.length  ; i++) {
                                var opt = document.createElement("option");
                                opt.text = $.trim(arr[i]);
                                opt.value = $.trim(arr[i]);
                                document.getElementById("complaint_no").options.add(opt);
                            }
                            document.getElementById("complaint_no").disabled = false;
                          /*  if(i == 1) {
                                document.getElementById("complaint_no").value = arr[1];
                            }  */
                          //  if( document.getElementById("assigned_comp").value== 'Aakash Patel')
                             //   {
                                    var complaint_no = document.getElementById("complaint_no").value;
                //  alert(complaint_no);
                if(complaint_no == 'Select') {
                    document.getElementById("complaint_no").focus();
                } else {
                    $.ajax({url: "complaintFeedbackCont.do", async: false ,data: "action1=getComplaintDetail&complaint_no_details=OOH DisplayComplaint_no&complaint_no="  + complaint_no , success: function(response_data) {

                            $("#complaint_no_details").html("");
                            //    var arr= response_data("&#;");
                            // alert(arr);
                            var  complaint_no_detail= response_data;
                            //   alert(complaint_no_detail);
                            document.getElementById("complaint_no_details").value =complaint_no_detail;
                        }});
                }
                               // }
                             }
                    });
                }
            }

            function singleCheckUncheck(id){
                //  alert(document.getElementById('insertTable').rows.length);
                if ($('#check_complaint'+id).is(':checked')) {
                    $("#feedback_id"+id).val("1");
                }else{
                    $("#feedback_id"+id).val("0");
                }
                if($('form input[type=checkbox]:checked').size() == 0){
                    $("#addAllRecords").attr("disabled", "disabled");
                }else{
                    $("#addAllRecords").removeAttr("disabled");
                }


            }


            <%--- change here ---%>
  
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
            <%--- change here ---%>
                function checkUncheck(id){
                    var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;
                    if(id == 'Check All'){
                        $('input[name=check_complaint]').attr("checked", true);
                        for(var i=1;i<rowCount;i++){
                            $("#feedback_id"+i).val("1");
                        }
                        $('#checkUncheckAll').val('Uncheck All');
                        $("#addAllRecords").removeAttr("disabled");
                    }else{
                        $('input[name=check_complaint]').attr("checked", false);
                        $('#checkUncheckAll').val('Check All');
                        $("#addAllRecords").attr("disabled", "disabled");
                        for(var i=1;i<rowCount;i++){
                            $("#feedback_id"+i).val("2");
                        }
                    }
                }



                function verify() {
            
                    try {

                        var result=true , feedback_type= "";
                        var assigned_comp,complaint_no,feedback_date;
                        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Update') {
                           assigned_comp = document.getElementById("assigned_comp").value;
                        if($.trim(assigned_comp).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Assigned For is required...</b></td>");
                            document.getElementById("assigned_comp").focus();
                            return false; // code to stop from submitting the form2.
                        }
                        complaint_no = document.getElementById("complaint_no").value;
                        if($.trim(complaint_no).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Complaint No is required...</b></td>");
                            document.getElementById("complaint_no").focus();
                            return false; // code to stop from submitting the form2.
                        }
                           feedback_type = $.trim(document.getElementById("feedback_type").value);
                            if($.trim(feedback_type).length == 0) {
                                $("#message").html("<td colspan='8' bgcolor='coral'><b>Feedback  Type is Required...</b></td>");
                                document.getElementById("feedback_type").focus();
                                return false; // code to stop from submitting the form2.
                            }
                          else if(feedback_type== 'other'){                                       
                          //    alert("bgbg");
                              $("#message").html("<td colspan='8' bgcolor='coral'><b>Feedback  Type is Required...</b></td>");
                                document.getElementById("other_feedback_type").focus();
                                return false; 
                          }

                        feedback_date = document.getElementById("feedback_date").value;
                        if($.trim(feedback_date).length == 0) {
                            $("#message").html("<td colspan='8' bgcolor='coral'><b>Feedback Date  is required...</b></td>");
                            document.getElementById("feedback_date").focus();
                            return false; // code to stop from submitting the form2.
                        }

                        if(document.getElementById("clickedButton").value == 'Save AS New'){
                                result = confirm("Are you sure you want to save it as New record?")
                                return result;
                            }
                         if(document.getElementById("clickedButton").value == 'Update'){
                                result = confirm("Are you sure you want to Update this record?")
                                return result;
                            }
                            if(document.getElementById("feedback_id").value == 0){
                                addRow( $("#complaint_no").val(),feedback_type, $("#feedback_date").val(),$("#submission_time_hour").val(),$("#submission_time_min").val(),$("#submission_time").val(),$("#remark").val(),$("#other_feedback_type").val(),$("#assigned_comp").val(),$("#complaint_status").val());
                                $("#message").html("");
                                return false;
                            }
                        }
                        else result = confirm("Are you sure you want to delete this record?")
                 
                    }catch(e){
                        alert(e);
                    }
                    return result;
           
                }
        function otherFeedbackType(id){
              if(document.getElementById("feedback_type").value=='other'){

                    //  element3.value =other_feedback_type;
                  // }else{
                  //  element3.value = feedback_type;
                   }
        }
function fillComplaintStatus(id){ 
  if(id=='complaint_status1'){      
        document.getElementById("complaint_status").value='yes';
    }
     if(id=='complaint_status'){
        document.getElementById("complaint_status").value='no';
    }
}

                function addRow( complaint_no,feedback_type , feedback_date,submission_time_hour,submission_time_min,submission_time,remark,other_feedback_type,assigned_comp,complaint_status) {
                  // alert(complaint_status);
                   var table = document.getElementById('insertTable');
                    var rowCount = table.rows.length;                // alert(rowCount);
                    var row = table.insertRow(rowCount);
                    var cell1 = row.insertCell(0);
                    cell1.innerHTML = rowCount;
                    var element1 = document.createElement("input");
                    element1.type = "hidden";
                    element1.name = "feedback_id";
                    element1.id = "feedback_id"+rowCount;
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
                    element2.name = "complaint_no";
                    element2.id = "complaint_no"+rowCount;
                    element2.value = complaint_no;
                    element2.readOnly = true;
                    cell2.appendChild(element2);
                
                    var cell3 = row.insertCell(2);
                    var element3 = document.createElement("input");
                    element3.type = "text";
                    element3.name = "feedback_type";
                    element3.id = "feedback_type"+rowCount;
                    element3.size = 30;
                   if(document.getElementById("feedback_type").value=='other'){
                      element3.value =other_feedback_type;
                   }else{
                    element3.value = feedback_type;
                   }
                   cell3.appendChild(element3);

                    var cell4 = row.insertCell(3);
                    var element4 = document.createElement("input");
                    element4.type = "text";
                    element4.name = "feedback_date";
                    element4.id = "feedback_date"+rowCount;
                    element4.value = feedback_date;
                    element4.readOnly = true;
                    cell4.appendChild(element4);
            <%--- change here---%>
                    var element5 = document.createElement("input");
                    element5.type = "text";
                    element5.size = "2";
                    element5.name = "submission_time_hour";
                    element5.id = "submission_time_hour"+rowCount;
                    element5.value = submission_time_hour;
                    element5.readOnly = true;
                    cell4.appendChild(element5);
            <%--- change here---%>
            <%--- change here---%>
                    var element6 = document.createElement("input");
                    element6.type = "text";
                    element6.size = "2";
                    element6.name = "submission_time_min";
                    element6.id = "submission_time_min"+rowCount;
                    element6.value = submission_time_min;
                    element6.readOnly = true;
                    cell4.appendChild(element6);
            <%--- change here---%>
            <%--- change here---%>
                    var element7 = document.createElement("input");
                    element7.type = "text";
                    element7.size = "2";
                    element7.name = "submission_time";
                    element7.id = "submission_time"+rowCount;
                    element7.value = submission_time;
                    element7.readOnly = true;
                    cell4.appendChild(element7);
            <%--- change here---%>
                   var cell5 = row.insertCell(4);
                   var element8 = document.createElement("input");
                   element8.type = "text";
                   element8.size = "12";
                   element8.name = "assigned_comp";
                   element8.id = "assigned_comp"+rowCount;
                   element8.value =assigned_comp;
                   element8.readOnly =true;
                   cell5.appendChild(element8);    
                        /*    var cell5 = row.insertCell(4);
                            var element9 = document.createElement("input");
                            element9.type = "text";
                            element9.size = "12";
                            element9.name = "assigned_comp";
                            element9.id = "assigned_comp"+rowCount;
                            element9.value =assigned_comp;
                            element9.readOnly =true;
                            cell5.appendChild(element9);   */
            <%--- change here---%>
                   var cell6 = row.insertCell(5);
                            var element10 = document.createElement("input");
                            element10.type = "text";
                            element10.size = "12";
                            element10.name = "remark";
                            element10.id = "remark"+rowCount;
                            element10.value =remark;
                            element10.readOnly =true;
                            cell6.appendChild(element10);
                    var cell7 = row.insertCell(6);
                            var element11 = document.createElement("input");
                            element11.type = "text";
                            element11.size = "12";
                            element11.name = "complaint_status";
                            element11.id = "complaint_status"+rowCount;
                            element11.value =complaint_status;
                            element11.readOnly =true;
                            cell7.appendChild(element11);

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
               
                    document.getElementById("other_feedback_type").disabled = false;
                    document.getElementById("assigned_comp").disabled = false;
                    document.getElementById("complaint_no_details").disabled = false;
                        document.getElementById("feedback_id").disabled = false;
                        document.getElementById("complaint_no").disabled = false;
                        document.getElementById("feedback_type").disabled = false;
                        document.getElementById("submission_time_hour").disabled = false;
                        document.getElementById("submission_time_min").disabled = false;
                        document.getElementById("submission_time").disabled = false;
                        document.getElementById("feedback_date").disabled = false;
                        document.getElementById("remark").disabled = false;
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("update").disabled = false;
                    document.getElementById("delete").disabled = false;
                    document.getElementById("save").disabled = true;
                    if(id == 'new') {
                      //  document.getElementById("assigned_comp").disabled = false;
                        document.getElementById("assigned_comp").value = '';
                        document.getElementById("feedback_id").value = '0';
                        document.getElementById("complaint_no").value = '';
                        document.getElementById("feedback_type").value = '';
                        document.getElementById("feedback_date").value = '';
                        document.getElementById("remark").value = '';
                        $("#message").html("");
                
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("update").disabled = true;
                    document.getElementById("save").disabled = false;
                      //  document.getElementById("edit").disabled = true;
                     //   document.getElementById("delete").disabled = true;
                        setDefaultColor(document.getElementById("noOfRowsTraversed").value, 7);
                    }

                   /*if(id == 'edit') {
                      //  document.getElementById("assigned_comp").disabled = true;
                        document.getElementById("save_As").disabled = false;
                        document.getElementById("delete").disabled = false;

                    } */

               
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


            
            
                function fillColumns(id){
                    var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;               
                    var noOfColumns = 7;
                    var columnId = id;                          
                    columnId = columnId.substring(3, id.length);
                    var lowerLimit, higherLimit, rowNo = 0;
                    for(var i = 0; i < noOfRowsTraversed; i++) {
                        lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
                        higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
                        rowNo++;
                        if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
                    }
                    var lower= lowerLimit;
                    var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
                    deleteRowWithoutResetForm();
                    setDefaultColor(noOfRowsTraversed, noOfColumns);
                    // set default color of rows (i.e. of multiple coloumns).
                    document.getElementById("feedback_id").value = myLeftTrim(document.getElementById("com_feedback_id" + rowNo).value);
              
                    document.getElementById("complaint_revision_no").value = document.getElementById("com_feedback_revision_no" + rowNo).value;
                    document.getElementById("complaint_no").value = document.getElementById(t1id +(lower+1)).innerHTML;
                    document.getElementById("feedback_type").value = document.getElementById(t1id +(lower+2)).innerHTML;

                    document.getElementById("feedback_date").value = document.getElementById(t1id +(lower+3)).innerHTML;
                    document.getElementById("remark").value = document.getElementById(t1id +(lower+5)).innerHTML;
                    document.getElementById("assigned_comp").value=document.getElementById(t1id +(lower+6)).innerHTML;
                    document.getElementById("submission_time_hour").value = document.getElementById("complaint_feedback_hrView" + rowNo).value;
                    document.getElementById("submission_time_min").value = document.getElementById("complaint_feedback_minView" + rowNo).value;
                    document.getElementById("submission_time").value = document.getElementById("complaint_feedback_timeView" + rowNo).value;
                    var complaint_status_id = document.getElementById("complaint_status_id" + rowNo).value;
                    if(complaint_status_id == 4){
                        document.getElementById("complaint_status1").checked = true;                        
                    }else{
                        document.getElementById("complaint_status").checked = true;  
                    }

                    //  var complaint_feeaback_date=complaintFeedbackDate(document.getElementById(t1id +(lower+3)).innerHTML);


            
                    for(var i = 0; i < noOfColumns; i++) {
                        document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                    }
                    makeEditable('');
                  /* document.getElementById("edit").disabled = false;
                    if(!document.getElementById("save").disabled) {
                        document.getElementById("save_As").disabled = true;
                        // if save button is already enabled, then make edit, and delete button enabled too.
                        document.getElementById("delete").disabled = false;
                    } */
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
                
                function setStatus(id) {
                    if(id == 'save') {
                        document.getElementById("clickedButton").value = "Save";
                    }
                    else if(id == 'save_As'){
                        document.getElementById("clickedButton").value = "Save AS New";
                    }
                    else if(id == 'update') {
                        document.getElementById("clickedButton").value = "Update";
                    } else
                        document.getElementById("clickedButton").value = "Delete";;
                }

            function ComplaintReportPdf() {
                var searchAssignedFor = document.getElementById("searchAssignedFor").value;
                var searchTypeOfComplaintNo = document.getElementById("searchTypeOfComplaintNo").value;
              /*  var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }  */
              //  var s_assigned_for = document.getElementById("s_assigned_for").value;
                var queryString = "task=PRINTComplaintFeedbackReport&searchAssignedFor="+searchAssignedFor+"&searchTypeOfComplaintNo="+searchTypeOfComplaintNo;
                var url = "complaintFeedbackCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Feedback Report", 500, 750);
            }
        function ComplaintReportExcel() {
              var searchAssignedFor = document.getElementById("searchAssignedFor").value;
              var searchTypeOfComplaintNo = document.getElementById("searchTypeOfComplaintNo").value;

            /*  var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }
                var s_assigned_for = document.getElementById("s_assigned_for").value; */
                var queryString = "task=PRINTComplaintFeedExcelReport&searchAssignedFor="+searchAssignedFor+"&searchTypeOfComplaintNo="+searchTypeOfComplaintNo;
                var url = "complaintFeedbackCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Report", 500, 750);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url ,window_name, window_features);
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
            <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                        <%--       <form name="form1" method="post" action="complaintFeedbackCont.do">   --%>
                        <table width="100%">
                            <tr>
                                <td>
                                    <table width="100%" border="4">

                                        <tr>
                                            <td align="center" class="header_table" width="90%">Complaint Feedback</td>
                                            <td><%@include file="/layout/complaint_menu.jsp" %></td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <%--  <tr>    --%>
                            <tr>  <%-- Start Complaint type of search table --%>
                                <td  align="center">
                                    <form name="form0" method="POST" action="complaintFeedbackCont.do">
                                        <table  align="center" width="800" class="heading1">
                                            <tr>
                                                <th>Assigned For </th>
                                                <td><input class="input" type="text" id="searchAssignedFor" name="searchAssignedFor" value="${searchAssignedFor}" size="20" ></td>
                                                <th>Complaint No</th>
                                                <td><input class="input" type="text" id="searchTypeOfComplaintNo" name="searchTypeOfComplaintNo" value="${searchTypeOfComplaintNo}" size="12" ></td>
                                                <th>Feedback Date</th>
                                                <td><input class="input" type="text" id="complaintFeedbackDate" name="searchFeedbackDate" value="${searchFeedbackDate}"  size="25"  >
                                                    <%---   <a href="#" onclick="setYears(1947,2022);showCalender(this,'searchFeedbackDate')">
                                                           <img alt=""  src="images/calender.png">
                                                       </a>   --%>
                                                </td>
                                                <td><input  type="submit" name="task" id="search" value="Search"></td>
                                                <td><input  type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="ComplaintReportPdf()"></td>
                                                <td><input class="excel_button" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="ComplaintReportExcel()"></td>
                                            </tr>
                                        </table>
                                    </form>
                                </td>
                            </tr <%--End Complaint type of search table --%>
                            <tr>
                                <td align="center">
                                    <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                        <form name="form1" method="post" action="complaintFeedbackCont.do">
                                            <table id="table1" border="1" align="center"  class="reference">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Complaint No</th>
                                                    <th class="heading">Feedback Type</th>
                                                    <th class="heading">FeedBack Date</th>
                                                    <th class="heading">FeedBack Time</th>
                                                    <th class="heading">Remark</th>
                                                    <th class="heading">Assigned For</th>
                                                </tr>
                                                <c:forEach var="asso" items="${ComplaintFeedbackList}" varStatus="loopCounter">
                                                    <tr>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="com_feedback_revision_no${loopCounter.count}" name="com_feedback_revision_no${loopCounter.count}" value="${asso.complaint_revision_no}">
                                                            <input type="hidden" id="com_feedback_id${loopCounter.count}" name="com_feedback_id${loopCounter.count}" value="${asso.feedback_id}">
                                                            <input type="hidden"  id="complaint_feedback_hrView${loopCounter.count}" name="complaint_feedback_hrView${loopCounter.count}" value="${asso.submission_time_hour}">
                                                            <input type="hidden"  id="complaint_feedback_minView${loopCounter.count}" name="complaint_feedback_minView${loopCounter.count}" value="${asso.submission_time_min}">
                                                            <input type="hidden"  id="complaint_feedback_timeView${loopCounter.count}"name="complaint_feedback_timeView${loopCounter.count}" value="${asso.submission_time}">
                                                            <input type="hidden" id="complaint_status_id${loopCounter.count}" name="complaint_status_id${loopCounter.count}" value="${asso.complaint_status_id}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.feedback_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.feedback_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.submission_time_hour}:${asso.submission_time_min} ${asso.submission_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.remark}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.assigned_for}</td>
                                                    </tr>

                                                    <%--    <input type="hidden"  id="complaint_feedback_hrView" value="${asso.feedback_date}">   --%>

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
                                                <input  type="hidden" name="searchTypeOfComplaintNo" value="${searchTypeOfComplaintNo}"  >
                                                <input  type="hidden" name="searchAssignedFor" value="${searchAssignedFor}"  >
                                            </table>
                                        </form>
                                    </DIV>


                                </td>
                            </tr>
                            <%--</c:if>--%>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv"  STYLE="overflow: auto; visibility: hidden;height: 0px">
                                        <form name="form2"  action="" method="post" >
                                            <table id="parentTable" class="content" border="1"  align="center" >
                                                <tr>
                                                    <td colspan="3">
                                                        <table id="insertTable" class="reference" border="1" align="center" width="100%">
                                                            <tr>
                                                                <th class="heading1" >S.No.</th>
                                                                <th class="heading1" >Complaint No</th>
                                                                <th class="heading1" >Feedback Type</th>
                                                                <th class="heading1" >Feedback Date</th>
                                                                <th class="heading1" >Assigned For</th>
                                                                <th class="heading1" >Remark</th>
                                                                <th class="heading1" >Complaint Status</th>

                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="8" align="center">
                                                        <input type="button" class="button" name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
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
                                        <form id="form3" name="form3" method="POST" action="complaintFeedbackCont.do" onsubmit="return verify()">
                                            <table border="0"  id="table2" align="center"  class="content" width="850" >
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                               <tr>
                                                    <th class="heading1" >Complaint Status </th>
                                                    <td>
                                                        <input type="radio" id="complaint_status"name="complaint_status" value="no" onclick="fillComplaintStatus(id);" checked>Not Complete
                                                        <input type="radio" id="complaint_status1" name="complaint_status" value="yes" onclick="fillComplaintStatus(id);">Complete
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Assigned For</th>
                                                    <td>

                                                        <select id="assigned_comp" name="assigned_comp" onchange="fill_ComplaintNo();" disabled>
                                                            <option value=" "selected>Select</option>
                                                            <c:forEach var="assigned" items="${assigned_for_list}">
                                                                <option>${assigned}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1" >Complaint No</th>
                                                    <td>
                                                        <select id="complaint_no" name="complaint_no" onchange="FillComplaintNODetails(id);" disabled>
                                                            <option value=" "selected>Select</option>
                                                            <c:forEach var="complaint" items="${complaint_no_list}">
                                                                <option>${complaint}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="text" id="complaint_no_details" name="complaint_no_details" value="" size="100" disabled>
                                                    </td>
                                                </tr>
                                               <tr>
                                                    <th class="heading1" >Feedback Type</th>
                                                    <td>
                                                        <select id="feedback_type" name="feedback_type" disabled>
                                                            <option value=" "selected>Select</option>
                                                            <c:forEach var="complaint_feedback_type" items="${feedback_type_list}">
                                                                <option>${complaint_feedback_type}</option>
                                                            </c:forEach>
                                                         <option>other</option>
                                                        </select>
                                                        <input type="text" name="other_feedback_type" id="other_feedback_type" value="" size="30" disabled> 
                                                    </td>                                                  
                                                </tr>                                            
                                              <tr>
                                                    <th class="heading1" >Feedback Date</th>
                                                    <td>
                                                        <input type="text" id="feedback_date" name="feedback_date" value="" disabled>
                                                        <%-- <a href="#" onclick="setYears(1947,2022);showCalender(this,'feedback_date')">
                                                             <img alt=""  src="images/calender.png">
                                                         </a>   --%>
                                                        <input  type="text" id="submission_time_hour" name="submission_time_hour"  value="" size="2"
                                                                onchange="IsNumeric(id)" Placeholder ="HH" onkeyup="IsNumeric(id)" disabled>
                                                        <%-- <b>:</b>   --%>
                                                        <input  type="text" id="submission_time_min" name="submission_time_min" Placeholder ="MM"
                                                                value="" size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)" disabled>
                                                        <select id="submission_time" name="submission_time" class="dropdown" style="width: auto" disabled>
                                                            <option>AM</option>
                                                            <option>PM</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Remark</th>
                                                    <td>
                                                        <input type="text" id="remark" name="remark" value="" disabled>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td align='center' colspan="4">
                                                        <input class="button"  type="submit" name="task" id="update" value="Update" onclick="setStatus(id);" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'update') ? '' : 'none'}--%>" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save"  onclick="setStatus(id);" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'save') ? '' : 'none'}--%>" disabled >
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id); "style="display:<%--${myfn:isContainPrivileges(privilegeList, 'save_As') ? '' : 'none'}--%>" disabled>
                                                        <input class="button" type="button" name="new" id="new" value="New" onclick="makeEditable(id)" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'new') ? '' : 'none'}--%>">
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" style="display:<%--${myfn:isContainPrivileges(privilegeList, 'delete') ? '' : 'none'}--%>" disabled>
                                                    </td>
                                                </tr>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="complaint_revision_no" name="complaint_revision_no" value="">
                                                <input type="hidden" id="feedback_id" name="feedback_id" value="">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                        </form>
                                    </DIV>

                                </td>
                            </tr>
                        </table>
                        <%--    </form>     --%>

                    </div>

                </td>            </tr>



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