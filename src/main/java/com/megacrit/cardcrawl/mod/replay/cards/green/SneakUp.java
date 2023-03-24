package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DiscardByTypeAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class SneakUp extends CustomCard {
    public static final String ID = "Sneak Up";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 3;

    public SneakUp() {
        super("Sneak Up", SneakUp.NAME, "replay/images/cards/sneakUp.png", SneakUp.COST, SneakUp.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
        this.isInnate = true;
        RefundVariable.setBaseValue(this, 2);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        //if (!this.upgraded) {
        AbstractDungeon.actionManager.addToBottom(new DiscardByTypeAction(p, AbstractCard.CardType.ATTACK, -1));
        //} else {
        //
        //}
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(this.cost - 1);
            RefundVariable.upgrade(this, -1);
            //this.rawDescription = SneakUp.UPGRADE_DESCRIPTION;
            //this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SneakUp();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Sneak Up");
        NAME = SneakUp.cardStrings.NAME;
        DESCRIPTION = SneakUp.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SneakUp.cardStrings.UPGRADE_DESCRIPTION;
    }
}
