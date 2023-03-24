package replayTheSpire.minions.grembo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class GremboTsundere extends AbstractFriendlyMonster {
    private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID = "GremboTsundere";
    private AbstractMonster target;
    private final int baseDamage;
    private final int baseBlock;

    public GremboTsundere(float rot) {
        super(GremboTsundere.NAME, GremboTsundere.ID, 15, -8.0f, 10.0f, 230.0f, 240.0f, "replay/images/monsters/exord/cook.png", -900.0f + 200.0f * (float) Math.cos(rot), -200.0f * (float) Math.sin(rot));
        this.img = null;
        this.loadAnimation("replay/images/monsters/theBottom/femaleGremlin/skeleton.atlas", "replay/images/monsters/theBottom/femaleGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 4;
        this.baseBlock = 6;
        addMoves();
    }

    private void addMoves() {
        this.moves.addMove(new MinionMove("Shield Bash", this, new Texture("replay/images/summons/atk_bubble.png"), "Deal " + this.baseDamage + " damage.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this, this.baseDamage, DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }));
        this.moves.addMove(new MinionMove("Protect", this, new Texture("replay/images/summons/block_bubble.png"), "Gives you " + this.baseBlock + " Block.", () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, this, this.baseBlock));
        }));
        this.moves.addMove(new MinionMove("Defend", this, new Texture("replay/images/summons/block_bubble.png"), "Gains " + this.baseBlock + " Block.", () -> {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.baseBlock));
        }));
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinTsundere");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}