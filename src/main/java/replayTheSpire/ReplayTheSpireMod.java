package replayTheSpire;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.mod.replay.cards.blue.*;
import com.megacrit.cardcrawl.mod.replay.cards.colorless.*;
import com.megacrit.cardcrawl.mod.replay.cards.curses.CommonCold;
import com.megacrit.cardcrawl.mod.replay.cards.curses.LoomingEvil;
import com.megacrit.cardcrawl.mod.replay.cards.curses.Voices;
import com.megacrit.cardcrawl.mod.replay.cards.green.*;
import com.megacrit.cardcrawl.mod.replay.cards.purple.*;
import com.megacrit.cardcrawl.mod.replay.cards.red.*;
import com.megacrit.cardcrawl.mod.replay.events.shrines.TrappedChest;
import com.megacrit.cardcrawl.mod.replay.events.thebottom.Stuck;
import com.megacrit.cardcrawl.mod.replay.events.thecity.GremboTheGreat;
import com.megacrit.cardcrawl.mod.replay.events.thecity.ReplayMapScoutEvent;
import com.megacrit.cardcrawl.mod.replay.modifiers.ALWAYSwhaleModifier;
import com.megacrit.cardcrawl.mod.replay.modifiers.ChaoticModifier;
import com.megacrit.cardcrawl.mod.replay.modifiers.LibraryLooterModifier;
import com.megacrit.cardcrawl.mod.replay.potions.*;
import com.megacrit.cardcrawl.mod.replay.powers.RidThyselfOfStatusCardsPower;
import com.megacrit.cardcrawl.mod.replay.powers.TheWorksPower;
import com.megacrit.cardcrawl.mod.replay.relics.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import replayTheSpire.patches.CreatureHealthPatches;
import replayTheSpire.variables.MagicArithmatic;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpireInitializer
public class ReplayTheSpireMod implements PostInitializeSubscriber,
        EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, PostDrawSubscriber, PotionGetSubscriber, PostUpdateSubscriber, StartGameSubscriber, EditKeywordsSubscriber, AddCustomModeModsSubscriber {

    public static final Logger logger = LogManager.getLogger(ReplayTheSpireMod.class.getName());
    public static TextureAtlas powerAtlas;

    public static final ArrayList<ReplayUnlockAchieve> unlockAchievements = new ArrayList<>();

    public enum PotionRarity {
        COMMON, UNCOMMON, RARE, ULTRA, SPECIAL, SHOP;

        PotionRarity() {
        }
    }

    public static EnumMap<ReplayTheSpireMod.PotionRarity, ArrayList<String>> potionsByRarity = new EnumMap<>(ReplayTheSpireMod.PotionRarity.class);

    public static boolean renderFishFG = false;
    public static TextureAtlas fishAtlas;
    public static TextureAtlas.AtlasRegion fishFG;
    public static Texture forestBG;
    public static Texture shieldingIcon;
    public static Texture bonfireIcon;
    public static Texture bonfireBG;
    public static Texture portalIcon;
    public static Texture portalBG;
    public static Texture multitaskButton;
    public static Texture mineButton;
    public static Texture polymerizeButton;
    public static Texture exploreButton;
    public static Texture rebottleButton;
    public static Texture burnButton;
    public static Texture brewButton;
    public static int playerShielding = 0;
    public static ArrayList<Integer> monsterShielding = new ArrayList<>();
    public static boolean monstersUsingShielding = false;
    public static boolean noSkipRewardsRoom;
    public static boolean foundmod_stslib = false;

    public ReplayTheSpireMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        logger.info("========================= ReplayTheSpireMod INIT =========================");

        ReplayTheSpireMod.initAchievementUnlocks();

        new ReplayTheSpireMod();

        foundmod_stslib = checkForMod("com.evacipated.cardcrawl.mod.stslib.StSLib");

        logger.info("================================================================");
    }

    public static void initAchievementUnlocks() {
        ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("abe_win", "Big Fish in a Small Pond", "Defeat Captain Abe", "Pondfish Scales"));
        ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("abe_perfect", "The Worst Pirate I've Ever Heard Of", "Defeat Captain Abe without his Deadweight power ever triggering", "Abe's Treasure"));
        ReplayTheSpireMod.unlockAchievements.add(new ReplayUnlockAchieve("complete_eye", "Baffling", "Complete a run with Snecko Eye", "Snecko Heart"));
    }

    public static boolean checkForMod(final String classPath) {
        try {
            Class.forName(classPath);
            ReplayTheSpireMod.logger.info("Found mod: " + classPath);
            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError ex) {
            ReplayTheSpireMod.logger.info("Could not find mod: " + classPath);
            return false;
        }
    }

    public static int shieldingAmount(AbstractCreature creature) {
        if (creature == null) {
            return 0;
        }
        return CreatureHealthPatches.ReplayCreatureFields.shielding.get(creature);
    }

    public static void addShielding(AbstractCreature creature, int amt) {
        if (creature == null) {
            return;
        }
        CreatureHealthPatches.ReplayCreatureFields.shielding.set(creature, CreatureHealthPatches.ReplayCreatureFields.shielding.get(creature) + amt);

    }

    public static void clearShielding(AbstractCreature creature) {
        if (creature == null) {
            return;
        }
        CreatureHealthPatches.ReplayCreatureFields.shielding.set(creature, 0);
    }

    public static void clearShielding() {
        if (AbstractDungeon.player != null)
            CreatureHealthPatches.ReplayCreatureFields.shielding.set(AbstractDungeon.player, 0);
        ReplayTheSpireMod.playerShielding = 0;
        ReplayTheSpireMod.monsterShielding = new ArrayList<>();
        ReplayTheSpireMod.monstersUsingShielding = false;
    }

    public static boolean BypassStupidBasemodRelicRenaming_hasRelic(String targetID) {
        if (AbstractDungeon.player == null) {
            return false;
        }
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                return true;
            }
        }
        return false;
    }

    public static boolean BypassStupidBasemodRelicRenaming_loseRelic(String targetID) {
        if (!ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic(targetID)) {
            return false;
        }
        AbstractRelic toRemove = null;
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                r.onUnequip();
                toRemove = r;
            }
        }
        if (toRemove == null) {
            logger.info("WHY WAS RELIC: " + AbstractDungeon.player.name + " NOT FOUND???");
            return false;
        }
        AbstractDungeon.player.relics.remove(toRemove);
        AbstractDungeon.player.reorganizeRelics();
        return true;
    }

    public static AbstractRelic BypassStupidBasemodRelicRenaming_getRelic(String targetID) {
        if (AbstractDungeon.player == null) {
            return null;
        }
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            String unren = r.relicId.substring(r.relicId.lastIndexOf(":") + 1);
            if (r.relicId.equals(targetID) || unren.equals(targetID)) {
                return r;
            }
        }
        return null;
    }

    public static boolean BypassStupidBasemodRelicRenaming_flashRelic(String targetID) {
        AbstractRelic r = ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_getRelic(targetID);
        if (r == null) {
            return false;
        }
        r.flash();
        return true;
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player.hasRelic(Thesaurus.ID)) Thesaurus.exchangeRelics();
    }


    public static void addPotionToSet(final Class potionClass, final Color liquidColor, final Color hybridColor, final Color spotsColor, final String potionID, final ReplayTheSpireMod.PotionRarity potionRarity) {
        BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);
        ReplayTheSpireMod.potionsByRarity.get(potionRarity).add(potionID);
    }

    @Override
    public void receivePostInitialize() {
        ReplayTheSpireMod.powerAtlas = new TextureAtlas("replay/images/powers/replayPowers.atlas");
        ReplayTheSpireMod.forestBG = ImageMaster.loadImage("replay/images/monsters/fadingForest/fadingForest_bg.png");
        ReplayTheSpireMod.shieldingIcon = ImageMaster.loadImage("replay/images/ui/replay/shielding.png");
        ReplayTheSpireMod.bonfireIcon = ImageMaster.loadImage("replay/images/ui/map/replay_bonfire.png");
        ReplayTheSpireMod.bonfireBG = ImageMaster.loadImage("replay/images/ui/map/replay_bonfireOutline.png");
        ReplayTheSpireMod.portalIcon = ImageMaster.loadImage("replay/images/ui/map/replay_portal.png");
        ReplayTheSpireMod.portalBG = ImageMaster.loadImage("replay/images/ui/map/replay_portalOutline.png");
        ReplayTheSpireMod.mineButton = ImageMaster.loadImage("replay/images/ui/campfire/replay/mine.png");
        ReplayTheSpireMod.polymerizeButton = ImageMaster.loadImage("replay/images/ui/campfire/replay/polymerize.png");
        ReplayTheSpireMod.multitaskButton = ImageMaster.loadImage("replay/images/ui/campfire/replay/multitask.png");
        ReplayTheSpireMod.exploreButton = ImageMaster.loadImage("replay/images/ui/campfire/replay/explore.png");
        ReplayTheSpireMod.rebottleButton = ImageMaster.loadImage("replay/images/ui/campfire/replay/rebottle.png");
        ReplayTheSpireMod.burnButton = ImageMaster.loadImage("replay/images/ui/campfire/ritual.png");
        ReplayTheSpireMod.brewButton = ImageMaster.loadImage("replay/images/ui/campfire/brew.png");
        ReplayTheSpireMod.renderFishFG = false;
        ReplayTheSpireMod.fishAtlas = new TextureAtlas(Gdx.files.internal("replay/images/monsters/city/fishfight.atlas"));
        ReplayTheSpireMod.fishFG = ReplayTheSpireMod.fishAtlas.findRegion("mod/fg");

        logger.info("Events");
        BaseMod.addEvent(Stuck.ID, Stuck.class, "Exordium");
        BaseMod.addEvent(ReplayMapScoutEvent.ID, ReplayMapScoutEvent.class, "TheCity");
        BaseMod.addEvent(TrappedChest.ID, TrappedChest.class);
        if (Loader.isModLoaded("Friendly_Minions_0987678")) {
            BaseMod.addEvent(GremboTheGreat.ID, GremboTheGreat.class, "TheCity");
        }

        logger.info("begin editting potions");
        ReplayTheSpireMod.potionsByRarity = new EnumMap<>(ReplayTheSpireMod.PotionRarity.class);
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.COMMON, new ArrayList<>());
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.UNCOMMON, new ArrayList<>());
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.RARE, new ArrayList<>());
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.ULTRA, new ArrayList<>());
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.SPECIAL, new ArrayList<>());
        ReplayTheSpireMod.potionsByRarity.put(ReplayTheSpireMod.PotionRarity.SHOP, new ArrayList<>());

        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Ancient Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Block Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Dexterity Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Energy Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Explosive Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Fire Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Strength Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Regen Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Swift Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Poison Potion");
        ReplayTheSpireMod.potionsByRarity.get(ReplayTheSpireMod.PotionRarity.COMMON).add("Weak Potion");

        ReplayTheSpireMod.addPotionToSet(
                ElixirPotion.class,
                Color.GOLD.cpy(),
                null,
                Color.DARK_GRAY.cpy(),
                "Elixir",
                ReplayTheSpireMod.PotionRarity.SHOP
        );

        ReplayTheSpireMod.addPotionToSet(
                CursedPotion.class,
                Color.DARK_GRAY.cpy(),
                null,
                Color.RED.cpy(),
                "Cursed Concoction",
                ReplayTheSpireMod.PotionRarity.ULTRA
        );
        ReplayTheSpireMod.addPotionToSet(
                DoomPotion.class,
                Color.valueOf("0d429dff"),
                Color.DARK_GRAY.cpy(),
                null,
                "Doom Potion",
                ReplayTheSpireMod.PotionRarity.ULTRA
        );
        ReplayTheSpireMod.addPotionToSet(
                AdrenalinePotion.class,
                Color.ORANGE.cpy(),
                null,
                null,
                "Adrenaline Potion",
                ReplayTheSpireMod.PotionRarity.COMMON
        );
        ReplayTheSpireMod.addPotionToSet(
                VenomPotion.class,
                Color.OLIVE.cpy(),
                null,
                Color.CHARTREUSE.cpy(),
                "Venom Potion",
                ReplayTheSpireMod.PotionRarity.UNCOMMON
        );
        ReplayTheSpireMod.addPotionToSet(
                ReflectiveCoating.class,
                Color.LIGHT_GRAY.cpy(),
                Color.WHITE.cpy(),
                null,
                ReflectiveCoating.POTION_ID,
                ReplayTheSpireMod.PotionRarity.UNCOMMON
        );
        ReplayTheSpireMod.addPotionToSet(
                LanguidPotion.class,
                Color.DARK_GRAY.cpy(),
                null,
                null,
                LanguidPotion.POTION_ID,
                ReplayTheSpireMod.PotionRarity.UNCOMMON
        );
        ReplayTheSpireMod.addPotionToSet(
                DeathPotion.class,
                Color.DARK_GRAY.cpy(),
                Color.FIREBRICK.cpy(),
                Color.CORAL.cpy(),
                "Death Potion",
                ReplayTheSpireMod.PotionRarity.RARE
        );
        ReplayTheSpireMod.addPotionToSet(
                MilkshakePotion.class,
                Color.LIGHT_GRAY.cpy(),
                Color.WHITE.cpy(),
                null,
                "Milkshake",
                ReplayTheSpireMod.PotionRarity.RARE
        );
        if (foundmod_stslib) {
            ReplayTheSpireMod.addPotionToSet(
                    LifebloodPotion.class,
                    Color.BLUE.cpy(),
                    null,
                    null,
                    LifebloodPotion.POTION_ID,
                    ReplayTheSpireMod.PotionRarity.UNCOMMON
            );
            ReplayTheSpireMod.addPotionToSet(
                    FlashbangPotion.class,
                    Color.YELLOW.cpy(),
                    null,
                    null,
                    "Flashbang",
                    ReplayTheSpireMod.PotionRarity.RARE
            );
        } else {
            ReplayTheSpireMod.addPotionToSet(
                    ShieldPotion.class,
                    Color.ROYAL.cpy(),
                    null,
                    null,
                    ShieldPotion.POTION_ID,
                    ReplayTheSpireMod.PotionRarity.UNCOMMON
            );
        }
        logger.info("end editting potions");
        logger.info("end post init");
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;

        ReplayTheSpireMod.receiveEditUnlocks();
    }

    @Override
    public void receiveEditKeywords() {
        final Gson gson = new Gson();
        String jsonPath = "replay/localization/";

        final String json = Gdx.files.internal(jsonPath + "ReplayKeywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        final Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (final Keyword keyword : keywords) {
                logger.info("Adding Keyword - " + keyword.PROPER_NAME + " | " + keyword.NAMES[0]);
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
        logger.info("begin editting relics");
        ReplayTheSpireMod.receiveEditUnlocks();
        
        // Add relics
        BaseMod.addRelic(new AntivirusSoftware(), RelicType.BLUE);
        BaseMod.addRelic(new SolarPanel(), RelicType.BLUE);
        BaseMod.addRelic(new Carrot(), RelicType.BLUE);
        BaseMod.addRelic(new Geode(), RelicType.BLUE);
        BaseMod.addRelic(new RaidersMask(), RelicType.BLUE);
        BaseMod.addRelic(new FrozenProgram(), RelicType.BLUE);

        BaseMod.addRelic(new ByrdSkull(), RelicType.GREEN);
        BaseMod.addRelic(new SneckoScales(), RelicType.GREEN);
        BaseMod.addRelic(new VampiricSpirits(), RelicType.GREEN);

        BaseMod.addRelic(new ElectricBlood(), RelicType.RED);
        BaseMod.addRelic(new OozeArmor(), RelicType.RED);
        BaseMod.addRelic(new Spearhead(), RelicType.RED);
        BaseMod.addRelic(new AnotherSword(), RelicType.RED);
        BaseMod.addRelic(new KingOfHearts(), RelicType.RED);

        BaseMod.addRelic(new Accelerometer(), RelicType.SHARED);
        BaseMod.addRelic(new AncientBell(), RelicType.SHARED);
        BaseMod.addRelic(new Arrowhead(), RelicType.SHARED);
        BaseMod.addRelic(new Baseball(), RelicType.SHARED);
        BaseMod.addRelic(new BottledEggs(), RelicType.SHARED);
        BaseMod.addRelic(new BottledFlurry(), RelicType.SHARED);
        BaseMod.addRelic(new BottledSteam(), RelicType.SHARED);
        BaseMod.addRelic(new BottledSnecko(), RelicType.SHARED);
        BaseMod.addRelic(new ChewingGum(), RelicType.SHARED);
        BaseMod.addRelic(new CursedCoin(), RelicType.SHARED);
        BaseMod.addRelic(new DivineProtection(), RelicType.SHARED);
        BaseMod.addRelic(new DimensionalGlitch(), RelicType.SHARED);
        BaseMod.addRelic(new Durian(), RelicType.SHARED);
        BaseMod.addRelic(new Funnel(), RelicType.SHARED);
        BaseMod.addRelic(new GoldenEgg(), RelicType.SHARED);
        BaseMod.addRelic(new GremlinFood(), RelicType.SHARED);
        BaseMod.addRelic(new GuideBook(), RelicType.SHARED);
        BaseMod.addRelic(new IronHammer(), RelicType.SHARED);
        BaseMod.addRelic(new Kintsugi(), RelicType.SHARED);
        BaseMod.addRelic(new LightBulb(), RelicType.SHARED);
        BaseMod.addRelic(new Mirror(), RelicType.SHARED);
        BaseMod.addRelic(new Kaleidoscope(), RelicType.SHARED);
        BaseMod.addRelic(new Multitool(), RelicType.SHARED);
        BaseMod.addRelic(new MysteryMachine(), RelicType.SHARED);
        BaseMod.addRelic(new HamsterWheel(), RelicType.SHARED);
        BaseMod.addRelic(new SweetFlower(), RelicType.SHARED);
        BaseMod.addRelic(new PondfishScales(), RelicType.SHARED);
        BaseMod.addRelic(new PetGhost(), RelicType.SHARED);
        BaseMod.addRelic(new QuantumEgg(), RelicType.SHARED);
        BaseMod.addRelic(new RingingSoul(), RelicType.SHARED);
        BaseMod.addRelic(new ShopPack(), RelicType.SHARED);
        BaseMod.addRelic(new SimpleRune(), RelicType.SHARED);
        BaseMod.addRelic(new SizzlingBlood(), RelicType.SHARED);
        BaseMod.addRelic(new SnackPack(), RelicType.SHARED);
        BaseMod.addRelic(new BlueDoll(), RelicType.SHARED);
        BaseMod.addRelic(new TigerMarble(), RelicType.SHARED);
        BaseMod.addRelic(new WantedPoster(), RelicType.SHARED);
        BaseMod.addRelic(new WaspNest(), RelicType.SHARED);
        BaseMod.addRelic(new RedPill(), RelicType.SHARED);
        BaseMod.addRelic(new BluePill(), RelicType.SHARED);
        BaseMod.addRelic(new FunFungus(), RelicType.SHARED);
        BaseMod.addRelic(new Thesaurus(), RelicType.SHARED);

        logger.info("done editting relics");
    }

    public static HashMap<AbstractCard.CardColor, ArrayList<AbstractCard>> REPLAY_CARDS = new HashMap<>();


    public static void AddAndUnlockCard(AbstractCard c) {
        if (!REPLAY_CARDS.containsKey(c.color)) {
            REPLAY_CARDS.put(c.color, new ArrayList<>());
        }
        REPLAY_CARDS.get(c.color).add(c);
        BaseMod.addCard(c);
        UnlockTracker.unlockCard(c.cardID);
    }


    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicMinusOne());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicPlusOne());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicPlusTwo());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicMinusTwo());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicTimesTwo());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicDivTwo());
        BaseMod.addDynamicVariable(new MagicArithmatic.MagicDivTwoUp());
        logger.info("[RtS] begin editting cards");
        logger.info("adding cards for Ironclad...");
        AddAndUnlockCard(new Abandon());
        AddAndUnlockCard(new Hemogenesis());
        AddAndUnlockCard(new LifeLink());
        AddAndUnlockCard(new LimbFromLimb());
        AddAndUnlockCard(new Massacre());
        AddAndUnlockCard(new RunThrough());
        AddAndUnlockCard(new DefyDeath());
        AddAndUnlockCard(new DemonicInfusion());
        AddAndUnlockCard(new Eclipse());
        AddAndUnlockCard(new StrikeFromHell());
        AddAndUnlockCard(new LeadingStrike());
        AddAndUnlockCard(new ReplayReversal());
        AddAndUnlockCard(new ReplayStacked());
        AddAndUnlockCard(new MuscleTraining());
        AddAndUnlockCard(new CorrosionCurse());
        AddAndUnlockCard(new UndeathsTouch());
        AddAndUnlockCard(new ManyHands());
        logger.info("adding cards for Silent...");
        AddAndUnlockCard(new AtomBomb());
        AddAndUnlockCard(new FluidMovement());
        AddAndUnlockCard(new HiddenBlade());
        AddAndUnlockCard(new SneakUp());
        AddAndUnlockCard(new ScrapShanks());
        AddAndUnlockCard(new TheWorks());
        AddAndUnlockCard(new FromAllSides());
        AddAndUnlockCard(new ExploitWeakness());
        AddAndUnlockCard(new ShivToss());
        AddAndUnlockCard(new SpeedTraining());
        AddAndUnlockCard(new TripWire());
        AddAndUnlockCard(new BagOfTricks());
        AddAndUnlockCard(new PoisonSmokescreen());
        logger.info("adding cards for Defect...");
        AddAndUnlockCard(new com.megacrit.cardcrawl.mod.replay.cards.blue.PanicButton());
        AddAndUnlockCard(new MirrorShield());
        AddAndUnlockCard(new BasicCrystalCard());
        AddAndUnlockCard(new ReplayRepulse());
        AddAndUnlockCard(new ReplayGoodbyeWorld());
        AddAndUnlockCard(new ReplayGash());
        AddAndUnlockCard(new ReplayOmegaCannon());
        AddAndUnlockCard(new ReplayRNGCard());
        AddAndUnlockCard(new FIFOQueue());
        AddAndUnlockCard(new ReplaySort());
        AddAndUnlockCard(new SystemScan());
        AddAndUnlockCard(new SolidLightProjector());
        AddAndUnlockCard(new CalculationTraining());
        AddAndUnlockCard(new TimeBomb());
        AddAndUnlockCard(new Crystallizer());
        AddAndUnlockCard(new ReroutePower());
        AddAndUnlockCard(new FabricateWheel());
        logger.info("adding cards for Watcher...");
        AddAndUnlockCard(new AllIn());
        AddAndUnlockCard(new AllOut());
        AddAndUnlockCard(new Anticipation());
        AddAndUnlockCard(new AtTheReady());
        AddAndUnlockCard(new Battleflow());
        AddAndUnlockCard(new BluntJabs());
        AddAndUnlockCard(new Cycle());
        AddAndUnlockCard(new DivineShield());
        AddAndUnlockCard(new Hum());
        AddAndUnlockCard(new LoomingThreat());
        AddAndUnlockCard(new ManipulateFuture());
        AddAndUnlockCard(new Vibes());
        logger.info("adding colorless cards...");
        AddAndUnlockCard(new PrivateReserves());
        AddAndUnlockCard(new Specialist());
        AddAndUnlockCard(new AwakenedRitual());
        AddAndUnlockCard(new SurveyOptions());
        AddAndUnlockCard(new MidasTouch());
        AddAndUnlockCard(new ReplayBrewmasterCard());
        AddAndUnlockCard(new Sssssssssstrike());
        AddAndUnlockCard(new Necrogeddon());
        AddAndUnlockCard(new BasicMightCard());
        AddAndUnlockCard(new Parry());
        AddAndUnlockCard(new PoisonedStrike());

        logger.info("adding curses...");
        AddAndUnlockCard(new CommonCold());
        AddAndUnlockCard(new Voices());
        AddAndUnlockCard(new LoomingEvil());
        logger.info("adding unobtainable cards...");
        AddAndUnlockCard(new GhostDefend());
        AddAndUnlockCard(new GhostSwipe());
        AddAndUnlockCard(new GhostFetch());
        AddAndUnlockCard(new RitualComponent());
        AddAndUnlockCard(new DarkEchoRitualCard());
        AddAndUnlockCard(new Survivalism());

        logger.info("done editting cards");
        //loadSettingsData();
    }

    public static String SINGLE_SUFFIX = "";
    public static String MULTI_SUFFIX = "s";
    public static String LOC_FULLSTOP = ".";

    private static void doStringOverrides(final Type stringType, final String jsonString) {
        HashMap<Type, String> typeMaps = ReflectionHacks.getPrivateStatic(BaseMod.class, "typeMaps");
        HashMap<Type, Type> typeTokens = ReflectionHacks.getPrivateStatic(BaseMod.class, "typeTokens");

        final String typeMap = typeMaps.get(stringType);
        final Type typeToken = typeTokens.get(stringType);
        final Map<String, String> localizationStrings = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, typeMap);
        final Map<String, String> map = new HashMap(BaseMod.gson.fromJson(jsonString, typeToken));
        localizationStrings.putAll(map);
        ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
    }

    @Override
    public void receiveEditStrings() {
        logger.info("begin editting strings");
        String jsonPath = "replay/localization/";
        // RelicStrings
        String relicStrings = Gdx.files.internal(jsonPath + "ReplayRelicStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        relicStrings = Gdx.files.internal(jsonPath + "ReplayOverrideRelicStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        ReplayTheSpireMod.doStringOverrides(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal(jsonPath + "ReplayCardStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        // PowerStrings
        String powerStrings = Gdx.files.internal(jsonPath + "ReplayPowerStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        // EventStrings
        String eventStrings = Gdx.files.internal(jsonPath + "ReplayEventStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
        String eventStringsF = Gdx.files.internal(jsonPath + "FadingForestEventStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(EventStrings.class, eventStringsF);
        // PotionStrings
        String potionStrings = Gdx.files.internal(jsonPath + "ReplayPotionStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
        // UIStrings
        String uiStrings = Gdx.files.internal(jsonPath + "ReplayUIStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
        String mStrings = Gdx.files.internal(jsonPath + "ReplayMonsterStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class, mStrings);
        String rmStrings = Gdx.files.internal(jsonPath + "ReplayRunModStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RunModStrings.class, rmStrings);
        // OrbStrings
        String orbStrings = Gdx.files.internal(jsonPath + "ReplayOrbStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
        // StanceStrings
        String stanceStrings = Gdx.files.internal(jsonPath + "ReplayStanceStrings.json").readString(
                String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(StanceStrings.class, stanceStrings);
        logger.info("done editting strings");
    }

    @Override
    public void receivePostDraw(AbstractCard c) {
        if (AbstractDungeon.player.hasPower(TheWorksPower.POWER_ID)) {
            AbstractDungeon.player.getPower(TheWorksPower.POWER_ID).onSpecificTrigger();
        }
        if (AbstractDungeon.player.hasPower(RidThyselfOfStatusCardsPower.POWER_ID) && c.type == AbstractCard.CardType.STATUS) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
            AbstractDungeon.player.getPower(RidThyselfOfStatusCardsPower.POWER_ID).onSpecificTrigger();
        }
    }

    @Override
    public void receiveCustomModeMods(List<CustomMod> l) {
        l.add(new CustomMod(LibraryLooterModifier.ID, "b", true));
        l.add(new CustomMod(ChaoticModifier.ID, "b", true));
        l.add(new CustomMod(ALWAYSwhaleModifier.ID, "b", true));
    }


    @Override
    public void receivePotionGet(final AbstractPotion p0) {
        if (ReplayTheSpireMod.BypassStupidBasemodRelicRenaming_hasRelic("Chameleon Ring")) {
            switch (p0.ID) {
                case "AttackPotion":
                    p0.description = "Add a random Upgraded Attack card to your hand, it costs #b0 this turn.";
                    p0.tips = new ArrayList<>();
                    p0.tips.add(new PowerTip(p0.name, p0.description));
                    break;
                case "SkillPotion":
                    p0.description = "Add a random Upgraded Skill card to your hand, it costs #b0 this turn.";
                    p0.tips = new ArrayList<>();
                    p0.tips.add(new PowerTip(p0.name, p0.description));
                    break;
                case "PowerPotion":
                    p0.description = "Add a random Upgraded Power card to your hand, it costs #b0 this turn.";
                    p0.tips = new ArrayList<>();
                    p0.tips.add(new PowerTip(p0.name, p0.description));
                    break;
            }
        }

    }

    public static void loadUnlocksData() {
        logger.info("ReplayTheSpireMod | Loading Unlock Data...");
        try {
            SpireConfig config = new SpireConfig("ReplayTheSpireMod", "replayUnlocksData");
            config.load();
            for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
                ach.isUnlocked = config.getBool(ach.achievementId);
            }
        } catch (IOException e) {
            logger.error("Failed to load ReplayTheSpireMod unlocks data!");
            e.printStackTrace();
        }
    }

    public static void receiveEditUnlocks() {
        loadUnlocksData();
        for (ReplayUnlockAchieve ach : ReplayTheSpireMod.unlockAchievements) {
            UnlockTracker.unlockReqs.computeIfAbsent(ach.relicId, k -> ach.desc + " to unlock.");
            if (ach.isUnlocked) {
                while (UnlockTracker.isRelicLocked(ach.relicId)) {
                    UnlockTracker.lockedRelics.remove(ach.relicId);
                }
            } else {
                if (!UnlockTracker.isRelicLocked(ach.relicId)) {
                    UnlockTracker.lockedRelics.add(ach.relicId);
                }
            }
        }
    }


    public static void loadRunData() {
        ReplayTheSpireMod.logger.info("Loading Save Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            BottledFlurry.load(config);
            BottledSteam.load(config);
            BottledEggs.load(config);
            Baseball.load(config);
            ReplayMapScoutEvent.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveRunData() {
        ReplayTheSpireMod.logger.info("Saving Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            BottledFlurry.save(config);
            BottledSteam.save(config);
            BottledEggs.save(config);
            Baseball.save(config);
            ReplayMapScoutEvent.save(config);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearRunData() {
        ReplayTheSpireMod.logger.info("Clearing Saved Data");
        try {
            final SpireConfig config = new SpireConfig("ReplayTheSpireMod", "SaveData");
            config.clear();
            config.save();
            BottledFlurry.clear();
            BottledSteam.clear();
            BottledEggs.clear();
            Baseball.clear();
            ReplayMapScoutEvent.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveStartGame() {
        ReplayMapScoutEvent.bannedBoss = "none";
        loadRunData();
    }

}