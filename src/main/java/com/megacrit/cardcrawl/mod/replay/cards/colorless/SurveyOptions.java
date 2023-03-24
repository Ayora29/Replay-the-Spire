package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.RefundDrawAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SurveyOptions extends CustomCard {
    public static final String ID = "Survey Options";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = -1;
    private static final int BASE_BLOCK = 7;

    public SurveyOptions() {
        super("Survey Options", SurveyOptions.NAME, "replay/images/cards/Survey_Options.png", -1, SurveyOptions.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        RefundVariable.setBaseValue(this, 1);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RefundDrawAction(p, 0, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SurveyOptions();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            RefundVariable.upgrade(this, 1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Survey Options");
        NAME = SurveyOptions.cardStrings.NAME;
        DESCRIPTION = SurveyOptions.cardStrings.DESCRIPTION;
    }
}
