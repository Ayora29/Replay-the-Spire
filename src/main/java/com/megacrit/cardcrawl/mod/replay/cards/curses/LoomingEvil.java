package com.megacrit.cardcrawl.mod.replay.cards.curses;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LoomingEvil
        extends CustomCard {
    public static final String ID = "Looming Evil";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Looming Evil");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 3;
    private static final int POOL = 2;

    public LoomingEvil() {
        super("Looming Evil", NAME, "replay/images/cards/loomingEvil.png", 3, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);

        this.baseMagicNumber = this.cost;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.dontTriggerOnUseCard) {
            this.exhaust = true;
        } else {
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WeakPower(AbstractDungeon.player, 1, true), 1));
            AbstractCard c = AbstractDungeon.returnRandomCurse().makeCopy();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, false));
            //AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
            if (this.cost > 0) {
                upgradeBaseCost(this.cost - 1);
                this.magicNumber = this.cost;
                this.rawDescription = LoomingEvil.EXTENDED_DESCRIPTION[0] + this.cost + LoomingEvil.EXTENDED_DESCRIPTION[1];
                this.initializeDescription();
            }
        }
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
        this.rawDescription = LoomingEvil.EXTENDED_DESCRIPTION[0] + this.cost + LoomingEvil.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (cardPlayable(m)) {
            return hasEnoughEnergy();
        }
        return false;
    }

    public AbstractCard makeCopy() {
        return new LoomingEvil();
    }

    public void upgrade() {
    }

}
