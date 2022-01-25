// Import the API & Provider and some utility functions
import { ApiPromise, WsProvider } from "@polkadot/api";
import { Keyring } from "@polkadot/keyring";

// Some constants we are using in this sample
const AMOUNT = 100;

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

  // Do something
  console.log(api.genesisHash.toHex());

  // known mnemonic, well, now it is - don't use it for funds
  const MNEMONIC =
    "sample split bamboo west visual approve brain fox arch impact relief smile";

  // type: sr25519, ssFormat: 42 (defaults)
  const keyring = new Keyring({ type: "sr25519" });
  const accountFromKeyring = keyring.createFromUri(MNEMONIC);

  // use the default as setup on init
  // 5FLiLdaQQiW7qm7tdZjdonfSV8HAcjLxFVcqv9WDbceTmBXA
  console.log("Substrate generic:", accountFromKeyring.address);

  // Get the nonce for the admin key
  const { nonce } = await api.query.system.account(accountFromKeyring.address);

  const recipient = "5GrwvaEF5zXb26Fz9rcQpDWS57CtERHpNehXCPcNoHGKutQY";

  console.log(
    "Sending",
    AMOUNT,
    "from",
    accountFromKeyring.address,
    "to",
    recipient,
    "with nonce",
    nonce.toString()
  );

  // Do the transfer and track the actual status
  api.tx.balances
    .transfer(recipient, AMOUNT)
    .signAndSend(accountFromKeyring, { nonce }, ({ events = [], status }) => {
      console.log("Transaction status:", status.type);

      if (status.isInBlock) {
        console.log("Included at block hash", status.asInBlock.toHex());
        console.log("Events:");

        events.forEach(({ event: { data, method, section }, phase }) => {
          console.log(
            "\t",
            phase.toString(),
            `: ${section}.${method}`,
            data.toString()
          );
        });
      } else if (status.isFinalized) {
        console.log("Finalized block hash", status.asFinalized.toHex());

        process.exit(0);
      }
    });
}

main().catch(console.error);
