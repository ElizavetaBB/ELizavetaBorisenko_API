package com.epam.tc.hw3.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.tc.hw3.dto.CardDTO;
import com.epam.tc.hw3.dto.StickerDTO;
import com.epam.tc.hw3.service.CardSteps;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

public class CardAssertions {

    public CardAssertions verifyName(CardDTO card, String expectedName) {
        assertThat(card.getName()).as("Card has an appropriate name : " + expectedName)
                .isEqualTo(expectedName);
        return this;
    }

    public CardAssertions verifyId(CardDTO card, String expectedId) {
        assertThat(card.getId()).as("Card has an appropriate id : " + expectedId).isEqualTo(expectedId);
        return this;
    }

    public CardAssertions verifyListId(CardDTO card, String expectedId) {
        assertThat(card.getIdList()).as("Card has an appropriate boardId : " + expectedId).isEqualTo(expectedId);
        return this;
    }

    public CardAssertions verifyStickers(CardDTO card, StickerDTO... expectedStickers) {
        SoftAssertions softAssertions = new SoftAssertions();
        StickerDTO[] stickers = new CardSteps().getStickers(card.getId());
        for (int i = 0; i < expectedStickers.length; i++) {
            softAssertions.assertThat(stickers[i].getImage()).as("Sticker image")
                    .isEqualTo(expectedStickers[i].getImage());
            softAssertions.assertThat(stickers[i].getLeft()).as("Sticker left position")
                    .isEqualTo(stickers[i].getLeft());
            softAssertions.assertThat(stickers[i].getTop()).as("Sticker right position")
                    .isEqualTo(expectedStickers[i].getTop());
            softAssertions.assertThat(stickers[i].getZIndex()).as("Sticker zIndex")
                    .isEqualTo(expectedStickers[i].getZIndex());
            softAssertions.assertThat(stickers[i].getIdCard()).as("Sticker idCard")
                    .isEqualTo(expectedStickers[i].getIdCard());
        }
        softAssertions.assertAll();
        return this;
    }

    public CardAssertions verifyDeletedCard(Response response) {
        String value = response.then().extract().path("_value");
        assertThat(value).as("Response _value is null").isNull();
        return this;
    }
}
