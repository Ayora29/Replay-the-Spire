package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class FrozenProgram extends ReplayAbstractRelic {
    public static final String ID = "frozen_program";

    public FrozenProgram() {
        super(ID, RelicTier.SHOP, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FrozenProgram();
    }

    @Override
    public void onEvokeOrb(final AbstractOrb orb) {
        if (orb instanceof Frost) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, 1), 1));
        }
    }
}
