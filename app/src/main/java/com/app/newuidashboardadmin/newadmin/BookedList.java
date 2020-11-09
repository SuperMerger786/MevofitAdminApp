
package com.app.newuidashboardadmin.newadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookedList {

    @SerializedName("average_rating")
    @Expose
    private Integer averageRating;
    @SerializedName("product_min_price")
    @Expose
    private String productMinPrice;
    @SerializedName("userBookingStatus")
    @Expose
    private String userBookingStatus;
    @SerializedName("total_duration")
    @Expose
    private String totalDuration;
    @SerializedName("per_slot_booking_allowed")
    @Expose
    private String perSlotBookingAllowed;
    @SerializedName("totalBookedSlot")
    @Expose
    private String totalBookedSlot;
    @SerializedName("remainingSlotToBook")
    @Expose
    private String remainingSlotToBook;
    @SerializedName("parent_bookingid")
    @Expose
    private String parentBookingid;
    @SerializedName("messenger_type_val")
    @Expose
    private String messengerTypeVal;
    @SerializedName("inst_bid")
    @Expose
    private String instBid;
    @SerializedName("call_failed_reason")
    @Expose
    private String callFailedReason;
    @SerializedName("seller_uid")
    @Expose
    private String sellerUid;
    @SerializedName("totalPaidAmount")
    @Expose
    private String totalPaidAmount;
    @SerializedName("nextDateToUseService")
    @Expose
    private String nextDateToUseService;
    @SerializedName("nextRenewalDate")
    @Expose
    private String nextRenewalDate;
    @SerializedName("callStatus")
    @Expose
    private String callStatus;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("trail_period_left")
    @Expose
    private String trailPeriodLeft;
    @SerializedName("trial_expire_date")
    @Expose
    private String trialExpireDate;
    @SerializedName("feature_type")
    @Expose
    private String featureType;
    @SerializedName("trial_period")
    @Expose
    private String trialPeriod;
    @SerializedName("feature_version")
    @Expose
    private String featureVersion;
    @SerializedName("installation_time")
    @Expose
    private String installationTime;
    @SerializedName("variant_bid")
    @Expose
    private String variantBid;
    @SerializedName("profilepic")
    @Expose
    private String profilepic;
    @SerializedName("BookingDuration")
    @Expose
    private String bookingDuration;
    @SerializedName("BookingPlan")
    @Expose
    private String bookingPlan;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("booking_for")
    @Expose
    private String bookingFor;
    @SerializedName("instance_title")
    @Expose
    private String instanceTitle;
    @SerializedName("category_title")
    @Expose
    private String categoryTitle;
    @SerializedName("category_boxid")
    @Expose
    private String categoryBoxid;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("BookingId")
    @Expose
    private String bookingId;
    @SerializedName("token_key")
    @Expose
    private String tokenKey;
    @SerializedName("meward_id")
    @Expose
    private String mewardId;
    @SerializedName("ItemTitle")
    @Expose
    private String itemTitle;
    @SerializedName("ItemBoxId")
    @Expose
    private String itemBoxId;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("customerphone")
    @Expose
    private String customerphone;
    @SerializedName("customeremail")
    @Expose
    private String customeremail;
    @SerializedName("resturantName")
    @Expose
    private String resturantName;
    @SerializedName("resturantLocation")
    @Expose
    private String resturantLocation;
    @SerializedName("resturantAddress")
    @Expose
    private String resturantAddress;
    @SerializedName("resturantPhone")
    @Expose
    private String resturantPhone;
    @SerializedName("noPeople")
    @Expose
    private String noPeople;
    @SerializedName("location_lat")
    @Expose
    private String locationLat;
    @SerializedName("location_lng")
    @Expose
    private String locationLng;
    @SerializedName("mapUrl")
    @Expose
    private String mapUrl;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("total_experience")
    @Expose
    private String totalExperience;
    @SerializedName("SellerName")
    @Expose
    private String sellerName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("BookingDate")
    @Expose
    private String bookingDate;
    @SerializedName("BookedDateOriginal")
    @Expose
    private String bookedDateOriginal;
    @SerializedName("BookedDate")
    @Expose
    private String bookedDate;
    @SerializedName("BookingAmmount")
    @Expose
    private String bookingAmmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("BoookingStatus")
    @Expose
    private String boookingStatus;
    @SerializedName("decline_reason")
    @Expose
    private String declineReason;
    @SerializedName("cancellation")
    @Expose
    private Cancellation cancellation;

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public String getProductMinPrice() {
        return productMinPrice;
    }

    public void setProductMinPrice(String productMinPrice) {
        this.productMinPrice = productMinPrice;
    }

    public String getUserBookingStatus() {
        return userBookingStatus;
    }

    public void setUserBookingStatus(String userBookingStatus) {
        this.userBookingStatus = userBookingStatus;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getPerSlotBookingAllowed() {
        return perSlotBookingAllowed;
    }

    public void setPerSlotBookingAllowed(String perSlotBookingAllowed) {
        this.perSlotBookingAllowed = perSlotBookingAllowed;
    }

    public String getTotalBookedSlot() {
        return totalBookedSlot;
    }

    public void setTotalBookedSlot(String totalBookedSlot) {
        this.totalBookedSlot = totalBookedSlot;
    }

    public String getRemainingSlotToBook() {
        return remainingSlotToBook;
    }

    public void setRemainingSlotToBook(String remainingSlotToBook) {
        this.remainingSlotToBook = remainingSlotToBook;
    }

    public String getParentBookingid() {
        return parentBookingid;
    }

    public void setParentBookingid(String parentBookingid) {
        this.parentBookingid = parentBookingid;
    }

    public String getMessengerTypeVal() {
        return messengerTypeVal;
    }

    public void setMessengerTypeVal(String messengerTypeVal) {
        this.messengerTypeVal = messengerTypeVal;
    }

    public String getInstBid() {
        return instBid;
    }

    public void setInstBid(String instBid) {
        this.instBid = instBid;
    }

    public String getCallFailedReason() {
        return callFailedReason;
    }

    public void setCallFailedReason(String callFailedReason) {
        this.callFailedReason = callFailedReason;
    }

    public String getSellerUid() {
        return sellerUid;
    }

    public void setSellerUid(String sellerUid) {
        this.sellerUid = sellerUid;
    }

    public String getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(String totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getNextDateToUseService() {
        return nextDateToUseService;
    }

    public void setNextDateToUseService(String nextDateToUseService) {
        this.nextDateToUseService = nextDateToUseService;
    }

    public String getNextRenewalDate() {
        return nextRenewalDate;
    }

    public void setNextRenewalDate(String nextRenewalDate) {
        this.nextRenewalDate = nextRenewalDate;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTrailPeriodLeft() {
        return trailPeriodLeft;
    }

    public void setTrailPeriodLeft(String trailPeriodLeft) {
        this.trailPeriodLeft = trailPeriodLeft;
    }

    public String getTrialExpireDate() {
        return trialExpireDate;
    }

    public void setTrialExpireDate(String trialExpireDate) {
        this.trialExpireDate = trialExpireDate;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(String trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public String getFeatureVersion() {
        return featureVersion;
    }

    public void setFeatureVersion(String featureVersion) {
        this.featureVersion = featureVersion;
    }

    public String getInstallationTime() {
        return installationTime;
    }

    public void setInstallationTime(String installationTime) {
        this.installationTime = installationTime;
    }

    public String getVariantBid() {
        return variantBid;
    }

    public void setVariantBid(String variantBid) {
        this.variantBid = variantBid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getBookingDuration() {
        return bookingDuration;
    }

    public void setBookingDuration(String bookingDuration) {
        this.bookingDuration = bookingDuration;
    }

    public String getBookingPlan() {
        return bookingPlan;
    }

    public void setBookingPlan(String bookingPlan) {
        this.bookingPlan = bookingPlan;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBookingFor() {
        return bookingFor;
    }

    public void setBookingFor(String bookingFor) {
        this.bookingFor = bookingFor;
    }

    public String getInstanceTitle() {
        return instanceTitle;
    }

    public void setInstanceTitle(String instanceTitle) {
        this.instanceTitle = instanceTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryBoxid() {
        return categoryBoxid;
    }

    public void setCategoryBoxid(String categoryBoxid) {
        this.categoryBoxid = categoryBoxid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getMewardId() {
        return mewardId;
    }

    public void setMewardId(String mewardId) {
        this.mewardId = mewardId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemBoxId() {
        return itemBoxId;
    }

    public void setItemBoxId(String itemBoxId) {
        this.itemBoxId = itemBoxId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone;
    }

    public String getCustomeremail() {
        return customeremail;
    }

    public void setCustomeremail(String customeremail) {
        this.customeremail = customeremail;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public String getResturantLocation() {
        return resturantLocation;
    }

    public void setResturantLocation(String resturantLocation) {
        this.resturantLocation = resturantLocation;
    }

    public String getResturantAddress() {
        return resturantAddress;
    }

    public void setResturantAddress(String resturantAddress) {
        this.resturantAddress = resturantAddress;
    }

    public String getResturantPhone() {
        return resturantPhone;
    }

    public void setResturantPhone(String resturantPhone) {
        this.resturantPhone = resturantPhone;
    }

    public String getNoPeople() {
        return noPeople;
    }

    public void setNoPeople(String noPeople) {
        this.noPeople = noPeople;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(String totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookedDateOriginal() {
        return bookedDateOriginal;
    }

    public void setBookedDateOriginal(String bookedDateOriginal) {
        this.bookedDateOriginal = bookedDateOriginal;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getBookingAmmount() {
        return bookingAmmount;
    }

    public void setBookingAmmount(String bookingAmmount) {
        this.bookingAmmount = bookingAmmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getBoookingStatus() {
        return boookingStatus;
    }

    public void setBoookingStatus(String boookingStatus) {
        this.boookingStatus = boookingStatus;
    }

    public String getDeclineReason() {
        return declineReason;
    }

    public void setDeclineReason(String declineReason) {
        this.declineReason = declineReason;
    }

    public Cancellation getCancellation() {
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation) {
        this.cancellation = cancellation;
    }

}
