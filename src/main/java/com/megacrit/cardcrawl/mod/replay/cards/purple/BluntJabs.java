package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class BluntJabs extends CustomCard {
    public static final String ID = "Replay:BluntJabs";
    private static final CardStrings cardStrings;

    public BluntJabs() {
        super(ID, BluntJabs.cardStrings.NAME, "replay/images/cards/blunt_jabs.png", 1, BluntJabs.cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 4;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractCard pCard = null;
        for (AbstractCard c : p.hand.group) {
            if (c == this) {
                if (pCard != null) {
                    pCard.retain = true;
                }
            } else if (pCard == this) {
                c.retain = true;
            }
            pCard = c;
        }
        for (int i = 0; i < this.magicNumber; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BluntJabs();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}