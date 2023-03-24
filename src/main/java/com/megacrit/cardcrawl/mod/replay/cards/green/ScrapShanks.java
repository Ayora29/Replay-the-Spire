package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.ScrapShanksPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ScrapShanks extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:Scrap Shanks";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;

    public ScrapShanks() {
        super(ID, ScrapShanks.NAME, "replay/images/cards/scrap_shanks.png", 1, ScrapShanks.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        int stackAmount = this.magicNumber;
        if (this.upgraded) {
            stackAmount *= -1;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ScrapShanksPower(p, stackAmount), stackAmount));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ScrapShanks();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = ScrapShanks.cardStrings.NAME;
        DESCRIPTION = ScrapShanks.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ScrapShanks.cardStrings.UPGRADE_DESCRIPTION;
    }
}
