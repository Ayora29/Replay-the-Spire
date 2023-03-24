package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PanicButton extends CustomCard {
    public static final String ID = "Panic Button";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public PanicButton() {
        super("Panic Button", PanicButton.NAME, "replay/images/cards/panicButton.png", PanicButton.COST, PanicButton.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.NONE);
        this.showEvokeValue = true;
        this.exhaust = true;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.retain = true;
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        final int orbCount = p.filledOrbCount();
        for (int i = 0; i < orbCount; ++i) {
            AbstractDungeon.actionManager.addToBottom(new AnimateOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PanicButton();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeMagicNumber(1);
            this.upgradeName();
            this.rawDescription = PanicButton.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Panic Button");
        NAME = PanicButton.cardStrings.NAME;
        DESCRIPTION = PanicButton.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = PanicButton.cardStrings.UPGRADE_DESCRIPTION;
    }
}
