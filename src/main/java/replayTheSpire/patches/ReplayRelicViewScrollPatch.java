package replayTheSpire.patches;

import java.util.*;
import replayTheSpire.*;
import com.megacrit.cardcrawl.screens.compendium.*;
import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.*;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.compendium.RelicViewScreen", method = "open")
public class ReplayRelicViewScrollPatch {
	
	public static void Postfix(RelicViewScreen __Instance) {
		ReflectionHacks.setPrivate(__Instance, RelicViewScreen.class, "scrollUpperBound", 2800.0f * Settings.scale);
	}
	
}