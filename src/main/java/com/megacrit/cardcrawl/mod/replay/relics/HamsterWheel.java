package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.SuperRareRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

import java.util.ArrayList;

public class HamsterWheel extends ReplayAbstractRelic implements SuperRareRelic {
    public static final String ID = "hamster_wheel";
    private static final ArrayList<AbstractCard> cardsIncreased = new ArrayList<>();

    public HamsterWheel() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(final AbstractCard drawnCard) {
        if (drawnCard == null) {
            return;
        }
        if (drawnCard.costForTurn >= 0) {
            drawnCard.setCostForTurn(drawnCard.costForTurn + 1);
            for (AbstractCard c : cardsIncreased) {
                if (c == drawnCard) {
                    drawnCard.setCostForTurn(drawnCard.costForTurn + 1);
                }
            }
            cardsIncreased.add(drawnCard);
        }
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
    }

    @Override
    public void atTurnStart() {
        cardsIncreased.clear();
    }

    @Override
    public void atBattleStartPreDraw() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cost == -1 && c.rawDescription.contains("X")) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.returnRandomCurse(), 1, true, true));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HamsterWheel();
    }
}
