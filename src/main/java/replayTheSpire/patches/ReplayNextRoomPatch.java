package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import replayTheSpire.ReplayTheSpireMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "nextRoomTransition", paramtypez = {})
public class ReplayNextRoomPatch {

    public static void Prefix() {
        ReplayTheSpireMod.clearShielding();
        RenderHandPatch.plsDontRenderHand = false;
        BeyondScenePatch.bg_controller = null;
    }
}