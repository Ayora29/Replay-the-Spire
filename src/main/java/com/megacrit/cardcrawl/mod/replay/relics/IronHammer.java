package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class IronHammer
        extends ReplayAbstractRelic {
    private boolean firstTurn = false;
    public static final String ID = "iron_hammer";

    public IronHammer() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        this.firstTurn = true;
        if (!this.pulse) {
            beginPulse();
            this.pulse = true;
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (this.pulse) {
            this.pulse = false;
            this.firstTurn = false;
        }
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (this.firstTurn) {
            if (drawnCard.canUpgrade()) {
                drawnCard.upgrade();
                drawnCard.superFlash();
                flash();
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new IronHammer();
    }
}
