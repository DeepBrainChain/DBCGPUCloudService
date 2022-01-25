package com.dbc.service.SUBDBC;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.util.Base64;
import com.dbc.dao.DBCWalletDao;
import io.emeraldpay.polkaj.api.StandardCommands;
import io.emeraldpay.polkaj.api.StandardSubscriptions;
import io.emeraldpay.polkaj.apiws.PolkadotWsApi;
import io.emeraldpay.polkaj.scale.ScaleExtract;
import io.emeraldpay.polkaj.scaletypes.AccountInfo;
import io.emeraldpay.polkaj.scaletypes.Metadata;
import io.emeraldpay.polkaj.scaletypes.MetadataReader;
import io.emeraldpay.polkaj.schnorrkel.Schnorrkel;
import io.emeraldpay.polkaj.schnorrkel.SchnorrkelException;
import io.emeraldpay.polkaj.ss58.SS58Codec;
import io.emeraldpay.polkaj.ss58.SS58Type;
import io.emeraldpay.polkaj.tx.AccountRequests;
import io.emeraldpay.polkaj.tx.ExtrinsicContext;
import io.emeraldpay.polkaj.types.Address;
import io.emeraldpay.polkaj.types.ByteData;
import io.emeraldpay.polkaj.types.DotAmount;
import io.emeraldpay.polkaj.types.Hash256;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class SUBDBCWallet {

			@Autowired
			private DBCWalletDao mDBCWalletDao;
			
			private  String getString(ByteBuffer buffer) {

				Charset charset = null;
				CharsetDecoder decoder = null;
				CharBuffer charBuffer = null;
				try {

					charset = Charset.defaultCharset();
					decoder = charset.newDecoder();
					charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
					return charBuffer.toString();

				} catch (Exception ex) {

					ex.printStackTrace();
					return "error";
				}

			}





			public float getBanlance(String dbcwalletapi,String wallet_address) throws Exception{//查询余额数量
				float banlance_float=0;

				

				try (PolkadotWsApi client = PolkadotWsApi.newBuilder().connectTo(dbcwalletapi).build()) {
					log.info("Connected: " + client.connect().get());


					AtomicLong height = new AtomicLong(0);
					CompletableFuture<Long> waitForBlocks = new CompletableFuture<>();
					client.subscribe(
							StandardSubscriptions.getInstance().newHeads()
					).get().handler((event) -> {
						long current = event.getResult().getNumber();
						System.out.println("Current height: " + current);
						if (height.get() == 0) {
							height.set(current);
						} else {
							long blocks = current - height.get();
							if (blocks > 3) {
								waitForBlocks.complete(current);
							}
						}
					});

					Address address = Address.from(wallet_address);
					log.info("Address: " + address);

					AccountInfo balance = AccountRequests.balanceOf(address).execute(client).get();
					if (balance == null) {
						System.out.println("NO BALANCE");
						return 0;
					}

					BigInteger dbc_amount=balance.getData().getFree().getValue().divide(new BigInteger("1000000000000000"));
					banlance_float=dbc_amount.floatValue();
				}
				return banlance_float;
			}

	
}
