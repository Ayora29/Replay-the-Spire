package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReflectionPower;
import replayTheSpire.ReplayTheSpireMod;

public class DivineShield extends CustomCard {
    public static final String ID = "Replay:DivineShield";
    private static final CardStrings cardStrings;

    public DivineShield() {
        super(ID, DivineShield.cardStrings.NAME, "replay/images/cards/divine_shield.png", 1, DivineShield.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 7;
        this.block = this.baseBlock;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (AbstractDungeon.player.currentBlock == 0 && ReplayTheSpireMod.shieldingAmount(p) < 1) {
            this.addToBot(new ReplayGainShieldingAction(p, p, this.block));
        } else {
            this.addToBot(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DivineShield();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
