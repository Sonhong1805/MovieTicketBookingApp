package com.example.mybtl;

public class Bill {
    private int Id;
    private String MovieName;
    private String MoviePremiere;
    private int MoviePrice;
    private String SelectedChair;

    private String SelectedFood;

    private String MethodPayment;
    private String Email;
    private int TotalPrice;

    public Bill(int id, String movieName, String moviePremiere, int moviePrice, String selectedChair, String selectedFood, String methodPayment, String email, int totalPrice) {
        Id = id;
        MovieName = movieName;
        MoviePremiere = moviePremiere;
        MoviePrice = moviePrice;
        SelectedChair = selectedChair;
        SelectedFood = selectedFood;
        MethodPayment = methodPayment;
        Email = email;
        TotalPrice = totalPrice;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getMoviePremiere() {
        return MoviePremiere;
    }

    public void setMoviePremiere(String moviePremiere) {
        MoviePremiere = moviePremiere;
    }

    public int getMoviePrice() {
        return MoviePrice;
    }

    public void setMoviePrice(int moviePrice) {
        MoviePrice = moviePrice;
    }

    public String getSelectedChair() {
        return SelectedChair;
    }

    public void setSelectedChair(String selectedChair) {
        SelectedChair = selectedChair;
    }

    public String getSelectedFood() {
        return SelectedFood;
    }

    public void setSelectedFood(String selectedFood) {
        SelectedFood = selectedFood;
    }

    public String getMethodPayment() {
        return MethodPayment;
    }

    public void setMethodPayment(String methodPayment) {
        MethodPayment = methodPayment;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
