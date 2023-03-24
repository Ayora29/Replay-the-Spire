package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.stances.GuardStance;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReflectionPower;

public class Anticipation extends CustomCard {
    public static final String ID = "Replay:Anticipation";
    private static final CardStrings cardStrings;

    public Anticipation() {
        super(ID, Anticipation.cardStrings.NAME, "replay/images/cards/anticipation.png", 1, Anticipation.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 2;
        ExhaustiveVariable.setBaseValue(this, 3);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ChangeStanceAction(new GuardStance()));
        this.addToBot(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
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
        return new Anticipation();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
