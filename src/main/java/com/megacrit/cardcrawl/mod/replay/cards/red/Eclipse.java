package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.EclipseAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Eclipse
        extends CustomCard {
    public static final String ID = "Eclipse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 3;

    public Eclipse() {
        super(ID, NAME, "replay/images/cards/eclipse.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.RED, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, this.magicNumber, false, true, false));
        AbstractDungeon.actionManager.addToBottom(new EclipseAction(p, this.magicNumber));
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    public AbstractCard makeCopy() {
        return new Eclipse();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            RefundVariable.upgrade(this, 1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
