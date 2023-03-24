package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.RidThyselfOfStatusCardsPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class AntivirusSoftware
        extends ReplayAbstractRelic {
    public static final String ID = "antivirus_software";
    private static final int ARTIF = 1;

    public AntivirusSoftware() {
        super(ID, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];// + AncientBracer.ARTIF + this.DESCRIPTIONS[1];
    }

    @Override
    public void onShuffle() {
        ++this.counter;
        if (this.counter == AntivirusSoftware.ARTIF) {
            this.counter = 0;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, 1), 1));
        }
    }

    /*
  public void atBattleStart()
  {
    flash();
    AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, AncientBracer.ARTIF), AncientBracer.ARTIF));
  }
  */
    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        this.pulse = false;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if (drawnCard.type == AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("Artifact") && !AbstractDungeon.player.hasPower(RidThyselfOfStatusCardsPower.POWER_ID)) {
            if (!this.pulse) {
                AbstractDungeon.player.getPower("Artifact").onSpecificTrigger();
                this.pulse = true;
                this.flash();
            }
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(drawnCard, AbstractDungeon.player.hand));
            if (drawnCard.cardID.equals("Void")) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            }
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }

    }

    @Override
    public AbstractRelic makeCopy() {
        return new AntivirusSoftware();
    }
}
