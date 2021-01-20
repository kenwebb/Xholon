package org.primordion.user.app.groksimpl.ch03;

import java.util.ArrayList;
import java.util.List;

/**
 * Coupon Dog.
 * based on: https://gist.github.com/kenwebb/6424d56a48695ad96175a20ef48394ed  CouponDog.js
 *           https://www.manning.com/books/grokking-simplicity
 * @author <a href="mailto:ken@primordion.com">Ken Webb</a>
 * @see <a href="http://www.primordion.com/Xholon">Xholon Project website</a>
 * @see <a href="https://gist.github.com/kenwebb/6424d56a48695ad96175a20ef48394ed">Coupon Dog workbook  Grokking Simplicity - book by Eric Normand</a>
 * @since 0.9.1 (Created on December 28, 2020
 */
public class CouponDog {
  
  // Populate Database ----------------------------------------------------------
  
  // Email database table
  // email,rec_count
  private static final String subscribersRawD =
"john@coldmail.com,2\n" +
"sam@pmail.co,16\n" +
"linda1989@oal.com,1\n" +
"jan1940@ahoy.com,0\n" +
"mrbig@pmail.co,25\n" +
"lol@lol.lol,0";
  
  // Coupon database table
  // coupon,rank
  private static final String couponsRawD = 
"MAYDISCOUNT,good\n" +
"10PERCENT,bad\n" +
"PROMOTION45,best\n" +
"IHEARTYOU,bad\n" +
"GETADEAL,best\n" +
"ILIKEDISCOUNTS,good";
  
  protected class Subscriber {
    String email = null;
    int rec_count = 0;
  }
  
  protected class Coupon {
    String coupon = null;
    String rank = null;
  }
  
  protected class Db {
    List<Subscriber> subscribers = null;
    List<Coupon> coupons = null;
  }
  
  protected class Email {
    String from = null;
    String to = null;
    String subject = null;
    String body = null;    
  }
  
  private final Db dbD = new Db();
  
  public void act() {
    dbD.subscribers = raw2listofsubC(subscribersRawD);
    dbD.coupons = raw2listofcpnC(couponsRawD);
    
    List<Subscriber> listOfSubscribersD = fetchSubscribersFromDbA();
    List<Coupon> listOfCouponsD = fetchCouponsFromDbA();
    
    List<String> listOfEmailsD = planListOfEmailsC(
      listOfSubscribersD,
      selectGoodCouponsC(listOfCouponsD),
      selectBestCouponsC(listOfCouponsD)
    );
    
    sendEmailsA(listOfEmailsD);
  }
  
  protected List<Subscriber> raw2listofsubC(String raw) {
    List<Subscriber> list = new ArrayList<Subscriber>();
    String[] lines = raw.split("\n");
    for (int i = 0; i < lines.length; i++) {
      String[] arr = lines[i].split(",");
      String data1 = arr[0];
      String data2 = arr[1];
      Subscriber sub = new Subscriber();
      sub.email = data1;
      sub.rec_count = Integer.parseInt(data2);
      list.add(sub);
    }
    return list;
  }
  
  protected List<Coupon> raw2listofcpnC(String raw) {
    List<Coupon> list = new ArrayList<Coupon>();
    String[] lines = raw.split("\n");
    for (int i = 0; i < lines.length; i++) {
      String[] arr = lines[i].split(",");
      String data1 = arr[0];
      String data2 = arr[1];
      Coupon cpn = new Coupon();
      cpn.coupon = data1;
      cpn.rank = data2;
      list.add(cpn);
    }
    return list;
  }
  
  // Fetch Lists from Database ---------------------------------------------------
  protected List<Subscriber> fetchSubscribersFromDbA() {return dbD.subscribers;}
  protected List<Coupon> fetchCouponsFromDbA() {return dbD.coupons;}
  
  // Select Coupons --------------------------------------------------------------
  protected List<String> selectCouponsByRankC(List<Coupon> coupons, String rank) {
    List<String> ret = new ArrayList<String>();
    for (int c = 0; c < coupons.size(); c++) {
      Coupon coupon = coupons.get(c);
      if (rank.equals(coupon.rank))
        ret.add(coupon.coupon);
    }
    return ret;
  }
  
  protected List<String> selectBestCouponsC(List<Coupon> coupons) {
    return selectCouponsByRankC(coupons, "best");
  }
  
  protected List<String> selectGoodCouponsC(List<Coupon> coupons) {
    return selectCouponsByRankC(coupons, "good");
  }
  
  protected String decideCouponRankC(Subscriber subscriber) {
    if (subscriber.rec_count >= 10) {
      return "best";
    }
    else {
      return "good";
    }
  }
  
  // Prepare and Send Emails -----------------------------------------------------
  protected String planEmailC(Subscriber subscriber, String couponRank, List<String> coupons) {
    return "{\n"
      + "  from: " + "\"newsletter@coupondog.co\"" + ",\n"
      + "  to: \""   + subscriber.email + "\",\n"
      + "  subject: \"" + "Your " + couponRank + " weekly coupons inside" + "\",\n"
      + "  body: \"" + "Here are the " + couponRank + " coupons: " + String.join(", ", coupons) + "\"\n"
    + "}\n";
  }
  
  protected String planEmailForSubscriberC(Subscriber subscriber, List<String> goods, List<String> bests) {
    String rank = decideCouponRankC(subscriber);
    if ("best".equals(rank)) {
      return planEmailC(subscriber, "good", goods);
    }
    else if ("good".equals(rank)) {
      return planEmailC(subscriber, "best", bests);
    }
    else {
      return null;
    }
  }
  
  protected List<String> planListOfEmailsC(List<Subscriber> subscribers, List<String> goods, List<String> bests) {
    List<String> emails = new ArrayList<String>();
    for (int s = 0; s < subscribers.size(); s++) {
      Subscriber subscriber = subscribers.get(s);
      String email = planEmailForSubscriberC(subscriber, goods, bests);
      emails.add(email);
    }
    return emails;
  }
  
  protected void sendEmailsA(List<String> emails) {
    consoleLog(emails);
    System.out.println(emails);
  }
  
  protected native void consoleLog(Object obj) /*-{
    $wnd.console.log(obj);
  }-*/;
  
  public static void main(String[] args) {
    CouponDog couponDog = new CouponDog();
    couponDog.act();
  }
}
