package com.megacrit.cardcrawl.mod.replay.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;


//stole this code from mad science mod. Thanks Twanvl!
public class ChooseAction extends AbstractGameAction {
    AbstractCard baseCard;
    AbstractMonster target;
    CardGroup choices;
    ArrayList<Runnable> actions;
    String message;

    public ChooseAction(final AbstractCard baseCard, final AbstractMonster target, final String message) {
        this(baseCard, target, message, 1);
    }

    public ChooseAction(final AbstractCard baseCard, final AbstractMonster target, final String message, final int toChoose) {
        this.choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.actions = new ArrayList<>();
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, 1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.baseCard = baseCard;
        this.message = message;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.amount = toChoose;
    }

    public void add(final String name, final String description, final Runnable action) {
        final AbstractCard choice = this.baseCard.makeStatEquivalentCopy();
        choice.name = name;
        choice.rawDescription = description;
        choice.initializeDescription();
        if (this.target != null) {
            choice.calculateCardDamage(this.target);
        } else {
            choice.applyPowers();
        }
        this.choices.addToTop(choice);
        this.actions.add(action);
    }

    @Override
    public void update() {
        if (this.choices.isEmpty()) {
            this.tickDuration();
            this.isDone = true;
            return;
        }
        if (this.duration != Settings.ACTION_DUR_FASTER) {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                final AbstractCard pick = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                final int i = this.choices.group.indexOf(pick);
                this.actions.get(i).run();
                this.actions.remove(i);
                this.choices.group.remove(i);
                this.amount--;
                if (this.amount > 0) {
                    this.duration = Settings.ACTION_DUR_FASTER;
                    this.isDone = false;
                    return;
                }
            }
            this.tickDuration();
            return;
        }
        AbstractDungeon.gridSelectScreen.open(this.choices, 1, this.message, false, false, false, false);
        this.tickDuration();
    }
}
