package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import replayTheSpire.ReplayAbstractRelic;

public class QuantumEgg extends ReplayAbstractRelic {
    public static final String ID = "quantum_egg";

    public QuantumEgg() {
        super(ID, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new QuantumEgg();
    }
}
