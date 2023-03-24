package replayTheSpire.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.relics.QuantumEgg;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import replayTheSpire.ReplayTheSpireMod;

public class MakeTempCardPatches {

    @SpirePatch(cls = "com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect", method = "update")
    public static class ReplayMakeCardInDrawPilePatch {

        public static void Prefix(ShowCardAndAddToDrawPileEffect __Instance) {
            if (__Instance != null && AbstractDungeon.player != null && __Instance.duration >= 1.5f - Gdx.graphics.getDeltaTime() && !__Instance.isDone) {
                if (AbstractDungeon.player.hasRelic(QuantumEgg.ID)) {
                    final AbstractCard c = ReflectionHacks.getPrivate(__Instance, ShowCardAndAddToDrawPileEffect.class, "card");
                    if (c != null && c.canUpgrade()) {
                        AbstractCard srcCard = null;
                        for (AbstractCard realcard : AbstractDungeon.player.drawPile.group) {
                            if (c.name.equals(realcard.name)
                                    && c.timesUpgraded == realcard.timesUpgraded
                                    && c.inBottleLightning == realcard.inBottleLightning
                                    && c.inBottleFlame == realcard.inBottleFlame
                                    && c.inBottleTornado == realcard.inBottleTornado
                                    && c.baseBlock == realcard.baseBlock
                                    && c.baseDamage == realcard.baseDamage
                                    && c.baseMagicNumber == realcard.baseMagicNumber
                                    && c.misc == realcard.misc
                                    && c.cost == realcard.cost) {
                                srcCard = realcard;
                                break;
                            }
                        }
                        if (srcCard != null && srcCard.canUpgrade()) {
                            srcCard.upgrade();
                            c.upgrade();
                            ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_flashRelic(QuantumEgg.ID);
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect", method = "update")
    public static class ReplayMakeCardInDiscardPilePatch {
        public static void Prefix(ShowCardAndAddToDiscardEffect __Instance) {
            if (__Instance != null && AbstractDungeon.player != null && __Instance.duration >= 1.5f - Gdx.graphics.getDeltaTime() && !__Instance.isDone) {
                if (AbstractDungeon.player.hasRelic(QuantumEgg.ID)) {
                    final AbstractCard c = ReflectionHacks.getPrivate(__Instance, ShowCardAndAddToDiscardEffect.class, "card");
                    if (c != null && c.canUpgrade()) {
                        AbstractCard srcCard = null;
                        for (AbstractCard realcard : AbstractDungeon.player.discardPile.group) {
                            if (c.name == realcard.name
                                    && c.timesUpgraded == realcard.timesUpgraded
                                    && c.inBottleLightning == realcard.inBottleLightning
                                    && c.inBottleFlame == realcard.inBottleFlame
                                    && c.inBottleTornado == realcard.inBottleTornado
                                    && c.baseBlock == realcard.baseBlock
                                    && c.baseDamage == realcard.baseDamage
                                    && c.baseMagicNumber == realcard.baseMagicNumber
                                    && c.misc == realcard.misc
                                    && c.cost == realcard.cost) {
                                srcCard = realcard;
                                break;
                            }
                        }
                        if (srcCard != null && srcCard.canUpgrade()) {
                            srcCard.upgrade();
                            c.upgrade();
                            ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_flashRelic(QuantumEgg.ID);
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction", method = "update")
    public static class ReplayMakeCardInHandPatch {

        public static void Prefix(MakeTempCardInHandAction __Instance) {
            if (__Instance != null && AbstractDungeon.player != null && __Instance.amount != 0 && !__Instance.isDone) {
                if (AbstractDungeon.player.hasRelic(QuantumEgg.ID)) {
                    final AbstractCard c = ReflectionHacks.getPrivate(__Instance, MakeTempCardInHandAction.class, "c");
                    if (c != null && c.canUpgrade()) {
                        c.upgrade();
                        ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_flashRelic(QuantumEgg.ID);
                    }
                }
            }
        }

    }
}
