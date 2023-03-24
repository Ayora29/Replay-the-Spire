package com.megacrit.cardcrawl.mod.replay.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Omamori;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import replayTheSpire.ReplayAbstractRelic;

public class Kintsugi extends ReplayAbstractRelic {
    public static final String ID = "kintsugi";
    private boolean cardsSelected;
    private boolean cursesSelected;
    private boolean cursesOpened;
    public static final int REMOVECOUNT = 5;
    public static final int CURSECOUNT = 2;
    public static final int CURSEOPTIONS = 5;

    public Kintsugi() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
        this.cardsSelected = true;
        this.cursesSelected = true;
        this.cursesOpened = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + REMOVECOUNT + this.DESCRIPTIONS[1] + CURSECOUNT + this.DESCRIPTIONS[2];
    }

    @Override
    public void onEquip() {
        this.cardsSelected = false;
        this.cursesSelected = false;
        this.cursesOpened = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), REMOVECOUNT, this.DESCRIPTIONS[3], false, false, false, true);
    }

    @Override
    public void update() {
        super.update();

        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == REMOVECOUNT) {
            this.cardsSelected = true;
            //AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2.0f - 30.0f * Settings.scale - AbstractCard.IMG_WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            //AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(AbstractDungeon.gridSelectScreen.selectedCards.get(1), Settings.WIDTH / 2.0f + 30.0f * Settings.scale + AbstractCard.IMG_WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
            for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (card.type != AbstractCard.CardType.CURSE) {
                    AbstractDungeon.player.masterDeck.removeCard(card);
                }
                AbstractDungeon.transformCard(card, false, AbstractDungeon.miscRng);
            }
            //AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        } else if (this.cardsSelected && !this.cursesSelected) {
            if (!this.cursesOpened) {
                this.cursesOpened = true;
                final CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (int i = 0; i < CURSEOPTIONS; i++) {
                    AbstractCard bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
                    while (bowlCurse.rarity == AbstractCard.CardRarity.SPECIAL) {
                        bowlCurse = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE).makeCopy();
                    }
                    UnlockTracker.markCardAsSeen(bowlCurse.cardID);
                    tmp.addToTop(bowlCurse);
                }
                if (AbstractDungeon.isScreenUp) {
                    AbstractDungeon.dynamicBanner.hide();
                    AbstractDungeon.overlayMenu.cancelButton.hide();
                    AbstractDungeon.previousScreen = AbstractDungeon.screen;
                }
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
                AbstractDungeon.gridSelectScreen.open(tmp, CURSECOUNT, this.DESCRIPTIONS[3], false, false, false, false);

            } else if (AbstractDungeon.gridSelectScreen.selectedCards.size() == CURSECOUNT) {
                this.cursesSelected = true;
                for (final AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (AbstractDungeon.player.hasRelic("Omamori") && AbstractDungeon.player.getRelic("Omamori").counter != 0) {
                        ((Omamori) AbstractDungeon.player.getRelic("Omamori")).use();
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card.makeCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                    }
                }
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Kintsugi();
    }
}
