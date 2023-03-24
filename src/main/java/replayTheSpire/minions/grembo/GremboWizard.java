package replayTheSpire.minions.grembo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;

import java.util.ArrayList;

public class GremboWizard extends AbstractFriendlyMonster {
    private static final MonsterStrings monsterStrings;
    public static String NAME;
    public static String ID;
    public int currentCharge;
    private final int baseDamage;

    public GremboWizard(boolean makeMini) {
        super(GremboWizard.NAME, GremboWizard.ID, 20, -8.0f, 10.0f, 230.0f, 240.0f, "replay/images/monsters/exord/cook.png", -700.0f, 0.0f);
        this.img = null;
        this.currentCharge = 2;
        this.baseDamage = 25;
        this.damage.add(new DamageInfo(this, this.baseDamage));
        this.loadAnimation("replay/images/monsters/theBottom/wizardGremlin/skeleton.atlas", "replay/images/monsters/theBottom/wizardGremlin/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = true;
        addMoves();
    }

    private MinionMove move_blast;
    private MinionMove move_charge;

    private void addMoves() {
        this.move_blast = new MinionMove(MOVES[1], this, new Texture("replay/images/summons/atk7_bubble.png"), "Deal " + this.baseDamage + " damage to ALL enemies.", () -> {
            final ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;
            final float[] tmp2 = new float[m.size()];
            for (int i = 0; i < tmp2.length; ++i) {
                tmp2[i] = this.baseDamage;
            }
            for (int i = 0; i < tmp2.length; ++i) {
                for (final AbstractPower p2 : this.powers) {
                    tmp2[i] = p2.atDamageGive(tmp2[i], DamageType.NORMAL);
                }
            }
            for (int i = 0; i < tmp2.length; ++i) {
                for (final AbstractPower p2 : this.powers) {
                    tmp2[i] = p2.atDamageFinalGive(tmp2[i], DamageType.NORMAL);
                }
            }
            for (int i = 0; i < tmp2.length; ++i) {
                if (tmp2[i] < 0.0f) {
                    tmp2[i] = 0.0f;
                }
            }
            int[] multiDamage = new int[tmp2.length];
            for (int i = 0; i < tmp2.length; ++i) {
                multiDamage[i] = MathUtils.floor(tmp2[i]);
            }
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(this.dialogX, this.dialogY, false)));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this, multiDamage, DamageType.NORMAL, AttackEffect.NONE));
            this.currentCharge = 2;
            this.updateMoveCharge();
        });
        this.move_charge = new MinionMove(MOVES[0], this, new Texture("replay/images/summons/charge_bubble.png"), "Charge the Ultimate Blast (" + this.currentCharge + " charges before blast can be used).", () -> {
            this.currentCharge--;
            this.updateMoveCharge();
        });
        this.moves.addMove(this.move_charge);
    }

    public void updateMoveCharge() {
        if (this.currentCharge <= 0) {
            if (this.moves.removeMove(this.move_charge.getID()) != null) {
                this.moves.addMove(this.move_blast);
            }
        } else {
            if (this.moves.removeMove(this.move_blast.getID()) != null) {
                this.moves.addMove(this.move_charge);
            }
        }
    }


    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinWizard");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}