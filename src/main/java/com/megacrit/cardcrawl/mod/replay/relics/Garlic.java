package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import replayTheSpire.ReplayAbstractRelic;

public class Garlic extends ReplayAbstractRelic {
    public static final String ID = "garlic";

    public Garlic() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEnterRoom(final AbstractRoom room) {
        if (room instanceof MonsterRoomElite || room instanceof MonsterRoomBoss || room.eliteTrigger) {
            this.pulse = true;
            this.beginPulse();
        } else {
            this.pulse = false;
        }
    }

    @Override
    public void onVictory() {
        this.pulse = false;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Garlic();
    }
}