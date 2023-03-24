package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.RetainSomeBlockPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FluidMovement extends CustomCard {
    public static final String ID = "Fluid Movement";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public FluidMovement() {
        super("Fluid Movement", FluidMovement.NAME, "replay/images/cards/fluidMovement.png", 1, FluidMovement.DESCRIPTION, CardType.POWER, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainSomeBlockPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FluidMovement();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Fluid Movement");
        NAME = FluidMovement.cardStrings.NAME;
        DESCRIPTION = FluidMovement.cardStrings.DESCRIPTION;
    }
}
