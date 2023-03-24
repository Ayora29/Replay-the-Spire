package com.megacrit.cardcrawl.mod.replay.rooms;

import coloredmap.ColoredRoom;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.mod.replay.events.TeleportEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import replayTheSpire.ReplayTheSpireMod;

@ColoredRoom
public class TeleportRoom extends AbstractRoom {
    private final EventRoom fakeRoom;
    private MapRoomNode teleDest;

    public TeleportRoom() {
        super();
        ReplayTheSpireMod.portalIcon = ImageMaster.loadImage("replay/images/ui/map/replay_portal.png");
        ReplayTheSpireMod.portalBG = ImageMaster.loadImage("replay/images/ui/map/replay_portalOutline.png");
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "PTL";
        this.mapImg = ReplayTheSpireMod.portalIcon;
        this.mapImgOutline = ReplayTheSpireMod.portalBG;
        fakeRoom = new EventRoom();
    }

    public TeleportRoom(MapRoomNode teleDest) {
        super();
        this.teleDest = teleDest;
        this.phase = RoomPhase.EVENT;
        this.mapSymbol = "PTL";
        this.mapImg = ReplayTheSpireMod.portalIcon;
        this.mapImgOutline = ReplayTheSpireMod.portalBG;
        fakeRoom = new EventRoom();
    }

    @Override
    public void onPlayerEntry() {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        event = fakeRoom.event = new TeleportEvent(teleDest);
        fakeRoom.event.onEnterRoom();
    }

    @Override
    public AbstractCard.CardRarity getCardRarity(int roll) {
        return fakeRoom.getCardRarity(roll);
    }

    @Override
    public void update() {
        fakeRoom.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        fakeRoom.render(sb);
        fakeRoom.renderEventTexts(sb);
    }

    @Override
    public void renderAboveTopPanel(SpriteBatch sb) {
        fakeRoom.renderAboveTopPanel(sb);
    }
}