package com.megacrit.cardcrawl.mod.replay.cards.purple;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.mod.replay.powers.LoomingThreatPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LoomingThreat extends CustomCard {
    public static final String ID = "Replay:LoomingThreat";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public LoomingThreat() {
        super(ID, LoomingThreat.NAME, "replay/images/cards/Looming_threat.png", 1, LoomingThreat.DESCRIPTION, CardType.POWER, CardColor.PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoomingThreatPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LoomingThreat();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = LoomingThreat.cardStrings.NAME;
        DESCRIPTION = LoomingThreat.cardStrings.DESCRIPTION;
    }
}
