package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.LifeLinkPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LifeLink
        extends CustomCard {
    public static final String ID = "Life Link";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Life Link");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int POOL = 1;
    private final int BASE_STR = 2;

    public LifeLink() {
        super("Life Link", NAME, "replay/images/cards/lifeBind.png", 1, DESCRIPTION, AbstractCard.CardType.POWER, AbstractCard.CardColor.RED, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);

        //this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LifeLinkPower(p, -1, true), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new LifeLinkPower(m, 1, false), 1));
    }

    public AbstractCard makeCopy() {
        return new LifeLink();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}