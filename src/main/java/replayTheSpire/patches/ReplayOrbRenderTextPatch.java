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
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.orbs.AbstractOrb", method = "renderText")
public class ReplayOrbRenderTextPatch {
	
	private static boolean forcingShowText;
	
	public static void Prefix(AbstractOrb __Instance, final SpriteBatch sb) {
		ReplayOrbRenderTextPatch.forcingShowText = false;
		if (__Instance != null && AbstractDungeon.player != null && !(__Instance instanceof EmptyOrbSlot) && !(__Instance instanceof GlassOrb)) {
			final boolean showEvokeValue = ReflectionHacks.getPrivate(__Instance, AbstractOrb.class, "showEvokeValue");
			if (!showEvokeValue && AbstractDungeon.player.orbs.get(AbstractDungeon.player.orbs.size() - 1) == __Instance) {
				AbstractOrb nxrb = AbstractDungeon.player.orbs.get(0);
				if (nxrb instanceof GlassOrb) {
					final boolean showNxEvokeValue = ReflectionHacks.getPrivate(nxrb, AbstractOrb.class, "showEvokeValue");
					//__Instance.showEvokeValue = true;
					if (showNxEvokeValue) {
						ReflectionHacks.setPrivate(__Instance, AbstractOrb.class, "showEvokeValue", true);
						ReplayOrbRenderTextPatch.forcingShowText = true;
					}
				}
			}
        }
	}
	public static void Postfix(AbstractOrb __Instance, final SpriteBatch sb) {
		if (__Instance != null && ReplayOrbRenderTextPatch.forcingShowText) {
			//__Instance.showEvokeValue = false;
			ReflectionHacks.setPrivate(__Instance, AbstractOrb.class, "showEvokeValue", false);
		}
		ReplayOrbRenderTextPatch.forcingShowText = false;
	}
	
}