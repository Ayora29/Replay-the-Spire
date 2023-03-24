package com.megacrit.cardcrawl.mod.replay.cards.colorless;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.SpecialistPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

public class Specialist extends CustomCard {
    public static final String ID = "Specialist";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    private static final int POOL = 1;
    private static final int COST = 0;
    private static final int DMG = 2;
    private static final int UPG_DMG = 1;

    public Specialist() {
        super("Specialist", Specialist.NAME, "replay/images/cards/specialist.png", Specialist.COST, Specialist.DESCRIPTION, CardType.POWER, CardColor.COLORLESS, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = Specialist.DMG;
        this.magicNumber = this.baseMagicNumber;
        this.buildDescription();
    }

    private void buildDescription() {
        this.rawDescription = "";
        if (AbstractDungeon.player == null || AbstractDungeon.player.masterMaxOrbs > 0) {
            this.rawDescription = EXTENDED_DESCRIPTION[0];
        }
        if (this.upgraded) {
            this.rawDescription += UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription += DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (p.maxOrbs > 0 || p.masterMaxOrbs > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpecialistPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Specialist();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(Specialist.UPG_DMG);
            this.isInnate = true;
            this.buildDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Specialist");
        NAME = Specialist.cardStrings.NAME;
        DESCRIPTION = Specialist.cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = Specialist.cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = Specialist.cardStrings.EXTENDED_DESCRIPTION;
    }
}
