package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.BasicFromDeckToHandAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostFetch extends CustomCard {
    public static final String ID = "Ghost Fetch";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;

    public GhostFetch() {
        super("Ghost Fetch", GhostFetch.NAME, "replay/images/cards/ghostfetch.png", 0, GhostFetch.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new BasicFromDeckToHandAction(1, this.upgraded));
    }

    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        boolean hasBasic = false;
        for (final AbstractCard c : p.drawPile.group) {
            if (c.rarity == AbstractCard.CardRarity.BASIC || c.cardID == "Ghost Defend" || c.cardID == "Ghost Swipe") {
                hasBasic = true;
            }
        }
        if (!hasBasic) {
            this.cantUseMessage = GhostFetch.EXTENDED_DESCRIPTION[0];
            canUse = false;
        }
        return canUse;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new GhostFetch();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = GhostFetch.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Fetch");
        NAME = GhostFetch.cardStrings.NAME;
        DESCRIPTION = GhostFetch.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = GhostFetch.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = GhostFetch.cardStrings.EXTENDED_DESCRIPTION;
    }
}
