package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostSwipe
        extends CustomCard {
    public static final String ID = "Ghost Swipe";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Ghost Swipe");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public GhostSwipe() {
        super("Ghost Swipe", NAME, "replay/images/cards/ghostswipe.png", 1, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.ENEMY);

        this.baseDamage = 6;
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new GhostSwipe();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}