package com.megacrit.cardcrawl.mod.replay.cards.curses;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Voices extends CustomCard implements StartupCard {
    public static final String ID = "Voices";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -2;

    public Voices() {
        super("Voices", Voices.NAME, "replay/images/cards/voices.png", -2, Voices.DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        this.exhaust = true;
        this.isEthereal = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

    }

    @Override
    public AbstractCard makeCopy() {
        return new Voices();
    }

    @Override
    public void upgrade() {
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Voices");
        NAME = Voices.cardStrings.NAME;
        DESCRIPTION = Voices.cardStrings.DESCRIPTION;
    }

    @Override
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(this.makeCopy(), this.magicNumber));
        return true;
    }
}
