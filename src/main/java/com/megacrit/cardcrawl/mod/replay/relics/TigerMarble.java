package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import replayTheSpire.ReplayAbstractRelic;

import java.util.ArrayList;
import java.util.Collections;

public class TigerMarble extends ReplayAbstractRelic {
    public static final String ID = "tiger_marble";
    private ArrayList<AbstractCard> possibleCards;

    public TigerMarble() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        if (this.possibleCards == null || this.possibleCards.isEmpty()) {
            this.onEquip();
        }
        final AbstractCard c = this.possibleCards.get(AbstractDungeon.cardRandomRng.random(this.possibleCards.size() - 1)).makeCopy();
        if (c.cost != -1) {
            c.setCostForTurn(0);
            c.freeToPlayOnce = true;
        }
        UnlockTracker.markCardAsSeen(c.cardID);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c));
        Collections.shuffle(this.possibleCards);
    }

    @Override
    public void onEquip() {
        this.possibleCards = new ArrayList<>();
        for (final AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
                this.possibleCards.add(c);
                this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
                this.possibleCards.add(c);
                this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
                this.possibleCards.add(c);
                this.possibleCards.add(c);
            }
        }
        for (final AbstractCard c : AbstractDungeon.srcColorlessCardPool.group) {
            if (c.exhaust && c.cost <= 2) {
                this.possibleCards.add(c);
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TigerMarble();
    }
}
