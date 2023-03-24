package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import javassist.CtBehavior;

@SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = {int.class, boolean.class})
public class PostHealPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(final AbstractCreature __instance, final int amount, final boolean showEffect) {

    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(final CtBehavior ctMethodToPatch) throws Exception {
            final Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCreature.class, "currentHealth");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            for (int i = 0; i < lines.length; i++) {
                lines[i]++;
            }
            return lines;
        }
    }
}
