import { ApiPromise, WsProvider } from '@polkadot/api';
import { Keyring } from "@polkadot/keyring";
import { cryptoWaitReady } from '@polkadot/util-crypto';
import minimist from "minimist";
const node = {
    dbc: 'wss://infotest.dbcwallet.io'
}

let api  = null
// 链上交互
export const GetApi = async () =>{
    if (!api) {
        const provider = new WsProvider(node.dbc)
        api = await ApiPromise.create({
            provider ,
            types: {
                "Pubkey": "Vec<u8>",
                "ReportId": "u64",
                "SlashId": "u64",
                "BoxPubkey": "[u8; 32]",
                "ReportHash": "[u8; 16]",
                "URL": "Text",
                "MachineId": "Text",
                "OrderId": "u64",
                "TelecomName": "Text",
                "StandardGpuPointPrice": {
                    "gpu_point": "u64",
                    "gpu_price": "u64"
                },
                "LiveMachine": {
                    "bonding_machine": "Vec<MachineId>",
                    "confirmed_machine": "Vec<MachineId>",
                    "booked_machine": "Vec<MachineId>",
                    "online_machine": "Vec<MachineId>",
                    "fulfilling_machine": "Vec<MachineId>",
                    "refused_machine": "Vec<MachineId>",
                    "rented_machine": "Vec<MachineId>",
                    "offline_machine": "Vec<MachineId>",
                    "refused_mut_hardware_machine": "Vec<MachineId>"
                },
                "StashMachine": {
                    "total_machine": "Vec<MachineId>",
                    "online_machine": "Vec<MachineId>",
                    "total_calc_points": "u64",
                    "total_gpu_num": "u64",
                    "total_rented_gpu": "u64",
                    "total_earned_reward": "Balance",
                    "total_claimed_reward": "Balance",
                    "can_claim_reward": "Balance",
                    "total_rent_fee": "Balance",
                    "total_burn_fee": "Balance"
                },
                "SysInfoDetail": {
                    "total_gpu_num": "u64",
                    "total_rented_gpu": "u64",
                    "total_staker": "u64",
                    "total_calc_points": "u64",
                    "total_stake": "Balance",
                    "total_rent_fee": "Balance",
                    "total_burn_fee": "Balance"
                },
                "UserReonlineStakeInfo": {
                    "stake_amount": "Balance",
                    "offline_time": "BlockNumber"
                },
                "MachineInfo": {
                    "controller": "AccountId",
                    "machine_stash": "AccountId",
                    "last_machine_renter": "Option<AccountId>",
                    "last_machine_restake": "BlockNumber",
                    "bonding_height": "BlockNumber",
                    "online_height": "BlockNumber",
                    "last_online_height": "BlockNumber",
                    "init_stake_per_gpu": "Balance",
                    "stake_amount": "Balance",
                    "machine_status": "MachineStatus",
                    "total_rented_duration": "u64",
                    "total_rented_times": "u64",
                    "total_rent_fee": "Balance",
                    "total_burn_fee": "Balance",
                    "machine_info_detail": "MachineInfoDetail",
                    "reward_committee": "Vec<AccountId>",
                    "reward_deadline": "EraIndex"
                },
                "MachineStatus": {
                    "_enum": {
                        "AddingCustomizeInfo": null,
                        "DistributingOrder": null,
                        "CommitteeVerifying": null,
                        "CommitteeRefused": "BlockNumber",
                        "WaitingFulfill": null,
                        "Online": null,
                        "StakerReportOffline": "(BlockNumber, Box<MachineStatus>)",
                        "ReporterReportOffline": "(StashSlashReason, Box<MachineStatus>, AccountId, Vec<AccountId>)",
                        "Creating": null,
                        "Rented": null
                    }
                },
                "MachineInfoDetail": {
                    "committee_upload_info": "CommitteeUploadInfo",
                    "staker_customize_info": "StakerCustomizeInfo"
                },
                "CommitteeUploadInfo": {
                    "machine_id": "MachineId",
                    "gpu_type": "Vec<u8>",
                    "gpu_num": "u32",
                    "cuda_core": "u32",
                    "gpu_mem": "u64",
                    "calc_point": "u64",
                    "sys_disk": "u64",
                    "data_disk": "u64",
                    "cpu_type": "Vec<u8>",
                    "cpu_core_num": "u32",
                    "cpu_rate": "u64",
                    "mem_num": "u64",
                    "rand_str": "Vec<u8>",
                    "is_support": "bool"
                },
                "StakerCustomizeInfo": {
                    "server_room": "H256",
                    "upload_net": "u64",
                    "download_net": "u64",
                    "longitude": "Longitude",
                    "latitude": "Latitude",
                    "telecom_operators": "Vec<TelecomName>"
                },
                "Longitude": {
                    "_enum": {
                        "East": "u64",
                        "West": "u64"
                    }
                },
                "Latitude": {
                    "_enum": {
                        "South": "u64",
                        "North": "u64"
                    }
                },
                "EraStashPoints": {
                    "total": "u64",
                    "staker_statistic": "BTreeMap<AccountId, StashMachineStatistics>"
                },
                "StashMachineStatistics": {
                    "online_gpu_num": "u64",
                    "inflation": "Perbill",
                    "machine_total_calc_point": "u64",
                    "rent_extra_grade": "u64"
                },
                "PosInfo": {
                    "online_gpu": "u64",
                    "offline_gpu": "u64",
                    "rented_gpu": "u64",
                    "online_gpu_calc_points": "u64"
                },
                "MachineGradeStatus": {
                    "basic_grade": "u64",
                    "is_rented": "bool"
                },
                "CommitteeList": {
                    "normal": "Vec<AccountId>",
                    "chill_list": "Vec<AccountId>",
                    "waiting_box_pubkey": "Vec<AccountId>",
                    "fulfilling_list": "Vec<AccountId>"
                },
                "OCCommitteeMachineList": {
                    "booked_machine": "Vec<MachineId>",
                    "hashed_machine": "Vec<MachineId>",
                    "confirmed_machine": "Vec<MachineId>",
                    "online_machine": "Vec<MachineId>"
                },
                "OCCommitteeOps": {
                    "staked_dbc": "Balance",
                    "verify_time": "Vec<BlockNumber>",
                    "confirm_hash": "[u8; 16]",
                    "hash_time": "BlockNumber",
                    "confirm_time": "BlockNumber",
                    "machine_status": "OCMachineStatus",
                    "machine_info": "CommitteeUploadInfo"
                },
                "OCMachineStatus": {
                    "_enum": ["Booked", "Hashed", "Confirmed"]
                },
                "OCMachineCommitteeList": {
                    "book_time": "BlockNumber",
                    "booked_committee": "Vec<AccountId>",
                    "hashed_committee": "Vec<AccountId>",
                    "confirm_start_time": "BlockNumber",
                    "confirmed_committee": "Vec<AccountId>",
                    "onlined_committee": "Vec<AccountId>",
                    "status": "OCVerifyStatus"
                },
                "OCVerifyStatus": {
                    "_enum": ["SubmittingHash", "SubmittingRaw", "Summarizing", "Finished"]
                },
                "ReporterReportList": {
                    "processing_report": "Vec<ReportId>",
                    "canceled_report": "Vec<ReportId>",
                    "succeed_report": "Vec<ReportId>",
                    "failed_report": "Vec<ReportId>"
                },
                "MTCommitteeOpsDetail": {
                    "booked_time": "BlockNumber",
                    "encrypted_err_info": "Option<Vec<u8>>",
                    "encrypted_time": "BlockNumber",
                    "confirm_hash": "ReportHash",
                    "hash_time": "BlockNumber",
                    "extra_err_info": "Vec<u8>",
                    "confirm_time": "BlockNumber",
                    "confirm_result": "bool",
                    "staked_balance": "Balance",
                    "order_status": "MTOrderStatus"
                },
                "MTOrderStatus": {
                    "_enum": ["WaitingEncrypt", "Verifying", "WaitingRaw", "Finished"]
                },
                "MTCommitteeOrderList": {
                    "booked_report": "Vec<ReportId>",
                    "hashed_report": "Vec<ReportId>",
                    "confirmed_report": "Vec<ReportId>",
                    "finished_report": "Vec<ReportId>"
                },
                "MTLiveReportList": {
                    "bookable_report": "Vec<ReportId>",
                    "verifying_report": "Vec<ReportId>",
                    "waiting_raw_report": "Vec<ReportId>",
                    "finished_report": "Vec<ReportId>"
                },
                "MTPendingSlashInfo": {
                    "slash_who": "AccountId",
                    "slash_time": "BlockNumber",
                    "slash_amount": "Balance",
                    "slash_exec_time": "BlockNumber",
                    "reward_to": "Vec<AccountId>",
                    "slash_reason": "MTReporterSlashReason"
                },
                "MTReporterSlashReason": {
                    "_enum": ["ReportRefused", "NotSubmitEncryptedInfo"]
                },
                "MTReportInfoDetail": {
                    "reporter": "AccountId",
                    "report_time": "BlockNumber",
                    "reporter_stake": "Balance",
                    "first_book_time": "BlockNumber",
                    "machine_id": "MachineId",
                    "err_info": "Vec<u8>",
                    "verifying_committee": "Option<AccountId>",
                    "booked_committee": "Vec<AccountId>",
                    "get_encrypted_info_committee": "Vec<AccountId>",
                    "hashed_committee": "Vec<AccountId>",
                    "confirm_start": "BlockNumber",
                    "confirmed_committee": "Vec<AccountId>",
                    "support_committee": "Vec<AccountId>",
                    "against_committee": "Vec<AccountId>",
                    "report_status": "ReportStatus",
                    "machine_fault_type": "MachineFaultType"
                },
                "ReportStatus": {
                    "_enum": [
                        "Reported",
                        "WaitingBook",
                        "Verifying",
                        "SubmittingRaw",
                        "CommitteeConfirmed"
                    ]
                },
                "MachineFaultType": {
                    "_enum": {
                        "RentedInaccessible": "MachineId",
                        "RentedHardwareMalfunction": "(ReportHash, BoxPubkey)",
                        "RentedHardwareCounterfeit": "(ReportHash, BoxPubkey)",
                        "OnlineRentFailed": "(ReportHash, BoxPubkey)"
                    }
                },
                "CMPendingSlashInfo": {
                    "slash_who": "AccountId",
                    "slash_time": "BlockNumber",
                    "slash_amount": "Balance",
                    "slash_exec_time": "BlockNumber",
                    "reward_to": "Vec<AccountId>",
                    "slash_reason": "CMSlashReason"
                },
                "CMSlashReason": {
                    "_enum": [
                        "OCNotSubmitHash",
                        "OCNotSubmitRaw",
                        "OCInconsistentSubmit",
                        "MCNotSubmitHash",
                        "MCNotSubmitRaw",
                        "MCInconsistentSubmit"
                    ]
                },
                "OnlineStakeParamsInfo": {
                    "online_stake_per_gpu": "Balance",
                    "online_stake_usd_limit": "u64",
                    "reonline_stake": "u64"
                },
                "OPPendingSlashInfo": {
                    "slash_who": "AccountId",
                    "slash_time": "BlockNumber",
                    "slash_amount": "Balance",
                    "slash_exec_time": "Option<BlockNumber>",
                    "reward_to": "Option<Vec<AccountId>>",
                    "slash_reason": "OPSlashReason"
                },
                "OPSlashReason": {
                    "_enum": {
                        "RentedReportOffline": "BlockNumber",
                        "OnlineReportOffline": "BlockNumber",
                        "RentedInaccessible": "BlockNumber",
                        "RentedHardwareMalfunction": "BlockNumber",
                        "RentedHardwareCounterfeit": "BlockNumber",
                        "OnlineRentFailed": "BlockNumber",
                        "CommitteeRefusedOnline": null,
                        "CommitteeRefusedMutHardware": null,
                        "ReonlineShouldReward": null
                    }
                },
                "RentOrderDetail": {
                    "renter": "AccountId",
                    "rent_start": "BlockNumber",
                    "confirm_rent": "BlockNumber",
                    "rent_end": "BlockNumber",
                    "stake_amount": "Balance",
                    "rent_status": "RentStatus"
                },
                "RentStatus": {
                    "_enum": ["WaitingVerifying", "Renting", "RentExpired"]
                },
                "CommitteeStakeParamsInfo": {
                    "stake_baseline": "Balance",
                    "stake_per_order": "Balance",
                    "min_free_stake_percent": "Perbill"
                },
                "CommitteeStakeInfo": {
                    "box_pubkey": "[u8; 32]",
                    "staked_amount": "Balance",
                    "used_stake": "Balance",
                    "can_claim_reward": "Balance",
                    "claimed_reward": "Balance"
                },
                "ReporterStakeParamsInfo": {
                    "stake_baseline": "Balance",
                    "stake_per_report": "Balance",
                    "min_free_stake_percent": "Perbill"
                },
                "ReporterStakeInfo": {
                    "staked_amount": "Balance",
                    "used_stake": "Balance",
                    "can_claim_reward": "Balance",
                    "claimed_reward": "Balance"
                },
                "AccountInfo": {
                    "nonce": "u32",
                    "consumers": "u32",
                    "providers": "u32",
                    "data": "AccountData"
                }
            }
        })
    }
    return { api }
}

const keyring = new Keyring({ type: "sr25519" });
var args = minimist(process.argv.slice(2), { string: ['key', 'data', 'id'] });
let i = 0;
console.log(args)
export const reletMachine = async () => {
    await GetApi();
    let accountFromKeyring = await keyring.addFromUri(args['key']);
    await cryptoWaitReady();
    await api?.tx.rentMachine
        .reletMachine( args["id"], args["data"] )
        .signAndSend( accountFromKeyring, ( { events = [], status , dispatchError  } ) => {
            if (status.isInBlock) {
                events.forEach( async ({ event: { method, data: [error] }}) => {
                    if (error.isModule && method == 'ExtrinsicFailed') {
                        console.log('ExtrinsicFailed');
                        process.exit(0);
                    }else if(method == 'ExtrinsicSuccess'){
                        console.log('ExtrinsicSuccess');
                        process.exit(0);
                    }
                });
            } else if (status.isFinalized) {
            }
        })
}
reletMachine()