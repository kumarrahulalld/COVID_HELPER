package com.kumarrahulalld.covid_helper;

public class Update {
    String Title,Description,Link,Image;
    public Update()
    {
    }
    public Update(String title, String description, String link, String image) {
        Title = title;
        Description = description;
        Link = link;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


}
