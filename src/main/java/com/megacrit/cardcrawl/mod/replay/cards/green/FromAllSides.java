package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.FromAllSidesAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class FromAllSides
        extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:From All Sides";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -1;

    public FromAllSides() {
        super(ID, NAME, "replay/images/cards/from_all_sides.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
        this.baseDamage = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new FromAllSidesAction(p, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new FromAllSides();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            //upgradeMagicNumber(1);
            upgradeDamage(1);
        }
    }
}