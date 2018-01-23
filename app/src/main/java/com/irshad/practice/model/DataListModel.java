package com.irshad.practice.model;

/**
 * Created by irshadvali on 21/01/18.
 */

public class DataListModel {
    String id;
    String title;
    String details;
    String dateString;
    String timeString;
    int like;
    int favourite;
    int poem;
    int story;
    public DataListModel() {
    }
    public DataListModel(String id, String title, String details, String dateString, String timeString, int like, int favourite,int poem,int story) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.dateString = dateString;
        this.timeString = timeString;
        this.like = like;
        this.favourite = favourite;
        this.poem=poem;
        this.story=story;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getPoem() {
        return poem;
    }

    public void setPoem(int poem) {
        this.poem = poem;
    }

    public int getStory() {
        return story;
    }

    public void setStory(int story) {
        this.story = story;
    }

}
