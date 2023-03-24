package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.mod.replay.relics.BlueDoll;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import replayTheSpire.ReplayTheSpireMod;

import java.util.ArrayList;

import static replayTheSpire.Constants.COLORED_CARD;
import static replayTheSpire.Constants.COLORLESS_CARD;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "renderCardsAndPrices")
public class ReplayShopRenderCardsPatch {

    public static void Postfix(ShopScreen __instance, final SpriteBatch sb) {
        if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(BlueDoll.ID)) {
            ArrayList<AbstractCard> coloredCards = ReflectionHacks.getPrivate(__instance, ShopScreen.class, COLORED_CARD);
            ArrayList<AbstractCard> colorlessCards = ReflectionHacks.getPrivate(__instance, ShopScreen.class, COLORLESS_CARD);
            for (int i = 0; i < ReplayShopInitCardsPatch.dollTags.size(); i++) {
                OnSaleTag t = ReplayShopInitCardsPatch.dollTags.get(i);
                Texture tmp = OnSaleTag.img;
                OnSaleTag.img = ReplayShopInitCardsPatch.dollTagImgs.get(i);
                if (coloredCards.contains(t.card)) {
                    t.render(sb);
                }
                if (colorlessCards.contains(t.card)) {
                    t.render(sb);
                }
                OnSaleTag.img = tmp;
            }
        }
    }

}