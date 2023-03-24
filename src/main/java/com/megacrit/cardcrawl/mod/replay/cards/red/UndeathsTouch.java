package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
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
import com.megacrit.cardcrawl.mod.replay.powers.DecrepitPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UndeathsTouch extends CustomCard {
    public static final String ID = "Replay:Undeath's Touch";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    public UndeathsTouch() {
        super(UndeathsTouch.ID, UndeathsTouch.NAME, "replay/images/cards/undeathstouch.png", UndeathsTouch.COST, UndeathsTouch.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.exhaust = true;
        this.baseDamage = 1;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new DecrepitPower(m, 1, false), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UndeathsTouch();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.exhaust = false;
            ExhaustiveVariable.setBaseValue(this, 2);
            ExhaustiveField.ExhaustiveFields.isExhaustiveUpgraded.set(this, true);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(UndeathsTouch.ID);
        NAME = UndeathsTouch.cardStrings.NAME;
        DESCRIPTION = UndeathsTouch.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = UndeathsTouch.cardStrings.UPGRADE_DESCRIPTION;
    }
}
