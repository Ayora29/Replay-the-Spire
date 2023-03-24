package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.Garlic;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.ApplyPowerAction", method = "update")
public class ReplayApplyPowerPatch {

    public static void Prefix(ApplyPowerAction __instance) {
        float duration = ReflectionHacks.getPrivate(__instance, AbstractGameAction.class, "duration");
        float startDuration = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "startingDuration");
        if (__instance != null && __instance.target != null && !__instance.target.isDying && !__instance.target.isDead && duration == startDuration) {
            AbstractPower powerToApply = ReflectionHacks.getPrivate(__instance, ApplyPowerAction.class, "powerToApply");

            if (!__instance.target.isPlayer && powerToApply.ID.equals(StrengthPower.POWER_ID) && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(Garlic.ID)) {
                AbstractRelic cbr = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(Garlic.ID);
                if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom().eliteTrigger) && (__instance.amount / 2) > 0) {
                    __instance.amount -= (__instance.amount / 2);
                    powerToApply.amount -= (powerToApply.amount / 2);
                    if (cbr != null) {
                        cbr.flash();
                    }
                }
            }
        }
    }

}