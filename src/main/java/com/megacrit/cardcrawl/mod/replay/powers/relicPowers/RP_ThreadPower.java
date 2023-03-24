package com.megacrit.cardcrawl.mod.replay.powers.relicPowers;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.actions.*;
import com.megacrit.cardcrawl.mod.replay.actions.common.*;
import com.megacrit.cardcrawl.mod.replay.cards.*;
import com.megacrit.cardcrawl.mod.replay.powers.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;

public class RP_ThreadPower extends AbstractPower
{
    public static final String POWER_ID = "RP_ThreadPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    public RP_ThreadPower(final AbstractCreature owner) {
        this.name = RP_ThreadPower.NAME;
        this.ID = "RP_ThreadPower";
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.img = ImageMaster.loadImage("replay/images/powers/pointyEnd.png");
        //this.loadRegion("platedarmor");
    }
    
    @Override
    public void updateDescription() {
        this.description = RP_ThreadPower.DESCRIPTIONS[0];
    }
    
    @Override
    public void atStartOfTurn() {
        if (this.amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, 5), 5));
			this.amount--;
        }
		this.updateDescription();
    }
    
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("RP_ThreadPower");
        NAME = RP_ThreadPower.powerStrings.NAME;
        DESCRIPTIONS = RP_ThreadPower.powerStrings.DESCRIPTIONS;
    }
}
