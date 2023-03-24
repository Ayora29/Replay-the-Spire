package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class RedPill
        extends ReplayAbstractRelic {
    public static final String ID = "red_pill";
    private static final int STR = 3;
    private static final int DEX = 1;

    public RedPill() {
        super(ID, RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + RedPill.STR + this.DESCRIPTIONS[1] + RedPill.DEX + this.DESCRIPTIONS[2];
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, RedPill.STR), RedPill.STR));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, -RedPill.DEX), -RedPill.DEX));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new RedPill();
    }
}
