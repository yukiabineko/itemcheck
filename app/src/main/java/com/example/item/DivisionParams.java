package com.example.item;

public class DivisionParams {
      private int id;
      private  int itemId;
      private  int userId;
      private String price;
      private String confirm;
      private String memo;
      private String number;
      private String name;
      private String email;
      private String shop;


      public void setId(int id){
          this.id = id;
      }
      public  void setItemId(int i){this.itemId = i;}
      public  void setUserId(int i){this.userId = i;}
      public void setPrice(String price){
          this.price = price;
      }
      public void setConfirm(String confirm){
          this.confirm = confirm;
      }
      public void setMemo(String memo){
          this.memo = memo;
      }
      public void setNumber(String num){this.number = num;}
      public void setEmail(String email) { this.email = email; }
      public void setName(String name) { this.name = name; }
      public void setShop(String shop) { this.shop = shop;}

      public int getId(){ return id; }
      public int getItemId(){return  itemId;}
      public int getUserId(){return  userId;}
      public String getPrice(){ return price; }
      public  String getConfirm(){ return confirm; }
      public String getMemo(){ return memo; }
      public String getNumber(){return  number;}
      public String getEmail() { return email; }
      public String getName() { return name; }
      public String getShop(){ return  shop;}
}
