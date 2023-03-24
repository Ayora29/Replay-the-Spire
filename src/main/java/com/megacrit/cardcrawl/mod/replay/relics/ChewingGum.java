package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class ChewingGum extends ReplayAbstractRelic {
    public static final String ID = "chewing_gum";
    public static final int SLIMED = 3;

    public ChewingGum() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ChewingGum.SLIMED + this.DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
    }
    
    /*@Override
    public void atBattleStartPreDraw() {
        this.flash();
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.player, AbstractDungeon.player, new Slimed(), ChewingGum.SLIMED, true, false));
	}*/

    @Override
    public void onShuffle() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Slimed(), ChewingGum.SLIMED, true, false));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ChewingGum();
    }
}
