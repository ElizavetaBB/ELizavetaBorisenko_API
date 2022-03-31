package com.epam.tc.hw3.data;

import com.epam.tc.hw3.dto.StickerDTO;
import org.testng.annotations.DataProvider;

public class TrelloDataProvider {

    @DataProvider(name = "Board names")
    public static Object[][] getBoardNames() {
        return new Object[][] {
                {"board"},
                {"board 2"},
                {"new board"},
                {"one more board"}
        };
    }

    @DataProvider(name = "List names")
    public static Object[][] getListNames() {
        return new Object[][] {
                {"list"},
                {"list 2"},
                {"new list"},
                {"one more list"}
        };
    }

    @DataProvider(name = "Card names")
    public static Object[][] getCardNames() {
        return new Object[][] {
                {"card"},
                {"card 2"},
                {"new card"},
                {"one more card"}
        };
    }

    @DataProvider(name = "Stickers")
    public static Object[][] getStickers() {
        return new Object[][] {
                {"card", new StickerDTO("taco-cool", 0, 0, 1),
                        new StickerDTO("taco-love", 40, 20, 2),
                        new StickerDTO("taco-angry", 90, -20, 3)},
                {"new card", new StickerDTO("taco-confused", -50, 100, 1),
                        new StickerDTO("taco-celebrate", -60, 100, 2),
                        new StickerDTO("taco-alert", 100, 100, 3),
                        new StickerDTO("taco-money", 50, 50, 4)}
        };
    }
}
