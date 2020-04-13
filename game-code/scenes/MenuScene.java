/**
 * Последнее обновление: 05.01.2020.
 * Разработчик: Андрей Караник.
 * Отчет: Оптимизировано. Работает.
 */

package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.DialogWindow;
import com.darkbrokengames.fallduly2.FlashEffect;
import com.darkbrokengames.fallduly2.GameData;
import com.darkbrokengames.fallduly2.LanguageWindow;
import com.darkbrokengames.fallduly2.objects.DefaultEffectButtonListener;

public class MenuScene extends Scene {

    private FlashEffect transitBetweenScenes;

    private Stage stage;
    private Group mainMenu;
    private Image headerImage, flagImage;
    private Button playButton, shopButton, exitButton, soundButton, musicButton, languageSelectButton;

    private Label versionLabel, languageLabel;
    private final String versionText = "version: 1.1.6";

    private DialogWindow exitDialogWindow;
    private LanguageWindow languageWindow;

    private Music music;

    public MenuScene(final GameSceneManager gsm) {
        super(gsm);

        gsm.showBanner(true);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        mainMenu = new Group();

        versionLabel = new Label(versionText, new LabelStyle(gsm.assets.lifeFont, Color.WHITE));
        versionLabel.setPosition(stage.getWidth(), stage.getHeight(), Align.topRight);
        stage.addActor(versionLabel);

        headerImage = new Image(gsm.assets.getTexture("textures/FallDuly2.png", true));
        headerImage.setSize(32f * Assets.UnitScale, 16 * Assets.UnitScale);
        headerImage.setOrigin(Align.center);
        headerImage.setPosition(0f, stage.getHeight(), Align.topLeft);
        headerImage.setScale(1f, 0f);
        headerImage.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.circle));

        stage.addActor(headerImage);

        //region |Инициализация кнопки| <PLAY>
        playButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonPlay.png", true)));
        playButton.setSize(18f * Assets.UnitScale, 5.625f * Assets.UnitScale);
        playButton.setOrigin(Align.center);
        playButton.setTransform(true);
        playButton.setPosition(stage.getWidth(), stage.getHeight() / 2f + 7f * Assets.UnitScale - playButton.getHeight() / 2f);
        playButton.addListener(new DefaultEffectButtonListener(playButton));
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                mainMenu.setTouchable(Touchable.disabled);

                headerImage.addAction(Actions.scaleTo(1f, 0f, 0.5f, Interpolation.circle));
                playButton.addAction(Actions.moveBy(stage.getWidth() / 2f + playButton.getWidth() / 2f, 0f, 0.75f, Interpolation.swingIn));
                shopButton.addAction(Actions.moveBy(stage.getWidth() / 2f + playButton.getWidth() / 2f, 0f, 0.875f, Interpolation.swingIn));
                exitButton.addAction(Actions.moveBy(stage.getWidth() / 2f + playButton.getWidth() / 2f, 0f, 1f, Interpolation.swingIn));
                soundButton.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));
                musicButton.addAction(Actions.scaleTo(0f, 0f, 1f, Interpolation.swingIn));

                final Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        transitBetweenScenes.begin(FlashEffect.fadeIn, 0.5f);
                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                music.stop();
                                gsm.set(new MainScene(gsm));
                            }
                        }, 0.5f);
                    }
                }, 0.5f);
            }
        });
        playButton.addAction(Actions.moveTo(stage.getWidth() / 2f - playButton.getWidth() / 2f, stage.getHeight()/2f + 7f * Assets.UnitScale - playButton.getHeight() / 2f, 0.75f, Interpolation.swingOut));
        //endregion
        mainMenu.addActor(playButton);
        //region |Инициализация кнопки| <SHOP>
        shopButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonShop.png", true)));
        shopButton.setSize(18f * Assets.UnitScale, 5.625f * Assets.UnitScale);
        shopButton.setOrigin(Align.center);
        shopButton.setTransform(true);
        shopButton.setPosition(stage.getWidth(), stage.getHeight() / 2f - shopButton.getHeight() / 2f);
        shopButton.addListener(new DefaultEffectButtonListener(shopButton));
        shopButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                mainMenu.setTouchable(Touchable.disabled);

                headerImage.addAction(Actions.scaleTo(1f, 0f, 0.5f, Interpolation.circle));
                playButton.addAction(Actions.scaleTo(1f, 0f, 0.75f, Interpolation.circle));
                shopButton.addAction(Actions.scaleTo(0f, 0f, 0.75f, Interpolation.swingIn));
                exitButton.addAction(Actions.scaleTo(1f, 0f, 0.75f, Interpolation.circle));
                soundButton.addAction(Actions.scaleTo(0f, 0f, 0.75f, Interpolation.swingIn));
                musicButton.addAction(Actions.scaleTo(0f, 0f, 0.75f, Interpolation.swingIn));

                final Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        transitBetweenScenes.begin(FlashEffect.fadeIn, 0.5f);
                        timer.scheduleTask(new Timer.Task() {
                            @Override
                            public void run() {
                                gsm.set(new ShopScene(gsm));
                            }
                        }, 0.5f);
                    }
                }, 0.5f);
            }
        });
        shopButton.addAction(Actions.moveTo(stage.getWidth() / 2f - shopButton.getWidth() / 2f, stage.getHeight()/2f - shopButton.getHeight() / 2f, 0.875f, Interpolation.swingOut));
        //endregion
        mainMenu.addActor(shopButton);
        //region |Инициализация кнопки| <EXIT>
        exitButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonExit.png", true)));
        exitButton.setSize(18f * Assets.UnitScale, 5.625f * Assets.UnitScale);
        exitButton.setPosition(stage.getWidth(), stage.getHeight() / 2f - 7f * Assets.UnitScale - exitButton.getHeight() / 2f);
        exitButton.setOrigin(Align.center);
        exitButton.setTransform(true);
        exitButton.addListener(new DefaultEffectButtonListener(exitButton));
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                exitDialogWindow.show(0.75f);
                mainMenu.setTouchable(Touchable.disabled);
            }
        });
        exitButton.addAction(Actions.moveTo(stage.getWidth() / 2f - exitButton.getWidth() / 2f, stage.getHeight()/2f - 7f * Assets.UnitScale - exitButton.getHeight() / 2f, 1f, Interpolation.swingOut));
        //endregion
        mainMenu.addActor(exitButton);

        //region |Инициализация кнопки| <SOUND>
        soundButton = new Button(new ButtonStyle());
        if (GameData.soundActive)
            soundButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonSoundOn.png", true));
        else
            soundButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonSoundOff.png", true));
        soundButton.setSize(5.75f * Assets.UnitScale, 5.75f * Assets.UnitScale);
        soundButton.setOrigin(Align.center);
        soundButton.setTransform(true);
        soundButton.setPosition(stage.getWidth()/2f - 7f * Assets.UnitScale,stage.getHeight()/2f - 17f * Assets.UnitScale, Align.center);
        soundButton.addListener(new DefaultEffectButtonListener(soundButton));
        soundButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                if (GameData.soundActive) {
                    soundButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonSoundOff.png", true));
                    GameData.volumeSound = 0;
                    GameData.soundActive = false;
                } else {
                    soundButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonSoundOn.png", true));
                    GameData.volumeSound = GameData.maxVolumeSound;
                    GameData.soundActive = true;
                }
            }
        });
        soundButton.setScale(0f);
        soundButton.addAction(Actions.scaleTo(1f, 1f, 1f, Interpolation.swingOut));
        //endregion
        mainMenu.addActor(soundButton);
        //region |Инициализация кнопки| <MUSIC>
        musicButton = new Button(new ButtonStyle());
        if (GameData.musicActive)
            musicButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonMusicOn.png", true));
        else
            musicButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonMusicOff.png", true));
        musicButton.setSize(5.75f * Assets.UnitScale, 5.75f * Assets.UnitScale);
        musicButton.setOrigin(Align.center);
        musicButton.setTransform(true);
        musicButton.setPosition(stage.getWidth()/2f + 7f * Assets.UnitScale,stage.getHeight()/2f - 17f * Assets.UnitScale, Align.center);
        musicButton.addListener(new DefaultEffectButtonListener(musicButton));
        musicButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                if (GameData.musicActive) {
                    musicButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonMusicOff.png", true));
                    GameData.volumeMusic = 0;
                    music.setVolume(0);
                    GameData.musicActive = false;
                } else {
                    musicButton.getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonMusicOn.png", true));
                    GameData.volumeMusic = GameData.maxVolumeMusic;
                    music.setVolume(GameData.maxVolumeMusic);
                    GameData.musicActive = true;
                }
            }
        });
        musicButton.setScale(0f);
        musicButton.addAction(Actions.scaleTo(1f, 1f, 1f, Interpolation.swingOut));
        //endregion
        mainMenu.addActor(musicButton);

        //region |Инициализация кнопки| <LANGUAGE>
        languageSelectButton = new Button(new ButtonStyle());
        languageSelectButton.setSize(6.75f * Assets.UnitScale, 5f * Assets.UnitScale);
        languageSelectButton.setOrigin(Align.center);
        languageSelectButton.setTransform(true);
        languageSelectButton.setPosition(versionLabel.getX() + versionLabel.getWidth() / 2f,  versionLabel.getY() - versionLabel.getHeight() / 2f - 0.5f * Assets.UnitScale, Align.center);
        languageSelectButton.addListener(new DefaultEffectButtonListener(languageSelectButton));
        languageSelectButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                languageWindow.show(0.75f);
            }
        });
        //endregion
        mainMenu.addActor(languageSelectButton);

        String textureName = "textures/flags/USA.png";

        if (GameData.getLanguage().equals("en"))
            textureName = "textures/flags/USA.png";
        else if (GameData.getLanguage().equals("ru"))
            textureName = "textures/flags/Russia.png";
        else if (GameData.getLanguage().equals("de"))
            textureName = "textures/flags/Germany.png";
        else if (GameData.getLanguage().equals("fr"))
            textureName = "textures/flags/France.png";
        else if (GameData.getLanguage().equals("es"))
            textureName = "textures/flags/Spain.png";

        flagImage = new Image(gsm.assets.getTexture(textureName, true));
        flagImage.setSize(3f * Assets.UnitScale, 2f * Assets.UnitScale);
        flagImage.setOrigin(Align.center);
        flagImage.setPosition(languageSelectButton.getX() + flagImage.getWidth() / 2f, languageSelectButton.getY() + languageSelectButton.getHeight() / 2f, Align.center);
        flagImage.setTouchable(Touchable.disabled);
        mainMenu.addActor(flagImage);
        languageLabel = new Label(gsm.assets.translation.H_LANGUAGE, new LabelStyle(gsm.assets.backPageFont, gsm.assets.pageFont.getColor()));
        languageLabel.setAlignment(Align.right);
        languageLabel.setPosition(languageSelectButton.getX() + languageSelectButton.getWidth(), languageSelectButton.getY() + languageSelectButton.getHeight() / 2f, Align.right);
        languageLabel.setTouchable(Touchable.disabled);
        mainMenu.addActor(languageLabel);

        stage.addActor(mainMenu);

        exitDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.cancelAndOk);
        exitDialogWindow.getButton1().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                exitDialogWindow.hide(0.75f);
                Timer timer = new Timer();
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        mainMenu.setTouchable(Touchable.enabled);
                    }
                }, 0.75f);
                timer.start();
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
                Gdx.app.exit();
            }
        });
        exitDialogWindow.getTitleLabel().setText("<!>");
        exitDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.WANT_TO_GO_OUT);
        stage.addActor(exitDialogWindow);

        languageWindow = new LanguageWindow(gsm.assets, stage);
        languageWindow.getButton1().addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                exitDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.WANT_TO_GO_OUT);
                String textureName = "textures/flags/USA.png";
                if (GameData.getLanguage().equals("en"))
                    textureName = "textures/flags/USA.png";
                else if (GameData.getLanguage().equals("ru"))
                    textureName = "textures/flags/Russia.png";
                else if (GameData.getLanguage().equals("de"))
                    textureName = "textures/flags/Germany.png";
                else if (GameData.getLanguage().equals("fr"))
                    textureName = "textures/flags/France.png";
                else if (GameData.getLanguage().equals("es"))
                    textureName = "textures/flags/Spain.png";

                flagImage.setDrawable(new TextureRegionDrawable(gsm.assets.getTexture(textureName, true)));
                languageLabel.setText(gsm.assets.translation.H_LANGUAGE);
            }
        });
        stage.addActor(languageWindow);

        transitBetweenScenes = new FlashEffect(gsm.assets.getTexture("textures/Square16x16.png", false), stage);
        transitBetweenScenes.begin(FlashEffect.fadeOut, 1f);

        music = (Music)gsm.assets.get("audio/Music.wav");
        music.setVolume(GameData.volumeMusic);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void update(float deltaTime) {
        camera.update();
        stage.act(deltaTime);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //...
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
