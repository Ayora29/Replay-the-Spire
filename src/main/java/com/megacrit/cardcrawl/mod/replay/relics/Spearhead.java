package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class Spearhead extends ReplayAbstractRelic {
    public static final String ID = "spearhead";

    public Spearhead() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        boolean triggered = false;
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (m.hasPower(VulnerablePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, m.getPower(VulnerablePower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(m, this));
                triggered = true;
            }
        }
        if (triggered) {
            this.flash();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Spearhead();
    }
}
