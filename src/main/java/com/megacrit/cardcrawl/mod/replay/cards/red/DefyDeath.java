package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.DefyDeathUpdateAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DefyDeath extends CustomCard {
    public static final String ID = "Defy Death";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 5;
    private static final int DEFENSE_GAINED = 10;

    public DefyDeath() {
        super("Defy Death", DefyDeath.NAME, "replay/images/cards/defyDeath.png", DefyDeath.COST, DefyDeath.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = DefyDeath.DEFENSE_GAINED;
        GraveField.grave.set(this, true);
    }

    public void updateDynamicCost() {
        this.setCostForTurn(this.cost - AbstractDungeon.player.exhaustPile.group.size());
    }

    @Override
    public void applyPowers() {
        this.updateDynamicCost();
        super.applyPowers();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.updateDynamicCost();
    }

    @Override
    public void triggerOnOtherCardPlayed(final AbstractCard c) {
        AbstractDungeon.actionManager.addToBottom(new DefyDeathUpdateAction(this));
		/*
		this.updateDynamicCost();
		if (c.exhaust)
			this.setCostForTurn(this.cost - AbstractDungeon.player.exhaustPile.group.size() + 1);
		*/
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
        //this.modifyCostForCombat(1);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DefyDeath();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defy Death");
        NAME = DefyDeath.cardStrings.NAME;
        DESCRIPTION = DefyDeath.cardStrings.DESCRIPTION;
    }
}
