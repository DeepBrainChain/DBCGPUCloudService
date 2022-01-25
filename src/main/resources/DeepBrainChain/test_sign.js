import { ApiPromise, WsProvider } from '@polkadot/api'
import { Keyring } from "@polkadot/keyring";
import { u8aToHex } from '@polkadot/util';
import { cryptoWaitReady, randomAsU8a, signatureVerify } from '@polkadot/util-crypto'
import minimist from "minimist";
import fs from 'fs'

const keyring = new Keyring({type: 'sr25519'})

const args = minimist(process.argv.slice(2), { string:['key'] })
// var json = fs.readFileSync('demo.json')

const CreateSignature = async () => {
  let nonce = randomWord();
  // let jsonStr4 = JSON.parse(json.toString())
  await cryptoWaitReady();
  // let signUrl = keyring.addFromJson(jsonStr4);
  // signUrl.unlock(password)
  let accountFromKeyring = await keyring.addFromUri(args["key"]);
  const signature = accountFromKeyring.sign(nonce);
  console.log(
    // 'nonce ---- ' + nonce ,
    // 'signature ---- ' + u8aToHex(signature));
  `{"nonce":"${nonce}","signature":"${u8aToHex(signature)}"}`)
  // Verify(nonce, signature)
}


// const Verify = async (msg, sign, wallet='5HL92dTnQrZSJZy7ckDVYVt9mMX3NsjShWsYDquB3eB3yb5R', ver = 'Verify1') => {
//   await cryptoWaitReady();
//   console.log(signatureVerify( msg, sign, wallet ), ver );
// }
CreateSignature()


function randomWord() {
  let str = "",
  arr = [
  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
  'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','0', '1', '2', '3', '4', '5', '6', '7', '8', '9',];
  
  for (let i = 0; i < 55; i++) {
    let pos = Math.round(Math.random() * (arr.length - 1));
    str += arr[pos];
  }
  return str;
}