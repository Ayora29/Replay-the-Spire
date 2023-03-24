package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RunThrough extends CustomCard {
    public static final String ID = "Run Through";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int BASE_DRAW = 1;
    private static final int POOL = 1;

    public RunThrough() {
        super("Run Through", RunThrough.NAME, "replay/images/cards/runThrough.png", 1, RunThrough.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = RunThrough.ATTACK_DMG;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(m, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RunThrough();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Run Through");
        NAME = RunThrough.cardStrings.NAME;
        DESCRIPTION = RunThrough.cardStrings.DESCRIPTION;
    }
}
