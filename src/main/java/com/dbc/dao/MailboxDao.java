package com.dbc.dao;

import com.dbc.entity.MailboxEntity;

public interface MailboxDao {

            public void insert (MailboxEntity mailboxEntity);

            public String SelectEmil(String wallet);
}
