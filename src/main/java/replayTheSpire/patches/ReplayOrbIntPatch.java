package replayTheSpire.patches;

import java.util.*;
import replayTheSpire.*;
import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.characters.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.mod.replay.orbs.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.mod.replay.vfx.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
public class ReplayOrbIntPatch {
@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "applyFocus")
	public static class ReplayAbstractOrbIntPatch {
		public static void Postfix(AbstractOrb __Instance) {
			/*final AbstractPower power = AbstractDungeon.player.getPower("Focus");
	        if (power == null) {
				final int basePassiveAmount = (int)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractOrb.class, "basePassiveAmount");
				final int baseEvokeAmount = (int)ReflectionHacks.getPrivate((Object)__Instance, (Class)AbstractOrb.class, "baseEvokeAmount");
	            __Instance.passiveAmount = Math.max(0, basePassiveAmount);
				if (!__Instance.ID.equals("Dark")) {
					__Instance.evokeAmount = Math.max(0, baseEvokeAmount);
				}
	        }*/
			int mypos = AbstractDungeon.player.orbs.indexOf(__Instance);
			if (mypos > -1 && !__Instance.ID.equals("Plasma")) {
				if (mypos > 0) {
					AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos - 1);
					if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
						if (!__Instance.ID.equals("Crystal")) {
							__Instance.passiveAmount += adorb.passiveAmount;
						}
						if (!__Instance.ID.equals("Dark")) {
							__Instance.evokeAmount += adorb.passiveAmount;
						}
					}
				}
				if (mypos < AbstractDungeon.player.orbs.size() - 1) {
					AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos + 1);
					if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
						if (!__Instance.ID.equals("Crystal")) {
							__Instance.passiveAmount += adorb.passiveAmount;
						}
						if (!__Instance.ID.equals("Dark")) {
							__Instance.evokeAmount += adorb.passiveAmount;
						}
					}
				}
			}
		}
	}
	@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.Dark", method = "applyFocus")
	public static class ReplayDarkOrbIntPatch {
		public static void Postfix(AbstractOrb __Instance) {
			final AbstractPower power = AbstractDungeon.player.getPower("Focus");
	        if (power == null) {
				final int basePassiveAmount = ReflectionHacks.getPrivate(__Instance, AbstractOrb.class, "basePassiveAmount");
	            __Instance.passiveAmount = Math.max(0, basePassiveAmount);
	        }
			int mypos = AbstractDungeon.player.orbs.indexOf(__Instance);
			if (mypos > -1) {
				if (mypos > 0) {
					AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos - 1);
					if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
						__Instance.passiveAmount += adorb.passiveAmount;
					}
				}
				if (mypos < AbstractDungeon.player.orbs.size() - 1) {
					AbstractOrb adorb = AbstractDungeon.player.orbs.get(mypos + 1);
					if (adorb != null && adorb.ID != null && adorb.ID.equals("Crystal")) {
						__Instance.passiveAmount += adorb.passiveAmount;
					}
				}
			}
		}
	}
}