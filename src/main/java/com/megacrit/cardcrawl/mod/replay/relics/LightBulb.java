package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import replayTheSpire.ReplayAbstractRelic;

public class LightBulb extends ReplayAbstractRelic {
    public static final String ID = "lightbulb";

    public LightBulb() {
        super(ID, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        if (EnergyPanel.totalCount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, EnergyPanel.totalCount)));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LightBulb();
    }

    @Override
    public int getPrice() {
        return 200;
    }
}