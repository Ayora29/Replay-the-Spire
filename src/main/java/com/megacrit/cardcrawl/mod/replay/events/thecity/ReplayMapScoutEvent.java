package com.megacrit.cardcrawl.mod.replay.events.thecity;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;

import java.util.ArrayList;

public class ReplayMapScoutEvent extends AbstractImageEvent {
    public static String bannedBoss = "none";
    public static final String ID = "ReplayMapScout";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private CurScreen screen;
    int goldCost;
    private final ArrayList<String> bossIDs;


    public static void save(final SpireConfig config) {
        if (AbstractDungeon.player != null) {
            config.setString("mapScout", bannedBoss);
        } else {
            config.remove("mapScout");
        }
    }

    public static void load(final SpireConfig config) {
        if (AbstractDungeon.player != null && config.has("mapScout")) {
            bannedBoss = config.getString("mapScout");
        }
    }

    public static void clear() {
        bannedBoss = "none lol";
    }

    public ReplayMapScoutEvent() {
        super(NAME, DIALOG_1, null);
        this.goldCost = 60;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldCost += 20;
        }
        this.bossIDs = new ArrayList<>();
        this.bossIDs.add("Awakened One");
        this.bossIDs.add("Donu and Deca");
        this.bossIDs.add("Time Eater");
        this.screen = CurScreen.INTRO;
        this.imageEventText.setDialogOption(OPTIONS[11]);
    }

    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO: {
                this.imageEventText.clearAllDialogs();
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                if (AbstractDungeon.player.gold >= this.goldCost) {
                    this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[3]);
                    this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[4]);
                    this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[5]);
                } else {
                    this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                    this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                    this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                }
                this.imageEventText.setDialogOption(OPTIONS[6]);
                this.screen = CurScreen.CHOICE;
                break;
            }
            case CHOICE: {
                this.screen = CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                if (buttonPressed >= this.bossIDs.size()) {
                    if (buttonPressed == this.bossIDs.size() + 1) {
                        this.screen = CurScreen.ARE_YOU_SURE;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.setDialogOption(OPTIONS[9]);
                        this.imageEventText.setDialogOption(OPTIONS[10]);
                        break;
                    } else {
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                    }
                } else {
                    bannedBoss = this.bossIDs.get(buttonPressed);
                    this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                    AbstractDungeon.player.loseGold(this.goldCost);
                }
                this.imageEventText.setDialogOption(OPTIONS[7]);
                break;
            }
            case ARE_YOU_SURE: {
                switch (buttonPressed) {
                    case 0:
                        this.screen = CurScreen.RESULT;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                        bannedBoss = "you're fucked lol";
                        this.imageEventText.setDialogOption(OPTIONS[7]);
                        break;
                    default:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText("...");
                        if (AbstractDungeon.player.gold >= this.goldCost) {
                            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[3]);
                            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[4]);
                            this.imageEventText.setDialogOption(OPTIONS[2] + this.goldCost + OPTIONS[1] + OPTIONS[5]);
                        } else {
                            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                            this.imageEventText.setDialogOption(OPTIONS[0] + this.goldCost + OPTIONS[1] + "]", true);
                        }
                        this.imageEventText.setDialogOption(OPTIONS[6]);
                        this.screen = CurScreen.CHOICE;
                        break;
                }
                break;
            }
            default: {
                this.openMap();
                break;
            }
        }
    }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
    }

    private enum CurScreen {
        INTRO,
        CHOICE,
        RESULT,
        ARE_YOU_SURE
    }
}