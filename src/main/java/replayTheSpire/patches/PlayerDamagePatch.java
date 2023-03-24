package replayTheSpire.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.mod.replay.powers.ForgedInHellfireAltPower;
import com.megacrit.cardcrawl.mod.replay.powers.SpecialistPower;
import com.megacrit.cardcrawl.mod.replay.relics.DimensionalGlitch;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import replayTheSpire.ReplayTheSpireMod;


@SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method = "damage")
public class PlayerDamagePatch {
    //68
    @SpireInsertPatch(rloc = 68)
    public static SpireReturn Insert(AbstractPlayer player, final DamageInfo info) {
        return SpireReturn.Continue();
    }

    private static int initialDamage;
    public static boolean altered;

    public static void Prefix(AbstractPlayer player, DamageInfo info) {
        altered = false;
        if (player != null && info.type != DamageInfo.DamageType.NORMAL && !player.hasPower(IntangiblePower.POWER_ID) && !player.hasPower(IntangiblePlayerPower.POWER_ID)) {
            PlayerDamagePatch.initialDamage = info.output;
            if (info.owner != null && info.owner != player && info.owner.hasPower(SpecialistPower.POWER_ID)) {
                altered = true;
                info.output += info.owner.getPower(SpecialistPower.POWER_ID).amount;
            }
            if (player.hasPower(ForgedInHellfireAltPower.POWER_ID)) {
                altered = ((ForgedInHellfireAltPower) player.getPower(ForgedInHellfireAltPower.POWER_ID)).patchAttacked(info) || altered;
            }
            if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID)) {
                altered = true;
                info.output += MathUtils.ceil(((float) info.output / 2.0f));
            }
        }
    }

    public static void Postfix(AbstractPlayer player, DamageInfo info) {
        if (altered) {//if (player != null && info.type != DamageInfo.DamageType.NORMAL && ((info.owner != null && info.owner != AbstractDungeon.player && info.owner.hasPower("Specialist")) || ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(DimensionalGlitch.ID))) {
            info.output = PlayerDamagePatch.initialDamage;
        }
    }
}
