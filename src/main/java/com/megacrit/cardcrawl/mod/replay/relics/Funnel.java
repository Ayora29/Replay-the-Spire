package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import replayTheSpire.ReplayAbstractRelic;

public class Funnel extends ReplayAbstractRelic {
    public static final String ID = "funnel";
    private int turnlimitmarker;

    public Funnel() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
        this.turnlimitmarker = 3;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    private boolean canConserveEnergy() {
        if (AbstractDungeon.player == null) {
            return false;
        }
        return (AbstractDungeon.player.hasRelic("Ice Cream") || AbstractDungeon.player.hasPower("Conserve"));
    }

    @Override
    public void onEnergyRecharge() {
        if (this.canConserveEnergy()) {
            this.turnlimitmarker = EnergyPanel.totalCount;
        }
    }

    @Override
    public void onPlayerEndTurn() {
        int energycounter = EnergyPanel.totalCount;
        if (this.canConserveEnergy()) {
            energycounter = Math.min(energycounter, AbstractDungeon.player.energy.energyMaster + Math.max(EnergyPanel.totalCount - this.turnlimitmarker, 0));
        }
        if (energycounter > 0) {
            this.flash();
            this.stopPulse();

            AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, energycounter * 4));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Funnel();
    }
}