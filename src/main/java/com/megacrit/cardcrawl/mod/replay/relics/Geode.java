package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.ImpulseAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;

public class Geode extends ReplayAbstractRelic {
    public static final String ID = "geode";
    private static boolean usedThisCombat;

    public Geode() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        Geode.usedThisCombat = false;
        this.pulse = true;
        this.beginPulse();
    }

    private boolean hasEmptySlot() {
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLoseHp(final int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !Geode.usedThisCombat && damageAmount > 0) {
            this.flash();
            this.pulse = false;
            Geode.usedThisCombat = true;
            if (this.hasEmptySlot()) {
                AbstractDungeon.actionManager.addToTop(new ImpulseAction());
                AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
            } else {
                AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
                AbstractDungeon.actionManager.addToTop(new ImpulseAction());
            }
			/*if (!this.orbs.isEmpty() && !(this.orbs.get(this.orbs.size() - 1) instanceof EmptyOrbSlot)) {
				AbstractDungeon.addToTop(new TriggerPassiveAction());
			}*/
        }
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Geode();
    }

    static {
        Geode.usedThisCombat = false;
    }
}
