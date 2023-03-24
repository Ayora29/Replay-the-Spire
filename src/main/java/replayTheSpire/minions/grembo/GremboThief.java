package replayTheSpire.minions.grembo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class GremboThief extends AbstractFriendlyMonster {
    private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID = "GremboThief";
    private final int baseDamage;
    private final int cardDraw;
    private AbstractMonster target;

    public GremboThief(float rot) {
        super(GremboThief.NAME, GremboThief.ID, 20, -8.0f, 10.0f, 230.0f, 240.0f, "replay/images/monsters/exord/cook.png", -900.0f + 200.0f * (float) Math.cos(rot), -200.0f * (float) Math.sin(rot));
        this.img = null;
        this.loadAnimation("replay/images/monsters/theBottom/thiefGremlin/skeleton.atlas", "replay/images/monsters/theBottom/thiefGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 9;
        this.cardDraw = 2;
        addMoves();
    }

    private void addMoves() {
        this.moves.addMove(new MinionMove("Stab", this, new Texture("replay/images/summons/atk2_bubble.png"), "Deal " + this.baseDamage + " damage.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this, this.baseDamage, DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }));
        this.moves.addMove(new MinionMove("Steal", this, new Texture("replay/images/summons/draw_bubble.png"), "Draw " + this.cardDraw + " card" + (this.cardDraw > 1 ? "s" : "") + " next turn.", () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, this.cardDraw), this.cardDraw));
        }));
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinThief");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}