package com.wenzhiguo.newstitlewenzhiguo.Bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.List;

/**
 * Created by liqingyi on 2016/12/30.
 */

@Table(name = "NewsSummary")
public class NewsSummary implements Parcelable {

    @Column(name = "id", isId = true)
    private int id;
    public String url_3w;
    @Column(name = "postid")
    public String postid;

    @Column(name = "hasCover")
    public boolean hasCover;

    @Column(name = "hasHead")
    public int hasHead;

    @Column(name = "replyCount")
    public int replyCount;

    @Column(name = "hasImg")
    public int hasImg;

    @Column(name = "digest")
    public String digest;

    @Column(name = "hasIcon")
    public boolean hasIcon;

    @Column(name = "docid")
    public String docid;

    @Column(name = "title")
    public String title;

    @Column(name = "order")
    public int order;

    @Column(name = "priority")
    public int priority;

    @Column(name = "lmodify")
    public String lmodify;

    @Column(name = "boardid")
    public String boardid;

    @Column(name = "photosetID")
    public String photosetID;

    @Column(name = "imgsum")
    public int imgsum;

    @Column(name = "topic_background")
    public String topic_background;

    @Column(name = "template")
    public String template;

    @Column(name = "votecount")
    public int votecount;

    @Column(name = "skipID")
    public String skipID;

    @Column(name = "alias")
    public String alias;

    @Column(name = "skipType")
    public String skipType;

    @Column(name = "title")
    public String cid;

    @Column(name = "hasAD")
    public int hasAD;

    @Column(name = "source")
    public String source;

    @Column(name = "ename")
    public String ename;

    @Column(name = "tname")
    public String tname;

    @Column(name = "imgsrc")
    public String imgsrc;

    @Column(name = "ptime")
    public String ptime;


    public List<AdsBean> ads;
    public List<AdsBean> imgextra;

    public List<AdsBean> getImgextra() {
        if (imgextra != null) {
            AdsBean temp = imgextra.get(imgextra.size() - 1);
            if (!TextUtils.equals(temp.imgsrc, imgsrc)) {
                AdsBean bean = new AdsBean(imgsrc);
                imgextra.add(bean);
            }
        }
        return imgextra;
    }

    @Override
    public String toString() {
        return "NewsSummary{" +
                "ads=" + ads +
                ", postid='" + postid + '\'' +
                ", hasCover=" + hasCover +
                ", hasHead=" + hasHead +
                ", replyCount=" + replyCount +
                ", hasImg=" + hasImg +
                ", digest='" + digest + '\'' +
                ", hasIcon=" + hasIcon +
                ", docid='" + docid + '\'' +
                ", title='" + title + '\'' +
                ", order=" + order +
                ", priority=" + priority +
                ", lmodify='" + lmodify + '\'' +
                ", boardid='" + boardid + '\'' +
                ", photosetID='" + photosetID + '\'' +
                ", imgsum=" + imgsum +
                ", topic_background='" + topic_background + '\'' +
                ", template='" + template + '\'' +
                ", votecount=" + votecount +
                ", skipID='" + skipID + '\'' +
                ", alias='" + alias + '\'' +
                ", skipType='" + skipType + '\'' +
                ", cid='" + cid + '\'' +
                ", hasAD=" + hasAD +
                ", source='" + source + '\'' +
                ", ename='" + ename + '\'' +
                ", tname='" + tname + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", ptime='" + ptime + '\'' +
                ", imgextra=" + imgextra +
                '}';
    }

    public static class AdsBean implements Parcelable {
        public String title;
        public String tag;
        public String imgsrc;
        public String subtitle;
        public String url;

        public AdsBean(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        @Override
        public String toString() {
            return "AdsBean{" +
                    "imgsrc='" + imgsrc + '\'' +
                    ", title='" + title + '\'' +
                    ", tag='" + tag + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.tag);
            dest.writeString(this.imgsrc);
            dest.writeString(this.subtitle);
            dest.writeString(this.url);
        }

        public AdsBean() {
        }

        protected AdsBean(Parcel in) {
            this.title = in.readString();
            this.tag = in.readString();
            this.imgsrc = in.readString();
            this.subtitle = in.readString();
            this.url = in.readString();
        }

        public static final Creator<AdsBean> CREATOR = new Creator<AdsBean>() {
            @Override
            public AdsBean createFromParcel(Parcel source) {
                return new AdsBean(source);
            }

            @Override
            public AdsBean[] newArray(int size) {
                return new AdsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postid);
        dest.writeByte(this.hasCover ? (byte) 1 : (byte) 0);
        dest.writeInt(this.hasHead);
        dest.writeInt(this.replyCount);
        dest.writeInt(this.hasImg);
        dest.writeString(this.digest);
        dest.writeByte(this.hasIcon ? (byte) 1 : (byte) 0);
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeInt(this.order);
        dest.writeInt(this.priority);
        dest.writeString(this.lmodify);
        dest.writeString(this.boardid);
        dest.writeString(this.photosetID);
        dest.writeInt(this.imgsum);
        dest.writeString(this.topic_background);
        dest.writeString(this.template);
        dest.writeInt(this.votecount);
        dest.writeString(this.skipID);
        dest.writeString(this.alias);
        dest.writeString(this.skipType);
        dest.writeString(this.cid);
        dest.writeInt(this.hasAD);
        dest.writeString(this.source);
        dest.writeString(this.ename);
        dest.writeString(this.tname);
        dest.writeString(this.imgsrc);
        dest.writeString(this.ptime);
        dest.writeTypedList(this.ads);
        dest.writeTypedList(this.imgextra);
    }

    public NewsSummary() {
    }

    protected NewsSummary(Parcel in) {
        this.postid = in.readString();
        this.hasCover = in.readByte() != 0;
        this.hasHead = in.readInt();
        this.replyCount = in.readInt();
        this.hasImg = in.readInt();
        this.digest = in.readString();
        this.hasIcon = in.readByte() != 0;
        this.docid = in.readString();
        this.title = in.readString();
        this.order = in.readInt();
        this.priority = in.readInt();
        this.lmodify = in.readString();
        this.boardid = in.readString();
        this.photosetID = in.readString();
        this.imgsum = in.readInt();
        this.topic_background = in.readString();
        this.template = in.readString();
        this.votecount = in.readInt();
        this.skipID = in.readString();
        this.alias = in.readString();
        this.skipType = in.readString();
        this.cid = in.readString();
        this.hasAD = in.readInt();
        this.source = in.readString();
        this.ename = in.readString();
        this.tname = in.readString();
        this.imgsrc = in.readString();
        this.ptime = in.readString();
        this.ads = in.createTypedArrayList(AdsBean.CREATOR);
        this.imgextra = in.createTypedArrayList(AdsBean.CREATOR);
    }

    public static final Creator<NewsSummary> CREATOR = new Creator<NewsSummary>() {
        @Override
        public NewsSummary createFromParcel(Parcel source) {
            return new NewsSummary(source);
        }

        @Override
        public NewsSummary[] newArray(int size) {
            return new NewsSummary[size];
        }
    };
}
