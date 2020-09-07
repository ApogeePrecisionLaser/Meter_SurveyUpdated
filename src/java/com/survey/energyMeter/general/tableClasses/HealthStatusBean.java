/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.general.tableClasses;

/**
 *
 * @author jpss
 */
public class HealthStatusBean {
    private int health_status_id;
    private String health_status;
    private String remark;
    private String health_status_value;
    private String[] health_status_idList;
    private String[] health_statusList;
    private String[] remarkList;
    private String[] health_status_valueList;

    public void setHealth_statusList(String[] health_statusList) {
        this.health_statusList = health_statusList;
    }

    public void setHealth_status_idList(String[] health_status_idList) {
        this.health_status_idList = health_status_idList;
    }

    public void setHealth_status_valueList(String[] health_status_valueList) {
        this.health_status_valueList = health_status_valueList;
    }

    public void setRemarkList(String[] remarkList) {
        this.remarkList = remarkList;
    }

    public String[] getHealth_statusList() {
        return health_statusList;
    }

    public String[] getHealth_status_idList() {
        return health_status_idList;
    }

    public String[] getHealth_status_valueList() {
        return health_status_valueList;
    }

    public String[] getRemarkList() {
        return remarkList;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public void setHealth_status_id(int health_status_id) {
        this.health_status_id = health_status_id;
    }

    public void setHealth_status_value(String health_status_value) {
        this.health_status_value = health_status_value;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHealth_status() {
        return health_status;
    }

    public int getHealth_status_id() {
        return health_status_id;
    }

    public String getHealth_status_value() {
        return health_status_value;
    }

    public String getRemark() {
        return remark;
    }


}
