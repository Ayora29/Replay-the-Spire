package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostDefend
        extends CustomCard {
    public static final String ID = "Ghost Defend";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Defend");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public GhostDefend() {
        super("Ghost Defend", NAME, "replay/images/cards/defend.png", 1, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);

        this.baseBlock = 5;
        this.exhaust = true;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
    }

    public AbstractCard makeCopy() {
        return new GhostDefend();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}