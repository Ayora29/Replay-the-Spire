package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class KingOfHearts
        extends ReplayAbstractRelic {
    public static final String ID = "king_of_hearts";

    boolean isActive = false;
    boolean isChecking = false;

    public KingOfHearts() {
        super(ID, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        isActive = true;
        isChecking = false;
        this.pulse = true;
        beginPulse();
    }

    @Override
    public void onPlayerEndTurn() {
        isChecking = true;
        this.pulse = false;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (isActive) {
            if (isChecking && AbstractDungeon.actionManager.turnHasEnded) {
                isActive = false;
                this.pulse = false;
            } else {
                flash();
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, damageAmount * 2));
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new KingOfHearts();
    }
}
