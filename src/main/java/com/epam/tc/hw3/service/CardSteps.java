package com.epam.tc.hw3.service;

import com.epam.tc.hw3.dto.CardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.dto.StickerDTO;
import com.epam.tc.hw3.utils.URI;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CardSteps extends CommonService {

    public CardDTO createCard(String name, ListDTO list) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idList", list.getId());
        Response response = makeRequestWithParams(Request.POST, URI.CARDS_END_POINT, params)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();
        return fromResponseToDTO(response, CardDTO.class);
    }

    public CardDTO getCardById(String id) {
        Response response = makeRequest(Request.GET_OK, String.format(URI.USER_CARDS_END_POINT, id));
        return fromResponseToDTO(response, CardDTO.class);
    }

    public Response getCardResponse(String id) {
        return makeRequest(Request.GET, String.format(URI.USER_CARDS_END_POINT, id));
    }

    public Response deleteCard(String id) {
        return makeRequest(Request.DELETE, String.format(URI.USER_CARDS_END_POINT, id));
    }

    public void createStickers(String cardId, StickerDTO... stickers) {
        Map<String, String> params = new HashMap<>();
        for (StickerDTO sticker : stickers) {
            params.put("image", sticker.getImage());
            params.put("top", String.valueOf(sticker.getTop()));
            params.put("left", String.valueOf(sticker.getLeft()));
            params.put("zIndex", String.valueOf(sticker.getZIndex()));
            makeRequestWithParams(Request.POST, String.format(URI.CARD_STICKERS_END_POINT, cardId), params);
        }
    }

    public StickerDTO[] getStickers(String cardId) {
        Response response = makeRequest(Request.GET_OK, String.format(URI.CARD_STICKERS_END_POINT, cardId));
        return fromResponseToDTO(response, StickerDTO[].class);
    }

}
