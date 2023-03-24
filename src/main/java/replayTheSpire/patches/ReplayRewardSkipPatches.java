package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuCancelButton;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import replayTheSpire.ReplayTheSpireMod;

import java.util.ArrayList;

public class ReplayRewardSkipPatches {
    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.CombatRewardScreen", method = "rewardViewUpdate")
    public static class ReplayRewardSkipPositionPatch {

        public static float HIDE_X = -1.0f;
        public static float SHOW_X = -1.0f;

        public static void Postfix(CombatRewardScreen __Instance) {
            if (ReplayTheSpireMod.noSkipRewardsRoom) {
                if (ReplayRewardSkipPositionPatch.HIDE_X == -1.0f) {
                    ReplayRewardSkipPositionPatch.HIDE_X = AbstractDungeon.topPanel.mapHb.cX - 400.0f * Settings.scale;
                    ReplayRewardSkipPositionPatch.SHOW_X = AbstractDungeon.topPanel.mapHb.cX;
                }
                if (__Instance.rewards.isEmpty()) {
                    AbstractDungeon.overlayMenu.proceedButton.show();
                    AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.SHOW_X, AbstractDungeon.topPanel.mapHb.cY);
                } else {
                    AbstractDungeon.overlayMenu.proceedButton.hide();
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    //ReflectionHacks.setPrivate((Object)__Instance, (Class)CombatRewardScreen.class, "labelOverride", (Object)null);
                    AbstractDungeon.topPanel.mapHb.move(ReplayRewardSkipPositionPatch.HIDE_X, AbstractDungeon.topPanel.mapHb.cY);
                }
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen", method = "open")
    public static class BossRelicScreenPatch {
        public static void Postfix(BossRelicSelectScreen __Instance, final ArrayList<AbstractRelic> chosenRelics) {
            if (ReplayTheSpireMod.noSkipRewardsRoom) {
                ((MenuCancelButton) ReflectionHacks.getPrivate(__Instance, BossRelicSelectScreen.class, "cancelButton")).hide();
                //__Instance.cancelButton.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen", method = "reopen")
    public static class BossRelicScreenPatch2 {
        public static void Postfix(BossRelicSelectScreen __Instance) {
            if (ReplayTheSpireMod.noSkipRewardsRoom) {
                ((MenuCancelButton) ReflectionHacks.getPrivate(__Instance, BossRelicSelectScreen.class, "cancelButton")).hide();
                //__Instance.cancelButton.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen", method = "refresh")
    public static class BossRelicScreenPatch3 {
        public static void Postfix(BossRelicSelectScreen __Instance) {
            if (ReplayTheSpireMod.noSkipRewardsRoom) {
                ((MenuCancelButton) ReflectionHacks.getPrivate(__Instance, BossRelicSelectScreen.class, "cancelButton")).hide();
                //__Instance.cancelButton.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
            }
        }
    }
}

