package com.megacrit.cardcrawl.mod.replay.relics;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import replayTheSpire.ReplayAbstractRelic;
import replayTheSpire.patches.ArrowheadPatches;

public class Arrowhead extends ReplayAbstractRelic {
    public static final String ID = "arrowhead";

    public Arrowhead() {
        super(ID, AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Arrowhead();
    }

    @Override
    public void onSmith() {
        if (ArrowheadPatches.didSecondUpgrade) {
            AbstractDungeon.overlayMenu.cancelButton.hide();
            ReflectionHacks.setPrivate(AbstractDungeon.gridSelectScreen, GridCardSelectScreen.class, "canCancel", false);
        }
    }

}
