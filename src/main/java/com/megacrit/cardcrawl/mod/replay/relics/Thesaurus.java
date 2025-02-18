package com.megacrit.cardcrawl.mod.replay.relics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.mod.replay.screens.RelicSelectScreen;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.ReplayAbstractRelic;

import java.util.ArrayList;

public class Thesaurus extends ReplayAbstractRelic {
    public static final String ID = "thesaurus";

    private final AbstractPlayer p = AbstractDungeon.player;
    private final GameActionManager am = AbstractDungeon.actionManager;
    private boolean relicSelected = true;
    private RelicSelectScreen relicSelectScreen;
    private boolean fakeHover = false;
    private static boolean loseRelic = false;
    private static AbstractRelic relicToRemove;
    private static AbstractRelic relicToGain = null;
    public static boolean relicScreenOpen = false;

    public Thesaurus() {
        super(ID, RelicTier.SHOP, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Thesaurus();
    }

    @Override
    public void onEquip() {
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        openRelicSelect();
        relicScreenOpen = true;
    }

    private void openRelicSelect() {
        relicSelected = false;
        ArrayList<AbstractRelic> relics = new ArrayList<>();
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r.tier != RelicTier.SPECIAL && !r.relicId.equals(ID)) {
                AbstractRelic re = r.makeCopy();
                re.isSeen = true;
                relics.add(re);
            }
        }
        relicSelectScreen = new RelicSelectScreen();
        relicSelectScreen.open(relics);
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        if (!relicSelected && fakeHover) {
            relicSelectScreen.render(sb);
        }
        if (fakeHover) {
            fakeHover = false;
            hb.hovered = false;
        } else {
            super.renderTip(sb);
        }
    }

    @Override
    public void update() {
        super.update();

        if (!relicSelected) {
            if (relicSelectScreen.doneSelecting()) {
                relicSelected = true;
                AbstractRelic relic = relicSelectScreen.getSelectedRelics().get(0);
                relicToRemove = AbstractDungeon.player.getRelic(relic.relicId);
                loseRelic = true;
                switch (relic.tier) {
                    case COMMON:
                        relicToGain = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
                        AbstractDungeon.commonRelicPool.removeIf(id -> id.equals(relicToGain.relicId));
                        break;
                    case UNCOMMON:
                        relicToGain = AbstractDungeon.returnRandomRelic(RelicTier.UNCOMMON);
                        AbstractDungeon.uncommonRelicPool.removeIf(id -> id.equals(relicToGain.relicId));
                        break;
                    case RARE:
                        relicToGain = AbstractDungeon.returnRandomRelic(RelicTier.RARE);
                        AbstractDungeon.rareRelicPool.removeIf(id -> id.equals(relicToGain.relicId));
                        break;
                    case BOSS:
                        relicToGain = AbstractDungeon.returnRandomRelic(RelicTier.BOSS);
                        AbstractDungeon.bossRelicPool.removeIf(id -> id.equals(relicToGain.relicId));
                        break;
                    case SHOP:
                        relicToGain = AbstractDungeon.returnRandomRelic(RelicTier.SHOP);
                        AbstractDungeon.shopRelicPool.removeIf(id -> id.equals(relicToGain.relicId));
                        break;
                    case STARTER:
                        relicToGain = starterRelic();
                }

                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            } else {
                relicSelectScreen.update();
                if (!hb.hovered) {
                    fakeHover = true;
                }
                hb.hovered = true;
            }
        }
    }

    private AbstractRelic starterRelic() {
        AbstractRelic relicToAdd = RelicLibrary.starterList.get(AbstractDungeon.cardRandomRng.random(RelicLibrary.starterList.size() - 1));

        if (relicToAdd.relicId.equals(relicToRemove.relicId)) {
            relicToAdd = starterRelic();
        }

        return relicToAdd;
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);

        if (!relicSelected && !fakeHover) {
            relicSelectScreen.render(sb);
        }
    }

    public static void exchangeRelics() {
        if (loseRelic) {
            AbstractDungeon.player.loseRelic(relicToRemove.relicId);
            relicToGain.instantObtain();
            loseRelic = false;
            relicScreenOpen = false;
        }

    }


}
