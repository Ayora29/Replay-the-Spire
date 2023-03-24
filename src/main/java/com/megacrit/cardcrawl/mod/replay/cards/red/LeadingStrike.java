package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.ReduceCostToForTurnAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LeadingStrike extends CustomCard {
    public static final String ID = "ReplayTheSpireMod:LeadingStrike";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int ATTACK_UP = 0;
    private static final int REFUND_AMT = 1;
    private static final int REFUND_UP = -1;

    public LeadingStrike() {
        super(ID, LeadingStrike.NAME, "replay/images/cards/leadingStrike.png", COST, LeadingStrike.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.baseDamage = LeadingStrike.ATTACK_DMG;
        this.baseMagicNumber = REFUND_AMT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RefundNextCardPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ReduceCostToForTurnAction(this.magicNumber, false, AbstractCard.CardType.ATTACK));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LeadingStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (ATTACK_UP > 0) {
                this.upgradeDamage(ATTACK_UP);
            }
            this.upgradeMagicNumber(REFUND_UP);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = LeadingStrike.cardStrings.NAME;
        DESCRIPTION = LeadingStrike.cardStrings.DESCRIPTION;
    }
}
