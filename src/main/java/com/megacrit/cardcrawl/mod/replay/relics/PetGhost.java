package com.megacrit.cardcrawl.mod.replay.relics;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GhostDefend;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GhostFetch;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GhostSwipe;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class PetGhost
        extends ReplayAbstractRelic {
    public static final String ID = "pet_ghost";

    public PetGhost() {
        super(ID, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEnergyRecharge() {
        AbstractCard c;
        switch (AbstractDungeon.cardRandomRng.random(0, 2)) {
            case 0:
                c = new GhostSwipe();
                break;
            case 1:
                c = new GhostDefend();
                break;
            default:
                boolean hasBasic = false;
                if (AbstractDungeon.player != null) {
                    for (final AbstractCard c2 : AbstractDungeon.player.drawPile.group) {
                        if (c2.rarity == AbstractCard.CardRarity.BASIC || "Ghost Defend".equals(c2.cardID) || "Ghost Swipe".equals(c2.cardID)) {
                            hasBasic = true;
                            break;
                        }
                    }
                }
                if (hasBasic) {
                    c = new GhostFetch();
                } else {
                    if (AbstractDungeon.cardRandomRng.randomBoolean()) {
                        c = new GhostSwipe();
                    } else {
                        c = new GhostDefend();
                    }
                }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, false));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public void onEquip() {
        BaseMod.MAX_HAND_SIZE++;
    }

    @Override
    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE--;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PetGhost();
    }
}
