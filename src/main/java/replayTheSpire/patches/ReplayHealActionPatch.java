package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.Garlic;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.HealAction", method = "update")
public class ReplayHealActionPatch {

    public static void Prefix(HealAction __instance) {
        float duration = ReflectionHacks.getPrivate(__instance, AbstractGameAction.class, "duration");
        float startDuration = 0.5f;//(float)ReflectionHacks.getPrivate((Object)__instance, (Class)HealAction.class, "startDuration");
        if (!__instance.target.isDying && !__instance.target.isDead && duration == startDuration) {
            if (!__instance.target.isPlayer && ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(Garlic.ID)) {
                AbstractRelic cbr = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(Garlic.ID);
                if ((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom().eliteTrigger) && (__instance.amount / 2) > 0) {
                    __instance.amount -= (__instance.amount / 2);
                    if (cbr != null) {
                        cbr.flash();
                    }
                }
            }
        }
    }

}