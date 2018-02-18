package bxute.readmore.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankit on 8/15/2017.
 */

public class SearchResponse {

    @SerializedName("kind")
    public String mKind;
    @SerializedName("totalItems")
    public int mTotalitems;
    @SerializedName("items")
    public List<Items> mItems;

    public static class Industryidentifiers {
        @SerializedName("type")
        public String mType;
        @SerializedName("identifier")
        public String mIdentifier;
    }

    public static class Readingmodes {
        @SerializedName("text")
        public boolean mText;
        @SerializedName("image")
        public boolean mImage;
    }

    public static class Panelizationsummary {
        @SerializedName("containsEpubBubbles")
        public boolean mContainsepubbubbles;
        @SerializedName("containsImageBubbles")
        public boolean mContainsimagebubbles;
    }

    public static class Imagelinks {
        @SerializedName("smallThumbnail")
        public String mSmallthumbnail;
        @SerializedName("thumbnail")
        public String mThumbnail;
    }

    public static class Volumeinfo {
        @SerializedName("title")
        public String mTitle;
        @SerializedName("authors")
        public List<String> mAuthors;
        @SerializedName("publisher")
        public String mPublisher;
        @SerializedName("publishedDate")
        public String mPublisheddate;
        @SerializedName("description")
        public String mDescription;
        @SerializedName("industryIdentifiers")
        public List<Industryidentifiers> mIndustryidentifiers;
        @SerializedName("readingModes")
        public Readingmodes mReadingmodes;
        @SerializedName("pageCount")
        public int mPagecount;
        @SerializedName("printType")
        public String mPrinttype;
        @SerializedName("categories")
        public List<String> mCategories;
        @SerializedName("maturityRating")
        public String mMaturityrating;
        @SerializedName("allowAnonLogging")
        public boolean mAllowanonlogging;
        @SerializedName("contentVersion")
        public String mContentversion;
        @SerializedName("panelizationSummary")
        public Panelizationsummary mPanelizationsummary;
        @SerializedName("imageLinks")
        public Imagelinks mImagelinks;
        @SerializedName("language")
        public String mLanguage;
        @SerializedName("previewLink")
        public String mPreviewlink;
        @SerializedName("infoLink")
        public String mInfolink;
        @SerializedName("canonicalVolumeLink")
        public String mCanonicalvolumelink;
    }

    public static class Listprice {
        @SerializedName("amount")
        public double mAmount;
        @SerializedName("currencyCode")
        public String mCurrencycode;
    }

    public static class Retailprice {
        @SerializedName("amount")
        public double mAmount;
        @SerializedName("currencyCode")
        public String mCurrencycode;
    }

    public static class Offers {
        @SerializedName("finskyOfferType")
        public int mFinskyoffertype;
        @SerializedName("listPrice")
        public Listprice mListprice;
        @SerializedName("retailPrice")
        public Retailprice mRetailprice;
    }

    public static class Saleinfo {
        @SerializedName("country")
        public String mCountry;
        @SerializedName("saleability")
        public String mSaleability;
        @SerializedName("isEbook")
        public boolean mIsebook;
        @SerializedName("listPrice")
        public Listprice mListprice;
        @SerializedName("retailPrice")
        public Retailprice mRetailprice;
        @SerializedName("buyLink")
        public String mBuylink;
        @SerializedName("offers")
        public List<Offers> mOffers;
    }

    public static class Epub {
        @SerializedName("isAvailable")
        public boolean mIsavailable;
        @SerializedName("acsTokenLink")
        public String mAcstokenlink;
    }

    public static class Pdf {
        @SerializedName("isAvailable")
        public boolean mIsavailable;
    }

    public static class Accessinfo {
        @SerializedName("country")
        public String mCountry;
        @SerializedName("viewability")
        public String mViewability;
        @SerializedName("embeddable")
        public boolean mEmbeddable;
        @SerializedName("publicDomain")
        public boolean mPublicdomain;
        @SerializedName("textToSpeechPermission")
        public String mTexttospeechpermission;
        @SerializedName("epub")
        public Epub mEpub;
        @SerializedName("pdf")
        public Pdf mPdf;
        @SerializedName("webReaderLink")
        public String mWebreaderlink;
        @SerializedName("accessViewStatus")
        public String mAccessviewstatus;
        @SerializedName("quoteSharingAllowed")
        public boolean mQuotesharingallowed;
    }

    public static class Searchinfo {
        @SerializedName("textSnippet")
        public String mTextsnippet;
    }

    public static class Items {
        @SerializedName("kind")
        public String mKind;
        @SerializedName("id")
        public String mId;
        @SerializedName("etag")
        public String mEtag;
        @SerializedName("selfLink")
        public String mSelflink;
        @SerializedName("volumeInfo")
        public Volumeinfo mVolumeinfo;
        @SerializedName("saleInfo")
        public Saleinfo mSaleinfo;
        @SerializedName("accessInfo")
        public Accessinfo mAccessinfo;
        @SerializedName("searchInfo")
        public Searchinfo mSearchinfo;
    }
}
