package replayTheSpire.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.HamsterWheel;

import java.util.ArrayList;

@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getRewardCards")
public class ReplayChaosPatch {

    public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result) {
        if (AbstractDungeon.player.hasRelic(HamsterWheel.ID)) {
            for (int i = 0; i < __result.size() - 2; i++) {
                if (__result.get(i).cost == -1 && __result.get(i).rawDescription.contains("X") && __result.get(i).rarity != AbstractCard.CardRarity.SPECIAL && __result.get(i).rarity != AbstractCard.CardRarity.BASIC) {
                    __result.set(i, AbstractDungeon.getCard(__result.get(i).rarity).makeCopy());
                }
            }
        }
		
        return __result;
    }

}