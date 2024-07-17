package com.example.mybtl;

public class Bill {
    private int Id;
    private int IdBill;
    private int IdMovie;
    private String MovieName;
    private String MoviePremiere;
    private int MoviePrice;
    private String DateOrder;
    private String TimeOrder;
    private String SelectedChair;
    private String SelectedFood;
    private String MethodPayment;
    private String Email;
    private int TotalPrice;

    public Bill(int id, int idBill, int idMovie, String movieName, String moviePremiere, int moviePrice, String dateOrder, String timeOrder, String selectedChair, String selectedFood, String methodPayment, String email, int totalPrice) {
        Id = id;
        IdBill = idBill;
        IdMovie = idMovie;
        MovieName = movieName;
        MoviePremiere = moviePremiere;
        MoviePrice = moviePrice;
        DateOrder = dateOrder;
        TimeOrder = timeOrder;
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

    public int getIdBill() {
        return IdBill;
    }

    public void setIdBill(int idBill) {
        IdBill = idBill;
    }

    public int getIdMovie() {
        return IdMovie;
    }

    public void setIdMovie(int idMovie) {
        IdMovie = idMovie;
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

    public String getDateOrder() {
        return DateOrder;
    }

    public void setDateOrder(String dateOrder) {
        DateOrder = dateOrder;
    }

    public String getTimeOrder() {
        return TimeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        TimeOrder = timeOrder;
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
