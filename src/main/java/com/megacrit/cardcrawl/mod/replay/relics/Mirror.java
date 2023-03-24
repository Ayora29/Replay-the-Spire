package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.ReplayTheSpireMod;

public class Mirror extends ReplayAbstractRelic implements OnReceivePowerRelic {
    public static final String ID = "mirror";

    public Mirror() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Mirror();
    }

    @Override
    public boolean onReceivePower(AbstractPower powerToApply, AbstractCreature source) {
        if (source != null && source != AbstractDungeon.player) {
            if (powerToApply.ID.equals(WeakPower.POWER_ID)) {
                ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(ID).flash();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(source, AbstractDungeon.player, new WeakPower(source, powerToApply.amount, true), powerToApply.amount));
            }
            if (powerToApply.ID.equals(VulnerablePower.POWER_ID)) {
                ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(ID).flash();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(source, AbstractDungeon.player, new VulnerablePower(source, powerToApply.amount, true), powerToApply.amount));
            }
        }
        return true;
    }
}
