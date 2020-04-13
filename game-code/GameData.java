package com.darkbrokengames.fallduly2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Locale;

public class GameData {

    public final static int mode = 1; //0 - androidMode, 1 - desktopMode.

    private static Preferences prefs;

    public static boolean soundActive = true;
    public static boolean musicActive = true;

    private static int currentPlayerID; // ID текущего скина игрока.
    private static int currentPage = 1;

    public static boolean isVideoForSpawn = false;

    private static boolean[] boughtPlayer = new boolean[54];
    public static int[] playerPrice; // Цены на скин игрока.
    public static int[] playerProperty1;
    public static int[] playerProperty2;
    public static int[] playerProperty3;
    public static int[] playerProperty4;
    public static int[] playerProperty5;
    public static int[] playerProperty6;
    public static String[] playerPropertyInfo = new String[54];

    private static int money; // Монеты.

    private static int bestScore; // Лучший счет.

    public final static float maxVolumeSound = 0.5f;
    public final static float maxVolumeMusic = 0.75f;

    public static float volumeMusic = 0f;
    public static float volumeSound = 0f;

    private static String language = ""; // default: english.


    public static void loadData() {
        prefs = Gdx.app.getPreferences("My Preferences"); // Получаем файл персональных данных.
        bestScore = prefs.getInteger("bestScore"); // Получаем текущий лучший результат.
        money = prefs.getInteger("money"); // Получаем монеты
        currentPage = prefs.getInteger("currentPage");
        currentPlayerID = prefs.getInteger("currentPlayerID");
        if (currentPage == 0)
            currentPage = 1;

        for (int i = 0; i < boughtPlayer.length; i++) {
            boughtPlayer[i] = prefs.getBoolean("boughtPlayer" + i);
        }
        boughtPlayer[0] = true;

        playerPrice = new int[]{
                0, 5, 10, 15, 20, 25, 30, 35, 40,
                10, 16, 22, 28, 34, 40, 46, 52, 58,
                16, 23, 30, 37, 44, 51, 58, 65, 72,
                23, 31, 39, 47, 55, 63, 71, 79, 87,
                31, 40, 49, 58, 67, 76, 85, 94, 103,
                40, 50, 60, 70, 80, 90, 100, 110, 120};
        playerProperty1 = new int[]{
                1, 4, 7, 10, 13, 16, 19, 22, 25,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerProperty2 = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                10, 15, 20, 25, 30, 35, 40, 45, 50,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerProperty3 = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                10, 20, 30, 40, 50, 60, 70, 80, 90,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerProperty4 = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                10, 20, 30, 40, 50, 60, 70, 80, 90,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerProperty5 = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                10, 15, 20, 25, 30, 35, 40, 45, 50,
                0, 0, 0, 0, 0, 0, 0, 0, 0};
        playerProperty6 = new int[]{
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                35, 40, 45, 50, 55, 60, 65, 70, 75};
        for (int i = 0; i < 9; i++)
            playerPropertyInfo[i] = "Winning Zone +" + playerProperty1[i] + "%";
        for (int i = 9; i < 9 * 2; i++)
            playerPropertyInfo[i] = "Green Block Chance +" + playerProperty2[i] + "%";
        for (int i = 9 * 2; i < 9 * 3; i++)
            playerPropertyInfo[i] = "Purple Block Chance -" + playerProperty3[i] + "%";
        for (int i = 9 * 3; i < 9 * 4; i++)
            playerPropertyInfo[i] = "Red Block Chance -" + playerProperty4[i] + "%";
        for (int i = 9 * 4; i < 9 * 5; i++)
            playerPropertyInfo[i] = "Blue Block Chance +" + playerProperty5[i] + "%";
        for (int i = 9 * 5; i < 9 * 6; i++)
            playerPropertyInfo[i] = "Gold Block Chance +" + playerProperty6[i] + "%";

        volumeMusic = maxVolumeMusic;
        volumeSound = maxVolumeSound;
        language = prefs.getString("language");
        if (language.equals(""))
            setLanguage(Locale.getDefault().getLanguage());
    }

    public static String getLanguage(){
        return language;
    }

    public static void setLanguage(String lang){
        language = lang;
        prefs.putString("language", language).flush();
    }

    public static void setMoney(int _money) {
        money += _money;
        prefs.putInteger("money", money);
        prefs.flush();
    }

    public static int getMoney() {
        return money;

    }

    public static void setBestScore(int score) {
        if (score > bestScore) {
            bestScore = score;
            prefs.putInteger("bestScore", bestScore);
            prefs.flush();
        }
    }

    public static int getBestScore() {
        return bestScore;

    }

    public static void setCurrentPlayerID(int _currentPlayerID) {
        currentPlayerID = _currentPlayerID;
        prefs.putInteger("currentPlayerID", currentPlayerID);
        prefs.flush();
    }

    public static int getCurrentPlayerID() {
        return currentPlayerID;

    }

    public static void setCurrentPage(int _currentPage) {
        currentPage = _currentPage;
        prefs.putInteger("currentPage", currentPage);
        prefs.flush();
    }

    public static int getCurrentPage() {
        return currentPage;

    }

    public static void setBoughtPlayer(int id) {
        boughtPlayer[id] = true;
        prefs.putBoolean("boughtPlayer" + id, boughtPlayer[id]);
        prefs.flush();
    }

    public static boolean getBoughtPlayer(int id) {
        return boughtPlayer[id];

    }
}
