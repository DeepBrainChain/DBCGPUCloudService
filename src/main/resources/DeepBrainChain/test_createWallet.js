import { Keyring } from "@polkadot/keyring";
import { u8aToHex } from '@polkadot/util';
import { cryptoWaitReady, randomAsU8a } from '@polkadot/util-crypto'

const keyring = new Keyring({type: 'sr25519'})

export const createAccountFromSeed = async () => {
  await cryptoWaitReady()
  const seed = u8aToHex(randomAsU8a())
  const pair = keyring.addFromUri(seed)
  const address = pair.address
  console.log(`{"seed":"${seed}","address":"${address}"}`)
}
createAccountFromSeed()
