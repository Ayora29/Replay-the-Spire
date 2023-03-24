package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.MidasAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MidasTouch
        extends CustomCard {
    public static final String ID = "Midas Touch";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Midas Touch");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public MidasTouch() {
        super("Midas Touch", NAME, "replay/images/cards/midas_touch.png", MidasTouch.COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.NONE);

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MidasAction(AbstractDungeon.player, AbstractDungeon.player, this.upgraded));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MidasTouch();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}