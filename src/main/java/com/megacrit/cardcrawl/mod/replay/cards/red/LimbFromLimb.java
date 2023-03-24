package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.LimbFromLimbAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LimbFromLimb extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:Limb From Limb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 24;
    private static final int DMG_UP = 4;
    private static final int THRESHOLD = 30;
    private static final int THRESHOLD_UP = 2;

    public LimbFromLimb() {
        super(ID, NAME, "replay/images/cards/limbFromLimb.png", COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = THRESHOLD;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LimbFromLimbAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LimbFromLimb();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(DMG_UP);
            this.upgradeMagicNumber(THRESHOLD_UP);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
