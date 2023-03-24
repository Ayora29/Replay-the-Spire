package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.BottledFlurry;
import com.megacrit.cardcrawl.mod.replay.relics.BottledSteam;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class NeowRewardPatches {

    ////////////BANNED STARTING RELICS/////

    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnRandomRelicKey")
    public static class BannedStartingRelics {

        static ArrayList<String> startingRelicBlacklist = new ArrayList<>();//you can't get these relics from neow

        static {
            startingRelicBlacklist.add(Ectoplasm.ID);
            startingRelicBlacklist.add(BottledFlame.ID);
            startingRelicBlacklist.add(BottledLightning.ID);
            startingRelicBlacklist.add(BottledTornado.ID);
            startingRelicBlacklist.add(BottledSteam.ID);
            startingRelicBlacklist.add(BottledFlurry.ID);
        }


        public static String Postfix(String __Result, final AbstractRelic.RelicTier tier) {
            if (UnlockTracker.isRelicLocked(__Result) && !Settings.treatEverythingAsUnlocked()) {
                return AbstractDungeon.returnRandomRelicKey(tier);
            }
            AbstractRoom room = AbstractDungeon.getCurrRoom();
            if (room != null) {
                if (room instanceof NeowRoom) {
                    if (startingRelicBlacklist.contains(__Result)) {
                        return AbstractDungeon.returnRandomRelicKey(tier);
                    }
                }
            }

            return __Result;
        }
    }
}
