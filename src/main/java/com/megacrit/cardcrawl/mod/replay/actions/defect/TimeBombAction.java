package com.megacrit.cardcrawl.mod.replay.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;

import java.util.*;

public class TimeBombAction extends AbstractGameAction
{
	private final int amount;
    public TimeBombAction(final int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
		this.amount = amount;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && !AbstractDungeon.player.orbs.isEmpty()) {
			AbstractOrb torb = null;
			int index = -1;
            for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i) {
                if (AbstractDungeon.player.orbs.get(i) instanceof Dark && i > index) {
                    torb = AbstractDungeon.player.orbs.get(i);
					index = i;
                }
            }
			if (torb != null) {
				for (int i = 0; i < this.amount; i++) {
					torb.onStartOfTurn();
					torb.onEndOfTurn();
				}
				if (index == 0 && AbstractDungeon.player.hasRelic("Cables")) {
					torb.onStartOfTurn();
					torb.onEndOfTurn();
				}
			}
        }
        this.tickDuration();
    }
}
