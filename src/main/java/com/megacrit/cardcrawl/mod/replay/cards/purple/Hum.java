package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.MightPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Hum extends CustomCard {
    public static final String ID = "Replay:Hum";
    private static final CardStrings cardStrings;

    public Hum() {
        super(ID, Hum.cardStrings.NAME, "replay/images/cards/hum.png", 1, Hum.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ScryAction(this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new MightPower(p, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hum();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
