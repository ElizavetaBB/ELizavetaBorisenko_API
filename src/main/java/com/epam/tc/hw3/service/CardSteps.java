package com.epam.tc.hw3.service;

import static com.epam.tc.hw3.utils.URI.CARDS_END_POINT;
import static com.epam.tc.hw3.utils.URI.CARD_STICKERS_END_POINT;

import com.epam.tc.hw3.dto.CardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.dto.StickerDTO;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class CardSteps extends CommonService {

    public CardDTO createCard(String name, ListDTO list) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idList", list.getId());
        Response response = makeRequest(Method.POST, CARDS_END_POINT, params)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, CardDTO.class);
    }

    public CardDTO getCardById(String id) {
        Response response = makeRequest(Method.GET, CARDS_END_POINT + id)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, CardDTO.class);
    }

    public Response getCardResponse(String id) {
        return makeRequest(Method.GET, CARDS_END_POINT + id);
    }

    public Response deleteCard(String id) {
        return makeRequest(Method.DELETE, CARDS_END_POINT + id);
    }

    public void createStickers(String cardId, StickerDTO... stickers) {
        Map<String, String> params = new HashMap<>();
        for (StickerDTO sticker : stickers) {
            params.put("image", sticker.getImage());
            params.put("top", String.valueOf(sticker.getTop()));
            params.put("left", String.valueOf(sticker.getLeft()));
            params.put("zIndex", String.valueOf(sticker.getZIndex()));
            makeRequest(Method.POST, String.format(CARD_STICKERS_END_POINT, cardId), params);
        }
    }

    public StickerDTO[] getStickers(String cardId) {
        Response response = makeRequest(Method.GET, String.format(CARD_STICKERS_END_POINT, cardId))
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, StickerDTO[].class);
    }
}
