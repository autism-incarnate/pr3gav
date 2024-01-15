package ru.pr3.framework.util;

public enum foodType {
    FRUIT ("Фрукт", "FRUIT"),
    VEGETABLE ("Овощ", "VEGETABLE");

    private final String rus;
    private final String eng;
    foodType(String rus, String eng) { this.rus = rus; this.eng = eng; }
    public String getFruitNameRus() { return rus; }
    public String getFruitNameEng() { return eng; }
}
