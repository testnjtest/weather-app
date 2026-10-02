package com.example.apiproject;

// You'll probably need to add a couple of more attributes. Maybe something like average price, brands, etc.
public class Weather {
    String date, condition;
    String minimum, maximum;

    int imageReaction;

    public String getDate() {
        return date;
    }

    public String getCondition() {
        return condition;
    }

    public String getMinimum() {
        return minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public int getImageReaction() {
        return imageReaction;
    }

    public Weather(String date, String condition, String minimum, String maximum) {
        this.date = date;
        this.condition = condition;
        this.minimum = minimum;
        this.maximum = maximum;
        this.imageReaction = imageReaction;
    }
}

