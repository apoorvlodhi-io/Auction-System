package com.arango.auction.repository;


import com.arango.auction.constants.AuctionStatus;
import com.arango.auction.jooq.Tables;
import com.arango.auction.jooq.tables.Items;
import com.arango.auction.jooq.tables.records.AuctionRecord;
import com.arango.auction.jooq.tables.records.ItemsRecord;
import com.arango.auction.model.Auction;
import com.arango.auction.model.Item;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public class ItemRepository{
    private static final com.arango.auction.jooq.tables.Items ITEMS = Tables.ITEMS.as("it");

    @Autowired
    private DSLContext dslContext;

    public Item insert(Item item) {
        ItemsRecord record = dslContext.newRecord(ITEMS);
        record.setItemName(item.getItemName());
        record.insert();
        return toItem(record);
    }

    public void updateItem(Long itemId, String itemName) {
        dslContext.update(ITEMS)
                .set(ITEMS.ITEM_NAME, itemName)
                .where(ITEMS.ITEM_ID.eq(itemId))
                .execute();
    }

    public Optional<Item> findById(Long id) {
        ItemsRecord record = dslContext.selectFrom(ITEMS)
                .where(ITEMS.ITEM_ID.eq(id))
                .fetchOne();
        if (record != null) {
            return Optional.of(toItem(record));
        }
        return Optional.empty();
    }

    public void deleteById(Long itemId) {
        dslContext.deleteFrom(ITEMS)
                .where(ITEMS.ITEM_ID.eq(itemId))
                .execute();
    }

    public List<Item> findAll() {
        return dslContext.selectFrom(ITEMS)
                .fetch()
                .map(record -> toItem(record));
    }

    private Item toItem(ItemsRecord re) {
        return Item.builder()
                .itemId(re.getItemId())
                .itemName(re.getItemName())
                .build();
    }


}
