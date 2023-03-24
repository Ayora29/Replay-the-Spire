package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.mod.replay.events.shrines.TrappedChest;
import com.megacrit.cardcrawl.mod.replay.events.thebottom.Stuck;

@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.EventHelper", method = "getEvent")
public class ReplayEventPatch {

    public static AbstractEvent Postfix(AbstractEvent __result, String key) {
        switch (key) {
            case Stuck.ID: {
                return new Stuck();
            }
            case TrappedChest.ID: {
                return new TrappedChest();
            }
            default:
                return __result;
        }
    }

}