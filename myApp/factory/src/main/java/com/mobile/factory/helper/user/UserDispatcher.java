package com.mobile.factory.helper.user;

import android.text.TextUtils;

import com.mobile.factory.helper.DbHelper;
import com.mobile.factory.model.db.entity.User;
import com.mobile.factory.model.db.identity.UserIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserDispatcher implements UserCenter {
    private static UserCenter instance;
    // Single-threaded pool; process the messages of the cards one by one for processing
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static UserCenter instance() {
        if (instance == null) {
            synchronized (UserDispatcher.class) {
                if (instance == null)
                    instance = new UserDispatcher();
            }
        }
        return instance;
    }

    @Override
    public void dispatch(UserIdentity... cards) {
        if (cards == null || cards.length == 0)
            return;

        // put in the single-threaded pool
        executor.execute(new UserCardHandler(cards));
    }

    /**
     * The run method is triggered when the thread is scheduled
     */
    private class UserCardHandler implements Runnable {
        private final UserIdentity[] cards;

        UserCardHandler(UserIdentity[] cards) {
            this.cards = cards;
        }

        @Override
        public void run() {
            // Triggered when a single thread is scheduled
            List<User> users = new ArrayList<>();
            for (UserIdentity card : cards) {
                // Perform filtering operations
                if (card == null || TextUtils.isEmpty(card.getId()))
                    continue;
                // Add operation
                users.add(card.build());
            }

            // Perform database storage and distribute notifications, asynchronous operations
            DbHelper.save(User.class, users.toArray(new User[0]));
        }
    }
}
