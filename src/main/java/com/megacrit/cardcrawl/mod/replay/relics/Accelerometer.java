package com.megacrit.cardcrawl.mod.replay.relics;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;

public class Accelerometer extends ReplayAbstractRelic implements ClickableRelic {

    public static final String ID = "accelerometer";
    private boolean usedThisFight;

    public Accelerometer() {
        super(ID, AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return this.CLICKABLE_DESCRIPTIONS()[0] + this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.usedThisFight = false;
        this.beginLongPulse();
    }

    @Override
    public void onVictory() {
        this.usedThisFight = true;
        this.stopPulse();
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!this.usedThisFight) {
            this.beginLongPulse();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        this.stopPulse();
    }

    @Override
    public void onRightClick() {
        if (!this.isObtained || this.usedThisFight || !this.pulse) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.stopPulse();
            this.flash();
            this.usedThisFight = true;
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p.type == AbstractPower.PowerType.DEBUFF && (boolean) ReflectionHacks.getPrivate(p, AbstractPower.class, "isTurnBased")) {
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, p, 1));
                }
            }
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m != null && !m.isDeadOrEscaped()) {
                    for (AbstractPower p : m.powers) {
                        if (p.type == AbstractPower.PowerType.DEBUFF && (boolean) ReflectionHacks.getPrivate(p, AbstractPower.class, "isTurnBased")) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, p, 1));
                        }
                    }
                }
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Accelerometer();
    }
}
