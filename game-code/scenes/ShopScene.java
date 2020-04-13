package com.darkbrokengames.fallduly2.scenes;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.darkbrokengames.fallduly2.Assets;
import com.darkbrokengames.fallduly2.DialogWindow;
import com.darkbrokengames.fallduly2.FlashEffect;
import com.darkbrokengames.fallduly2.GameData;
import com.darkbrokengames.fallduly2.objects.DefaultEffectButtonListener;
import com.darkbrokengames.fallduly2.objects.ShopButton;

public class ShopScene extends Scene implements ShopMessage {

    private int page = 1;
    private int maxPage = 6;
    private int currentIdForBuy = 0;

    private FlashEffect transitBetweenScenes;

    private Stage stage;
    private Image topPanel, bottomPanel, moneyImage;
    private Button backButton, backPageButton, nextPageButton, watchTheVideoButton;
    private DialogWindow buyDialogWindow, notEnoughMoneyDialogWindow, notVideoDialogWindow;
    private Label headerLabel, moneyLabel, backPageLabel, nextPageLabel, pageLabel, descriptionLabel;
    private Group shopItemsGroup;
    private Array<ShopButton> shopButtons;
    private Array<Image> skins;
    private Array<Image> moneyPriceImages;
    private Array<Image> buyImages;
    private Array<Label> priceTexts;

    public ShopScene(final GameSceneManager gsm) {
        super(gsm);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        topPanel = new Image(gsm.assets.getTexture("textures/Square16x16.png", true));
        topPanel.setColor(0.15f, 0.15f, 0.15f, 1f);
        topPanel.setSize(stage.getWidth(), 7.5f * Assets.UnitScale);
        topPanel.setOrigin(Align.top);
        topPanel.setPosition(0f, stage.getHeight(), Align.topLeft);
        topPanel.setScale(1f, 0f);
        topPanel.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.circle));
        stage.addActor(topPanel);

        bottomPanel = new Image(gsm.assets.getTexture("textures/Square16x16.png", true));
        bottomPanel.setColor(0.15f, 0.15f, 0.15f, 1f);
        bottomPanel.setSize(stage.getWidth(), 5f * Assets.UnitScale);
        bottomPanel.setOrigin(Align.bottom);
        bottomPanel.setPosition(0f, 0f);
        bottomPanel.setScale(1f, 0f);
        bottomPanel.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.circle));
        stage.addActor(bottomPanel);

        backButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonBack.png", true)));
        backButton.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        backButton.setOrigin(Align.center);
        backButton.setTransform(true);
        backButton.setPosition(3.75f * Assets.UnitScale, stage.getHeight() - 3.75f * Assets.UnitScale, Align.center);
        backButton.addListener(new DefaultEffectButtonListener(backButton));
        backButton.addListener(new InputListener(){
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
        backButton.setScale(0f);
        backButton.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.swingOut));
        stage.addActor(backButton);

        watchTheVideoButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonWatchTheVideo.png", true)));
        watchTheVideoButton.setSize(3.5f * Assets.UnitScale, 3.5f * Assets.UnitScale);
        watchTheVideoButton.setOrigin(Align.center);
        watchTheVideoButton.setTransform(true);
        watchTheVideoButton.setPosition(stage.getWidth() - 3.75f * Assets.UnitScale, stage.getHeight() - 7.5f * Assets.UnitScale, Align.center);
        watchTheVideoButton.addListener(new DefaultEffectButtonListener(watchTheVideoButton));
        watchTheVideoButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                nextPageButton.setTouchable(Touchable.disabled);
                backPageButton.setTouchable(Touchable.disabled);
                watchTheVideoButton.setTouchable(Touchable.disabled);
                shopItemsGroup.setTouchable(Touchable.disabled);

                if (gsm.rewardedAdIsLoaded()) {
                    gsm.showRewardedAd();
                } else {
                    notVideoDialogWindow.show(0.75f);
                }

                notVideoDialogWindow.show(0.75f);
            }
        });
        watchTheVideoButton.setScale(0f);
        watchTheVideoButton.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.swingOut));
        stage.addActor(watchTheVideoButton);

        backPageButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonBackPage.png", true)));
        backPageButton.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        backPageButton.setOrigin(Align.center);
        backPageButton.setTransform(true);
        backPageButton.setPosition(stage.getWidth() / 2f - 12.25f * Assets.UnitScale, stage.getHeight() / 2f - 15f * Assets.UnitScale, Align.center);
        backPageButton.addListener(new DefaultEffectButtonListener(backPageButton));
        backPageButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                if (page != 1) {
                    page--;

                    for (int i = 0; i < 9; i++) {
                        skins.get(i).setDrawable(new TextureRegionDrawable(gsm.assets.getTextureAtlas("skins/PlayerSkins.atlas").findRegion("Player" + (i + (page - 1) * 9))));
                        shopButtons.get(i).setId(i + (page - 1) * 9);
                        shopButtons.get(i).setPage(page);
                        shopButtons.get(i).getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonItemShop.png", true));
                    }
                }
            }
        });
        backPageButton.setScale(0f);
        backPageButton.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.swingOut));
        stage.addActor(backPageButton);

        nextPageButton = new Button(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonNextPage.png", true)));
        nextPageButton.setSize(5f * Assets.UnitScale, 5f * Assets.UnitScale);
        nextPageButton.setOrigin(Align.center);
        nextPageButton.setTransform(true);
        nextPageButton.setPosition(stage.getWidth() / 2f + 12.25f * Assets.UnitScale, stage.getHeight() / 2f - 15f * Assets.UnitScale, Align.center);
        nextPageButton.addListener(new DefaultEffectButtonListener(nextPageButton));
        nextPageButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                if (page < maxPage) {
                    page++;

                    for (int i = 0; i < 9; i++) {
                        skins.get(i).setDrawable(new TextureRegionDrawable(gsm.assets.getTextureAtlas("skins/PlayerSkins.atlas").findRegion("Player" + (i + (page - 1) * 9))));
                        shopButtons.get(i).setId(i + (page - 1) * 9);
                        shopButtons.get(i).setPage(page);
                        shopButtons.get(i).getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonItemShop.png", true));
                    }
                }
            }
        });
        nextPageButton.setScale(0f);
        nextPageButton.addAction(Actions.scaleTo(1f, 1f, 0.75f, Interpolation.swingOut));
        stage.addActor(nextPageButton);

        headerLabel = new Label(gsm.assets.translation.SHOP, new Label.LabelStyle(gsm.assets.shopFont, Color.WHITE));
        headerLabel.setAlignment(Align.center);
        headerLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() - 3.5f * Assets.UnitScale, Align.center);
        stage.addActor(headerLabel);

        moneyImage = new Image(gsm.assets.getTextureAtlas("skins/BlockSkins.atlas").findRegion("GoldBlock"));
        moneyImage.setSize(2f * Assets.UnitScale, 2f * Assets.UnitScale);
        moneyImage.setOrigin(Align.center);
        moneyImage.setPosition(stage.getWidth() - 3f * Assets.UnitScale, stage.getHeight() - 3.75f * Assets.UnitScale, Align.center);
        moneyImage.setRotation(45f);
        moneyImage.setScale(0f, 0f);
        moneyImage.addAction(Actions.scaleTo(1f, 1f, 0.5f, Interpolation.circle));
        stage.addActor(moneyImage);

        moneyLabel = new Label("0", new Label.LabelStyle(gsm.assets.moneyFont, Color.WHITE));
        moneyLabel.setAlignment(Align.right);
        moneyLabel.setPosition(stage.getWidth() - 5.25f * Assets.UnitScale, stage.getHeight() - 3.75f * Assets.UnitScale, Align.center);
        moneyLabel.setText(GameData.getMoney());
        stage.addActor(moneyLabel);

        shopItemsGroup = new Group();
        shopButtons = new Array<ShopButton>();
        skins = new Array<Image>();
        buyImages = new Array<Image>();
        moneyPriceImages = new Array<Image>();
        priceTexts = new Array<Label>();

        //region <Создаем кнопки>
        int id = 0;
        float y = 12f;
        for (int i = 0; i < 3; i++){
            float x = -9f;
            for (int j = 0; j < 3; j++) {
                shopButtons.add(new ShopButton(new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonItemShop.png", true)), this, gsm.assets));
                shopButtons.get(id).setPosition(stage.getWidth() / 2f + x * Assets.UnitScale, stage.getHeight() / 2f + y * Assets.UnitScale, Align.center);
                skins.add(new Image(gsm.assets.getTextureAtlas("skins/PlayerSkins.atlas").findRegion("Player" + id)));
                skins.get(id).setSize(1.75f * Assets.UnitScale, 1.75f * Assets.UnitScale);
                skins.get(id).setPosition(stage.getWidth() / 2f + x * Assets.UnitScale, stage.getHeight() / 2f + y * Assets.UnitScale, Align.center);
                skins.get(id).setTouchable(Touchable.disabled);
                buyImages.add(new Image(gsm.assets.getTexture("textures/BuyImage.png", true)));
                buyImages.get(id).setSize(1.75f * Assets.UnitScale, 1.75f * Assets.UnitScale);
                buyImages.get(id).setPosition(stage.getWidth() / 2f + x * Assets.UnitScale, stage.getHeight() / 2f + (y - 1.5f) * Assets.UnitScale, Align.center);
                buyImages.get(id).setTouchable(Touchable.disabled);
                moneyPriceImages.add(new Image(gsm.assets.getTextureAtlas("skins/BlockSkins.atlas").findRegion("GoldBlock")));
                moneyPriceImages.get(id).setSize(1.5f * Assets.UnitScale, 1.5f * Assets.UnitScale);
                moneyPriceImages.get(id).setPosition(stage.getWidth() / 2f + (x + 1.85f) * Assets.UnitScale, stage.getHeight() / 2f + (y - 4f) * Assets.UnitScale, Align.center);
                moneyPriceImages.get(id).setOrigin(Align.center);
                moneyPriceImages.get(id).setRotation(45f);
                moneyPriceImages.get(id).setTouchable(Touchable.disabled);
                shopButtons.get(id).setId(id);
                shopButtons.get(id).setPage(page);
                priceTexts.add(new Label("0", new Label.LabelStyle(gsm.assets.priceFont, gsm.assets.priceFont.getColor())));
                priceTexts.get(id).setAlignment(Align.right);
                priceTexts.get(id).setPosition(stage.getWidth() / 2f + (x - 0.25f) * Assets.UnitScale, stage.getHeight() / 2f + (y - 4f) * Assets.UnitScale, Align.center);
                shopItemsGroup.addActor(shopButtons.get(id));
                shopItemsGroup.addActor(skins.get(id));
                shopItemsGroup.addActor(buyImages.get(id));
                shopItemsGroup.addActor(moneyPriceImages.get(id));
                shopItemsGroup.addActor(priceTexts.get(id));
                x += 9f;
                id++;
            }
            y -= 9f;
        }
        //endregion

        stage.addActor(shopItemsGroup);

        page = GameData.getCurrentPage();

        for (int i = 0; i < 9; i++) {
            skins.get(i).setDrawable(new TextureRegionDrawable(gsm.assets.getTextureAtlas("skins/PlayerSkins.atlas").findRegion("Player" + (i + (page - 1) * 9))));
            shopButtons.get(i).setId(i + (page - 1) * 9);
            shopButtons.get(i).setPage(page);
            shopButtons.get(i).getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonItemShop.png", true));
        }

        pageLabel = new Label("0", new Label.LabelStyle(gsm.assets.pageFont, gsm.assets.moneyFont.getColor()));
        pageLabel.setAlignment(Align.center);
        pageLabel.setPosition(stage.getWidth() / 2f + 0f * Assets.UnitScale, stage.getHeight() / 2f - 15f * Assets.UnitScale, Align.center);
        pageLabel.setText(page);
        stage.addActor(pageLabel);

        backPageLabel = new Label("0", new Label.LabelStyle(gsm.assets.backPageFont, gsm.assets.moneyFont.getColor()));
        backPageLabel.setAlignment(Align.left);
        backPageLabel.setPosition(stage.getWidth() / 2f - 5f * Assets.UnitScale, stage.getHeight() / 2f - 15f * Assets.UnitScale, Align.center);
        backPageLabel.setText(page - 1);
        stage.addActor(backPageLabel);

        nextPageLabel = new Label("0", new Label.LabelStyle(gsm.assets.backPageFont, gsm.assets.moneyFont.getColor()));
        nextPageLabel.setAlignment(Align.right);
        nextPageLabel.setPosition(stage.getWidth() / 2f + 5f * Assets.UnitScale, stage.getHeight() / 2f - 15f * Assets.UnitScale, Align.center);
        nextPageLabel.setText(page + 1);
        stage.addActor(nextPageLabel);

        notVideoDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.justOk);
        notVideoDialogWindow.getButton1().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                nextPageButton.setTouchable(Touchable.enabled);
                backPageButton.setTouchable(Touchable.enabled);
                watchTheVideoButton.setTouchable(Touchable.enabled);
                shopItemsGroup.setTouchable(Touchable.enabled);
                notVideoDialogWindow.hide(0.75f);
            }
        });
        notVideoDialogWindow.getTitleLabel().setText("<!>");
        notVideoDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.NOT_VIDEO);
        stage.addActor(notVideoDialogWindow);
        notEnoughMoneyDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.justOk);
        notEnoughMoneyDialogWindow.getButton1().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                nextPageButton.setTouchable(Touchable.enabled);
                backPageButton.setTouchable(Touchable.enabled);
                watchTheVideoButton.setTouchable(Touchable.enabled);
                shopItemsGroup.setTouchable(Touchable.enabled);
                notEnoughMoneyDialogWindow.hide(0.75f);
            }
        });
        notEnoughMoneyDialogWindow.getTitleLabel().setText("<!>");
        notEnoughMoneyDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.NOT_HAVE_ENOUGH_MONEY);
        stage.addActor(notEnoughMoneyDialogWindow);
        buyDialogWindow = new DialogWindow(gsm.assets, stage, DialogWindow.cancelAndOk);
        buyDialogWindow.getButton1().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                nextPageButton.setTouchable(Touchable.enabled);
                backPageButton.setTouchable(Touchable.enabled);
                watchTheVideoButton.setTouchable(Touchable.enabled);
                shopItemsGroup.setTouchable(Touchable.enabled);
                buyDialogWindow.hide(0.75f);
                descriptionLabel.setVisible(false);
            }
        });
        buyDialogWindow.getButton2().addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gsm.assets.getSound("audio/Sound02.wav").play(GameData.volumeSound);
                GameData.setMoney(-1 * GameData.playerPrice[currentIdForBuy]);
                GameData.setBoughtPlayer(currentIdForBuy);
                GameData.setCurrentPlayerID(currentIdForBuy);
                GameData.setCurrentPage(page);
                nextPageButton.setTouchable(Touchable.enabled);
                backPageButton.setTouchable(Touchable.enabled);
                watchTheVideoButton.setTouchable(Touchable.enabled);
                shopItemsGroup.setTouchable(Touchable.enabled);
                descriptionLabel.setVisible(false);
                buyDialogWindow.hide(0.75f);
            }
        });
        buyDialogWindow.getTitleLabel().setText("<!>");
        buyDialogWindow.getDescriptionLabel().setText(gsm.assets.translation.WANT_TO_BUY_SKIN);

        descriptionLabel = new Label("0", new Label.LabelStyle(gsm.assets.messageFont2, gsm.assets.messageFont2.getColor()));
        descriptionLabel.setAlignment(Align.center);
        descriptionLabel.setPosition(stage.getWidth() / 2f, stage.getHeight() / 2f + 5.25f * Assets.UnitScale, Align.center);
        buyDialogWindow.addActor(descriptionLabel);

        stage.addActor(buyDialogWindow);

        transitBetweenScenes = new FlashEffect(gsm.assets.getTexture("textures/Square16x16.png", false), stage);
        transitBetweenScenes.begin(FlashEffect.fadeOut, 1f);
    }

    @Override
    public void update(float deltaTime) {
        camera.update();
        stage.act(deltaTime);

        for (int i = 0; i < 9; i++)
            shopButtons.get(i).getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonItemShop.png", false));

        if (page == GameData.getCurrentPage())
            shopButtons.get(GameData.getCurrentPlayerID() - ((page-1) * 9)).getStyle().up = new TextureRegionDrawable(gsm.assets.getTexture("textures/ButtonCurrentItemShop.png", false));


        moneyLabel.setText(GameData.getMoney());
        pageLabel.setText(page);
        if (page < maxPage) {
            nextPageLabel.setText("" + (page + 1));
        } else {
            nextPageLabel.setText("");
        }
        if (page != 1) {
            backPageLabel.setText("" + (page - 1));
        } else {
            backPageLabel.setText("");
        }

        for(int i = 0; i < 9; i++) {
            if (GameData.getBoughtPlayer(i + (page - 1) * 9)){
                priceTexts.get(i).setVisible(false);
                buyImages.get(i).setVisible(false);
                moneyPriceImages.get(i).setVisible(false);
            } else {
                priceTexts.get(i).setVisible(true);
                buyImages.get(i).setVisible(true);
                moneyPriceImages.get(i).setVisible(true);
                priceTexts.get(i).setText("" + GameData.playerPrice[i + (page - 1) * 9]);
            }
        }
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

    @Override
    public void showMessageBuy() {
        buyDialogWindow.show(0.75f);
        nextPageButton.setTouchable(Touchable.disabled);
        backPageButton.setTouchable(Touchable.disabled);
        watchTheVideoButton.setTouchable(Touchable.disabled);
        shopItemsGroup.setTouchable(Touchable.disabled);
        descriptionLabel.setText(GameData.playerPropertyInfo[currentIdForBuy]);
        descriptionLabel.setVisible(false);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                descriptionLabel.setVisible(true);
            }
        }, 0.75f * 0.75f);
    }

    @Override
    public void showMessageNoMoney() {
        notEnoughMoneyDialogWindow.show(0.75f);
        nextPageButton.setTouchable(Touchable.disabled);
        backPageButton.setTouchable(Touchable.disabled);
        watchTheVideoButton.setTouchable(Touchable.disabled);
        shopItemsGroup.setTouchable(Touchable.disabled);
    }

    @Override
    public void setCurrentIdForBuy(int currentIdForBuy) {
        this.currentIdForBuy = currentIdForBuy;

    }
}