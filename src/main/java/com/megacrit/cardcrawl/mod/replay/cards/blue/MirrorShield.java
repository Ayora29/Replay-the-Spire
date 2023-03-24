package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.ReflectionPower;

public class MirrorShield extends CustomCard {
    public static final String ID = "Mirror Shield";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public MirrorShield() {
        super("Mirror Shield", MirrorShield.NAME, "replay/images/cards/mirrorShield.png", MirrorShield.COST, MirrorShield.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 2;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = 5;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new CrystalOrb()));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MirrorShield();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(3);
            //this.upgradeBaseCost(MirrorShield.COST - 1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Mirror Shield");
        NAME = MirrorShield.cardStrings.NAME;
        DESCRIPTION = MirrorShield.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = MirrorShield.cardStrings.UPGRADE_DESCRIPTION;
    }
}
