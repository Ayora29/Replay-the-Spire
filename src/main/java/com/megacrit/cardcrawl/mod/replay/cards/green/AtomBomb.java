package com.megacrit.cardcrawl.mod.replay.cards.green;

import basemod.abstracts.CustomCard;
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
import com.megacrit.cardcrawl.powers.PoisonPower;

public class AtomBomb extends CustomCard {
    public static final String ID = "Atom Bomb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int DAMAGE = 60;
    private static final int POISON_AMT = 3;
    private static final int COST = 4;
    private static final int POOL = 1;

    public AtomBomb() {
        super("Atom Bomb", AtomBomb.NAME, "replay/images/cards/atomBomb.png", 4, AtomBomb.DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.GREEN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = 60;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer abstractPlayer, final AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new PoisonPower(abstractPlayer, abstractPlayer, this.magicNumber), this.magicNumber));
        for (final AbstractMonster abstractMonster2 : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractMonster2, abstractPlayer, new PoisonPower(abstractMonster2, abstractPlayer, this.magicNumber), this.magicNumber));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new AtomBomb();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(15);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Atom Bomb");
        NAME = AtomBomb.cardStrings.NAME;
        DESCRIPTION = AtomBomb.cardStrings.DESCRIPTION;
    }
}
