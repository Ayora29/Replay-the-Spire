package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReflectionPower;

public class Parry extends CustomCard {
    public static final String ID = "Replay:Parry";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 1;

    public Parry() {
        super(ID, Parry.NAME, "replay/images/cards/parry.png", COST, Parry.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        ExhaustiveVariable.setBaseValue(this, 3);
        this.isEthereal = true;
        this.baseDamage = 4;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (m.intent == AbstractMonster.Intent.ATTACK || m.intent == AbstractMonster.Intent.ATTACK_BUFF || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Parry();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = Parry.cardStrings.NAME;
        DESCRIPTION = Parry.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Parry.cardStrings.UPGRADE_DESCRIPTION;
    }
}
