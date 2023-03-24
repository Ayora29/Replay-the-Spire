package com.megacrit.cardcrawl.mod.replay.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Hemogenesis extends CustomCard {
    public static final String ID = "Hemogenesis";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 3;
    private int upgradeTick = 2;

    public Hemogenesis() {
        this(0, 2);
    }

    public Hemogenesis(final int upgrades) {
        this(upgrades, 2);
    }

    public Hemogenesis(final int upgrades, final int ticks) {
        super("Hemogenesis", Hemogenesis.NAME, "replay/images/cards/hemogenesis.png", COST, Hemogenesis.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseBlock = 5;
        this.baseMagicNumber = 5;
        this.upgradeTick = ticks;
        this.magicNumber = this.baseMagicNumber;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hemogenesis(this.timesUpgraded, this.upgradeTick);
    }

    @Override
    public void upgrade() {
        this.name = Hemogenesis.NAME;
        boolean isSmithUpgrade = (AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getMonsters().areMonstersBasicallyDead());
        if (isSmithUpgrade) {
            this.upgradeTick = 3;
        }
        this.upgradeDamage(this.upgradeTick);// + this.timesUpgraded
        this.upgradeBlock(this.upgradeTick);// + this.timesUpgraded
        this.upgradeMagicNumber(this.upgradeTick);// + this.timesUpgraded
        //if (!isSmithUpgrade) {
        ++this.timesUpgraded;
        //}
        this.upgraded = true;
        if (this.upgradeTick > 2) {
            this.name += "+";
        }
        if (!isSmithUpgrade) {
            int dispup = this.timesUpgraded;
            if (this.upgradeTick > 2) {
                dispup -= 1;
            }
            this.name += "+" + dispup;
        }
        this.initializeTitle();
    }

    @Override
    public boolean canUpgrade() {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            return true;
        }
        return (this.upgradeTick < 3 && !this.upgraded);
    }

    @Override
    public void tookDamage() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            this.upgrade();
            this.superFlash();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Hemogenesis");
        NAME = Hemogenesis.cardStrings.NAME;
        DESCRIPTION = Hemogenesis.cardStrings.DESCRIPTION;
    }
}
