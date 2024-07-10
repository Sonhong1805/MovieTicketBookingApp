package com.example.mybtl;

public class Movie {
    private int Id;
    private String Name;
    private int Image;
    private String Content;
    private String Category;
    private int Trailer;
    private String Premiere;
    private int Price;

    public Movie(int id, String name, int image, String content, String category, int trailer, String premiere, int price) {
        Id = id;
        Name = name;
        Image = image;
        Content = content;
        Category = category;
        Trailer = trailer;
        Premiere = premiere;
        Price = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getTrailer() {
        return Trailer;
    }

    public void setTrailer(int trailer) {
        Trailer = trailer;
    }

    public String getPremiere() {
        return Premiere;
    }

    public void setPremiere(String premiere) {
        Premiere = premiere;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
