package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.GremlinFood;
import com.megacrit.cardcrawl.ui.campfire.RestOption;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.campfire.RestOption", method = "useOption")
public class ReplayCampFirePatch {

    public static void Postfix(RestOption __instance) {
        if (AbstractDungeon.player.hasRelic(GremlinFood.ID)) {
            final ArrayList<AbstractCard> upgradableCards = new ArrayList<>();
            for (final AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }
            Collections.shuffle(upgradableCards);
            if (!upgradableCards.isEmpty()) {
                upgradableCards.get(0).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
            }
        }
    }

}