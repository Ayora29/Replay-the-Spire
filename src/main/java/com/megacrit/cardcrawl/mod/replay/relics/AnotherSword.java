package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class AnotherSword extends ReplayAbstractRelic {
    public static final String ID = "another_sword";

    public AnotherSword() {
        super(ID, RelicTier.RARE, LandingSound.SOLID);
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        if (this.counter > 0) {
            this.counter--;
            if (this.counter <= 0) {
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.pulse = true;
            }
        }
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!card.purgeOnUse && (PerfectedStrike.isStrike(card) || card.hasTag(AbstractCard.CardTags.STRIKE)) && this.counter <= 0) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }
            final AbstractCard tmp = card.makeStatEquivalentCopy();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0f;
            tmp.freeToPlayOnce = true;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
            this.counter = 1 + Math.max(0, card.costForTurn);
            this.pulse = false;
        }
    }

    @Override
    public void atPreBattle() {
        this.counter = 1;
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new AnotherSword();
    }
}
