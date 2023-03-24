package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.stances.GuardStance;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AtTheReady extends CustomCard {
    public static final String ID = "Replay:AtTheReady";
    private static final CardStrings cardStrings;

    public AtTheReady() {
        super(ID, AtTheReady.cardStrings.NAME, "replay/images/cards/at_the_ready.png", 1, AtTheReady.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF);
        this.exhaust = true;
        this.selfRetain = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ChangeStanceAction(new GuardStance()));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AtTheReady();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
