package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import replayTheSpire.ReplayAbstractRelic;

public class CursedCoin extends ReplayAbstractRelic {
    public static final String ID = "cursed_coin";
    private static final int GOLD_AMT = 150;

    public CursedCoin() {
        super(ID, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + GOLD_AMT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        CardCrawlGame.sound.play("GOLD_GAIN");
        AbstractDungeon.player.gainGold(GOLD_AMT);
        final AbstractCard greedCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
        UnlockTracker.markCardAsSeen(greedCurse.cardID);
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(greedCurse, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, false));
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CursedCoin();
    }
}
