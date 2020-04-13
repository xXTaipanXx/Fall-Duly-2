package com.darkbrokengames.fallduly2.scenes;

public interface AdHandler {
    void showBanner(boolean show);
    void showInterstitialAd();
    void showRewardedAd();
    boolean interstitialAdIsLoaded();
    boolean rewardedAdIsLoaded();
}
