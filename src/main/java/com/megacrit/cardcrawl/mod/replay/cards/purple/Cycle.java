package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Cycle extends CustomCard {
    public static final String ID = "Replay:Cycle";
    private static final CardStrings cardStrings;

    public Cycle() {
        super(ID, Cycle.cardStrings.NAME, "replay/images/cards/cycle.png", 0, Cycle.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = this.magicNumber = 1;
        ExhaustiveVariable.setBaseValue(this, 3);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public void triggerOnScry() {
        this.addToBot(new DiscardToHandAction(this));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cycle();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
