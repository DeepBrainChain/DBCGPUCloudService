// refer: https://polkadot.js.org/docs/substrate/storage
// https://polkadot.js.org/docs/api/examples/promise/read-storage

// // Import the API & Provider and some utility functions
// import { ApiPromise, WsProvider } from '@polkadot/api';
// import { Keyring } from '@polkadot/keyring';

// Import the API
import { ApiPromise, WsProvider } from "@polkadot/api";

// Our address for Alice on the dev chain
const ALICE = "5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY";

async function main() {
  // Construct
  const wsProvider = new WsProvider("wss://infotest.dbcwallet.io");

  var data = fs.readFileSync("types.json");
  var type_json = JSON.parse(data);

  // Create the API and wait until ready
  const api = await ApiPromise.create({
    provider: wsProvider,
    types: type_json,
  });

  // Make our basic chain state/storage queries, all in one go
  const [{ nonce: accountNonce }, now, validators] = await Promise.all([
    api.query.system.account(ALICE),
    api.query.timestamp.now(),
    api.query.session.validators(),
  ]);

  console.log(`accountNonce(${ALICE}) ${accountNonce}`);
  console.log(`last block timestamp ${now.toNumber()}`);

  if (validators && validators.length > 0) {
    // Retrieve the balances for all validators
    const validatorBalances = await Promise.all(
      validators.map((authorityId) => api.query.system.account(authorityId))
    );

    // Print out the authorityIds and balances of all validators
    console.log(
      "validators",
      validators.map((authorityId, index) => ({
        address: authorityId.toString(),
        balance: validatorBalances[index].data.free.toHuman(),
        nonce: validatorBalances[index].nonce.toHuman(),
      }))
    );
  }
}

main()
  .catch(console.error)
  .finally(() => process.exit());
