package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.mod.replay.orbs.HellFireOrb;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "getRandomOrb")
public class ReplayOrbRandomPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"orbs"})
    public static void addInfiniteSpireOrbs(boolean useCardRng, ArrayList<AbstractOrb> orbs) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("Iron Core")) {
            orbs.add(new HellFireOrb());
        } else {
            orbs.add(new CrystalOrb());
        }
    }

    public static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }

}