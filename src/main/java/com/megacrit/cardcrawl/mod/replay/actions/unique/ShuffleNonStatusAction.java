package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ShuffleNonStatusAction extends AbstractGameAction {
    private boolean shuffled;
    private boolean vfxDone;
    private int count;

    public ShuffleNonStatusAction() {
        this.shuffled = true;
        this.vfxDone = false;
        this.setValues(null, null, this.count = 0);
        this.actionType = ActionType.SHUFFLE;
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            r.onShuffle();
        }
    }

    @Override
    public void update() {
        if (!this.shuffled) {
            this.shuffled = true;
            AbstractDungeon.player.discardPile.shuffle(AbstractDungeon.shuffleRng);
        }
        if (!this.vfxDone) {
            final Iterator<AbstractCard> c = AbstractDungeon.player.discardPile.group.iterator();
            while (c.hasNext()) {
                ++this.count;
                final AbstractCard e = c.next();
                if (e.type != AbstractCard.CardType.STATUS) {
                    c.remove();
                    AbstractDungeon.getCurrRoom().souls.shuffle(e, this.count >= 11);
                    return;
                }
            }
            this.vfxDone = true;
        }
        this.isDone = true;
    }
}
