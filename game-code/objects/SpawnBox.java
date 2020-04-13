package com.darkbrokengames.fallduly2.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.darkbrokengames.fallduly2.Assets;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class SpawnBox {

    private Sprite sprite;
    private Player player; // Игрок.
    private PointLight playerLight; // Точечный источник света игрока.
    private int countPlayer = 1; // Количество создаваемых игроков.
    private boolean playerWasSpawned = false;

    private float speed = 10f; // Скорость движения SpawnBox.
    private float direction = 1f; // Направление движения SpawnBox.

    private boolean effect1 = false; // Эффект (1) у игрока.
    private int effectStep = 0; // Шаг эффекта.
    private int count = 500; // Время эффекта.


    public SpawnBox(Vector2 position, Texture texture, RayHandler rayHandler) {
        sprite = new Sprite(texture);
        sprite.setSize(3.5f, 3.5f);
        sprite.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        sprite.setCenter(position.x, position.y);
        playerLight = new PointLight(rayHandler, 100, new Color(0.1f, 0.1f, 0.1f, 1f), 10f, 0, 0);
        playerLight.setActive(false);
    }

    public void update(float delta){
        sprite.translateX(speed * delta * direction); // Двигаем SpawnBox.
        if (sprite.getX() + sprite.getWidth() / 2f > 14.25f)
            direction = -1f; // Меняем направление влево.
        else if (sprite.getX() + sprite.getWidth() / 2f < -14.25f)
            direction = 1f; // Меняем направление вправо.

        if (effect1) {
            switch (effectStep) {
                case 0:
                    player.getBody().setGravityScale(-8f);
                    player.getSprite().setColor(new Color(1f,0,1f,1f));
                    effectStep++;
                    break;
                case 1:
                    if (count > 0) {
                        count -= delta * 1000;
                    } else {
                        effectStep++;
                    }
                    break;
                case 2:
                    player.getBody().setGravityScale(6f);
                    player.getSprite().setColor(new Color(1f,1f,1f,1f));
                    count = 500;
                    effectStep = 0;
                    effect1 = false;
                    break;
            }
        }

    }

    // Устанавливаем значение countPlayer.
    public void setCountPlayer(int countPlayer){
        this.countPlayer += countPlayer;

    }
    // Получаем значение countPlayer.
    public int getCountPlayer(){
        return countPlayer;

    }

    // Создаем игрока.
    public void spawnPlayer(World world, Assets assets){
        if (!playerWasSpawned) {
            player = new Player(new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f), world, assets); //Создаем игрока.
            playerLight.setActive(true);
            playerLight.attachToBody(player.getBody());
            playerLight.setSoftnessLength(0);
            playerWasSpawned = true;

            effect1 = false;
            effectStep = 0;
            count = 500;
        }
    }
    // Рисуем игрока.
    public void draw(SpriteBatch batch){
        if (player != null && playerWasSpawned)
            player.draw(batch); // Рисуем игрока.
        sprite.draw(batch);
    }
    // Получаем игрока.
    public Player getPlayer(){
        return player;

    }
    // Устанавливаем значение playerWasSpawned.
    public void setPlayerWasSpawned(boolean wasSpawned){
        playerWasSpawned = wasSpawned;

    }
    // Устанавливаем активность playerLight.
    public void setPlayerLightActive(boolean playerLightActive){
        playerLight.setActive(playerLightActive);

    }
    // Устанавливаем скорость движения механизма.
    public void setSpeed(float speed){
        this.speed = speed;

    }
    // Устанавливаем активность эффекта у игрока.
    public void setActiveEffect(boolean active){
        effect1 = active;

    }
}
