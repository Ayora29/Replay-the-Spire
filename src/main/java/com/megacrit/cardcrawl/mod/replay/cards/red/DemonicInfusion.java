package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DemonicInfusionAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DemonicInfusion extends CustomCard {
    public static final String ID = "Demonic Infusion";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -1;

    public DemonicInfusion() {
        super("Demonic Infusion", DemonicInfusion.NAME, "replay/images/cards/demonicInfusion.png", -1, DemonicInfusion.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        RefundVariable.setBaseValue(this, 1);
        this.exhaust = true;
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DemonicInfusionAction(AbstractDungeon.player, this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DemonicInfusion();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = DemonicInfusion.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Demonic Infusion");
        NAME = DemonicInfusion.cardStrings.NAME;
        DESCRIPTION = DemonicInfusion.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = DemonicInfusion.cardStrings.UPGRADE_DESCRIPTION;
    }
}
