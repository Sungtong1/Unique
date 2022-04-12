<template>
  <div class="page-wrap">
    <!-- header  -->
    <header class="header-section has-header-main">
      <!-- Header main -->
      <HeaderMain></HeaderMain>
    </header>
    <div style="max-width: 1200px; margin: auto">
      <div class="loginbar d-flex justify-content-end align-items-center" style="position: sticky; top: 70px; height: 60px; padding-right: 50px">
        <!-- sell버튼 시작 난중에 내가 이 nft의 주인이면 조건걸기 -->
        <router-link v-if="onsale === `Not for Sale` && Owner == userId" :to="{ name: 'SaleCreate', params: { id: this.$route.params.id } }" class="btn btn-dark d-block mb-2">Sell</router-link>
        <!-- sell버튼 끝-->
      </div>
    </div>
    <div class="d-flex flex-column"></div>
    <section class="item-detail-section section-space">
      <div class="container">
        <div class="row">
          <div class="col-lg-6 pe-xl-5">
            <div class="item-detail-content">
              <div class="item-detail-img-container mb-4">
                <img :src="imgLg" />
              </div>
              <!-- end item-detail-img-container -->
              <!-- 사진아래1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111 -->
              <div class="item-detail-tab">
                <ul class="nav nav-tabs nav-tabs-s1" id="myTab" role="tablist">
                  <li class="nav-item" role="presentation" v-for="list in SectionData.itemDetailData.itemDetailTabNav" :key="list.id">
                    <button class="nav-link" :class="list.isActive" :id="list.slug" data-bs-toggle="tab" :data-bs-target="list.bsTarget" type="button">{{ list.title }}</button>
                  </li>
                </ul>
                <div class="tab-content mt-3" id="myTabContent">
                  <div class="tab-pane fade show active" id="owners" role="tabpanel" aria-labelledby="owners-tab">
                    <div class="item-detail-tab-wrap">
                      <div class="card-media card-media-s2 mb-3">
                        <router-link :to="`/profile/${itemDetailList[1].path}`" class="card-media-img flex-shrink-0 d-block">
                          <img :src="itemDetailList[1].avatar" alt="avatar" />
                        </router-link>
                        <div class="card-media-body text-truncate">
                          <p class="fw-semibold text-truncate">
                            <router-link :to="`/profile/${itemDetailList[1].path}`" class="text-black">{{ itemDetailList[1].title }}</router-link>
                          </p>
                          <p class="small">{{ itemDetailHistoryList[0].subTitle }}</p>
                        </div>
                      </div>
                      <!-- end card -->
                    </div>
                    <!-- end item-detail-tab-wrap -->
                  </div>
                  <!-- end tab-pane -->
                  <!-- end tab-pane -->
                  <div class="tab-pane fade" id="history" role="tabpanel" aria-labelledby="history-tab">
                    <div class="item-detail-tab-wrap">
                      <div class="card-media card-media-s2 mb-3" v-for="item in itemDetailHistoryList" :key="item.id">
                        <router-link :to="`/profile/${item.path}`" class="card-media-img flex-shrink-0 d-block">
                          <img :src="item.avatar" alt="avatar" />
                        </router-link>
                        <div class="card-media-body text-truncate">
                          <p class="fw-semibold text-black text-truncate">{{ item.title }}</p>
                          <p class="small text-truncate">{{ item.subTitle }}</p>
                        </div>
                      </div>
                      <!-- end card -->
                    </div>
                    <!-- end item-detail-tab-wrap -->
                  </div>
                  <div class="tab-pane fade" id="detail" role="tabpanel" aria-labelledby="detail-tab">
                    <div class="item-detail-tab-wrap">
                      <ul>
                        <li class="mb-2">
                          <p><strong class="text-black"> Contract Address </strong> {{ nftCA }}</p>
                        </li>
                        <li>
                          <p><strong class="text-black">Token ID :</strong> #{{ id }}</p>
                        </li>
                        <li>
                          <p><strong class="text-black">Price :</strong> {{ price }} SSF</p>
                        </li>
                        <li>
                          <span><strong class="text-black"> Token Standard : </strong></span>
                          <span>ERC - 721</span>
                        </li>
                        <li>
                          <p><strong class="text-black"> BlockChain : </strong> SSAFY Network</p>
                        </li>
                      </ul>
                      <!-- end card -->
                    </div>
                    <!-- end item-detail-tab-wrap -->
                  </div>
                  <!-- end tab-pane -->
                </div>
              </div>
            </div>
            <!-- end item-detail-content -->
          </div>
          <!-- end col -->
          <div class="col-lg-6">
            <div class="item-detail-content mt-4 mt-lg-0">
              <div class="d-flex flex-row">
                <h1 class="item-detail-title mb-2">{{ title }}</h1>
              </div>
              <div class="item-detail-meta d-flex flex-wrap align-items-center mb-3">
                <span class="item-detail-text-meta">{{ onsale }}</span>
                <span class="dot-separeted"></span>
                <span class="item-detail-text-meta">Price : {{ price }} SSF</span>
              </div>
              <p class="item-detail-text mb-4">{{ content }}</p>
              <div class="item-credits">
                <div class="row g-4">
                  <div class="col-xl-6" v-for="item in itemDetailList" :key="item.id">
                    <div class="card-media card-media-s1">
                      <router-link :to="`/profile/${item.path}`" class="card-media-img flex-shrink-0 d-block">
                        <img :src="item.avatar" alt="avatar" />
                      </router-link>
                      <div class="card-media-body">
                        <router-link :to="`/profile/${item.path}`" class="fw-semibold">@{{ item.title }}</router-link>
                        <p class="fw-medium small">{{ item.subTitle }}</p>
                      </div>
                    </div>
                    <!-- end card -->
                  </div>
                  <!-- end col-->
                  <!-- end col-->
                </div>
                <!-- end row -->
              </div>
              <!-- end row -->
              <div class="item-detail-btns mt-4">
                <ul class="btns-group d-flex">
                  <li class="flex-grow-1">
                    <!-- <a href="#" data-bs-toggle="modal" data-bs-target="#placeBidModal" class="btn btn-dark d-block">Purchase</a> -->
                    <!-- <Purchase :product="product"></Purchase> -->
                    <DetailPurchase
                      v-if="onsale === `On Sale` && Owner != userId"
                      :marketContractAddress="marketContractAddress"
                      :nftName="title"
                      :price="price"
                      :nftTokenId="nftTokenId"
                      :nftSeq="nftSeq"
                      :marketId="marketId"
                    ></DetailPurchase>
                  </li>
                </ul>
              </div>
              <!-- end item-detail-btns -->
            </div>
            <!-- end item-detail-content -->
          </div>
          <!-- end col -->
        </div>
        <!-- end row -->
      </div>
      <!-- .container -->
    </section>
    <!-- end item-detail-section -->
    <!-- Related product -->
    <!-- <RelatedProduct></RelatedProduct> -->
    <Featured></Featured>
    <!-- Footer  -->
    <Footer classname="bg-black on-dark"></Footer>
    <!-- Modal -->
    <!-- end modal-->
  </div>
  <!-- end page-wrap -->
</template>

<script>
// Import component data. You can change the data in the store to reflect in all component
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import axios from "axios";
import SectionData from "@/store/store.js";
import { mapState } from "vuex";
import { mapGetters } from "vuex";
import DetailPurchase from "@/components/common/DetailPurchase";
// import Purchase from "@/components/common/Purchase";

export default {
  name: "ProductDetail",
  // props: ["product"],
  data() {
    return {
      // product: product,
      marketContractAddress: null,
      nftTokenId: null,
      Owner: null,
      nftSeq: null,
      SectionData,
      marketId: null,
      id: this.$route.params.id,
      nftCA: null,
      title: "작품 이름", // 작품이름
      imgLg: "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/42iI/image/fgznKzwTj38hXdylF03JXH2Gv5E",

      onsale: "On Sale", // 판매 중 인지
      price: "500 editions",
      metaTextThree: "500 editions",
      content: 'Digital-only* "CB Galaxy" style Coke Boys LA sneakers wearable in the Decentraland metaverse',
      ownerlist: [
        {
          id: 1,
          title: "French Montana",
          subTitle: "450 editions not for sale",
          avatar: require("@/images/thumb/avatar-4.jpg"),
          path: "/author",
        },
      ],
      itemDetailHistoryList: [
        {
          id: 1,
          title: "Not for Sale",
          subTitle: "",
          avatar: require("@/images/thumb/avatar-4.jpg"),
          path: "",
        },
      ],
      itemDetailList: [
        {
          id: 1,
          title: "작가 이름",
          subTitle: "Artist",
          avatar: require("@/images/thumb/avatar.jpg"),
          path: "/author",
        },
        {
          id: 2,
          title: "보유자 이름",
          subTitle: "Owner",
          avatar: require("@/images/thumb/avatar-2.jpg"),
          path: "/author",
        },
      ],
    };
  },
  components: {
    DetailPurchase,
    // Purchase,
  },
  created: function () {
    this.getItmeDetail();
  },
  methods: {
    strsplit(req) {
      const beforeStr = req;
      const afterStr = beforeStr.split("/");
      return afterStr[0];
    },
    getItmeDetail() {
      axios({
        method: "GET",
        url: `${SERVER_URL}/api/nft/detail/${this.$route.params.id}`,
        headers: {
          // Authorization: token,
          Authorization: this.authToken,
        },
      }).then((res) => {
        console.log(res.data, "eeeeeeeeeeeeeeee");
        this.nowInfo = res.data;
        // -------------------
        // this.marketContractAddress = res.data.marketList[res.data.marketList.length - 1].marketContractAddress;

        this.itemDetailList[0].title = res.data.AuthorMember.memberId;
        this.itemDetailList[1].title = res.data.OwnerMember.memberId;
        this.itemDetailList[0].avatar = `https://j6e205.p.ssafy.io/${res.data.AuthorMember.profileImageUrl}`;
        this.itemDetailList[1].avatar = `https://j6e205.p.ssafy.io/${res.data.OwnerMember.profileImageUrl}`;
        this.itemDetailList[0].path = res.data.AuthorMember.memberSeq;
        this.itemDetailList[1].path = res.data.OwnerMember.memberSeq;

        this.nftSeq = res.data.nft.nftSeq;
        this.title = res.data.nft.nftName;
        this.nftTokenId = res.data.nft.nftTokenId;
        this.content = res.data.nft.nftDescription;
        this.nftCA = res.data.nft.nftContractAddress;
        this.imgLg = `https://j6e205.p.ssafy.io/${res.data.nft.fileUrl}`;
        this.Owner = res.data.nft.nftOwnerSeq;
        if (res.data.marketList.length >= 1) {
          this.price = res.data.marketList[res.data.marketList.length - 1].price;
          // this.marketId = res.data.marketList[res.data.marketList.length - 1].marketId;
          this.marketId = res.data.marketList[res.data.marketList.length - 1];
          this.marketContractAddress = res.data.marketList[res.data.marketList.length - 1].marketContractAddress;
        } else {
          this.onsale = "Not for Sale";
        }
        for (var i = 0; i < res.data.marketList.length; i++) {
          if (res.data.buyerList[i] == null) {
            break;
          }
          if (res.data.buyerList[res.data.buyerList.length - 1]) {
            this.onsale = "Not for Sale";
          }
          if (i === 0) {
            this.itemDetailHistoryList[0] = {
              id: i,
              title: `Purchased for ${res.data.marketList[i].price} SSF By ${res.data.buyerList[i].memberId}`,
              subTitle: `By Unique at ${res.data.marketList[i].endTime.date.year}/${res.data.marketList[i].endTime.date.month}/${res.data.marketList[i].endTime.date.day}`,
              avatar: `https://j6e205.p.ssafy.io/${res.data.buyerList[i].profileImageUrl}`,
              path: res.data.buyerList[i].memberSeq,
              // avatar: res.data.buyerList[i].profileImageUrl
            };
          } else {
            this.itemDetailHistoryList.push({
              id: i,
              title: `Purchased for ${res.data.marketList[i].price} SSF By ${res.data.buyerList[i].memberId}`,
              subTitle: `By Unique at ${res.data.marketList[i].endTime.date.year}/${res.data.marketList[i].endTime.date.month}/${res.data.marketList[i].endTime.date.day}`,
              avatar: `https://j6e205.p.ssafy.io/${res.data.buyerList[i].profileImageUrl}`,
              path: res.data.buyerList[i].memberSeq,
              // avatar: res.data.buyerList[i].profileImageUrl
            });
          }
        }
        // this.itemDetailHistoryList = res.data.marketList
      });
    },
  },
  computed: {
    ...mapState(["authToken", "userId"]),
    ...mapGetters(["isLogin"]),
  },
};
</script>
