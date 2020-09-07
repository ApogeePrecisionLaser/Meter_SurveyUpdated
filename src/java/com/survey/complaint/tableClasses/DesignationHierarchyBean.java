/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.tableClasses;

/**
 *
 * @author jpss
 */
public class DesignationHierarchyBean {
   private int designation_hierarchy_id;
   private int designation_id;
   private int higher_designation_id;
   private String designation;
   private String higherDesignation;
   private String description;
   private String remark;
   private int created_by;

   private String[] designationHierarchy_idM;
   private String[] designationM;
   private String[] higherDesignationM;
   private String[] descriptionM;
   private String[] remarkM;

    public int getCreated_by() {
        return created_by;
    }

    public String getDescription() {
        return description;
    }

    public String[] getDescriptionM() {
        return descriptionM;
    }

    public String getDesignation() {
        return designation;
    }

    public String[] getDesignationHierarchy_idM() {
        return designationHierarchy_idM;
    }

    public String[] getDesignationM() {
        return designationM;
    }

    public int getDesignation_hierarchy_id() {
        return designation_hierarchy_id;
    }

    public int getDesignation_id() {
        return designation_id;
    }

    public String getHigherDesignation() {
        return higherDesignation;
    }

    public String[] getHigherDesignationM() {
        return higherDesignationM;
    }

    public int getHigher_designation_id() {
        return higher_designation_id;
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

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setDesignationHierarchy_idM(String[] designationHierarchy_idM) {
        this.designationHierarchy_idM = designationHierarchy_idM;
    }

    public void setDesignationM(String[] designationM) {
        this.designationM = designationM;
    }

    public void setDesignation_hierarchy_id(int designation_hierarchy_id) {
        this.designation_hierarchy_id = designation_hierarchy_id;
    }

    public void setDesignation_id(int designation_id) {
        this.designation_id = designation_id;
    }

    public void setHigherDesignation(String higherDesignation) {
        this.higherDesignation = higherDesignation;
    }

    public void setHigherDesignationM(String[] higherDesignationM) {
        this.higherDesignationM = higherDesignationM;
    }

    public void setHigher_designation_id(int higher_designation_id) {
        this.higher_designation_id = higher_designation_id;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRemarkM(String[] remarkM) {
        this.remarkM = remarkM;
    }

   

}
