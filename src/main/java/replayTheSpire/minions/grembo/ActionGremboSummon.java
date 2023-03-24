package replayTheSpire.minions.grembo;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.GrembosGang;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import kobting.friendlyminions.characters.AbstractPlayerWithMinions;
import kobting.friendlyminions.helpers.BasePlayerMinionHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

public class ActionGremboSummon
        extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int magic;
    private final AbstractPlayer p;
    private AbstractMonster m;
    private final int energyOnUse;

    public ActionGremboSummon(AbstractPlayer p, int magic, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.magic = magic;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                if (this.p instanceof AbstractPlayerWithMinions) {
                    AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) this.p;
                    AbstractFriendlyMonster grem = GrembosGang.GetRandomGremboi(effect);
                    player.addMinion(grem);
                } else {
                    BasePlayerMinionHelper.addMinion(this.p, GrembosGang.GetRandomGremboi(effect));
                }
            }
        }
        if (!this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}