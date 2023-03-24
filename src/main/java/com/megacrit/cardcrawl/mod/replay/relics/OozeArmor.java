package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MalleablePower;
import com.megacrit.cardcrawl.powers.SlowPower2;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class OozeArmor extends ReplayAbstractRelic {
    public static final String ID = "ooze_armor";

    public OozeArmor() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[3] + this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
        //final AbstractPlayer player = AbstractDungeon.player;
        //player.masterHandSize += 1;
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
        //final AbstractPlayer player = AbstractDungeon.player;
        //player.masterHandSize -= 1;
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower2(AbstractDungeon.player, -3), -3));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SlowPower2(AbstractDungeon.player, 0), 0));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MalleablePower(AbstractDungeon.player, 4)));
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, 2), 2));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.player, AbstractDungeon.player, new Slimed(), 2, true, false));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OozeArmor();
    }

}
