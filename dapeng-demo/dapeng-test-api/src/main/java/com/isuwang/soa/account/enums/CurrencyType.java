package com.isuwang.soa.account.enums;

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        * 

 币种

        **/
        public enum CurrencyType implements com.isuwang.org.apache.thrift.TEnum{
        
            /**
            *

 人民币

            **/
            CNY(0),
          
            /**
            *

 美元

            **/
            USD(1);
          

        private final int value;

        private CurrencyType(int value){
            this.value = value;
        }

        public int getValue(){
            return this.value;
        }

        public static CurrencyType findByValue(int value){
            switch(value){
            
                 case 0:
                    return CNY;
               
                 case 1:
                    return USD;
               
               default:
                   return null;
            }
        }
        }
      