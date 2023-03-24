package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class VampiricSpirits extends ReplayAbstractRelic {
    public static final String ID = "vampiric_spirits";
    public static final int POWER = 25;

    public VampiricSpirits() {
        super(ID, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + VampiricSpirits.POWER + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        // this.flash();
        this.pulse = true;
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new ThieveryPower((AbstractCreature)AbstractDungeon.player, Bandana.POWER), 1));
        //AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
    }

    @Override
    public void onAttack(final DamageInfo damageInfo, final int n, final AbstractCreature abstractCreature) {
        if (this.pulse && damageInfo.type == DamageInfo.DamageType.NORMAL && damageInfo.output > 0 && abstractCreature != null && abstractCreature != AbstractDungeon.player) {
            int healAmount = (n * VampiricSpirits.POWER) / 100;
            if (healAmount < 0) {
                return;
            }
            if (healAmount > abstractCreature.currentHealth) {
                healAmount = abstractCreature.currentHealth;
            }
            if (healAmount > 0) {
                AbstractDungeon.actionManager.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, healAmount));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            }
        }
    }

    @Override
    public void atPreBattle() {
        this.pulse = true;
    }

    @Override
    public void onPlayerEndTurn() {
        this.pulse = false;
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new VampiricSpirits();
    }
}