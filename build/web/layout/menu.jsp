<%--
    Document   : index
    Created on : Jan 7, 2013, 3:26:07 PM
    Author     : Neha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!DOCTYPE html>

<html>
    <head>
        <title>Menu</title>
        <script type="text/javascript">
            $(function() {
                if ($.browser.msie && $.browser.version.substr(0,1)<7)
                {
                    $('li').has('ul').mouseover(function(){
                        $(this).children('ul').show();
                    }).mouseout(function(){
                        $(this).children('ul').hide();
                    })
                }
            });
        </script>

        <link type="text/css" href="style/menu.css" rel="stylesheet"/>



    </head>
    <body>
        <div>
            <ul id="menu">
                <li><a id="mpeb" href="#">Data Entry</a>                    
                    <ul>
                        <li><a href="meterCont1.do">Service Connection</a></li>
                        <li><a id="mpeb0" href="#">Switching Point</a>
                            <ul>
                                <li><a href="switchingPointReport.do">Switching Pt. Summery</a></li>
                                <li><a href="switchingpontCont.do">Meter Switching Point</a></li>
                                <li><a href="switchingPointSurvey">Switching Point Detail</a></li>
                                <li><a href="switchingPointMap.do">Switching Point Map</a></li>
                                <%--   <li><a href="switchingSurveyCont">Switching Point Survey</a></li>  --%>
                                 
                            </ul>
                        </li>
                        <li><a id="mpeb1" href="#">Pole Detail</a>
                            <ul>
                                <li><a href="poleDetailCont">Pole Detail</a></li>
                                <li><a href="poleTypeCont">Pole Type</a></li>
                                <li><a href="mountingTypeCont">Mounting Type</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="surveyCont">Survey</a>
                        </li>
                        <li>
                            <a href="newSwitchingPointSurveyCont.do">Switching Point Survey</a>
                        </li>
                        <li><a id="mpeb2" href="#">City Detail</a>
                            <ul>
                                <li><a href="wardTypeCont">Ward Detail</a></li>
<!--                                <li><a href="areaTypeCont">Area Detail</a></li>-->
                            </ul>
                        </li>

                        <li><a id="mpeb3" href="#">Road Detail</a>
                            <ul>
                                <li><a href="roadTypeCont">Road Detail</a></li>
                                <li><a href="roadUseTypeCont">Road Use</a></li>
                                <li><a href="roadCatTypeCont">Road Category </a></li>
                            </ul>
                        </li>
                        <li><a id="mpeb4" href="#">Light Detail</a>
                            <ul>
                                <li><a href="lightTypeCont">Light Type</a></li>
                                <li><a href="lightSourceTypeCont">Light Source Type</a></li>
                                <li><a href="wattageTypeCont">Wattage</a></li>
                            </ul>
                        </li>
                        <li><a href="trafficTypeCont">Traffic Type</a></li>
                        <li><a href="controlMechanismTypeCont">Control Mechanism</a></li>
                        <li><a href="fuseTypeCont">Fuse</a></li>
                        <li><a href="contacterTypeCont">Contacter</a></li>
                        <li><a href="mccbTypeCont">Mccb</a></li>
                        <li><a href="timerTypeCont">Timer</a></li>
                        <li><a href="timeIntervalCont.do">Time Interval</a></li>

                    </ul>
                </li>
                <li><a id="mpeb1" href="#">TubeWell Survey</a>
                    <ul>
                        <li><a id="mpeb12" href="divisionCont">Division </a>

                        </li>
                        <li><a id="mpeb10" href="zoneCont">Zone</a>
                        </li>

                        <li><a id="mpeb11" href="wardCont">Ward</a></li>
                        <li><a href="areaTypeCont">Area Detail</a></li>
                        <li>
                            <a href="tubeWellUserCont">TubeWellUser</a>
                        </li>
                        <li>
                            <a href="tubeWellUserDataCont">TubeWellUserData</a>
                        </li>
                        <li>
                            <a href="tubeWellDetailCont">TubeWell Detail</a>
                        </li>
                         <li>
                            <a href="tubeWellBoreDataCont">TubeWellBoreData</a>
                        </li>
                        <li>
                            <a href="tubeWellSurveyCont">TubeWell Survey</a>
                        </li>

                        <li>
                            <a href="TypeOfUseCont.do">Type Of Use</a>
                        </li>
                        <li>
                            <a href="MotorTypeCont.do">Motor Type</a>
                        </li>
                          <li>
                            <a href="BoreCasingTypeCont.do">Bore Casing Type</a>

                        </li>
                    </ul>
                </li>
                <li id="water_menu" style="display: block"><a id="status" href="#">Water</a>
                    <ul>
                        <li id="waterTreatmentPlantCont.do" style="display: block"><a href="waterTreatmentPlantCont.do">Water Treatment Plant</a></li>
                        <li id="overHeadTankCont.do" style="display: block"><a href="overHeadTankCont.do">Overhead Tank</a></li>
                        <li id="ohLevelCont.do" style="display: block"><a href="ohLevelCont.do">Overhead Tank Level</a></li>
                        <li id="rawWaterCont.do" style="display: block"><a href="rawWaterCont.do">Raw Water</a></li>
                        <li id="rawWaterLevelCont.do" style="display: block"><a href="rawWaterLevelCont.do">Raw Water Level</a></li>
                        <li id="sourceWaterCont.do" style="display: block"><a href="sourceWaterCont.do">Source Water</a></li>
                        <li id="sourceWaterLevelCont.do" style="display: block"><a href="sourceWaterLevelCont.do">Source Water Level</a></li>
                    </ul>
                </li>

                <li><a id="mpeb1" href="#">Energy Meter</a>
                    <ul>
                        <li><a id="mpeb12" href="#">Date Entry</a>
                            <ul>
                                <li><a href="junctionCont.do">Junction</a></li>
                                <li><a href="healthStatusCont.do">Health Status</a></li>
                                <li><a href="JunSunriseSunsetCont.do">Sunrise Sunset</a></li>
                                <li><a href="errorMessageCont.do">Error Message</a></li>                             
                            </ul>
                        </li>
                        <li><a id="mpeb10" href="#">Log Detail</a>
                            <ul>
                                <li><a href="connectedIpCont.do">Connected Ip</a></li>
                                <li><a href="loggedInJunctionCont.do">Logged In Junction</a></li>
                                <li><a href="logHistoryCont.do">Log History</a></li>
                                <li><a href="logErrorCont">Error Log</a></li>
                            </ul>
                        </li>
                        <li><a id="mpeb11" href="#">Reading</a>
                            <ul>
                                <li><a href="meterReadingCont.do">Meter Reading</a></li>
                                <li><a href="healthStatusMapCont.do">Health Status Reading</a></li>
                                <li><a href="junctionDetailCont.do">Junction Detail</a></li>
                                <li><a href="junctionDetailCont1.do">Junction Detail-1</a></li>
                                 <li><a href="OverheadtankMeterMapCont.do">OverheadTank Meter Map</a></li>
                            </ul>                            
                        </li>
                    </ul>
                </li>

                <li><a id="mpeb" href="complaintregisterCont.do">Complain</a>
                     <li><a id="mpeb" href="misCont.do">MIS</a>
                </li>
                <li><a href="#">Basic Entry</a>
                    <ul>
                        <li><a href="startertypeCont.do">Starter Type</a></li>
                        <li><a href="startermakerCont.do">Starter Make</a></li>
                        <li><a href="enclosuretypeCont.do">Enclosure Type</a></li>
                        <li><a href="switchingtypeCont.do">Switching Type</a></li>
                         <li><a href="ShowMeterDataCont.do">Meter Details</a></li>
                         <li><a href="PrimarySurveyController">Primary Survey</a></li>
                           <li><a href="CircuitSurveyController">Circuit Survey</a></li>
                    </ul>

                </li>
            </ul>
        </div>
    </body>
</html>