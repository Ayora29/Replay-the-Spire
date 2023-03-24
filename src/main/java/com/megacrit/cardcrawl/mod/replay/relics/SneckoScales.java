package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class SneckoScales extends ReplayAbstractRelic {
    public static final String ID = "snecko_scales";
    public static int DRAWCOUNT = 3;

    public SneckoScales() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + SneckoScales.DRAWCOUNT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
        if (targetCard == null || targetCard.freeToPlayOnce || targetCard.costForTurn <= 0) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize += SneckoScales.DRAWCOUNT;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize -= SneckoScales.DRAWCOUNT;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SneckoScales();
    }
}
