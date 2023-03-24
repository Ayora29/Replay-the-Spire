package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class DivineProtection
        extends ReplayAbstractRelic {
    public static final String ID = "divine_protection";
    protected static final int HEALTH_AMT = 8;


    public DivineProtection() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);

    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DivineProtection.HEALTH_AMT + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, DivineProtection.HEALTH_AMT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DivineProtection();
    }
}
