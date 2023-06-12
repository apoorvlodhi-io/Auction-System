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
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.arango.auction.jooq.tables.Items.ITEMS;



@Repository
public class ItemRepository{

    @Autowired
    private DSLContext dslContext;

    public Item insert(Item item) {
        ItemsRecord record = dslContext.newRecord(ITEMS);
        record.setItemName(item.getItemName());
        ItemsRecord savedRecord = dslContext.insertInto(ITEMS)
                .set(record)
                .returning(ITEMS.asterisk()).fetchOne();
        return toItem(Objects.requireNonNull(savedRecord));
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
