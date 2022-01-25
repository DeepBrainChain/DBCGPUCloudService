# JS example for developer

完整文档参考：https://polkadot.js.org/docs 这里仅提供几个可以快速上手的例子，以作参考。

测试请使用 wss://infotest.dbcwallet.io 作为测试节点

+ 转账并监测是否成功：

  ```bash
  yarn install
  node transfer_example.js
  ```

+ 查询链上存储

  ```bash
  node query_storage.js
  ```

+ 发送链上交易
  ```
  node sign_txs.js
  ```

  + 示例
  ```
  ❯ node sign_txs.js --port="wss://infotest.dbcwallet.io" --module onlineProfile --func bondMachine --key "sample split bamboo west visual approve brain fox arch impact relief smile" 5FHneW46xGXgs5mUiveU4sbTyGBzmstUspZC92UhjJM694ty 2gfpp3MAB4Aq2ZPEU72neZTVcZkbzDzX96op9d3fvi3
  2021-05-31 14:11:06        API/INIT: RPC methods not decorated: onlineProfile_getOpInfo, onlineProfile_getStakerIdentity, onlineProfile_getStakerInfo, onlineProfile_getStakerList, onlineProfile_getStakerListInfo, onlineProfile_getStakerNum
  2021-05-31 14:11:06        METADATA: Unknown types found, no types for LCCommitteeList, LCCommitteeOps, MachineInfoByCommittee, TestMachineInfo
  Tx_status: Ready
  Tx_status: InBlock
  Tx_inBlock: 0x649aa5ab1ec9a3fe7e09e66ef8e249033455788d53655d276959b0209df3beda
  Event: {"applyExtrinsic":1} onlineProfile.BondMachine ["5FLiLdaQQiW7qm7tdZjdonfSV8HAcjLxFVcqv9WDbceTmBXA","2gfpp3MAB4Aq2ZPEU72neZTVcZkbzDzX96op9d3fvi3",0]
  Event: {"applyExtrinsic":1} treasury.Deposit [146500007192]
  Event: {"applyExtrinsic":1} system.ExtrinsicSuccess [{"weight":10000,"class":"Normal","paysFee":"Yes"}]
  Tx_status: Finalized
  Finalized_block_hash: 0x649aa5ab1ec9a3fe7e09e66ef8e249033455788d53655d276959b0209df3beda
  ```

