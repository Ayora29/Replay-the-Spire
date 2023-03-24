package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class GremlinFood extends ReplayAbstractRelic {
    public static final String ID = "gremlin_food";

    public GremlinFood() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GremlinFood();
    }
}
