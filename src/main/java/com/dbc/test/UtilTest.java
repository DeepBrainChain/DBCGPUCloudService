package com.dbc.test;

import io.emeraldpay.polkaj.schnorrkel.Schnorrkel;
import io.emeraldpay.polkaj.schnorrkel.SchnorrkelException;
import io.emeraldpay.polkaj.ss58.SS58Type;
import io.emeraldpay.polkaj.types.*;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Random;
import java.util.Scanner;

public class UtilTest {

            public static void createNewKey() throws SchnorrkelException {                          // 创建新密钥
                System.out.println("Generate a new Root Key + derive a `demo` address from that key");
                System.out.println("");
                Schnorrkel.KeyPair rootKey = Schnorrkel.getInstance().generateKeyPair();
                System.out.println("  Root Key: " + Hex.encodeHexString(rootKey.getSecretKey()));
                System.out.println("");
                Schnorrkel.KeyPair key = Schnorrkel.getInstance().deriveKeyPair(rootKey, Schnorrkel.ChainCode.from("demo".getBytes()));

                Address address = Address.from("5GNFCp5MuxScP8jdPcvRm64moHPChQi17cLtJSSDauuVeqAY");
                System.out.println("   Address: " + address);
                System.out.println("Public Key: " + Hex.encodeHexString(key.getPublicKey()));
                System.out.println("Secret Key: " + Hex.encodeHexString(key.getSecretKey()));
            }

            public static void derive() throws SchnorrkelException {
                System.out.println("Derive new Address from existing");
                System.out.println("");
                Address address = Address.from("5GNFCp5MuxScP8jdPcvRm64moHPChQi17cLtJSSDauuVeqAY");
                System.out.println("  Address (curr): " + address);
                Schnorrkel.PublicKey publicKey = new Schnorrkel.PublicKey(address.getPubkey());

                Schnorrkel.PublicKey derived = Schnorrkel.getInstance().derivePublicKeySoft(publicKey, Schnorrkel.ChainCode.from("demo".getBytes()));
                Address anotherAddress = new Address(SS58Type.Network.CANARY, derived.getPublicKey());
                System.out.println("   Address (new): " + anotherAddress);
                System.out.println("Public Key (new): " + Hex.encodeHexString(derived.getPublicKey()));
            }

            /**
             *         密钥 = e5be9a5092b81bca64be81d212e7f2f9eba183bb7a90954f7b76361f6edb5c0a
             *          0x   4d078fb748f7062b52f0f192838e9b1e9daed20e632c90acc554fbfa818efcfa
             * @throws DecoderException
             * @throws SchnorrkelException
             */

            public static String sign(String key) throws DecoderException, SchnorrkelException {                // 签名
                Schnorrkel.KeyPair aliceKey = Schnorrkel.getInstance().generateKeyPairFromSeed(
                        Hex.decodeHex(key)
                );
                byte[] message = Hex.decodeHex(
                        getItemID()
                );
                byte[] signature = Schnorrkel.getInstance().sign(message, aliceKey);
//                System.out.println("Signature: " + Hex.encodeHexString(signature));
                return  Hex.encodeHexString(signature);
            }

            public static boolean verifyKey(String key) throws DecoderException, SchnorrkelException {            // 用密钥验证
                Schnorrkel.KeyPair aliceKey = Schnorrkel.getInstance().generateKeyPairFromSeed(
                        Hex.decodeHex(key)
                );
                byte[] message = Hex.decodeHex(
                        "8a3476995d076964c8c8517c8a1a504da91dc2b16ab36fb04ca22734e572619be108ee699592ccb9b1344835352e42c9"
                );
                byte[] signature = Hex.decodeHex("e2525d278d3d4b32ca3372b6d2c32c1405f641a5c2309a94da416c32359ac76e485c6baa69baa66def1c3a46c76fc38fb58d88ee0312bfb0bc135b851df0928f");

                // We have both Private Key and Public Key for Alice here, but let's pretend we have only Public Key:
                Schnorrkel.PublicKey signer = new Schnorrkel.PublicKey(aliceKey.getPublicKey());
                // Verify the signature
                boolean valid = Schnorrkel.getInstance().verify(signature, message, signer);
//                System.out.println("Valid: " + valid + " for pubkey " + Hex.encodeHexString(aliceKey.getPublicKey()));
                return valid;
            }

            public static boolean verifyWithAddress(String wallet) throws DecoderException, SchnorrkelException {       // 用钱包地址验证
                Address alice = Address.from(wallet);
                byte[] message = Hex.decodeHex(
                        "8a3476995d076964c8c8517c8a1a504da91dc2b16ab36fb04ca22734e572619be108ee699592ccb9b1344835352e42c9"
                );
                byte[] signature = Hex.decodeHex("e2525d278d3d4b32ca3372b6d2c32c1405f641a5c2309a94da416c32359ac76e485c6baa69baa66def1c3a46c76fc38fb58d88ee0312bfb0bc135b851df0928f");

                // Public key actually is an Address. So lets say you have only address of the signer, like:
                Schnorrkel.PublicKey signer = new Schnorrkel.PublicKey(alice.getPubkey());
                // Verify the signature
                boolean valid = Schnorrkel.getInstance().verify(signature, message, signer);
//                System.out.println("Valid: " + valid + " for address " + alice);
                return  valid;
            }
            // 5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY
            @SuppressWarnings("unchecked")

            /**
             * 生成随机数当作getItemID
             * n ： 需要的长度
             * @return
             */
            private static String getItemID()
            {
                String val = "";
                Random random = new Random();
                for ( int i = 0; i < 56; i++ )
                {
                    String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
                    if ( "char".equalsIgnoreCase( str ) )
                    { // 产生字母
                        int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                        // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                        val += (char) ( nextInt + random.nextInt( 6 ) );
                    }
                    else if ( "num".equalsIgnoreCase( str ) )
                    { // 产生数字
                        val += String.valueOf( random.nextInt( 10 ) );
                    }
                }
                System.out.println(val);
                return val;

            }

            public static void main(String[] args) throws DecoderException, SchnorrkelException {

//                verifyWithAddress();
                createNewKey();
                derive();
                Scanner sc= new Scanner(System.in);
                while(sc.hasNext())
                {
                    System.out.println("signature:" + sign(sc.next()));
                    System.out.println("密钥验证");
                    System.out.println(verifyKey(sc.next()));
                    System.out.println("钱包地址验证");
                    System.out.println(verifyWithAddress(sc.next()));
                }
            }

}
