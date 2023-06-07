package com.arango.auction.repository;

import com.arango.auction.jooq.Tables;
import com.arango.auction.jooq.tables.records.ItemsRecord;
import com.arango.auction.jooq.tables.records.UsersRecord;
import com.arango.auction.model.Item;
import com.arango.auction.model.User;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final com.arango.auction.jooq.tables.Users USERS = Tables.USERS.as("us");

    @Autowired
    private DSLContext dslContext;

    public User insert(User user) {
        UsersRecord record = dslContext.newRecord(USERS);
        record.setName(user.getName());
        record.setEmail(user.getEmail());
        record.insert();
        return toUser(record);
    }

    public Optional<User> findById(Long id) {
        UsersRecord record = dslContext.selectFrom(USERS)
                .where(USERS.USER_ID.eq(id))
                .fetchOne();
        if (record != null) {
            return Optional.of(toUser(record));
        }
        return Optional.empty();
    }

    public void deleteById(Long userId) {
        dslContext.deleteFrom(USERS)
                .where(USERS.USER_ID.eq(userId))
                .execute();
    }

    public List<User> findAll() {
        return dslContext.selectFrom(USERS)
                .fetch()
                .map(this::toUser);
    }

    private User toUser(UsersRecord re) {
        return User.builder()
                .userId(re.getUserId())
                .name(re.getName())
                .email(re.getEmail())
                .build();
    }

}