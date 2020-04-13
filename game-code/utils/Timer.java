package com.darkbrokengames.fallduly2.utils;

public class Timer{

    private boolean isSleeping = false;
    private boolean isEnd = true;
    private float time = 0f;
    private float period;

    public Timer(float period){
        this.period = period;
    }

    public void update(float deltaTime){
        if (isSleeping && !isEnd){
            time += deltaTime;
            if (time >= period){
                time = 0;
                isEnd = true;
            }
        }
    }

    public void setPeriod(float period){
        this.period = period;
    }

    public void begin(){
        time = 0;
        isSleeping = true;
        isEnd = false;
    }

    public void end(){
        time = 0;
        isSleeping = false;
        isEnd = true;
    }

    public boolean isSleeping(){
        return isSleeping;
    }

    public boolean isEnd(){
        return isEnd;
    }

}
