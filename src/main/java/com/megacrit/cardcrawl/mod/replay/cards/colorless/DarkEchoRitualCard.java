package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkEchoRitualCard extends CustomCard {
    public static final String ID = "Dark Echo";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int HEAL_AMT = 2;
    private static final int POOL = 1;

    public DarkEchoRitualCard() {
        super("Dark Echo", DarkEchoRitualCard.NAME, "replay/images/cards/dark_echo.png", 1, DarkEchoRitualCard.DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        this.baseDamage = 42;
        this.isMultiDamage = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(14);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkEchoRitualCard();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Dark Echo");
        NAME = DarkEchoRitualCard.cardStrings.NAME;
        DESCRIPTION = DarkEchoRitualCard.cardStrings.DESCRIPTION;
    }
}
