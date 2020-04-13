package com.darkbrokengames.fallduly2.utils;

import com.badlogic.gdx.math.Vector2;
import com.darkbrokengames.fallduly2.Assets;

import java.util.Random;

public class Utils {

    private static Random random = new Random();

    public static float valueToUIPixel(float value){
        return value * Assets.UnitScale;
    }
    public static Vector2 vectorToUIPixel(Vector2 vector2){
        return vector2.set(vector2.x * Assets.UnitScale, vector2.y * Assets.UnitScale);
    }

    public static int random(int min, int max){
        if (min > max)
            min = max;
        return random.nextInt(max + 1 - min) + min;
    }

    public static float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
}
