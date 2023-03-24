package com.megacrit.cardcrawl.mod.replay.cards.blue;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Claw;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class ReplayGash extends CustomCard implements StartupCard {
    public static final String ID = "ReplayGash";
    private static final CardStrings cardStrings;
    public static final String NAME;
    public static final String DESCRIPTION;
    private static final int COST = 2;

    public ReplayGash() {
        super(ID, NAME, "replay/images/cards/claw_rake.png", COST, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 2;
        this.isMultiDamage = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ReplayGash();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("ReplayGash");
        NAME = ReplayGash.cardStrings.NAME;
        DESCRIPTION = ReplayGash.cardStrings.DESCRIPTION;
    }

    @Override
    public boolean atBattleStartPreDraw() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID == Claw.ID) {
                return false;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Claw(), 1, true, true));
        return true;
    }
}