package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;


public class BlueDoll extends ReplayAbstractRelic {
    public static final String ID = "blue_doll";

    public BlueDoll() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlueDoll();
    }
}
