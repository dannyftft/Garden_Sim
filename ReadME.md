# Garden Sim

A Java desktop game where you buy seeds, grow plants in real time, and sell them for profit. No win condition, just a garden.

## How to Play

Click an empty bed to open the shop and buy a seed. Once planted, the bed image updates as the plant grows through its stages. Click a bed with a plant to see its details and sell it.

Sell price goes up with each growth stage, so waiting pays off. Leave it too long though and it withers, dropping below what the seed cost you.

Hit **Save** in the top bar whenever you want. Your garden is restored exactly as you left it next time you load.

## Shop

**Standard Seeds** are always available. **Special Offers** shows two rotating rare plants that swap out every hour.

Prices vary slightly per plant due to a small random offset rolled at planting. What you see in the shop is the base range.

## Plants

### Standard Seeds

| Plant | Seed Cost | Base Sell | Grow Time | Stages |
|---|---|---|---|---|
| Radish | $10 | $35 | 10 min | 4 |
| Wheat | $15 | $50 | 15 min | 4 |
| Carrot | $20 | $65 | 20 min | 4 |
| Potato | $35 | $110 | 35 min | 4 |
| Beetroot | $45 | $140 | 50 min | 4 |
| Kale | $50 | $155 | 1 hr | 4 |
| Cabbage | $60 | $185 | 1.5 hr | 4 |
| Cauliflower | $90 | $270 | 2 hr | 4 |
| Pumpkin | $130 | $390 | 4 hr | 4 |
| Sunflower | $160 | $480 | 6 hr | 4 |

### Special Offers

| Plant | Seed Cost | Base Sell | Grow Time | Stages |
|---|---|---|---|---|
| Pansy | $500 | $1,500 | 6 hr | 3 |
| Tulip | $650 | $1,950 | 7 hr | 3 |
| Rose | $850 | $2,500 | 8 hr | 3 |
| Lavender | $1,100 | $3,200 | 10 hr | 3 |

## Debug Console

Click the invisible button in the very top left corner of the green bar, to the left of the balance text, to open it.

| Control | What it does |
|---|---|
| Bed spinner | Picks which bed to target |
| +$1000 | Adds $1,000 to your balance |
| Advance Stage | Advances the plant in the selected bed by one stage |
| Force Wither | Instantly withers the plant in the selected bed |

## Project Structure

```
src/
  Main.java                   
  bed/
    GardenBed.java            Represents one bed slot and the plant inside it
  data/
    BedSaveData.java          Per-bed data written to the save file
    SaveData.java             Save file structure
    SaveManager.java          Reading and writing the save file
  game/
    EconomyConfig.java        Price jitter and growth style multipliers
    Game.java                 Core logic: buying, planting, selling, debug tools
    GameData.java             Loads plants.json and handles plant lookup
    Player.java               Tracks the player's money
    PlayerConfig.java         Starting money and bed count
  plant/
    Plant.java                Base class with shared plant fields
    PlantData.java            Raw data container filled from plants.json
    RegularPlant.java         Growth stages, timing, and price calculation
  ui/
    BedButton.java            Clickable bed that shows the current plant stage image
    DebugWindow.java          The hidden developer tools panel
    InfoWindow.java           Shows plant details and the sell button
    MainWindow.java           The main garden grid
    MenuScreen.java           Title screen with New Game, Load Game, and Quit
Resources/
  plants.json                 All plant definitions and economy settings
  images/                     Plant stage sprites and empty image
```