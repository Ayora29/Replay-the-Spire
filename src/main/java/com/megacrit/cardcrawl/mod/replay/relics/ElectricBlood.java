package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import replayTheSpire.ReplayAbstractRelic;
 
public class ElectricBlood extends ReplayAbstractRelic {
    public static final String ID = "electric_blood";
    public static final int FREE_PER_TURN = 2;

    public ElectricBlood() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + ElectricBlood.FREE_PER_TURN + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStart() {
        this.counter = ElectricBlood.FREE_PER_TURN;
        this.pulse = false;
    }

    @Override
    public void atBattleStart() {
        this.counter = ElectricBlood.FREE_PER_TURN;
    }

    @Override
    public void onUseCard(final AbstractCard targetCard, final UseCardAction useCardAction) {
        if (targetCard.freeToPlayOnce) {
            return;
        }
        int ccost = targetCard.costForTurn;
        if (ccost == -1) {
            ccost = EnergyPanel.totalCount;
        }
        if (ccost <= 0) {
            return;
        }
        if (!this.pulse) {
            this.counter -= ccost;
            if (this.counter < 0) {
                this.pulse = true;
                ccost = this.counter * -1;
                this.counter = 0;
            } else {
                if (this.counter == 0) {
                    this.pulse = true;
                }
                return;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, ccost));
        //AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, targetCard.costForTurn, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void onVictory() {
        this.pulse = false;
        this.counter = -1;
    }

    @Override
    public void onEquip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        ++energy.energyMaster;
        ++energy.energyMaster;
    }

    @Override
    public void onUnequip() {
        final EnergyManager energy = AbstractDungeon.player.energy;
        --energy.energyMaster;
        --energy.energyMaster;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ElectricBlood();
    }
}
