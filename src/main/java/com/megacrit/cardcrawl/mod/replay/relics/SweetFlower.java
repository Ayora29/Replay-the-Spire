package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;

public class SweetFlower extends ReplayAbstractRelic {
    public static final String ID = "sweet_flower";
    private static final int HEAL_AMT = 6;

    public SweetFlower() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }

    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (AbstractDungeon.player.hasRelic("hubris:VirtuousBlindfold")) {
            this.flash();
            AbstractDungeon.player.heal(SweetFlower.HEAL_AMT);
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SweetFlower();
    }
}
