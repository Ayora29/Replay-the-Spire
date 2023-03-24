package com.megacrit.cardcrawl.mod.replay.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import replayTheSpire.ReplayAbstractRelic;

import java.util.ArrayList;

public class BottledEggs extends ReplayAbstractRelic {
    public static final String ID = "bottled_eggs";
    private boolean cardSelected;
    public String card;

    public BottledEggs() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
        this.cardSelected = true;
        this.card = null;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck), 1, this.DESCRIPTIONS[1], false, false, false, false);
    }


    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
            ArrayList<AbstractCard> upgradedCards = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c != null && c.cardID.equals(this.card) && c.canUpgrade()) {
                    c.upgrade();
                    upgradedCards.add(c);
                }
            }
            for (int i = 0; i < upgradedCards.size(); i++) {
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(upgradedCards.get(i).makeStatEquivalentCopy(), Settings.WIDTH / 2.0f - (AbstractCard.IMG_WIDTH / 2.0f) * (upgradedCards.size() - 1) + AbstractCard.IMG_WIDTH * i - ((upgradedCards.size() - 1) * -20.0f + i * 40.0f) * Settings.scale, Settings.HEIGHT / 2.0f));
            }
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card, "y") + this.DESCRIPTIONS[3];
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
    }

    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card, "y") + this.DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void onObtainCard(final AbstractCard c) {
        if (c.cardID.equals(this.card) && c.canUpgrade()) {
            c.upgrade();
            this.flash();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledEggs();
    }

    public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
            final BottledEggs relic = (BottledEggs) AbstractDungeon.player.getRelic(ID);
            config.setString("bottledEggs", relic.card);
        } else {
            config.remove("bottledEggs");
        }
    }

    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player.hasRelic(ID) && config.has("bottledEggs")) {
            final BottledEggs relic = (BottledEggs) AbstractDungeon.player.getRelic(ID);
            relic.card = config.getString("bottledEggs");
        }
    }

    public static void clear() {
    }

}