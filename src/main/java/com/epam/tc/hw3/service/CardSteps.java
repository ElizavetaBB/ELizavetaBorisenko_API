package com.epam.tc.hw3.service;

import com.epam.tc.hw3.dto.CardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.dto.StickerDTO;
import com.epam.tc.hw3.utils.URI;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CardSteps extends CommonService {

    public CardDTO createCard(String name, ListDTO list) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idList", list.getId());
        Response response = post(URI.CARDS_END_POINT, params)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();
        return new Gson().fromJson(response.getBody().asString(), CardDTO.class);
    }

    public CardDTO getCardById(String id) {
        return new Gson().fromJson(getWithOKCode(String.format(URI.USER_CARDS_END_POINT, id)).getBody().asString(), CardDTO.class);
    }

    public Response getCardResponse(String id) {
        return get(String.format(URI.USER_CARDS_END_POINT, id));
    }

    public Response deleteCard(String id) {
        return delete(String.format(URI.USER_CARDS_END_POINT, id));
    }

    public void createStickers(String cardId, StickerDTO... stickers) {
        Map<String, String> params = new HashMap<>();
        for (StickerDTO sticker : stickers) {
            params.put("image", sticker.getImage());
            params.put("top", String.valueOf(sticker.getTop()));
            params.put("left", String.valueOf(sticker.getLeft()));
            params.put("zIndex", String.valueOf(sticker.getZIndex()));
            post(String.format(URI.CARD_STICKERS_END_POINT, cardId), params);
        }
    }

    public StickerDTO[] getStickers(String cardId) {
        return new Gson().fromJson(getWithOKCode(String.format(URI.CARD_STICKERS_END_POINT, cardId)).getBody().asString(),
                StickerDTO[].class);
    }

}
