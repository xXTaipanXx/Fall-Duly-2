package com.darkbrokengames.fallduly2;

import java.util.Random;

public class LVLSettings{

    private float spawnBoxSpeed = 10f;
    private float winZoneSize = 16f;

    private int minBlocks = 0;
    private int maxBlocks = 0;

    private int minMiniBlocks = 0;
    private int maxMiniBlocks = 0;

    private int maxGreenBlockChance = 1;
    private int maxPurpleBlockChance = 1;
    private int maxRedBlockChance = 1;
    private int maxBlueBlockChance = 1;
    private int maxGoldBlockChance = 1;
    private int maxWhiteBlockChance = 15;

    private int maxCountGreenBlocksChance = 1;
    private int maxCountPurpleBlocksChance = 1;
    private int maxCountRedBlocksChance = 1;
    private int maxCountBlueBlocksChance = 1;
    private int maxCountGoldBlocksChance = 1;
    private int maxCountWhiteBlocksChance = 1;

    private static Random random;

    public LVLSettings(int lvl) {
        setLVLSettings(lvl);
        random = new Random();
    }

    public int randomInt(int min, int max){
        if (min > max)
            min = max;
        return random.nextInt(max + 1 - min) + min;
    }
    public float randomFloat(float min, float max){
        return (float)(Math.random()*(max-min))+min;

    }

    public float getWinZoneSizeX(){
        return winZoneSize + ((winZoneSize * GameData.playerProperty1[GameData.getCurrentPlayerID()]) / 100f);
    }

    public float getWinZoneSpeed(){
        return spawnBoxSpeed;

    }

    public float getWinZonePositionX(){
        return randomFloat(-16f + winZoneSize / 2f, 16f - winZoneSize / 2f);
    }

    public int getCountBlocks(boolean isSpecialBlock, int specialBlockId){
        int rand = 0;
        if (!isSpecialBlock) {
            rand = randomInt(0, maxBlocks);
            if (rand < minBlocks)
                rand = minBlocks;
        }else{
            switch (specialBlockId){
                case 0:
                    break;
                case 1:
                    if(randomInt(1, maxGreenBlockChance - (int)((maxGreenBlockChance * GameData.playerProperty2[GameData.getCurrentPlayerID()]) / 100f)) == 1){
                        rand = randomInt(1, maxCountGreenBlocksChance);
                    }
                    break;
                case 2:
                    if(randomInt(1, maxPurpleBlockChance + (int)((maxPurpleBlockChance * GameData.playerProperty3[GameData.getCurrentPlayerID()]) / 100f)) == 1){
                        rand = randomInt(1, maxCountPurpleBlocksChance);
                    }
                    break;
                case 3:
                    if(randomInt(1, maxRedBlockChance + (int)((maxRedBlockChance * GameData.playerProperty4[GameData.getCurrentPlayerID()]) / 100f)) == 1){
                        rand = randomInt(1, maxCountRedBlocksChance);
                    }
                    break;
                case 4:
                    if(randomInt(1, maxBlueBlockChance - (int)((maxBlueBlockChance * GameData.playerProperty5[GameData.getCurrentPlayerID()]) / 100f)) == 1){
                        rand = randomInt(1, maxCountBlueBlocksChance);
                    }
                    break;
                case 5:
                    if(randomInt(1, maxGoldBlockChance - (int)((maxGoldBlockChance * GameData.playerProperty6[GameData.getCurrentPlayerID()]) / 100f)) == 1){
                        rand = randomInt(1, maxCountGoldBlocksChance);
                    }
                    break;
                case 6:
                    if(randomInt(1, maxWhiteBlockChance) == 1){
                        rand = randomInt(1, maxCountWhiteBlocksChance);
                    }
                    break;
            }
        }
        return rand;
    }

    public int getCountMiniBlocks(){
        int rand = randomInt(0, maxMiniBlocks);
        if (rand < minMiniBlocks)
            rand = minMiniBlocks;
        return rand;
    }

    public void setLVLSettings(int lvl){
        switch (lvl){
            case 0:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 2;
                minMiniBlocks = 0; maxMiniBlocks = 0;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 10;
                break;
            case 5:
                winZoneSize = 20f; spawnBoxSpeed = 12f;
                minBlocks = 1; maxBlocks = 3;
                minMiniBlocks = 0; maxMiniBlocks = 0;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 7; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
                break;
            case 10:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 2; maxBlocks = 3;
                minMiniBlocks = 0; maxMiniBlocks = 0;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 3; maxRedBlockChance = 5; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
                break;
            case 15:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 4;
                minMiniBlocks = 0; maxMiniBlocks = 0;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
                maxCountPurpleBlocksChance = 2;
                break;
            case 20:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 4;
                minMiniBlocks = 0; maxMiniBlocks = 2;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
                maxCountGoldBlocksChance = 2;
                break;
            case 25:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 4;
                minMiniBlocks = 0; maxMiniBlocks = 4;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 5; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
            case 30:
                winZoneSize = 20f; spawnBoxSpeed = 12f;
                minBlocks = 0; maxBlocks = 3;
                minMiniBlocks = 0; maxMiniBlocks = 0;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
            case 35:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 4;
                minMiniBlocks = 0; maxMiniBlocks = 2;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
            case 50:
                winZoneSize = 20f; spawnBoxSpeed = 10f;
                minBlocks = 0; maxBlocks = 5;
                minMiniBlocks = 0; maxMiniBlocks = 4;
                maxGreenBlockChance = 10; maxPurpleBlockChance = 5; maxRedBlockChance = 10; maxBlueBlockChance = 10; maxGoldBlockChance = 5;
                //...
        }
    }
}
