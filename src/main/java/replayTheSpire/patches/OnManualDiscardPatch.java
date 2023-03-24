package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.ScrapShanksPower;

@SpirePatch(cls = "com.megacrit.cardcrawl.actions.GameActionManager", method = "incrementDiscard")
public class OnManualDiscardPatch {

    public static void Postfix(final boolean endOfTurn) {
        if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
            if (AbstractDungeon.player.hasPower(ScrapShanksPower.POWER_ID)) {
                AbstractDungeon.player.getPower(ScrapShanksPower.POWER_ID).onSpecificTrigger();
                //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Shiv(), AbstractDungeon.player.getPower("Scrap Shanks").amount));
            }
        }
    }

}