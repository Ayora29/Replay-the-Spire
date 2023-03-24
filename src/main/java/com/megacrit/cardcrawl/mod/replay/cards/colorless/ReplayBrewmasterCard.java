package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.ReplayBrewPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReplayBrewmasterCard extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:Brewmster";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 3;

    public ReplayBrewmasterCard() {
        super(ID, ReplayBrewmasterCard.NAME, "replay/images/cards/brewmaster.png", ReplayBrewmasterCard.COST, ReplayBrewmasterCard.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.RARE, CardTarget.SELF);
        //this.isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReplayBrewPower(p, 1), 1));

    }

    @Override
    public AbstractCard makeCopy() {
        return new ReplayBrewmasterCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            //this.isEthereal = false;
            //this.rawDescription = ReplayBrewmasterCard.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ReplayBrewmasterCard.cardStrings.NAME;
        DESCRIPTION = ReplayBrewmasterCard.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayBrewmasterCard.cardStrings.UPGRADE_DESCRIPTION;
    }
}
