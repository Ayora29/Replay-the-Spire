package replayTheSpire.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.vfx.paralax.ParalaxController;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;

public class BeyondScenePatch {

    public static ParalaxController bg_controller = null;

    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomBg")
    public static class BottomSceneParalaxBGPatch {
        public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
            if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && bg_controller != null) {
                bg_controller.Render(sb);
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBeyondScene", method = "renderCombatRoomBg")
    public static class BeyondSceneParalaxBGPatch {
        public static void Postfix(TheBeyondScene __instance, final SpriteBatch sb) {
            if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom() instanceof MonsterRoom && bg_controller != null) {
                bg_controller.Render(sb);
            }
        }
    }

}
