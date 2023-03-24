package com.megacrit.cardcrawl.mod.replay.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.mod.replay.orbs.CrystalOrb;
import com.megacrit.cardcrawl.mod.replay.orbs.GlassOrb;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import replayTheSpire.ReplayTheSpireMod;

public class CrystallizerPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = "Crystallizer";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int orbAmount;

    public CrystallizerPower(final AbstractCreature owner, final int amount) {
        this(owner, amount, 1);
    }

    public CrystallizerPower(final AbstractCreature owner, final int amount, final int orbAmount) {
        this.name = CrystallizerPower.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.orbAmount = orbAmount;
        this.updateDescription();
        //this.img = ImageMaster.loadImage("replay/images/powers/xanatos.png");
        this.loadRegion("crystallizer");
    }

    @Override
    protected void loadRegion(final String fileName) {
        this.region48 = ReplayTheSpireMod.powerAtlas.findRegion("48/" + fileName);
        this.region128 = ReplayTheSpireMod.powerAtlas.findRegion("128/" + fileName);
    }

    @Override
    public void updateDescription() {
        this.description = CrystallizerPower.DESCRIPTIONS[0] + this.amount + CrystallizerPower.DESCRIPTIONS[1] + this.orbAmount + CrystallizerPower.DESCRIPTIONS[2];
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.orbAmount += 1;
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        int emptyslots = 0;
        int filledslots = 0;
        for (final AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof EmptyOrbSlot || o instanceof GlassOrb) {
                emptyslots++;
            } else {
                filledslots++;
            }
        }
        if (emptyslots >= filledslots) {
            this.flash();
            for (int i = 0; i < this.orbAmount; i++) {
                AbstractDungeon.actionManager.addToTop(new ChannelAction(new CrystalOrb()));
            }
        }
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Crystallizer");
        NAME = CrystallizerPower.powerStrings.NAME;
        DESCRIPTIONS = CrystallizerPower.powerStrings.DESCRIPTIONS;
    }

    @Override
    public AbstractPower makeCopy() {
        return new CrystallizerPower(owner, amount, orbAmount);
    }
}
