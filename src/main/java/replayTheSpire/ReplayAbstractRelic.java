package replayTheSpire;


import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.mod.replay.tools.TextureLoader;

public abstract class ReplayAbstractRelic extends CustomRelic {

    public ReplayAbstractRelic(String id, RelicTier tier, LandingSound sfx) {
        super(id,
                TextureLoader.getTexture("replay/images/relics/" + id + ".png"),
                TextureLoader.getTexture("replay/images/relics/outline/" + id + ".png"),
                tier,
                sfx);
    }


}
