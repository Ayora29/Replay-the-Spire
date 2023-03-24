package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;


public class RingingSoul extends ReplayAbstractRelic {
    public static final String ID = "ringing_soul";

    public static final int HPGAIN = 3;

    public RingingSoul() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.pulse = true;
    }

    @Override
    public void onEnterRoom(final AbstractRoom room) {
        this.pulse = true;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + HPGAIN + this.DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RingingSoul();
    }

    @Override
    public void onObtainCard(final AbstractCard card) {
        if (this.pulse) {
            AbstractDungeon.player.increaseMaxHp(HPGAIN, true);
            this.pulse = false;
        }
    }
}
