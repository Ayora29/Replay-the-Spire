package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class Carrot extends ReplayAbstractRelic {
    public static final String ID = "carrot";
    public int TurnsLeft;

    public Carrot() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
        this.TurnsLeft = 4;
    }

    @Override
    public void atBattleStart() {
        this.TurnsLeft = 4;
        this.flash();
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
        AbstractDungeon.player.addPower(new FocusPower(AbstractDungeon.player, 4));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        if (this.TurnsLeft > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.player.addPower(new FocusPower(AbstractDungeon.player, -1));
            --this.TurnsLeft;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Carrot();
    }
}
