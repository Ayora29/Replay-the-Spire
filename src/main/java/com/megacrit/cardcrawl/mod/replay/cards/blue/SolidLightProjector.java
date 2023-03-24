package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.common.ReplayGainShieldingAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SolidLightProjector extends CustomCard {
    public static final String ID = "ReplaySolidLightProjector";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int COST = 1;

    public SolidLightProjector() {
        super(ID, SolidLightProjector.NAME, "replay/images/cards/solid_light_projector.png", COST, SolidLightProjector.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 2;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        ExhaustiveVariable.setBaseValue(this, 2);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReplayGainShieldingAction(p, p, this.block * (AbstractDungeon.player.discardPile.size() / this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SolidLightProjector();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = SolidLightProjector.DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0] + (this.block * (AbstractDungeon.player.discardPile.size() / this.magicNumber)) + EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = SolidLightProjector.cardStrings.NAME;
        DESCRIPTION = SolidLightProjector.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = SolidLightProjector.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = SolidLightProjector.cardStrings.EXTENDED_DESCRIPTION;
    }
}
