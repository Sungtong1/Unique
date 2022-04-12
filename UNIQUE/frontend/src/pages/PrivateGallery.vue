<template>
  <div class="page-wrap">
    <!-- header  -->
    <header class="header-section has-header-main">
      <!-- Header main -->
      <HeaderMain></HeaderMain>
      <!-- hero -->
      <HeroFour classname="hero-title" :title="SectionData.breadcrumbData.breadcrumbListFive.title" :lists="SectionData.breadcrumbData.breadcrumbListFive.navList"></HeroFour>
    </header>
    <div style="max-width: 1200px; margin: auto">
      <div class="loginbar d-flex justify-content-end align-items-center" style="position: sticky; top: 70px; height: 60px; padding-right: 50px;">
        <router-link :to="{ name: 'PrivateCreate'}" class="btn btn-dark d-block mb-2">Create</router-link>
      </div>
    </div>
    <!-- Blog  -->
    <section class="section-space-b blog-section">
      <div class="container">
        <!-- blog section -->
        <div class="row g-gs">
          <div class="col-lg-4 col-md-6" v-for="(item,index) of displayedRecords" :key="item.id">
            <div class="card card-full card-blog" @click="moveToDetail(item.exhibitionId)">
              <div class="d-block card-image">
                <!-- <img :src="item.img" class="card-img-top" alt="" /> -->
                <img :src="`https://j6e205.p.ssafy.io/${nftList[index].fileUrl}`" class="card-img-top" alt="background" />
                <div class="bg-dark-transparent card-overlay">
                  <div class="d-flex align-items-center card-author">
                    <div class="flex-shrink-0 avatar avatar-2">
                      <img :src="`https://j6e205.p.ssafy.io/${item.member.profileImageUrl}`" alt="" class="rounded-circle" />
                      <!-- <div>{{item.member.profileImageUrl}}</div> -->
                    </div>
                    <div class="flex-grow-1 ms-2 text-white">
                      <div>{{nftList[index].nftWork}}</div>
                      <span>{{ item.member.memberId }}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="card-body card-body-s1" style="max-height: 148px; overflow:hidden;">
                <h4 class="card-title mb-3">{{ item.exhibitionTitle }}</h4>
                <p class="card-text">{{ item.exhibitionDescription }}</p>
              </div>
              <!-- end card-body -->
              <hr class="my-0" />
              <div class="card-body card-body-s1 py-3">
                <div class="card-action-info">
                  <span><em class="ni ni-calender-date me-1"></em>{{ item.regDt }}</span>
                  <span>
                    <span class="me-3"><em class="ni ni-comments me-1"></em>{{ item.numberText }}</span>
                    <span><em class="ni ni-heart me-1"></em>{{ item.numberTextTwo }}</span>
                  </span>
                </div>
                <!-- end card-action-info -->
              </div>
              <!-- end card-body -->
              <!-- <router-link
                class="details"
                :to="{
                  name: 'PrivateGalleryDetail',
                  params: {
                    id: item.id,
                    title: item.title,
                    img: item.img,
                    avatar: item.avatar,
                    userName: item.userName,
                  },
                }"
              >
              </router-link> -->
            </div>
            <!-- end card -->
          </div>
          <!-- end col -->
        </div>

        <div class="text-center mt-4 mt-md-5">
          <Pagination v-if="exhibitionList" :records="exhibitionList.length" v-model="page" :per-page="perPage"></Pagination>
          <Pagination v-else :records="0" v-model="page" :per-page="perPage"></Pagination>
        </div>
      </div>
      <!-- .container -->
    </section>
    <!-- end blog-section -->
    <Footer classname="bg-black on-dark"></Footer>
  </div>
  <!-- end page-wrap -->
</template>

<script>
// Import component data. You can change the data in the store to reflect in all component
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
import SectionData from "@/store/store.js";
import HeroFour from "@/components/section/HeroFour.vue";
import Pagination from "v-pagination-3";
import axios from "axios";
import { mapState } from "vuex";

export default {
  name: "PrivateGallery",
  components: {
    HeroFour,
    Pagination,
  },
  data() {
    return {
      SectionData,
      exhibitionList: null,
      nftList:null,
      page: 1,
      perPage: 6,
    };
  },
  computed: {
    ...mapState(["authToken"]),
    displayedRecords() {
      if (!this.exhibitionList) {
        this.getGallerys();
      }
      // 마켓아이템스에 아이템이 담겨져 있으면 현재 페이지에 맞춰서 잘라서 보내기
      else {
        const startIndex = this.perPage * (this.page - 1);
        const endIndex = startIndex + this.perPage;
        return this.exhibitionList.slice(startIndex, endIndex);
      }
    },
  },
  methods: {
    moveToDetail(productId) {
      this.$router.push({
        name: "PrivateGalleryDetail",
        params: {
          id: productId,
        },
      });
    },
    getGallerys() {
      axios({
        method: "GET",
        url: `${SERVER_URL}/api/exhibition`,
        headers: {
          Authorization: this.authToken,
        },
        params: { limit: 100, offset: 0, type: "PRI" },
      }).then((res) => {
        console.log(res.data);
        this.exhibitionList = res.data.exhibitionList;
        this.nftList = res.data.nftList;
      });
    },
  },
  created: function () {
    this.getGallerys();
  },
};
</script>

<style lang="css" scoped>
.details {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}
.btn {
  z-index: 2;
  position: relative;
}
</style>
