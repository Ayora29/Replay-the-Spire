package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class PoisonedStrike
        extends CustomCard {
    public static final String ID = "Poisoned Strike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Poisoned Strike");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public PoisonedStrike() {
        super("Poisoned Strike", NAME, "replay/images/cards/poisonedstrike.png", 0, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCard.CardColor.COLORLESS, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.damage), this.damage, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    public AbstractCard makeCopy() {
        return new PoisonedStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }
}