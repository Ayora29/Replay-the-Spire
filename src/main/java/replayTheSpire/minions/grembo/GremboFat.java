package replayTheSpire.minions.grembo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.mod.replay.powers.DelayedLoseStrengthPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

public class GremboFat extends AbstractFriendlyMonster {
    private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID = "GremboFat";
    private AbstractMonster target;
    private final int baseDamage;
    private final int heal;
    private final int strength;

    public GremboFat(float rot) {
        super(GremboFat.NAME, GremboFat.ID, 14, -8.0f, 10.0f, 230.0f, 240.0f, "replay/images/monsters/exord/cook.png", -900.0f + 200.0f * (float) Math.cos(rot), -200.0f * (float) Math.sin(rot));
        this.img = null;
        this.loadAnimation("replay/images/monsters/theBottom/fatGremlin/skeleton.atlas", "replay/images/monsters/theBottom/fatGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        this.baseDamage = 4;
        this.heal = 6;
        this.strength = 2;
        addMoves();
    }

    private void addMoves() {
        this.moves.addMove(new MinionMove(MOVES[0], this, new Texture("replay/images/summons/atk_bubble.png"), "Deal " + this.baseDamage + " damage and apply 1 Weak.", () -> {
            target = AbstractDungeon.getRandomMonster();
            DamageInfo info = new DamageInfo(this, this.baseDamage, DamageInfo.DamageType.NORMAL);
            info.applyPowers(this, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, this, new WeakPower(target, 1, true), 1));
        }));
        this.moves.addMove(new MinionMove("Eat", this, new Texture("replay/images/summons/heal_bubble.png"), this.name + " heals " + this.heal + " HP and gains " + this.strength + " Strength for 1 round.", () -> {
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, this.heal));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strength), this.strength));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DelayedLoseStrengthPower(this, this.strength), this.strength));
        }));
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinFat");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}