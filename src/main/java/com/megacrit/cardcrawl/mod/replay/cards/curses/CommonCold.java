package com.megacrit.cardcrawl.mod.replay.cards.curses;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CommonCold
        extends CustomCard {
    public static final String ID = "CommonCold";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public CommonCold() {
        super(ID, NAME, "replay/images/cards/CommonCold.png", COST, DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic("Medical Kit")) {
            p.getRelic("Medical Kit").flash();
            this.exhaust = true;
        }
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "ah- @CHOO!@", 2.0f, 2.0f));
    }

    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }

    public AbstractCard makeCopy() {
        return new CommonCold();
    }

    public void upgrade() {
    }
}
