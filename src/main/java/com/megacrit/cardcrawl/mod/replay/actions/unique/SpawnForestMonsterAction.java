package com.megacrit.cardcrawl.mod.replay.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SpawnForestMonsterAction extends AbstractGameAction {
    private boolean used;
    private static final float DURATION = 0.1f;
    private final AbstractMonster m;
    private final boolean minion;
    private final int targetSlot;

    public SpawnForestMonsterAction(final AbstractMonster m, final boolean isMinion) {
        this(m, isMinion, -99);
    }

    public SpawnForestMonsterAction(final AbstractMonster m, final boolean isMinion, final int slot) {
        this.used = false;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1f;
        this.m = m;
        this.minion = isMinion;
        this.targetSlot = slot;
        if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
            m.addPower(new StrengthPower(m, 2));
        }
    }

    @Override
    public void update() {
        if (!this.used) {
            this.m.init();
            this.m.applyPowers();
            if (this.targetSlot < 0) {
                AbstractDungeon.getCurrRoom().monsters.addSpawnedMonster(this.m);
            } else {
                AbstractDungeon.getCurrRoom().monsters.addMonster(this.targetSlot, this.m);
            }
            this.m.showHealthBar();
            if (ModHelper.isModEnabled("Lethality")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }
            if (ModHelper.isModEnabled("Time Dilation")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }
            if (this.minion) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }
            this.m.usePreBattleAction();
            this.used = true;
        }
        this.tickDuration();
    }
}
