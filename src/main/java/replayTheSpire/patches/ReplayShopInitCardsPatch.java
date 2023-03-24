package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.mod.replay.relics.BlueDoll;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import replayTheSpire.ReplayTheSpireMod;

import java.util.ArrayList;

import static replayTheSpire.Constants.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.shop.ShopScreen", method = "initCards")
public class ReplayShopInitCardsPatch {

    public static String NORMAL_TAG = "images/npcs/sale_tag/eng.png";
    public static String DOUBLE_TAG = "replay/images/ui/shop/2for1Tag.png";
    public static String SPECIAL_TAG = "replay/images/ui/shop/specialEditionTag.png";

    public static AbstractCard doubleCard;
    public static ArrayList<OnSaleTag> dollTags;
    public static ArrayList<Texture> dollTagImgs;

    public static void Postfix(ShopScreen __instance) {

        OnSaleTag saleTag = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "saleTag");
        AbstractCard saleCard = saleTag.card;
        saleCard.price *= 2;
        ReplayShopInitCardsPatch.dollTags = new ArrayList<>();
        ReplayShopInitCardsPatch.dollTagImgs = new ArrayList<>();
        ArrayList<String> tagList = new ArrayList<>();
        for (int i = 0; i < SETTING_TAG_NORMAL_CHANCE; i++) {
            tagList.add(ReplayShopInitCardsPatch.NORMAL_TAG);
        }
        for (int i = 0; i < SETTING_TAG_DOUBLE_CHANCE; i++) {
            tagList.add(ReplayShopInitCardsPatch.DOUBLE_TAG);
        }
        if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(BlueDoll.ID)) {
            for (int i = 0; i < SETTING_TAG_SPECIAL_CHANCE; i++) {
                tagList.add(ReplayShopInitCardsPatch.SPECIAL_TAG);
            }
        }
        String tagtype = tagList.get(AbstractDungeon.merchantRng.random(0, tagList.size() - 1));
        OnSaleTag.img = ImageMaster.loadImage(tagtype);
        if (tagtype == ReplayShopInitCardsPatch.DOUBLE_TAG) {
            ReplayShopInitCardsPatch.doubleCard = saleCard;
            saleCard.price *= 3;
            saleCard.price /= 4;
        } else if (tagtype == ReplayShopInitCardsPatch.SPECIAL_TAG) {
            saleCard.upgrade();
            saleCard.price *= 3;
            saleCard.price /= 4;
        } else {
            saleCard.price /= 2;
        }

        if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(BlueDoll.ID)) {
            saleCard.price -= 10;
            ArrayList<AbstractCard> coloredCards = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "coloredCards");
            ArrayList<AbstractCard> colorlessCards = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "colorlessCards");
            AbstractCard newTagCard = null;
            if (tagtype != ReplayShopInitCardsPatch.NORMAL_TAG) {
                ArrayList<AbstractCard> validTargets = new ArrayList<>();
                for (AbstractCard c : coloredCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                for (AbstractCard c : colorlessCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                if (validTargets.size() > 0) {
                    newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
                    newTagCard.price /= 2;
                    newTagCard.price -= 10;
                    OnSaleTag newtag = new OnSaleTag(newTagCard);
                    dollTags.add(newtag);
                    ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.NORMAL_TAG));
                }
            }
            if (tagtype != ReplayShopInitCardsPatch.DOUBLE_TAG) {
                ArrayList<AbstractCard> validTargets = new ArrayList<>();
                for (AbstractCard c : coloredCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                for (AbstractCard c : colorlessCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                if (validTargets.size() > 0) {
                    newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
                    newTagCard.price *= 3;
                    newTagCard.price /= 4;
                    newTagCard.price -= 10;
                    ReplayShopInitCardsPatch.doubleCard = newTagCard;
                    OnSaleTag newtag = new OnSaleTag(newTagCard);
                    dollTags.add(newtag);
                    ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.DOUBLE_TAG));
                }
            }
            if (tagtype != ReplayShopInitCardsPatch.SPECIAL_TAG) {
                ArrayList<AbstractCard> validTargets = new ArrayList<>();
                for (AbstractCard c : coloredCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                for (AbstractCard c : colorlessCards) {
                    if (c != newTagCard && c != saleCard) {
                        validTargets.add(c);
                    }
                }
                if (validTargets.size() > 0) {
                    newTagCard = validTargets.get(AbstractDungeon.merchantRng.random(0, validTargets.size() - 1));
                    newTagCard.price *= 3;
                    newTagCard.price /= 4;
                    newTagCard.price -= 10;
                    newTagCard.upgrade();
                    OnSaleTag newtag = new OnSaleTag(newTagCard);
                    dollTags.add(newtag);
                    ReplayShopInitCardsPatch.dollTagImgs.add(ImageMaster.loadImage(ReplayShopInitCardsPatch.SPECIAL_TAG));
                }
            }
        }

    }

}