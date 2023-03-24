package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FIFOQueue extends CustomCard {
    public static final String ID = "FIFO Queue";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public FIFOQueue() {
        super("FIFO Queue", FIFOQueue.NAME, "replay/images/cards/FIFO_queue.png", FIFOQueue.COST, FIFOQueue.DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FIFOQueue();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = FIFOQueue.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("FIFO Queue");
        NAME = FIFOQueue.cardStrings.NAME;
        DESCRIPTION = FIFOQueue.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = FIFOQueue.cardStrings.UPGRADE_DESCRIPTION;
    }
}