package com.example.item;

public class DivisionParams {
      private int id;
      private String price;
      private String confirm;
      private String memo;

      public void setId(int id){
          this.id = id;
      }
      public void setPrice(String price){
          this.price = price;
      }
      public void setConfirm(String confirm){
          this.confirm = confirm;
      }
      public void setMemo(String memo){
          this.memo = memo;
      }

      public int getId(){
          return id;
      }
      public String getPrice(){
          return price;
      }
      public  String getConfirm(){
          return confirm;
      }
      public String getMemo(){
          return memo;
      }



}
