package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class Kaleidoscope extends ReplayAbstractRelic {
    public static final String ID = "kaleidoscope";

    public Kaleidoscope() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Kaleidoscope();
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
        if (targetCard != null && targetCard.baseBlock > 0 && targetCard.rawDescription.contains("!B!")) {
            this.counter++;
            if (this.counter >= 3) {
                this.counter = 0;
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, 1), 1));
            }
        }
    }
}
