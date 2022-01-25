// refer: https://polkadot.js.org/docs/substrate/extrinsics

// 使用： node sign_txs.js
// --port="wss://infotest.dbcwallet.io"
// --module=onlineProfile --func=bondMachine
// --key="sample split bamboo west visual approve brain fox arch impact relief smile"

// Import the API & Provider and some utility functions
import { ApiPromise, WsProvider } from "@polkadot/api";
import { Keyring } from "@polkadot/keyring";
import fs from "fs";
import minimist from "minimist";

async function main() {

  // 读取参数
  const args = minimist(process.argv.slice(2), {string: ["key"]});


  // 构建连接
  const wsProvider = new WsProvider(args["port"]);
  const data = fs.readFileSync(args["js"]);
  const type_json = JSON.parse(data);
  // Create the API and wait until ready
  const api = await ApiPromise.create({
    provider: wsProvider,
    types: type_json,
  });

  // 读取密钥 type: sr25519, ssFormat: 42 (defaults)
  const keyring = new Keyring({ type: "sr25519" });
  // const accountFromKeyring = keyring.createFromUri(args["key"]); // 从助记词生成账户
  const accountFromKeyring = keyring.addFromUri(args["key"]); // 从私钥生成账户对

  // 获取账户nonce
  const { nonce } = await api.query.system.account(accountFromKeyring.address);

  // 创建方法map
  var funcMap = {};
  // funcMap["onlineProfile"] = {};
  // funcMap["dbcTesting"] = {};
  // funcMap["leaseCommittee"] = {};
  // funcMap["maintainCommittee"] = {};
  // funcMap["onlineProfile"]["bondMachine"] = api.tx.onlineProfile.bondMachine;
  // funcMap["onlineProfile"]["fulfillBond"] = api.tx.onlineProfile.fulfillBond;
  // funcMap["onlineProfile"]["stakerChangeMachineInfo"] = api.tx.onlineProfile.stakerChangeMachineInfo;
  // funcMap["onlineProfile"]["stakerChangeImagesName"] = api.tx.onlineProfile.stakerChangeImagesName;
  // funcMap["dbcTesting"]["sayHello"] = api.tx.dbcTesting.sayHello;
  // funcMap["leaseCommittee"]["addConfirmHash"] = api.tx.leaseCommittee.addConfirmHash;
  // funcMap["leaseCommittee"]["submitConfirmRaw"] = api.tx.leaseCommittee.submitConfirmRaw;
  // funcMap["maintainCommittee"]["bookOne"] = api.tx.maintainCommittee.bookOne;
  // funcMap["maintainCommittee"]["stakerReportOffline"] = api.tx.maintainCommittee.stakerReportOffline;
  // funcMap["maintainCommittee"]["stakerReportOnline"] = api.tx.maintainCommittee.stakerReportOnline;
  // funcMap["maintainCommittee"]["committeeAddPubkey"] = api.tx.maintainCommittee.committeeAddPubkey;
  // funcMap["maintainCommittee"]["reporterAddErrorInfo"] = api.tx.maintainCommittee.reporterAddErrorInfo;
  // funcMap["maintainCommittee"]["reportMachineState"] = api.tx.maintainCommittee.reportMachineState;
  // funcMap["maintainCommittee"]["submitConfirmHash"] = api.tx.maintainCommittee.submitConfirmHash;
  funcMap["rentMachine"] = {};
  funcMap["rentMachine"]["rentMachine"] = api.tx.rentMachine.rentMachine;

  var callFunc = funcMap[args["module"]][args["func"]];
  await do_sign_tx(callFunc, accountFromKeyring, nonce, ...args._).catch(
      (error) => console.log(error.message)
  );
}

async function do_sign_tx(callFunc, accountFromKeyring, nonce, ...args) {
  const a = await callFunc(...args).signAndSend(
      accountFromKeyring,
      { nonce },
      ({ events = [], status }) => {
        console.log(`{"Tx_status":"${status.type}"}`);

        if (status.isInBlock) {
          console.log(`{"Tx_inBlock":"${status.asInBlock.toHex()}"}`);

          events.forEach(({ event: { data, method, section }, phase }) => {
            console.log(
                `{"Event":${phase.toString()},"func":"${section}.${method}","data":${data.toString()}}`
            );
          });
        } else if (status.isFinalized) {
          console.log(`{"Finalized_block_hash:":"${status.asFinalized.toHex()}"}`);

          process.exit(0);
        }
      }
  );
}

// main().catch(console.error);
main().catch((error) => console.log(error.message));