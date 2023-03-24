package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.actions.defect.TimeBombAction;
import com.megacrit.cardcrawl.mod.replay.orbs.GlassOrb;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Dark;

public class TimeBomb extends CustomCard {
    public static final String ID = "Time Bomb";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 1;

    public TimeBomb() {
        super("Time Bomb", TimeBomb.NAME, "replay/images/cards/time_bomb.png", TimeBomb.COST, TimeBomb.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, -1), -1));
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new GlassOrb()));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new GlassOrb()));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Dark()));
        AbstractDungeon.actionManager.addToBottom(new TimeBombAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new TimeBomb();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Time Bomb");
        NAME = TimeBomb.cardStrings.NAME;
        DESCRIPTION = TimeBomb.cardStrings.DESCRIPTION;
    }
}