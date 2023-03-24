package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import replayTheSpire.ReplayAbstractRelic;

public class GoldenEgg extends ReplayAbstractRelic {
    public static final String ID = "golden_egg";

    public GoldenEgg() {
        super(ID, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onObtainCard(final AbstractCard c) {
        if ((c.rarity == AbstractCard.CardRarity.RARE || AbstractDungeon.getCurrRoom() instanceof ShopRoom) && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
            this.flash();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GoldenEgg();
    }
}
