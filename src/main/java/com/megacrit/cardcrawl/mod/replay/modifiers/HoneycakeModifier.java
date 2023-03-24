package com.megacrit.cardcrawl.mod.replay.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.RunModStrings;

public class HoneycakeModifier extends AbstractDailyMod {
    public static final String ID = "replay:honeycakes";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESC = modStrings.DESCRIPTION;

    public HoneycakeModifier() {
        super(ID, NAME, DESC, null, true);//DESC.replaceAll("{A}", (new HoneyJar()).name).replaceAll("{B}", (new CatFaceCupcake()).name)
        this.img = ImageMaster.loadImage("replay/images/relics/cursedBlood.png");
    }
}