package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class SnackPack extends ReplayAbstractRelic {
    public static final String ID = "snack_pack";

    public SnackPack() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onUseCard(final AbstractCard abstractCard, final UseCardAction useCardAction) {
        if (abstractCard.type == AbstractCard.CardType.ATTACK) {
            ++this.counter;
            if (this.counter % 3 == 0) {
                this.flash();
                this.counter = 0;
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
            }
        }
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SnackPack();
    }
}
