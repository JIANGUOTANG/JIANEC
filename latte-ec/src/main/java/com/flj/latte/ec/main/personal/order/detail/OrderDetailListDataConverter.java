package com.flj.latte.ec.main.personal.order.detail;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flj.latte.ec.main.personal.order.OrderItemFields;
import com.flj.latte.ec.main.personal.order.OrderListItemType;
import com.flj.latte.ui.recycler.DataConverter;
import com.flj.latte.ui.recycler.MultipleFields;
import com.flj.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by 傅令杰
 */

public class OrderDetailListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("image");
            final String name = data.getString("name");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final String count = data.getString("count");


            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.IMAGE_URL, thumb)
                    .setField(MultipleFields.TITLE, name)
                    .setField(OrderItemFields.PRICE, price)
                    .setField(OrderItemDetailFields.COUNT,count)
                    .build();

            ENTITIES.add(entity);
        }

        return ENTITIES;
    }
}
