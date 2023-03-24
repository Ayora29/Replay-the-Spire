package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.actions.defect.RaiderMaskAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class RaidersMask extends ReplayAbstractRelic {
    public static final String ID = "raiders_mask";

    public RaidersMask() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RaidersMask();
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractDungeon.actionManager.addToBottom(new RaiderMaskAction());
    }
}
