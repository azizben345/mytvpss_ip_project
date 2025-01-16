//SchoolProgramInfo.java
package com.example.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "schoolProgramInfo")
public class SchoolProgramInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "schoolName", nullable = false)
    private String schoolName;

    @Column(name = "schoolCode")
    private String schoolCode;
    
    @Column(name = "contactNumber", nullable = false)
    private String contactNumber;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "channelLink")
    private String channelLink;

    @Column(name = "hasLogo", nullable = false)
    private Boolean hasLogo = false;

    @Column(name = "isVenueAvailable", nullable = false)
    private Boolean isVenueAvailable = false;
    
    @Column(name = "hasInSchoolRecording", nullable = false)
    private Boolean hasInSchoolRecording = false;
    
    @Column(name = "isUploadedToYouTube", nullable = false)
    private Boolean isUploadedToYouTube = false;
    
    @Column(name = "hasInAndOutSchoolRecording", nullable = false)
    private Boolean hasInAndOutSchoolRecording = false;
    
    @Column(name = "hasOutsideCollaboration", nullable = false)
    private Boolean hasOutsideCollaboration = false;
    
    @Column(name = "hasGreenScreen", nullable = false)
    private Boolean hasGreenScreen = false;
    
    //Studio and Equipment Section
    @Column(name = "hasReadingCorner", nullable = false)
    private Boolean hasReadingCorner = false;
    
    @Column(name = "hasEditingCorner", nullable = false)
    private Boolean hasEditingCorner = false;
    
    @Column(name = "studioLevel", nullable = false)
    private int studioLevel;
    
    @Column(name = "submissionDate", nullable = false)
    private Date submissionDate;

    @Column(name = "validationDate")
    private Date validationDate;

    @Column(name = "status", nullable = false)
    private String status = "Submitted";

    // Getters, setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
    
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public Boolean getIsVenueAvailable() {
        return isVenueAvailable;
    }

    public void setIsVenueAvailable(boolean isVenueAvailable) {
        this.isVenueAvailable = isVenueAvailable;
    }

    public Boolean getHasInSchoolRecording() {
        return hasInSchoolRecording;
    }

    public void setHasInSchoolRecording(boolean hasInSchoolRecording) {
        this.hasInSchoolRecording = hasInSchoolRecording;
    }

    public Boolean getIsUploadedToYouTube() {
        return isUploadedToYouTube;
    }

    public void setIsUploadedToYouTube(boolean isUploadedToYouTube) {
        this.isUploadedToYouTube = isUploadedToYouTube;
    }

    public Boolean getHasInAndOutSchoolRecording() {
        return hasInAndOutSchoolRecording;
    }

    public void setHasInAndOutSchoolRecording(boolean hasInAndOutSchoolRecording) {
        this.hasInAndOutSchoolRecording = hasInAndOutSchoolRecording;
    }

    public Boolean getHasOutsideCollaboration() {
        return hasOutsideCollaboration;
    }

    public void setHasOutsideCollaboration(boolean hasOutsideCollaboration) {
        this.hasOutsideCollaboration = hasOutsideCollaboration;
    }

    public Boolean getHasGreenScreen() {
        return hasGreenScreen;
    }

    public void setHasGreenScreen(boolean hasGreenScreen) {
        this.hasGreenScreen = hasGreenScreen;
    }

    public String getChannelLink() {
        return channelLink;
    }

    public void setChannelLink(String channelLink) {
        this.channelLink = channelLink;
    }
    
    public Boolean getHasReadingCorner() {
        return hasReadingCorner;
    }

    public void setHasReadingCorner(Boolean hasReadingCorner) {
        this.hasReadingCorner = hasReadingCorner;
    }
    
    public Boolean getHasEditingCorner() {
        return hasEditingCorner;
    }

    public void setHasEditingCorner(Boolean hasEditingCorner) {
        this.hasEditingCorner = hasEditingCorner;
    }
    
    public int getStudioLevel() {
        return studioLevel;
    }

    public void setStudioLevel(int studioLevel) {
        this.studioLevel = studioLevel;
    }
    
    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

