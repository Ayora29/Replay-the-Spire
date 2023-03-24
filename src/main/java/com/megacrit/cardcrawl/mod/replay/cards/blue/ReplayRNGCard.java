package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.RNGOrbsAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReplayRNGCard extends CustomCard {
    public static final String ID = "ReplayRNGCard";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = -1;

    public ReplayRNGCard() {
        super("ReplayRNGCard", ReplayRNGCard.NAME, "replay/images/cards/RNG.png", ReplayRNGCard.COST, ReplayRNGCard.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RNGOrbsAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ReplayRNGCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = ReplayRNGCard.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReplayRNGCard");
        NAME = ReplayRNGCard.cardStrings.NAME;
        DESCRIPTION = ReplayRNGCard.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = ReplayRNGCard.cardStrings.UPGRADE_DESCRIPTION;
    }
}
