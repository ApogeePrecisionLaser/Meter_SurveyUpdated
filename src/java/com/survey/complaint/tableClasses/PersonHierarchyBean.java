/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.tableClasses;

/**
 *
 * @author jpss
 */
public class PersonHierarchyBean {
    private int person_hierarchy_id;
    private int designation_hierarchy_id;
    private int designated_person_id;
    private int higher_designated_person_id;
    private String office_type;
    private String designated_person;
    private String higher_designated_person;
    private String description;
    private String remark;
    private String designation;
    private String higher_designation;
    private int created_by;

    private String[] person_hierarchy_idM;
    private String[] designation_hierarchy_idM;
    private String[] office_typeM;
    private String[] designated_personM;
    private String[] higher_designated_personM;
    private String[] descriptionM;
    private String[] remarkM;

    public String getDesignation() {
        return designation;
    }

    public String getHigher_designation() {
        return higher_designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setHigher_designation(String higher_designation) {
        this.higher_designation = higher_designation;
    }

    

    public String[] getOffice_typeM(){
        return office_typeM;
    }

    public void setOffice_typeM(String[] office_typeM){
        this.office_typeM = office_typeM;
    }

    public int getCreated_by() {
        return created_by;
    }

    public String getDescription() {
        return description;
    }

    public String[] getDescriptionM() {
        return descriptionM;
    }

    public String getDesignated_person() {
        return designated_person;
    }

    public String[] getDesignated_personM() {
        return designated_personM;
    }

    public int getDesignated_person_id() {
        return designated_person_id;
    }

    public String[] getPerson_hierarchy_idM() {
        return person_hierarchy_idM;
    }

    public int getDesignation_hierarchy_id() {
        return designation_hierarchy_id;
    }

    public String[] getDesignation_hierarchy_idM() {
        return designation_hierarchy_idM;
    }

    public String getHigher_designated_person() {
        return higher_designated_person;
    }

    public String[] getHigher_designated_personM() {
        return higher_designated_personM;
    }

    public int getHigher_designated_person_id() {
        return higher_designated_person_id;
    }

    public String getOffice_type() {
        return office_type;
    }

    public int getPerson_hierarchy_id() {
        return person_hierarchy_id;
    }

    public String getRemark() {
        return remark;
    }

    public String[] getRemarkM() {
        return remarkM;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDescriptionM(String[] descriptionM) {
        this.descriptionM = descriptionM;
    }

    public void setDesignated_person(String designated_person) {
        this.designated_person = designated_person;
    }

    public void setDesignated_personM(String[] designated_personM) {
        this.designated_personM = designated_personM;
    }

    public void setDesignated_person_id(int designated_person_id) {
        this.designated_person_id = designated_person_id;
    }

    public void setPerson_hierarchy_idM(String[] person_hierarchy_idM) {
        this.person_hierarchy_idM = person_hierarchy_idM;
    }

    public void setDesignation_hierarchy_id(int designation_hierarchy_id) {
        this.designation_hierarchy_id = designation_hierarchy_id;
    }

    public void setDesignation_hierarchy_idM(String[] designation_hierarchy_idM) {
        this.designation_hierarchy_idM = designation_hierarchy_idM;
    }

    public void setHigher_designated_person(String higher_designated_person) {
        this.higher_designated_person = higher_designated_person;
    }

    public void setHigher_designated_personM(String[] higher_designated_personM) {
        this.higher_designated_personM = higher_designated_personM;
    }

    public void setHigher_designated_person_id(int higher_designated_person_id) {
        this.higher_designated_person_id = higher_designated_person_id;
    }

    public void setOffice_type(String office_type) {
        this.office_type = office_type;
    }

    public void setPerson_hierarchy_id(int person_hierarchy_id) {
        this.person_hierarchy_id = person_hierarchy_id;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRemarkM(String[] remarkM) {
        this.remarkM = remarkM;
    }




}
