package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class BluePill extends ReplayAbstractRelic {
    public static final String ID = "blue_pill";
    private static final int BUFF = 1;

    public BluePill() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + BUFF + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BufferPower(AbstractDungeon.player, BUFF), BUFF, true));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new BluePill();
    }
}