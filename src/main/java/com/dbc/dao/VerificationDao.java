package com.dbc.dao;

import com.dbc.entity.Verification;

import java.util.List;

public interface VerificationDao {

            public void addVerification(Verification verification);

            Verification getVer (int id);
}
