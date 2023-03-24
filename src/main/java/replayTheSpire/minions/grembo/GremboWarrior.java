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
import com.megacrit.cardcrawl.powers.AngryPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class GremboWarrior extends AbstractFriendlyMonster {
    private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID = "GremboWarrior";
    private AbstractMonster target;
    private final int baseDamage;
    private final int strength;

    public GremboWarrior(float rot) {
        super(GremboWarrior.NAME, GremboWarrior.ID, 20, -8.0f, 10.0f, 230.0f, 240.0f, "replay/images/monsters/exord/cook.png", -900.0f + 200.0f * (float) Math.cos(rot), -200.0f * (float) Math.sin(rot));
        this.img = null;
        this.loadAnimation("replay/images/monsters/theBottom/angryGremlin/skeleton.atlas", "replay/images/monsters/theBottom/angryGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 4;
        this.strength = 2;
        addMoves();
    }

    private void addMoves() {
        this.moves.addMove(new MinionMove(MOVES[0], this, new Texture("replay/images/summons/atk_bubble.png"), "Deal " + this.baseDamage + " damage.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this, this.baseDamage, DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }));
        this.moves.addMove(new MinionMove("Enrage", this, new Texture("replay/images/summons/flex_bubble.png"), this.name + " gains " + this.strength + " Strength.", () -> {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strength), this.strength));
        }));
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngryPower(this, 1), 1));
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWarrior");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}