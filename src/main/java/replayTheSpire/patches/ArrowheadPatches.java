package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.Arrowhead;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;

public class ArrowheadPatches {

    public static boolean hasSecondUpgrade;
    public static boolean didSecondUpgrade;

    @SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = "update")
    public static class VfxUpdatePatch {

        @SpireInsertPatch(rloc = 16)
        public static void Insert(CampfireSmithEffect __Instance) {
            //ReplayTheSpireMod.logger.info((boolean)ReflectionHacks.getPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard"));
            if (ArrowheadPatches.hasSecondUpgrade && AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0) {// && (boolean)ReflectionHacks.getPrivate((Object)__Instance, (Class)CampfireSmithEffect.class, "selectedCard")
                ReflectionHacks.setPrivate(__Instance, CampfireSmithEffect.class, "openedScreen", false);
                ReflectionHacks.setPrivate(__Instance, CampfireSmithEffect.class, "selectedCard", false);
                ReflectionHacks.setPrivate(__Instance, CampfireSmithEffect.class, "screenColor", AbstractDungeon.fadeColor.cpy());
                ReflectionHacks.setPrivate(__Instance, CampfireSmithEffect.class, "duration", 1.5f);
                //AbstractDungeon.effectList.add(new CampfireSmithEffect());
                ArrowheadPatches.hasSecondUpgrade = false;
                ArrowheadPatches.didSecondUpgrade = true;
            }
        }

    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect", method = SpirePatch.CONSTRUCTOR)
    public static class VfxConstPatch {

        public static void Postfix(CampfireSmithEffect __Instance) {
            ArrowheadPatches.didSecondUpgrade = false;
            ArrowheadPatches.hasSecondUpgrade = AbstractDungeon.player.hasRelic(Arrowhead.ID) && AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 1;
        }

    }


}
