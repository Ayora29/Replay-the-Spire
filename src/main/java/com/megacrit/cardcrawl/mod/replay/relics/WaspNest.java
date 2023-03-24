package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;

public class WaspNest extends ReplayAbstractRelic {
    public static final String ID = "wasp_nest";
    public static final int THORNS_AMT = 2;

    public WaspNest() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + THORNS_AMT + this.DESCRIPTIONS[1];
    }

    @Override
    public void onLoseHp(int damageAmount) {
        if (damageAmount > 0 && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, THORNS_AMT), THORNS_AMT));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new WaspNest();
    }
}
