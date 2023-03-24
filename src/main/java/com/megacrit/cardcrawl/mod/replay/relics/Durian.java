package com.megacrit.cardcrawl.mod.replay.relics;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class Durian extends ReplayAbstractRelic implements OnReceivePowerRelic {
    public static final String ID = "durian";
    public static final int HP_BOOST = 5;


    public Durian() {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(Durian.HP_BOOST, true);
    }

    public void onDebuffRecieved(ApplyPowerAction __instance) {
        AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(__instance.target, __instance.target, Math.abs(__instance.amount)));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + Durian.HP_BOOST + ".";
    }

    @Override
    public void atTurnStart() {
        boolean gotem = false;
        for (final AbstractPower p : AbstractDungeon.player.powers) {
            //if (p.ID.equals("Frail") || p.ID.equals("Weakened") || p.ID.equals("Vulnerable")) {
            if (p.type == AbstractPower.PowerType.DEBUFF && (boolean) ReflectionHacks.getPrivate(p, AbstractPower.class, "isTurnBased")) {
                if (p.amount > 2) {
                    AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, p.ID, p.amount - 2));
                    gotem = true;
                }
            }
        }
        if (gotem) {
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Durian();
    }

    @Override
    public boolean onReceivePower(AbstractPower powerToApply, AbstractCreature source) {
        if (powerToApply.type == AbstractPower.PowerType.DEBUFF && !powerToApply.ID.equals(LoseStrengthPower.POWER_ID) && !powerToApply.ID.equals(LoseDexterityPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, Math.abs(powerToApply.amount)));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
        return true;
    }
}
