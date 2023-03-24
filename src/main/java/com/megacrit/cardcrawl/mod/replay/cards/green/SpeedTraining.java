package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class SpeedTraining extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:Speed Training";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 0;

    public SpeedTraining() {
        super(ID, NAME, "replay/images/cards/speedTraining.png", COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 1, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SpeedTraining();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ExhaustiveVariable.upgrade(this, 1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SpeedTraining.cardStrings.NAME;
        DESCRIPTION = SpeedTraining.cardStrings.DESCRIPTION;
    }
}
