package com.kumarrahulalld.covid_helper;

public class Requests {
    String Name;
    String Description;
    String Latitude;
    String Longitude;
    String Email;
    String Phone;
    String Status;
    String Helpers;
    String HelpersId;

    public Requests(String name, String description, String latitude, String longitude, String email, String phone, String status, String Helpers, String HelpersId) {
        Name = name;
        Description = description;
        Latitude = latitude;
        Longitude = longitude;
        Email = email;
        Phone = phone;
        Status = status;
        this.Helpers = Helpers;
        this.HelpersId = HelpersId;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getHelpers() {
        return Helpers;
    }

    public void setHelpers(String Helpers) {
        this.Helpers = Helpers;
    }

    public String getHelpersId() {
        return HelpersId;
    }

    public void setHelpersId(String HelpersId) {
        this.HelpersId = HelpersId;
    }

    public Requests()
    {
    }


}
