package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Survivalism extends CustomCard {
    public static final String ID = "m_Survivalism";
    private static final CardStrings cardStrings;

    public Survivalism() {
        super("m_Survivalism", Survivalism.cardStrings.NAME, "replay/images/cards/survivalism.png", 1, Survivalism.cardStrings.DESCRIPTION, CardType.SKILL,
                (AbstractDungeon.player == null) ? AbstractCard.CardColor.COLORLESS : ((AbstractDungeon.player instanceof Watcher) ? AbstractCard.CardColor.GREEN : AbstractCard.CardColor.PURPLE),
                CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 8;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new ScryAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(1);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Survivalism();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
