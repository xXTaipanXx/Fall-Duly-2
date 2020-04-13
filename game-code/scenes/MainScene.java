package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.DialogWindow;
import com.darkbrokengames.fallduly2.FlashEffect;
import com.darkbrokengames.fallduly2.GameData;
import com.darkbrokengames.fallduly2.LVLSettings;
import com.darkbrokengames.fallduly2.MyContactListener;
import com.darkbrokengames.fallduly2.objects.Block;
import com.darkbrokengames.fallduly2.objects.DefaultEffectButtonListener;
import com.darkbrokengames.fallduly2.objects.MiniBlock;
import com.darkbrokengames.fallduly2.objects.SpawnBox;
import com.darkbrokengames.fallduly2.objects.Zone;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class MainScene extends Scene {

    private FlashEffect transitBetweenScenes;

    private Stage stage, backgroundStage;
    private Image topPanel, bottomPanel, bottomPanel2, moneyImage;
    private Button pauseButton, spawnPlayerButton;
    private Label scoreLabel, bestScoreLabel, moneyLabel, lifeLabel, bonusMoneyLabel;
    private DialogWindow respawnDialogWindow, exitDialogWindow;
    private FlashEffect flashEffect;

    private Box2DDebugRenderer b2dr; // Отладка BOX2D.
    private World world; // Физический мир.
    private RayHandler rayHandler; // Обработчик лучей.
    private Array<PointLight> pointLights; // Точечные источники света.

    private Sprite spawnBoxLine;
    private SpawnBox spawnBox;

    private Array<Sprite> shadowBlocks;
    private Array<Block> blocks; // Блоки.
    private Array<MiniBlock> miniBlocks; // Мини-блоки.
    private Zone winZone; // Зона выйгрыша.
    private Zone deathZone; // Зона смерти.

    private LVLSettings lvlSettings; // Настройки уровня.
    private int lvl = 0; // Текущий уровень.
    private int bonusMoney = 0; // Бонус: +1 монета.
    private boolean bonusLife = true; // Дополнительная жизнь.

    public MainScene(final GameSceneManager gsm) {
        super(gsm);

        gsm.showBanner(false);
        GameData.isVideoForSpawn = true;

        stage = new Stage();
        backgroundStage = new Stage();
        Gdx.input.setInputProcessor(stage);

        topPanel = new Image(gsm.assets.getTexture("textures/Square16x16.png", true));
        topPanel.setColor(0.15f, 0.15f, 0.15f, 1f);
        topPanel.setSize(stage.getWidth(), 7.5f * Assets.UnitScale);
        topPanel.setOrigin(Align.top);
        topPanel.setPosition(0f, stage.getHeight(), Align.topLeft);
        topPanel.setScale(1f, 0f);
        topPanel.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.circle));
        backgroundStage.addActor(topPanel);

        bottomPanel = new Image(gsm.assets.getTexture("textures/Square16x16.png", true));
        bottomPanel.setColor(0.15f, 0.15f, 0.15f, 1f);
        bottomPanel.setSize(stage.getWidth(), 5f * Assets.UnitScale);
        bottomPanel.setOrigin(Align.bottom);
        bottomPanel.setPosition(0f, 0f);
        bottomPanel.setScale(1f, 0f);
        bottomPanel.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.circle));
        backgroundStage.addActor(bottomPanel);

        bottomPanel2 = new Image(gsm.assets.getTexture("textures/Square16x16.png", true));
        bottomPanel2.setColor(0.075f, 0.075f, 0.075f, 1f);
        bottomPanel2.setSize(stage.getWidth(), 1f * Assets.UnitScale);
        bottomPanel2.setOrigin(Align.bottom);
        bottomPanel2.setPosition(0f, 0f);
        bottomPanel2.setScale(1f, 0f);
        bottomPanel2.addAction(Actions.scaleTo(1f, 1f, 0.25f, Interpolation.circle));
        backgroundStage.addActor(bottomPanel2);

        scoreLabel = new Label("0", new Label.LabelStyle(gsm.assets.scoreFont, Color.WHITE));
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f + 10f * Assets.UnitScale, Align.center);
        backgroundStage.addActor(scoreLabel);

        bestScoreLabel = new Label(gsm.assets.translation.BEST_SCORE + ": 0", new Label.LabelStyle(gsm.assets.bestScoreFont, gsm.assets.bestScoreFont.getColor()));
        bestScoreLabel.setAlignment(Align.left);
        bestScoreLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - 3.5f * Assets.UnitScale, Align.center);
        bestScoreLabel.setText(gsm.assets.translation.BEST_SCORE + ": " + GameData.getBestScore());
        stage.addActor(bestScoreLabel);

        moneyImage = new Image(gsm.assets.getTextureAtlas("skins/BlockSkins.atlas").findRegion("GoldBlock"));
        moneyImage.setSize(2f * Assets.UnitScale, 2f * Assets.UnitScale);
        moneyImage.setOrigin(Align.center);
        moneyImage.setPosition(stage.getWidth() - 3f * Assets.UnitScale, stage.getHeight() - topPanel.getHeight() / 3f * 2.25f, Align.center);
        moneyImage.setRotation(45f);
        moneyImage.setScale(0f, 0f);
        moneyImage.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.circle));
        stage.addActor(moneyImage);

        moneyLabel = new Label("0", new Label.LabelStyle(gsm.assets.moneyFont, Color.WHITE));
        moneyLabel.setAlignment(Align.right);
        moneyLabel.setPosition(stage.getWidth() - 5.25f * Assets.UnitScale, stage.getHeight() - topPanel.getHeight() / 3f * 2.25f, Align.center);
        moneyLabel.setText(GameData.getMoney());
        stage.addActor(moneyLabel);

        lifeLabel = new Label(gsm.assets.translation.LIFE + ": 1", new Label.LabelStyle(gsm.assets.lifeFont, Color.WHITE));
        lifeLabel.setAlignment(Align.center);
        lifeLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - topPanel.getHeight() / 3f * 2.25f, Align.center);
        stage.addActor(lifeLabel);

        bonusMoneyLabel = new Label("+1", new Label.LabelStyle(gsm.assets.bonusMoneyFont, gsm.assets.bonusMoneyFont.getColor()));
        bonusMoneyLabel.setAlignment(Align.center);
        bonusMoneyLabel.setPosition(stage.getWidth() - 1.25f * Assets.UnitScale, stage.getHeight() - topPanel.getHeight() / 3f * 2.5f, Align.center);
        bonusMoneyLabel.setVisible(false);
        stage.addActor(bonusMoneyLabel);

        //region |Инициализация кнопки| <PAUSE>
        pauseButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonPause.png", true)));
        pauseButton.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        pauseButton.setOrigin(Align.center);
        pauseButton.setTransform(true);
        pauseButton.setPosition(3.75f * Assets.UnitScale, stage.getHeight() - 3.75f * Assets.UnitScale, Align.center);
        pauseButton.addListener(new DefaultEffectButtonListener(pauseButton));
        pauseButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                spawnPlayerButton.setTouchable(Touchable.disabled);
                pauseButton.setTouchable(Touchable.disabled);
                exitDialogWindow.show(0.75f);
            }
        });
        pauseButton.setScale(0f);
        pauseButton.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.swingOut));
        //endregion
        stage.addActor(pauseButton);

        //region |Инициализация кнопки| <SPAWN>
        spawnPlayerButton = new Button(new Button.ButtonStyle());
        spawnPlayerButton.setSize(stage.getWidth(), stage.getHeight() * 0.75f);
        spawnPlayerButton.setOrigin(Align.center);
        spawnPlayerButton.setTransform(true);
        spawnPlayerButton.setPosition(0f, 0f);
        spawnPlayerButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                spawnBox.spawnPlayer(world, gsm.assets);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
        //endregion
        stage.addActor(spawnPlayerButton);

        respawnDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.cancelAndOk);
        respawnDialogWindow.getTitleLabel().setText("<!>");
        respawnDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.WATCH_VIDEO_LIFE);
        respawnDialogWindow.getButton1().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                Death(true);
            }
        });
        respawnDialogWindow.getButton2().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                spawnPlayerButton.setTouchable(Touchable.enabled);
                pauseButton.setTouchable(Touchable.enabled);
                respawnDialogWindow.hide(0.75f);
                addBonusLife();
            }
        });
        stage.addActor(respawnDialogWindow);

        exitDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.cancelAndOk);
        exitDialogWindow.getTitleLabel().setText("<!>");
        exitDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.WANT_TO_GO_MENU);
        exitDialogWindow.getButton1().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                spawnPlayerButton.setTouchable(Touchable.enabled);
                pauseButton.setTouchable(Touchable.enabled);
                exitDialogWindow.hide(0.75f);
            }
        });
        exitDialogWindow.getButton2().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                gsm.set(new MenuScene(gsm));
            }
        });
        stage.addActor(exitDialogWindow);

        flashEffect = new FlashEffect(gsm.assets.getTexture("textures/Square16x16.png", false), stage);

        transitBetweenScenes = new FlashEffect(gsm.assets.getTexture("textures/Square16x16.png", false), stage);
        transitBetweenScenes.begin(FlashEffect.fadeOut, 1f);


        spawnBoxLine = new Sprite(gsm.assets.getTexture("textures/SpawnBoxLine.png", false));
        spawnBoxLine.setSize(camera.viewportWidth, 1f);
        spawnBoxLine.setOrigin(spawnBoxLine.getWidth() / 2f, spawnBoxLine.getHeight() / 2f);
        spawnBoxLine.setCenter(0f, 14f);

        world = new World(new Vector2(0, -9.8f), false);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        // Создаем обработчик лучей (света).
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f);
        // Создаем источники света (точечные).
        pointLights = new Array<PointLight>();
        pointLights.add(new PointLight(rayHandler, 200, new Color(0.3f,0.3f,0.5f,1f), 25, 20, 11));
        pointLights.add(new PointLight(rayHandler, 200, new Color(0.3f,0.3f,0.5f,1f), 25, -20, 11));

        spawnBox = new SpawnBox(new Vector2(0f, 14f), gsm.assets.getTexture("textures/SpawnBox.png", false), rayHandler);

        deathZone = new Zone(new Vector2(0f, -camera.viewportHeight / 2f - 1f), new Vector2(320f, 1f), world, gsm.assets);
        deathZone.tag = "DeathZone";

        shadowBlocks = new Array<Sprite>();

        //region |Создаем shadowBlocks|
        float y = 2f;
        for (int i = 0; i < 4; i++){
            float x = -13f;
            for (int j = 0; j < 5; j++) {
                shadowBlocks.add(new Sprite(gsm.assets.getTexture("textures/Square16x16.png", false)));
                shadowBlocks.get(shadowBlocks.size - 1).setSize(3.5f, 3.5f);
                shadowBlocks.get(shadowBlocks.size - 1).setOrigin(shadowBlocks.get(shadowBlocks.size - 1).getWidth() / 2f, shadowBlocks.get(shadowBlocks.size - 1).getHeight() / 2f);
                shadowBlocks.get(shadowBlocks.size - 1).setCenter(x, y);
                shadowBlocks.get(shadowBlocks.size - 1).setRotation(45f);
                shadowBlocks.get(shadowBlocks.size - 1).setColor(new Color(0.1f, 0.1f, 0.1f, 1f));
                x += 6.5f;
            }
            y -= 6.5f;
        }
        y = -1.25f;
        for (int i = 0; i < 3; i++){
            float x = -9.75f;
            for (int j = 0; j < 4; j++) {
                shadowBlocks.add(new Sprite(gsm.assets.getTexture("textures/Square16x16.png", false)));
                shadowBlocks.get(shadowBlocks.size - 1).setSize(1.5f, 1.5f);
                shadowBlocks.get(shadowBlocks.size - 1).setOrigin(shadowBlocks.get(shadowBlocks.size - 1).getWidth() / 2f, shadowBlocks.get(shadowBlocks.size - 1).getHeight() / 2f);
                shadowBlocks.get(shadowBlocks.size - 1).setCenter(x, y);
                shadowBlocks.get(shadowBlocks.size - 1).setRotation(45f);
                shadowBlocks.get(shadowBlocks.size - 1).setColor(new Color(0.1f, 0.1f, 0.1f, 1f));
                x += 6.5f;
            }
            y -= 6.5f;
        }
        //endregion

        lvlSettings = new LVLSettings(lvl);
        generateLvl();
    }

    @Override
    public void update(float deltaTime) {
        camera.update();
        backgroundStage.act(deltaTime);
        stage.act(deltaTime);
        world.step(deltaTime, 6, 2);
        rayHandler.update();
        spawnBox.update(deltaTime);

        //region <Проверяем контакт игрока с физ. объектами>
        if (spawnBox.getPlayer() != null) {

            //region <Проверяем на контакт с блоками>
            switch (spawnBox.getPlayer().getContactSpecialBlock()) {
                case 0:
                    break;
                case 1:
                    flashEffect.setColor(new Color(0f, 1f, 0.5f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    bonusMoney += 10;
                    bonusMoneyLabel.setVisible(true);
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    break;
                case 2:
                    flashEffect.setColor(new Color(1f, 0f, 1f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    spawnBox.setActiveEffect(true);
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    break;
                case 3:
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    flashEffect.setColor(new Color(1f, 0f, 0f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    Death(false);
                    break;
                case 4:
                    flashEffect.setColor(new Color(0f, 0f, 1f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    Win(4);
                    break;
                case 5:
                    if (bonusMoney > 0) {
                        GameData.setMoney(2);
                    } else {
                        GameData.setMoney(1);
                    }
                    moneyLabel.setText(GameData.getMoney());
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    break;
                case 6:
                    flashEffect.setColor(new Color(1f, 1f, 1f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    spawnBox.setCountPlayer(1);
                    lifeLabel.setText(gsm.assets.translation.LIFE + ": " + spawnBox.getCountPlayer());
                    spawnBox.getPlayer().setContactSpecialBlock(0);
                    break;
            }
            //endregion

            //region <Проверяем на контакт с зонами>
            switch (spawnBox.getPlayer().getContactZone()) {
                case 0:
                    break;
                case 1:
                    spawnBox.getPlayer().setContactZone(0);
                    Win(0);
                    break;
                case 2:
                    spawnBox.getPlayer().setContactZone(0);
                    flashEffect.setColor(new Color(1f, 0f, 0f, 0));
                    flashEffect.begin(FlashEffect.flash, 0.125f, 0.25f);
                    Death(false);
                    break;
            }
            //endregion

        }
        //endregion
    }

    @Override
    public void render(SpriteBatch batch) {
        backgroundStage.draw();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        winZone.draw(batch);
        for (int i = 0; i < shadowBlocks.size; i++)
            shadowBlocks.get(i).draw(batch);
        for (int i = 0; i < blocks.size; i++)
            blocks.get(i).draw(batch);
        for (int i = 0; i < miniBlocks.size; i++)
            miniBlocks.get(i).draw(batch);
        spawnBoxLine.draw(batch);
        spawnBox.draw(batch);
        batch.end();
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.render();
        //b2dr.render(world, camera.combined);
        stage.draw();
    }

    private void addBonusLife(){
        gsm.showRewardedAd();
        bonusLife = false;
    }

    private void Win(int bonusLvl){
        spawnBox.setPlayerLightActive(false);
        gsm.assets.getSound("audio/Sound03.wav").play(GameData.volumeSound);

        if (bonusMoney > 0)
            bonusMoney--;
        else
            bonusMoneyLabel.setVisible(false);

        lvl++;
        lvl += bonusLvl;
        scoreLabel.setText(lvl + "");
        generateLvl();
    }

    private void Death(boolean fullDead){
        Gdx.input.vibrate(200);
        gsm.assets.getSound("audio/Sound03.wav").play(GameData.volumeSound);

        if (gsm.rewardedAdIsLoaded() && bonusLife && spawnBox.getCountPlayer() == 1 && !fullDead){
            respawnDialogWindow.show(0.75f);
            spawnPlayerButton.setTouchable(Touchable.disabled);
            pauseButton.setTouchable(Touchable.disabled);
            Win(0);
        }else {
            if (spawnBox.getCountPlayer() == 1 || fullDead) {
                GameData.isVideoForSpawn = false;
                GameData.setBestScore(lvl);
                gsm.showInterstitialAd();
                gsm.set(new MenuScene(gsm));
            } else {
                spawnBox.setCountPlayer(-1);
                spawnBox.setPlayerLightActive(false);
                Win(0);
            }
        }
    }

    private void generateLvl(){
        if (lvl != 0) {
            for (Block block : blocks) {
                block.destroy();
            }
            for (MiniBlock miniBlock : miniBlocks) {
                miniBlock.destroy();
            }
            winZone.destroy();
            spawnBox.getPlayer().destroy();
            world.getContactList().clear();
        }

        lvlSettings.setLVLSettings(lvl);
        spawnBox.setSpeed(lvlSettings.getWinZoneSpeed());
        blocks = new Array<Block>();
        miniBlocks = new Array<MiniBlock>();
        winZone = new Zone(new Vector2(lvlSettings.getWinZonePositionX(), -camera.viewportHeight / 2f + 0.5f), new Vector2(lvlSettings.getWinZoneSizeX(), 1f), world, gsm.assets);
        winZone.tag = "WinZone";

        //region <Создаем блоки>
        float posY = 2f;
        float[] posX = {-13f, -6.5f, 0f, 6.5f, 13f};
        int id = 0;
        for (int i = 0; i < 4; i++){
            boolean[] isEmpty = new boolean[5];
            int max = lvlSettings.getCountBlocks(false, 0);
            for (int j = 0; j < max; j++) {
                while (true) {
                    int rand = (int) (Math.random() * 5);
                    if (!isEmpty[rand]) {
                        blocks.add(new Block(new Vector2(posX[rand], posY), world, gsm.assets));
                        blocks.get(id).worldId = id;
                        blocks.get(id).tag = "Block";
                        isEmpty[rand] = true;
                        id++;
                        break;
                    }
                }
            }
            posY -= 6.5f;
        }


        posY = -1.25f;
        float[] posX2 = {-9.75f, -3.25f, 3.25f, 9.75f};
        for (int i = 0; i < 3; i++){
            boolean[] isEmpty = new boolean[4];
            int max = lvlSettings.getCountMiniBlocks();
            for (int j = 0; j < max; j++) {
                while (true) {
                    int rand = (int) (Math.random() * 4);
                    if (!isEmpty[rand]) {
                        miniBlocks.add(new MiniBlock(new Vector2(posX2[rand], posY), world, gsm.assets));
                        isEmpty[rand] = true;
                        break;
                    }
                }
            }
            posY -= 6.5f;
        }


        int countBlocks = blocks.size;

        for (int i = 1; i < 7; i++){
            int countSpecialBlocks = lvlSettings.getCountBlocks(true, i);
            while (countSpecialBlocks > 0 && countBlocks > 0){
                int rand = (int)(Math.random() * blocks.size);
                if (!blocks.get(rand).getIsSpecialBlock()){
                    switch (i){
                        case 1:
                            blocks.get(rand).greenBlock();
                            break;
                        case 2:
                            blocks.get(rand).purpleBlock();
                            break;
                        case 3:
                            blocks.get(rand).redBlock();
                            break;
                        case 4:
                            blocks.get(rand).blueBlock();
                            break;
                        case 5:
                            blocks.get(rand).goldBlock();
                            break;
                        case 6:
                            blocks.get(rand).whiteBlock();
                            break;
                    }
                    countSpecialBlocks--;
                    countBlocks--;
                }
            }
        }
        //endregion

        lifeLabel.setText(gsm.assets.translation.LIFE + ": " + spawnBox.getCountPlayer());
        spawnBox.setPlayerWasSpawned(false);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        rayHandler.dispose();
    }
}
