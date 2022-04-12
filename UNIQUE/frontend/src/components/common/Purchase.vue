<template>
  <div v-if="this.product.nft.nftOwnerAddress != myAddress">
    <button type="button" data-bs-toggle="modal" :data-bs-target="`#modal` + `${product.marketId}`" class="btn btn-sm btn-dark d-block">Purchase</button>
    <div class="modal fade" :id="`modal${product.marketId}`" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <!-- <div>{{ product.marketId }}</div> -->
            <h4 class="modal-title">Complete checkout</h4>
            <button type="button" class="btn-close icon-btn" data-bs-dismiss="modal" aria-label="Close">
              <em class="ni ni-cross"></em>
            </button>
          </div>
          <!-- end modal-header -->
          <!-- <p class="">hihihi</p> -->
          <div class="modal-header">If you'd like to purchase,<strong>Please sign for this transaction</strong></div>
          <div class="modal-body">
            <!-- <div class="mb-3">
              <label class="form-label">아아</label>
              <input type="text" class="form-control form-control-s1" v-model="authorPrivateKey" placeholder="please typing your Private Key" />
            </div> -->
            <div class="mb-3">
              <label class="form-label">Your Private Key</label>
              <input type="text" class="form-control form-control-s1" v-model="authorPrivateKey" placeholder="please typing your Private Key" />
            </div>
            <button class="btn btn-dark d-block" @click="purchaseNFT" :data-bs-target="`#modal` + `${product.marketId}`" data-bs-toggle="modal">Confirm</button>
            <!-- <a :href="SectionData.purchaseNFTModal.btnLink" class="btn btn-dark d-block" @click="purchaseNFT"></a> -->
          </div>
          <!-- end modal-body -->
        </div>
        <!-- end modal-content -->
      </div>
      <!-- end modal-dialog -->
    </div>
    <!-- end firstmodal -->
    <!-- start second modal -->
    <div class="modal fade" :id="`modal${product.marketId}`" tabindex="-1" aria-hidden="true" v-if="this.purchase == 1">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header d-flex flex-column">
            <h4 class="modal-title d-flex justify-content-center">You get {{ product.nft.nftName }}!!</h4>
            <button type="button" class="btn-close icon-btn" data-bs-dismiss="modal" aria-label="Close">
              <em class="ni ni-cross"></em>
            </button>
            <p class="d-flex justify-content-center">Wow! You just create your NFT</p>
          </div>
          <!-- end second modal-header -->
          <div class="modal-body d-flex flex-column">
            <div class="mb-3 d-flex justify-content-center">
              <img src="" class="justify-content-center" alt="" />
              <!-- <img src=`${}` alt="" /> -->
            </div>
            <!-- <a :href="SectionData.purchaseNFTModal.btnLink" class="btn btn-dark d-block" @click="purchaseNFT"></a> -->
          </div>
          <!-- end second modal-body -->
        </div>
        <!-- end second modal-content -->
      </div>
      <!-- end second modal-dialog -->
    </div>
  </div>
</template>

<script>
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const GANACHE_SERVER_URL = "http://20.196.209.2:8545";

import { mapState } from "vuex";
import axios from "axios";
import Web3 from "web3";
import SectionData from "@/store/store.js";
import getAddressFrom from "../../utils/AddressExtractor";
// import ABI from "../../common/ABI";
import SsafyToken from "../../../smart-contracts/build/contracts/SsafyToken.json";
import SsafyNFT from "../../../smart-contracts/build/contracts/SsafyNFT.json";
import SaleFactory from "../../../smart-contracts/build/contracts/SaleFactory.json";
import Sale from "../../../smart-contracts/build/contracts/Sale.json";
let TOKEN_ABI = SsafyToken.abi;
let TOKEN_CA = "0x6c927304104cdaa5a8b3691e0ade8a3ded41a333";
let NFT_ABI = SsafyNFT.abi;
let NFT_CA = SsafyNFT.networks["202112031219"].address;
let SALE_FACTORY_ABI = SaleFactory.abi;
let SALE_FACTORY_CA = SaleFactory.networks["202112031219"].address;
let SALE_ABI = Sale.abi;
// 네트워크 연결
let web3 = new Web3(new Web3.providers.HttpProvider(GANACHE_SERVER_URL));

export default {
  name: "Purchase",
  data() {
    return {
      authorPrivateKey: null,
      marketContractAddress: this.product.marketContractAddress,
      price: this.product.price,
      nftOwnerAddress: this.product.nftOwnerAddress,
      nftTokenId: this.product.nft.nftTokenId,
      modalId: "",
      modalLink: "",
      purchase: 0,
    };
  },
  props: ["product", "marketId"],
  methods: {
    // async check() {
    //   const tokenCont = await new web3.eth.Contract(NFT_ABI, NFT_CA);
    //   // const ressa = await tokenCont.methods.ownerOf(128).call();
    //   const ressdda = await tokenCont.methods.balanceOf(this.myAddress).call();
    //   // console.log(ressa, "ressa");
    //   console.log(ressdda, "ressdda");

    //   const ssafyToken1 = await new web3.eth.Contract(TOKEN_ABI, TOKEN_CA);
    //   const getbalance2 = await ssafyToken1.methods.balanceOf(this.myAddress).call();
    //   const NFTContractInstance = await new web3.eth.Contract(NFT_ABI, NFT_CA);
    //   let aca = await NFTContractInstance.methods.ownerOf(this.product.nft.nftTokenId).call();
    //   // let csc = await NFTContractInstance.methods.ownerOf(33).call();
    //   let vas = await NFTContractInstance.methods.getApproved(this.product.nft.nftTokenId).call();
    //   console.log(aca, "aca");
    //   // console.log(csc, "csc");
    //   console.log(vas, "vas");
    //   console.log(getbalance2, "맞나이거");
    //   // console.log(TOKEN_CA, "토큰");
    // },

    async purchaseNFT() {
      console.log(this.marketContractAddress);
      const checkPubKey = await getAddressFrom(this.authorPrivateKey);
      const myAccount = this.myAddress;
      if (checkPubKey === myAccount) {
        // 해당 세일컨트랙트인스턴스 생성
        // const saleContractInstance = await new web3.eth.Contract(SALE_ABI, this.marketContractAddress);
        // console.log(saleContractInstance, "saleContractInstance");

        // ----------------erc20토큰관련----------------
        // 싸피토큰컨트랙트 인스턴스생성
        const tokenContractInstance = await new web3.eth.Contract(TOKEN_ABI, TOKEN_CA);
        // 내 토큰 잔액확인
        // console.log(tokenContractInstance, "tokenContractInstance");
        const myTokenBalance = await tokenContractInstance.methods.balanceOf(myAccount).call();
        console.log(myTokenBalance, "myTokenBalance");
        // 해당 Sale의 판매 시점이 유효한 경우
        // 끝난시간보다 현재시각이 아직 남았어야하고
        // 현재시간보다 판매시점이 전이어야함
        // 만약 계좌잔액이 현재 구매가 보다 작다면 실행 안됨
        if (myTokenBalance > this.price) {
          // 만든 세일에 대한 CA, 판매자,
          // 마켓CA에게 돈 빠져나갈 수 있는 권한 부여
          const resultOfApprovedTokenResult = await tokenContractInstance.methods.approve(this.marketContractAddress, this.price);
          // console.log(resultOfApprovedTokenResult, "resultOfApprovedTokenResult");
          const resultOfApprovedTokenEncodeMethod = await resultOfApprovedTokenResult.encodeABI();
          // const ApprovedGasEstimate = await resultOfApprovedTokenResult.estimateGas({ from: myAccount });
          const Approve20TokenRawTx = {
            from: myAccount,
            // to: SaleCA,
            to: TOKEN_CA,
            // to:SaleCA ??????
            gas: 50000000,
            data: resultOfApprovedTokenEncodeMethod,
          };
          // console.log(Approve20TokenRawTx, "Approve20TokenRawTx");
          const walletAccount = await web3.eth.accounts.privateKeyToAccount(this.authorPrivateKey);
          console.log(walletAccount, "walletAccount");
          const signedTx = await walletAccount.signTransaction(Approve20TokenRawTx);
          console.log(signedTx, "signedTx");
          if (signedTx == null) {
            alert("TransactionSignFailedException");
          }
          // 서명할게 있으면
          else {
            // console.log("안됩니까 지금");
            // 인출 권한 서명
            const noAw = await web3.eth.sendSignedTransaction(signedTx.rawTransaction);
            console.log(noAw, "noAw");
            // erc721 소유권 이전
            // 계좌에돈이 증가하면??????
            // NFT컨트랙트 인스턴스 생성
            console.log("18181283812");
            const NFTContractInstance = await new web3.eth.Contract(NFT_ABI, NFT_CA);

            // 인스턴스에서 NFT 인출 권한 해당 세일CA에 부여
            // this.nftTokenId;
            // console.log(this.marketContractAddress, this.nftTokenId);
            // console.log(this.nftTokenId);
            // let chekcing = NFTContractInstance.methods.getApproved(this.nftTokenId).call();
            // console.log(chekcing, "chekcing");
            // console.log(this.nftTokenId, "토큰아이디");

            // const ressss = await NFTContractInstance.methods.ownerOf(this.nftTokenId).call();
            // console.log("거래되기전 주인 ");

            // console.log(ressss, "ressss");
            // const NFTContractResult = await NFTContractInstance.methods.setApprovalForAll(this.marketContractAddress, this.nftTokenId);
            // console.log(NFTContractResult, "NFTContractResult");
            console.log("여기 맞나여?");
            // let zsc = await NFTContractInstance.methods.ownerOf(this.product.nft.nftTokenId).call();

            // let checking = await NFTContractInstance.methods.getApproved(this.product.nft.nftTokenId).call();
            // console.log(zsc, "zsc 구매 전,");

            // console.log(checking, " checking 구매전");
            // chekcing = NFTContractInstance.methods.getApproved(this.nftTokenId).call();
            // console.log(chekcing, "chekcing");
            // const NFTContractInstanceEncodeMethod = await NFTContractResult.encodeABI();
            // console.log(NFTContractInstanceEncodeMethod, "NFTContractInstanceEncodeMethod");
            // const NFTgasEstimate = await NFTContractResult.estimateGas({});
            // const NFTRawTX = {
            //   from: myAccount,
            //   // to: NFT_CA,
            //   to: this.marketContractAddress,
            //   gas: 500000,
            //   data: NFTContractInstanceEncodeMethod,
            // };

            // console.log();
            // const NFTsignedTX = await walletAccount.signTransaction(NFTRawTX);
            // if (NFTsignedTX === null) {
            //   alert("The sign of Approbation for NFT is not completed");
            // } else {
            // console.log("혹시 여기아닙니까?");
            // // 거래 서명
            // console.log(NFTsignedTX, "NFTsignedTX");
            // const NFTsigneResult = await web3.eth.sendSignedTransaction(NFTsignedTX.rawTransaction);
            // console.log(NFTsigneResult, "된거가 뭔데");
            // console.log("뭡니까 이거!!!!!!!!!!");
            // zsc = await NFTContractInstance.methods.ownerOf(this.nftTokenId).call();
            // console.log(zsc, "zsc 서명해서 approve되었을 떄");
            // -----------------------권한 부여 다했으니 이제 구매함수 호출
            // purchase함수 호출을 위한 컨트랙트 인스턴스 생성
            const createSaleInstance = await new web3.eth.Contract(SALE_ABI, this.marketContractAddress);
            console.log(createSaleInstance, "createSaleInstance");
            const purchsaeFunctionCallResult = await createSaleInstance.methods.purchase();
            // zsc = await NFTContractInstance.methods.ownerOf(this.product.nft.nftTokenId).call();
            // checking = await NFTContractInstance.methods.getApproved(this.product.nft.nftTokenId).call();
            // console.log(zsc, "zsc 구매 직후,");
            // console.log(checking, " checking 구매직후 전송권한");
            // const sendCoin = await NFTContractInstance.methods.transferFrom(this.marketContractAddress, myAccount, 33);
            // console.log(purchsaeFunctionCallResult, "purchsaeFunctionCallResult");
            const purchaseFunctionMethodEndcoded = await purchsaeFunctionCallResult.encodeABI();
            // const sendCoinEncode = await sendCoin.encodeABI();

            console.log(purchaseFunctionMethodEndcoded, "purchaseFunctionMethodEndcoded");
            // const purchaseGasEstimate = await purchsaeFunctionCallResult.estimateGas({ from: myAccount });
            // console.log(purchaseGasEstimate, "purchaseGasEstimate");

            const purchaseRawTx = {
              from: myAccount,
              to: this.marketContractAddress,
              // nonce: ,
              // gasPrice: "0x00",
              // gasLimit: 3000000,
              gas: 5000000,
              // to: this.marketContractAddress,
              data: purchaseFunctionMethodEndcoded,
              // chainId: 31221,
            };
            const walletAccount = await web3.eth.accounts.privateKeyToAccount(this.authorPrivateKey);
            console.log(purchaseRawTx, "purchaseRawTx");

            const purchaseSingedTx = await walletAccount.signTransaction(purchaseRawTx);
            console.log(purchaseSingedTx, "purchaseSingedTx");

            if (purchaseSingedTx == null) {
              alert("The sign purchase NFT is not completed");
            } else {
              console.log("이제 제발 되어라!!!!!!!!!");
              // const finalresults = await createSaleInstance.methods.getSaleInfo().call();
              // console.log(finalresults, "finalresults");
              const purchaseSingedResult = await web3.eth.sendSignedTransaction(purchaseSingedTx.rawTransaction);
              console.log(purchaseSingedResult, "purchaseSingedResult");
              let zsc = await NFTContractInstance.methods.ownerOf(this.product.nft.nftTokenId).call();
              // checking = await NFTContractInstance.methods.getApproved(this.product.nft.nftTokenId).call();
              console.log(zsc, "zsc 구매 후,");
              // console.log(checking, " checking 구매후 전송권한");
              console.log("거래되고나서주인");

              // 백엔드에 저장
              axios({
                method: "put",
                url: `${SERVER_URL}/api/market/purchase`,
                headers: {
                  // Authorization: token,
                  Authorization: this.authToken,
                  // "Content-Type": "multipart/form-data",
                },
                data: {
                  nftSeq: this.product.nft.nftSeq,
                  marketId: this.product.marketId,
                },
              })
                .then((res) => {
                  console.log(res.data);
                  this.purchase = 1;
                  // marketInfoData.value = res.data;
                })
                .then((res) => {
                  alert("Congratulations!! your transaction has been completed.");
                  this.$router.go();
                })
                .catch(() => {
                  alert("Market Info is not recorded");
                });
            }
            // }
          }
        } else {
          alert("please, check your account's balance");
          // 새로고침
          // this.$router.go();
        }
        // 내 계좌에서 컨트랙트에다가 토큰 전송 권한 부여
      }
      // 프라이빗키가 일치하지 않으면
      else {
        alert("Please, check your private key");
        this.authorPrivateKey = null;
      }
    },
  },
  created() {
    // this.check();
    // console.log(this.product);
    // this.marketId = this.product.marketId;
    // this.nftSeq = this.product.nft.nftSeq;
    // console.log(this.marketContractAddress);
    // console.log(this.product.marketId);
    // console.log(this.product.marketId);
    // console.log(this.ids, "ids");
    // console.log(this.product);
    // console.log(this.marketId);
    // console.log(this.product.marketContractAddress);
    // axios({
    //   method: "get",
    //   url: `${SERVER_URL}/api/market/detail/${id}`,
    //   headers: {
    //     // Authorization: token,
    //     Authorization: authToken,
    //     // "Content-Type": "multipart/form-data",
    //   },
    // })
    //   .then((res) => {
    //     // console.log(res.data);
    //     marketInfoData.value = res.data;
    //   })
    //   .catch(() => {
    //     alert("There is no item in our Market.");
    //   });
    // console.log(getbalance);
    // console.log(this.product);
    // this.marketContractAddress = this.product.marketContractAddress;
    // this.price = this.product.price;
    // this.nftOwnerAddress = this.product.nftOwnerAddress;
    // this.nftTokenId = this.product.nftTokenId;
  },
  computed: {
    ...mapState(["myAddress"]),
    ...mapState(["authToken"]),
  },
};
</script>

<style></style>
