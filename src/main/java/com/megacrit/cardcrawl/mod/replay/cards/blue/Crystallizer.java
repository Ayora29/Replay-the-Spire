package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.CrystallizerPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Crystallizer extends CustomCard {
    public static final String ID = "Crystallizer";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public Crystallizer() {
        super("Crystallizer", Crystallizer.NAME, "replay/images/cards/crystallizer.png", Crystallizer.COST, Crystallizer.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CrystallizerPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Crystallizer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isInnate) {
                this.upgradeMagicNumber(1);
            }
            this.isInnate = true;
            this.rawDescription = Crystallizer.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Crystallizer");
        NAME = Crystallizer.cardStrings.NAME;
        DESCRIPTION = Crystallizer.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Crystallizer.cardStrings.UPGRADE_DESCRIPTION;
    }
}
