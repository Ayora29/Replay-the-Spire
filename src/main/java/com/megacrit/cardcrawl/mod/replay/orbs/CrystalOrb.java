package com.megacrit.cardcrawl.mod.replay.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.mod.replay.actions.unique.CrystalOrbUpdateAction;
import com.megacrit.cardcrawl.mod.replay.powers.CrystallizerPower;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;

public class CrystalOrb extends AbstractOrb {
    public static final String ORB_ID = "Crystal";
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
    public static final String[] DESC = orbString.DESCRIPTION;//{"#yPassive: Adjacent orbs have #b+", " #yFocus. NL #yEvoke: If you have less than #b", " orb slots, gain an orb slot. NL #yPassive effect is not affected by other Crystal orbs."};;
    private static final Color validEvokeColor = Color.GREEN.cpy();
    private static final Color invalidEvokeColor = Color.RED.cpy();

    private static Texture img1;
    private static Texture img2;
    private static Texture img3;
    private final boolean hFlip1;
    private final boolean hFlip2;
    private float vfxTimer;
    private final float vfxIntervalMin;
    private final float vfxIntervalMax;

    public CrystalOrb() {
        this.vfxTimer = 1.0f;
        this.vfxIntervalMin = 0.15f;
        this.vfxIntervalMax = 0.8f;
        if (CrystalOrb.img1 == null) {
            CrystalOrb.img1 = ImageMaster.loadImage("replay/images/orbs/replay/crystalRight.png");
            CrystalOrb.img2 = ImageMaster.loadImage("replay/images/orbs/replay/crystalLeft.png");
            CrystalOrb.img3 = ImageMaster.loadImage("replay/images/orbs/replay/crystalMid.png");
        }
        this.hFlip1 = MathUtils.randomBoolean();
        this.hFlip2 = MathUtils.randomBoolean();
        this.ID = ORB_ID;
        //this.img = ImageMaster.ORB_LIGHTNING;
        this.name = CrystalOrb.orbString.NAME;
        this.baseEvokeAmount = 3;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
    }

    @Override
    public void updateDescription() {
        this.applyFocus();
        this.description = CrystalOrb.DESC[0] + this.passiveAmount + CrystalOrb.DESC[1] + this.evokeAmount + CrystalOrb.DESC[2];
    }

    @Override
    public void onEvoke() {
        //AbstractDungeon.actionManager.addToTop(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), null));
        if (AbstractDungeon.player.maxOrbs < this.evokeAmount) {
            AbstractDungeon.actionManager.addToTop(new IncreaseMaxOrbAction(1));
        }
        AbstractDungeon.actionManager.addToTop(new CrystalOrbUpdateAction());
        AbstractDungeon.actionManager.addToBottom(new CrystalOrbUpdateAction());
    }

    @Override
    public void applyFocus() {
        super.applyFocus();
        this.passiveAmount = this.basePassiveAmount;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(CrystallizerPower.POWER_ID)) {
            this.passiveAmount += AbstractDungeon.player.getPower(CrystallizerPower.POWER_ID).amount;
        }
    }

    /*
    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new LightningOrbPassiveAction(new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), this));
    }
    
    @Override
    public void triggerEvokeAnimation() {
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }
    */
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 180.0f;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0f) {
            AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
            }
            this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
        }
    }

    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(CrystalOrb.img1, this.cX - 48.0f + this.bobEffect.y / 4.0f, this.cY - 48.0f + this.bobEffect.y / 4.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(CrystalOrb.img2, this.cX - 48.0f + this.bobEffect.y / 4.0f, this.cY - 48.0f - this.bobEffect.y / 4.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip1, false);
        sb.draw(CrystalOrb.img3, this.cX - 48.0f - this.bobEffect.y / 4.0f, this.cY - 48.0f + this.bobEffect.y / 2.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, this.hFlip2, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void playChannelSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1f);
    }

    @Override
    public AbstractOrb makeCopy() {
        return new CrystalOrb();
    }

    @Override
    protected void renderText(final SpriteBatch sb) {
        if (this.showEvokeValue) {
            if (AbstractDungeon.player.maxOrbs < this.evokeAmount) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, new Color(CrystalOrb.validEvokeColor.r, CrystalOrb.validEvokeColor.g, CrystalOrb.validEvokeColor.b, this.c.a), this.fontScale);
            } else {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.evokeAmount), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, new Color(CrystalOrb.invalidEvokeColor.r, CrystalOrb.invalidEvokeColor.g, CrystalOrb.invalidEvokeColor.b, this.c.a), this.fontScale);
            }
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.passiveAmount), this.cX + AbstractOrb.NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0f + AbstractOrb.NUM_Y_OFFSET, this.c, this.fontScale);
        }
    }
}
